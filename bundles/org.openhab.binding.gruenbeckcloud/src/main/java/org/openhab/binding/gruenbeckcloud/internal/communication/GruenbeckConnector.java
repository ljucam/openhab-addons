package org.openhab.binding.gruenbeckcloud.internal.communication;

import static org.openhab.binding.gruenbeckcloud.internal.communication.ConnectorEvent.REQUEST_ACCESS_DENIED;
import static org.openhab.binding.gruenbeckcloud.internal.communication.ConnectorEvent.REQUEST_FAILED;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.openhab.binding.gruenbeckcloud.internal.api.model.*;
import org.openhab.binding.gruenbeckcloud.internal.communication.exception.AccessDniedException;
import org.openhab.binding.gruenbeckcloud.internal.communication.exception.CommunicationException;
import org.openhab.binding.gruenbeckcloud.internal.communication.listener.ConnectionStatusListener;
import org.openhab.binding.gruenbeckcloud.internal.communication.listener.DeviceUpdateListener;
import org.openhab.binding.gruenbeckcloud.internal.communication.listener.WebsocketEventListener;
import org.openhab.core.common.ThreadPoolManager;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link GruenbeckConnector} class handles all request, connection issues and keeps the
 * connection to gruenbeck cloud alive.
 *
 * @author Mario Aerni - Initial contribution
 */
public class GruenbeckConnector implements WebsocketEventListener {
    private static final int MAX_WEBSOCKET_CONNECT_RETRIES = 3;
    private final Logger logger = LoggerFactory.getLogger(GruenbeckConnector.class);
    private final ScheduledExecutorService scheduler = ThreadPoolManager.getScheduledPool("gruenbeckcloud-connector");
    private final AtomicBoolean connected = new AtomicBoolean(false);
    private final AtomicInteger websocketConnectRetries = new AtomicInteger(0);
    private final Set<ConnectionStatusListener> connectionStatusListeners = ConcurrentHashMap.newKeySet();
    private final Set<DeviceUpdateListener> deviceUpdateListeners = ConcurrentHashMap.newKeySet();
    private final AtomicBoolean closing = new AtomicBoolean(false);
    private volatile RequestHandler requestHandler;
    private volatile String email;
    private volatile String password;
    private volatile ScheduledFuture<?> loginFuture;
    private volatile ScheduledFuture<?> refreshTokensFuture;
    private volatile ScheduledFuture<?> websocketFuture;
    private volatile GruenbeckWebsocket websocket;

    public GruenbeckConnector() {
    }

    public void init(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public synchronized void login() {
        logger.info("start login process");

        try {
            if (requestHandler == null) {
                requestHandler = new RequestHandler(email, password);
            }

            try {
                // perform a login
                requestHandler.login();
            } catch (AccessDniedException e) {
                logger.error(
                        "invalid credentials, could not login to gruenbeck cloud, please check bridge configuration!",
                        e);
                handleConnectorEvent(ConnectorEvent.INVALID_CREDENTIALS);
                return;
            }

            // get OAuth token
            requestHandler.retrieveTokens();

            // set refresh task - 50 min
            // TODO: check if we have an token expiry (e.g. expires_in) in the response from initial
            // request, in case of 401 always try token refresh before doing initialize login again
            scheduleRefreshToken(false);
            handleConnectorEvent(ConnectorEvent.LOGIN_SUCCEEDED);
        } catch (CommunicationException e) {
            logger.error(e.getMessage(), e);
            handleConnectorEvent(ConnectorEvent.LOGIN_FAILED);
        } catch (InterruptedException e) {
            logger.warn("current thread was interrupted during async initialization", e);
            handleConnectorEvent(ConnectorEvent.LOGIN_FAILED);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.error("error occurred during async initialization", e);
            handleConnectorEvent(ConnectorEvent.LOGIN_FAILED);
        }
    }

    private synchronized void scheduleLogin() {
        // reset
        connected.set(false);
        cancelLogin(false);
        cancelRefreshTokens(false);
        cancelWebsocket(false);
        closeWebsocket();
        websocketConnectRetries.set(0);

        // reschedule
        loginFuture = scheduler.schedule(this::login, 20, TimeUnit.SECONDS);
    }

    private void refreshTokens() {
        try {
            requestHandler.refreshTokens();
            handleConnectorEvent(ConnectorEvent.TOKEN_REFRESH_SUCCEEDED);
        } catch (InterruptedException e) {
            logger.warn("current thread was interrupted during token refresh", e);
            handleConnectorEvent(ConnectorEvent.TOKEN_REFRESH_FAILED);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.error("an error occurred during token refresh", e);
            handleConnectorEvent(ConnectorEvent.TOKEN_REFRESH_FAILED);
        }
    }

    private synchronized void scheduleRefreshToken(boolean immediately) {
        cancelRefreshTokens(false);
        int initialDelay = immediately ? 0 : 50;
        refreshTokensFuture = scheduler.scheduleWithFixedDelay(this::refreshTokens, initialDelay, 50, TimeUnit.MINUTES);
    }

    private void connectWebsocket() {
        try {
            websocket = requestHandler.createWebsocket(this);
            handleConnectorEvent(ConnectorEvent.WEBSOCKET_CREATION_SUCCEEDED);
        } catch (AccessDniedException e) {
            logger.debug("Could not connect to websocket. Access was denied, call token refresh!", e);
            handleConnectorEvent(REQUEST_ACCESS_DENIED);
        } catch (InterruptedException e) {
            logger.warn("current thread was interrupted during websocket creation", e);
            handleConnectorEvent(ConnectorEvent.WEBSOCKET_CREATION_FAILED);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.error("an error occurred during websocket creation", e);
            handleConnectorEvent(ConnectorEvent.WEBSOCKET_CREATION_FAILED);
        }
    }

    private synchronized void scheduleWebsocket(int startDelayInSeconds) {
        // reset
        cancelWebsocket(false);
        closeWebsocket();

        // reschedule
        websocketFuture = scheduler.schedule(this::connectWebsocket, startDelayInSeconds, TimeUnit.SECONDS);
    }

    private synchronized void handleConnectorEvent(ConnectorEvent connectorEvent) {
        if (closing.get()) {
            return;
        }
        switch (connectorEvent) {
            case INVALID_CREDENTIALS:
                callConnectionListener(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR);
                return;

            case LOGIN_FAILED:
            case REQUEST_FAILED:
            case TOKEN_REFRESH_FAILED:
                /*
                 * These cases contain all problems NOT directly related to access (401) problems,
                 * we assume that the request itself didn't go well.
                 */
                scheduleLogin();
                callConnectionListener(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR);
                return;
            case LOGIN_SUCCEEDED:
                connected.set(true);
                callConnectionListener(ThingStatus.ONLINE, null);
                scheduleWebsocket(10);
                return;
            case TOKEN_REFRESH_SUCCEEDED:
                // if a refresh happens and was successful, we need to recreate the websocket,
                // because its token is probably out of date.
                scheduleWebsocket(0);
                return;
            case WEBSOCKET_CREATION_FAILED:
                if (websocketConnectRetries.getAndIncrement() > MAX_WEBSOCKET_CONNECT_RETRIES) {
                    logger.warn("max websocket connection retry limit ({}) reached", MAX_WEBSOCKET_CONNECT_RETRIES);
                    scheduleLogin();
                    callConnectionListener(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR);
                } else {
                    scheduleWebsocket(10);
                }
                return;
            case WEBSOCKET_CREATION_SUCCEEDED:
                // yeah, we have a connection, let's reset the retries!
                websocketConnectRetries.set(0);
                return;
            case WEBSOCKET_CONNECTION_FAILED:
                scheduleWebsocket(5);
                return;
            case REQUEST_ACCESS_DENIED:
                scheduleRefreshToken(true);
                return;
        }
    }

    public synchronized void dispose() {
        closing.set(true);
        connected.set(false);
        connectionStatusListeners.clear();
        deviceUpdateListeners.clear();
        cancelLogin(true);
        cancelRefreshTokens(true);
        cancelWebsocket(true);
        closeWebsocket();
        requestHandler.close();
    }

    private void cancelRefreshTokens(boolean mayInterruptIfRunning) {
        if (refreshTokensFuture != null) {
            refreshTokensFuture.cancel(mayInterruptIfRunning);
            refreshTokensFuture = null;
        }
    }

    private void cancelLogin(boolean mayInterruptIfRunning) {
        if (loginFuture != null) {
            loginFuture.cancel(mayInterruptIfRunning);
            loginFuture = null;
        }
    }

    private void cancelWebsocket(boolean mayInterruptIfRunning) {
        if (websocketFuture != null) {
            websocketFuture.cancel(mayInterruptIfRunning);
            websocketFuture = null;
        }
    }

    private void closeWebsocket() {
        if (websocket != null) {
            websocket.close();
            websocket = null;
        }
    }

    public void addConnectionStatusListener(ConnectionStatusListener connectionStatusListener) {
        connectionStatusListeners.add(connectionStatusListener);
    }

    public void removeConnectionStatusListener(ConnectionStatusListener connectionStatusListener) {
        connectionStatusListeners.remove(connectionStatusListener);
    }

    private void callConnectionListener(ThingStatus status, ThingStatusDetail thingStatusDetail) {
        connectionStatusListeners.forEach(listener -> listener.updateConnectionStatus(status, thingStatusDetail));
    }

    public boolean isConnected() {
        return connected.get();
    }

    @Override
    public void obWebsocketError() {
        /*
         * TODO: Maybe our websocket access token is no longer valid and needs
         * to be refreshed. Since the token is provided by the connector
         * and part of the url, we need to re-create the websocket.
         */
        handleConnectorEvent(ConnectorEvent.WEBSOCKET_CONNECTION_FAILED);
    }

    @Override
    public void onCurrentUpdate(final Current current) {
        deviceUpdateListeners.forEach(deviceUpdateListener -> deviceUpdateListener.onCurrentUpdate(current));
    }

    @Override
    public void onCurSlowUpdate(final CurrSlow currSlow) {
        deviceUpdateListeners.forEach(deviceUpdateListener -> deviceUpdateListener.onCurSlowUpdate(currSlow));
    }

    public List<Device> getDevices() {
        if (isConnected()) {
            return requestHandler.getDevices();
        }

        return Collections.emptyList();
    }

    public DeviceInformation getDeviceInformation(Device device) {
        if (isConnected()) {
            try {
                return requestHandler.getDeviceInformation(device);
            } catch (AccessDniedException e) {
                logger.debug(
                        "could not retrieve device information for device {}. Access was denied, call token refresh!",
                        device.getSerialNumber());
                handleConnectorEvent(REQUEST_ACCESS_DENIED);
            } catch (InterruptedException e) {
                logger.warn("current thread was interrupted during device information retrieval for device {}",
                        device.getSerialNumber(), e);
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                logger.error("an error occurred during device information retrieval for device {}",
                        device.getSerialNumber(), e);
            }
        }

        return null;
    }

    public DeviceStatistic getDeviceStatistics(Device device) {
        if (isConnected()) {
            try {
                return requestHandler.getDeviceStatistics(device);
            } catch (AccessDniedException e) {
                logger.debug(
                        "could not retrieve device statistics for device {}. Access was denied, call token refresh!",
                        device.getSerialNumber());
                handleConnectorEvent(REQUEST_ACCESS_DENIED);
            } catch (InterruptedException e) {
                logger.warn("current thread was interrupted during device statistics retrieval for device {}",
                        device.getSerialNumber(), e);
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                logger.error("an error occurred during device statistics retrieval for device {}",
                        device.getSerialNumber(), e);
            }
        }

        return null;
    }

    public boolean enterSD(Device device) {
        if (isConnected()) {
            try {
                return requestHandler.enterSD(device.getId());
            } catch (AccessDniedException e) {
                logger.debug("could not enter sd (realtime) for device {}. Access was denied, call token refresh!",
                        device.getSerialNumber());
                handleConnectorEvent(REQUEST_ACCESS_DENIED);
            } catch (InterruptedException e) {
                logger.warn("current thread was interrupted during entering sd (realtime) for device {}",
                        device.getSerialNumber(), e);
                handleConnectorEvent(REQUEST_FAILED);
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                logger.error("an error occurred during entering sd (realtime) for device {}", device.getSerialNumber(),
                        e);
                handleConnectorEvent(REQUEST_FAILED);
            }
        }

        return false;
    }

    public boolean refreshSD(Device device) {
        if (isConnected()) {
            try {
                return requestHandler.refreshSD(device.getId());
            } catch (AccessDniedException e) {
                logger.debug("could not refresh sd (realtime) for device {}. Access was denied, call token refresh!",
                        device.getSerialNumber());
                handleConnectorEvent(REQUEST_ACCESS_DENIED);
            } catch (InterruptedException e) {
                logger.warn("current thread was interrupted during refresh sd (realtime) for device {}",
                        device.getSerialNumber(), e);
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                logger.error("an error occurred during refresh sd (realtime) for device {}", device.getSerialNumber(),
                        e);
            }
        }
        return false;
    }

    public void leaveSD(Device device) {
        if (isConnected()) {
            try {
                requestHandler.leaveSD(device.getId());
            } catch (AccessDniedException e) {
                logger.debug("could not leave sd (realtime) for device {}. Access was denied, call token refresh!",
                        device.getSerialNumber());
                handleConnectorEvent(REQUEST_ACCESS_DENIED);
            } catch (InterruptedException e) {
                logger.warn("current thread was interrupted during leaving sd (realtime) for device {}",
                        device.getSerialNumber(), e);
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                logger.error("an error occurred during leaving sd (realtime) for device {}", device.getSerialNumber(),
                        e);
            }
        }
    }

    public void addDeviceUpdateListener(DeviceUpdateListener deviceUpdateListener) {
        deviceUpdateListeners.add(deviceUpdateListener);
    }

    public void removeDeviceUpdateListener(DeviceUpdateListener deviceUpdateListener) {
        deviceUpdateListeners.remove(deviceUpdateListener);
    }

    public void handleCommand(String id, String parameter, Object value) {
        if (isConnected()) {
            try {
                requestHandler.pushParameter(id, parameter, value);
            } catch (AccessDniedException e) {
                logger.debug("could not execute command ({}={}) for device {}", parameter, value, id, e);
            } catch (InterruptedException e) {
                logger.warn("current thread was interrupted during push of parameter ({}={}) for device {}", parameter,
                        value, id, e);
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                logger.warn("an error occurred during push of parameter ({}={}) for device {}", parameter, value, id,
                        e);
            }
        }
    }

    public void handleRegenerationRequest(String id) {
        if (isConnected()) {
            try {
                requestHandler.requestRegeneration(id);
            } catch (AccessDniedException e) {
                logger.debug("could not request regeneration for device {}", id, e);
            } catch (InterruptedException e) {
                logger.warn("current thread was interrupted during regeneration request for device {}", id, e);
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                logger.warn("an error occurred during regeneration request for device {}", id, e);
            }
        }
    }
}
