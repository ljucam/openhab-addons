/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * <p>See the NOTICE file(s) distributed with this work for additional information.
 *
 * <p>This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0
 *
 * <p>SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.gruenbeckcloud.internal.handler;

import static org.openhab.binding.gruenbeckcloud.internal.GruenbeckCloudBindingConstants.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import org.openhab.binding.gruenbeckcloud.internal.GruenbeckCloudBridgeConfiguration;
import org.openhab.binding.gruenbeckcloud.internal.api.model.*;
import org.openhab.binding.gruenbeckcloud.internal.communication.GruenbeckConnector;
import org.openhab.binding.gruenbeckcloud.internal.communication.listener.ConnectionStatusListener;
import org.openhab.binding.gruenbeckcloud.internal.communication.listener.DeviceUpdateListener;
import org.openhab.core.thing.*;
import org.openhab.core.thing.binding.BaseBridgeHandler;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link GruenbeckCloudBridgeHandler} is the main class to manage data exchange with softeners and
 * gruenbeckcloud.
 *
 * @author Mario Aerni - Initial contribution
 */
public class GruenbeckCloudBridgeHandler extends BaseBridgeHandler
        implements ConnectionStatusListener, DeviceUpdateListener {
    /*
     * Project Reference: https://github.com/RonnyWinkler/homey.gruenbeck/blob/master/gruenbeck/gruenbeckSD.js
     * Referenced by: https://github.com/TA2k/ioBroker.gruenbeck
     */

    private static final int LOGIN_STARTUP_DELAY_SECONDS = 15;
    private static final int REFRESH_INTERVAL_SECONDS = 1;
    private static final int DEFAULT_REFRESH_INTERVAL_QUICK_SECONDS = 3;

    private final Logger logger = LoggerFactory.getLogger(GruenbeckCloudBridgeHandler.class);
    private final GruenbeckConnector connector = new GruenbeckConnector();
    private final ConcurrentHashMap<String, GruenbeckCloudSoftenerHandler> softenerHandlers = new ConcurrentHashMap<>();
    private final AtomicInteger softenerStatsRefreshCounter = new AtomicInteger(0);

    private final Set<ScheduledFuture<?>> leaveSDFutures = ConcurrentHashMap.newKeySet();

    private volatile ScheduledFuture<?> loginFuture;
    private volatile ScheduledFuture<?> softenerStatsRefreshFuture;

    private GruenbeckCloudBridgeConfiguration bridgeConfiguration;

    public GruenbeckCloudBridgeHandler(Bridge bridge) {
        super(bridge);
    }

    @Override
    public void initialize() {
        logger.info("Start initializing!");
        bridgeConfiguration = getConfigAs(GruenbeckCloudBridgeConfiguration.class);
        connector.init(bridgeConfiguration.email, bridgeConfiguration.password);
        connector.addConnectionStatusListener(this);
        connector.addDeviceUpdateListener(this);
        loginFuture = scheduler.schedule(connector::login, LOGIN_STARTUP_DELAY_SECONDS, TimeUnit.SECONDS);
        scheduleSoftenerStatsRefresh();
        updateStatus(ThingStatus.UNKNOWN);
    }

    @Override
    public void childHandlerInitialized(ThingHandler softenerHandler, Thing softenerThing) {
        String serialNumber = (String) softenerThing.getConfiguration().get(SOFTENER_SERIAL_NUMBER);
        softenerHandlers.put(serialNumber, (GruenbeckCloudSoftenerHandler) softenerHandler);
        scheduleQuickSoftenerStatsRefresh();
        logger.debug("GruenbeckCloudBridgeHandler: Saving softener handler for {} with serialNumber {}",
                softenerThing.getUID(), serialNumber);
    }

    @Override
    public void childHandlerDisposed(ThingHandler softenerHandler, Thing softenerThing) {
        String serialNumber = (String) softenerThing.getConfiguration().get(SOFTENER_SERIAL_NUMBER);
        softenerHandlers.remove(serialNumber);
        logger.debug("GruenbeckCloudBridgeHandler: Removing softener handler for {} with serialNumber {}",
                softenerThing.getUID(), serialNumber);
    }

    @Override
    public void dispose() {
        logger.info("dispose GruenbeckCloudBridgeHandler");
        connector.removeConnectionStatusListener(this);
        connector.removeDeviceUpdateListener(this);
        cancelSoftenerStatsRefresh(true);
        cancelLoginFuture(true);
        leaveSDFutures.forEach(scheduledFuture -> {
            try {
                scheduledFuture.cancel(true);
            } catch (Exception ignored) {
            }
        });
        leaveSDFutures.clear();
        try {
            connector.dispose();
        } catch (Exception ignored) {
        }
    }

    public List<Device> getDevices() {
        return connector.getDevices();
    }

    @Override
    public void onCurrentUpdate(Current current) {
        // note: in this case id represents the serialNumber
        GruenbeckCloudSoftenerHandler softenerHandler = softenerHandlers.get(current.getId());

        if (softenerHandler == null) {
            logger.warn("could not find softener with serialNumber {} for 'current' update", current.getId());
            return;
        }

        softenerHandler.onCurrentUpdate(current);
    }

    @Override
    public void onCurSlowUpdate(CurrSlow currSlow) {
        // note: in this case id represents the serialNumber
        GruenbeckCloudSoftenerHandler softenerHandler = softenerHandlers.get(currSlow.getId());

        if (softenerHandler == null) {
            logger.warn("could not find softener with serialNumber {} for 'curSlow' update", currSlow.getId());
            return;
        }

        softenerHandler.onCurrSlowUpdate(currSlow);
    }

    @Override
    public void updateConnectionStatus(ThingStatus status, ThingStatusDetail thingStatusDetail) {
        if (thingStatusDetail != null) {
            updateStatus(status, thingStatusDetail);
        } else {
            updateStatus(status);
        }
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        // not needed
    }

    public void handleCommand(final String deviceId, final String channelId, final Object value) {
        if (!getThing().getStatus().equals(ThingStatus.ONLINE) || !connector.isConnected()) {
            logger.warn("bridge / connector are not online, command will be ignored");
            return;
        }

        connector.handleCommand(deviceId, channelId, value);
    }

    public void handleRegenerationRequest(String deviceId) {
        if (!getThing().getStatus().equals(ThingStatus.ONLINE) || !connector.isConnected()) {
            logger.warn("bridge / connector are not online, command will be ignored");
            return;
        }

        connector.handleRegenerationRequest(deviceId);
    }

    private synchronized void refreshSoftenerStats() {
        try {
            /*
             * Note: refresh should only happen if the websocket ia already connected,
             * otherwise current data won't be retrieved. One could say tough,
             * that this information was not really necessary and could be
             * retrieved after the given refresh period.
             */
            if (!getThing().getStatus().equals(ThingStatus.ONLINE)) {
                return;
            }

            if (softenerStatsRefreshCounter.getAndDecrement() > 0) {
                return;
            }

            if (!connector.isConnected()) {
                logger.debug("connector is currently not connected, won't perform softener stats refresh");
                return;
            }

            logger.debug("refresh all softener stats");

            // refresh period passed or enforced, reset refresh period and perform regular execution
            softenerStatsRefreshCounter.set(bridgeConfiguration.refreshPeriod);

            // get a list of all devices, maybe put this in a cache for usage with discovery?
            final List<Device> devices = getDevices();

            for (final Device device : devices) {
                logger.debug("process device {} for refreshing", device.getSerialNumber());
                try {

                    if (SUPPORTED_SOFTENER.contains(device.getSeries())) {

                        // get the corresponding handler
                        GruenbeckCloudSoftenerHandler softenerHandler = softenerHandlers.get(device.getSerialNumber());

                        // fail fast, if the handler is not present (that is possible if not all softener are
                        // configured)
                        if (softenerHandler == null) {
                            logger.debug("Softener {} doesn't have any registered handler yet and will be ignored!",
                                    device.getSerialNumber());
                            continue;
                        }

                        DeviceInformation deviceInformation = connector.getDeviceInformation(device);
                        DeviceStatistic deviceStatistic = connector.getDeviceStatistics(device);
                        softenerHandler.onParameterChange(device.getSerialNumber(), deviceInformation);
                        softenerHandler.onStatisticsChange(device.getSerialNumber(), deviceStatistic);

                        /*
                         * in case enter sd fails, we trigger a re-login
                         */
                        if (connector.enterSD(device)) {
                            if (connector.refreshSD(device)) {
                                logger.debug("successfully refreshed sd for device {}", device.getSerialNumber());
                            }

                            // close sd connection (realtime) to prevent too much duplicate websocket updates
                            // we track the scheduler just for cleanup reasons
                            leaveSDFutures
                                    .add(scheduler.schedule(() -> connector.leaveSD(device), 5, TimeUnit.SECONDS));
                        }
                    } else {
                        logger.error("the series {} for device with serial number {} is not supported!",
                                device.getSeries(), device.getSerialNumber());
                    }
                } catch (Exception e) {
                    logger.warn("an unexpected exception occurred during refresh interval of thing {}.",
                            device.getSerialNumber(), e);
                }
            }
        } catch (Exception e) {
            logger.warn("an unexpected exception occurred during refresh interval, thread was saved.", e);
        }
    }

    private void scheduleSoftenerStatsRefresh() {
        cancelSoftenerStatsRefresh(false);
        softenerStatsRefreshCounter.set(bridgeConfiguration.refreshPeriod);
        softenerStatsRefreshFuture = scheduler.scheduleWithFixedDelay(this::refreshSoftenerStats, 5,
                REFRESH_INTERVAL_SECONDS, TimeUnit.SECONDS);
    }

    /** Softener will be immediately refreshed, after at least */
    private void scheduleQuickSoftenerStatsRefresh() {
        softenerStatsRefreshCounter.set(DEFAULT_REFRESH_INTERVAL_QUICK_SECONDS);
    }

    private void cancelLoginFuture(boolean mayInterruptIfRunning) {
        if (loginFuture != null) {
            try {
                loginFuture.cancel(mayInterruptIfRunning);
            } catch (Exception ignored) {
            }
            loginFuture = null;
        }
    }

    private void cancelSoftenerStatsRefresh(boolean mayInterruptIfRunning) {
        if (softenerStatsRefreshFuture != null) {
            try {
                softenerStatsRefreshFuture.cancel(mayInterruptIfRunning);
            } catch (Exception ignored) {
            }
            softenerStatsRefreshFuture = null;
        }
    }
}
