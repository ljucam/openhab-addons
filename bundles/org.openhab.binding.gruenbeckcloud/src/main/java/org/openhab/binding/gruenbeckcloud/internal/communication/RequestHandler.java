package org.openhab.binding.gruenbeckcloud.internal.communication;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.openhab.binding.gruenbeckcloud.internal.api.model.Device;
import org.openhab.binding.gruenbeckcloud.internal.api.model.DeviceInformation;
import org.openhab.binding.gruenbeckcloud.internal.api.model.DeviceStatistic;
import org.openhab.binding.gruenbeckcloud.internal.communication.exception.AccessDniedException;
import org.openhab.binding.gruenbeckcloud.internal.communication.exception.CommunicationException;
import org.openhab.binding.gruenbeckcloud.internal.communication.listener.WebsocketEventListener;
import org.openhab.binding.gruenbeckcloud.internal.communication.model.ClientSettings;
import org.openhab.binding.gruenbeckcloud.internal.communication.model.Token;
import org.openhab.binding.gruenbeckcloud.internal.communication.model.WebsocketToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * The {@link RequestHandler} class executes all requests against the gruenbeck cloud.
 *
 * @author Mario Aerni - Initial contribution
 */
public class RequestHandler {
    private static final int LOG_LEVEL_NONE = 0;
    private static final int LOG_LEVEL_TRACE = 1;
    private static final int LOG_LEVEL_DEBUG = 2;
    private final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SslContextFactory sslContextFactory = new SslContextFactory();
    private final UrlBuilder urlBuilder = new UrlBuilder();
    private final String email;
    private final String password;
    private final HttpClient httpClient;
    private ClientSettings clientSettings;
    private String code;
    private Token token;

    public RequestHandler(String email, String password) throws Exception {
        this.email = email;
        this.password = password;

        httpClient = new HttpClient(sslContextFactory);
        httpClient.start();

        // FIXME: un-comment this as soon as we know, everything is ok
        // objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configOverride(LocalDateTime.class)
                .setFormat(JsonFormat.Value.forPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }

    /**
     * Performs a login to gruenbeck cloud. If successful a code for token retrieval is created and
     * stored, otherwise an Exception is thrown.
     */
    public void login() throws ExecutionException, InterruptedException, TimeoutException, AccessDniedException,
            CommunicationException {
        logger.info("start login process");

        // prepare client
        ContentResponse response = httpClient.GET(urlBuilder.clientPreparationUrl());
        logAndCheckResponse("client preparation", response, LOG_LEVEL_NONE);
        clientSettings = new ClientSettings(response);

        // prepare login
        Request request = httpClient.newRequest(urlBuilder.loginPreparationUrl(clientSettings));
        urlBuilder.addLoginPreparationHeader(request, clientSettings);
        urlBuilder.addLoginPreparationFields(request, email, password);
        request.method(HttpMethod.POST);

        response = request.send();
        logAndCheckResponse("login preparation", response);

        // perform login
        httpClient.setFollowRedirects(false);
        request = httpClient.newRequest(urlBuilder.loginUrl(clientSettings));
        urlBuilder.addLoginHeader(request, clientSettings);
        request.method(HttpMethod.GET);

        logger.debug("login, send request: {}", request);
        response = request.send();
        logAndCheckResponse("login", response);

        code = getCode(response);
    }

    /**
     * Retrieves the OAuth tokens after a successful login using the retrieved code. This is also the
     * base for token refreshment
     */
    public void retrieveTokens() throws ExecutionException, InterruptedException, TimeoutException, IOException,
            CommunicationException, AccessDniedException {
        logger.info("retrieve initial tokens");

        httpClient.setFollowRedirects(false);
        Request request = httpClient.newRequest(urlBuilder.initialTokenUrl(clientSettings));
        urlBuilder.addTokenHeaders(request);
        urlBuilder.addInitialTokenFields(request, code);
        request.method(HttpMethod.POST);

        ContentResponse response = request.send();
        logAndCheckResponse("initial token retrieval", response);

        token = objectMapper.readValue(response.getContentAsString(), Token.class);
    }

    public void refreshTokens() throws ExecutionException, InterruptedException, TimeoutException,
            JsonProcessingException, CommunicationException, AccessDniedException {
        logger.info("refresh access tokens");

        httpClient.setFollowRedirects(false);
        Request request = httpClient.newRequest(urlBuilder.refreshTokenUrl(clientSettings));
        urlBuilder.addTokenHeaders(request);
        urlBuilder.addRefreshTokenFields(request, token);
        request.method(HttpMethod.POST);

        logger.debug("refreshToken request: {}", request);
        ContentResponse response = request.send();
        logAndCheckResponse("refresh tokens", response);

        token = objectMapper.readValue(response.getContentAsString(), Token.class);
    }

    /** Returns a list of all devices in the cloud. */
    public List<Device> getDevices() {
        logger.info("retrieve devices");

        if (token == null) {
            return Collections.emptyList();
        }

        try {
            httpClient.setFollowRedirects(false);
            Request request = httpClient.newRequest(urlBuilder.deviceListUrl());
            urlBuilder.addDeviceHeader(request, token);
            request.method(HttpMethod.GET);

            ContentResponse response = request.send();
            logAndCheckResponse("get devices", response);
            return objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {
            });
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("current thread was interrupted during device retrieval", e);
        } catch (Exception e) {
            logger.error("an error occurred during device retrieval", e);
        }

        return Collections.emptyList();
    }

    public DeviceInformation getDeviceInformation(Device device) throws ExecutionException, InterruptedException,
            TimeoutException, CommunicationException, AccessDniedException, JsonProcessingException {
        logger.info("update device information for device: {}", device.getSerialNumber());

        if (token == null) {
            logger.debug("Could not retrieve device information for device {}, because token was null!",
                    device.getSerialNumber());
            return null;
        }

        httpClient.setFollowRedirects(false);
        Request request = httpClient.newRequest(urlBuilder.deviceInformationUrl(device.getId()));
        urlBuilder.addDeviceHeader(request, token);
        request.method(HttpMethod.GET);

        ContentResponse response = request.send();
        logAndCheckResponse("device information", response);

        return objectMapper.readValue(response.getContentAsString(), DeviceInformation.class);
    }

    public DeviceStatistic getDeviceStatistics(Device device) throws ExecutionException, InterruptedException,
            TimeoutException, CommunicationException, AccessDniedException, JsonProcessingException {
        logger.info("retrieve device statistics for device: {}", device.getSerialNumber());

        if (token == null) {
            logger.debug("Could not retrieve device statistics for device {}, because token was null!",
                    device.getSerialNumber());
            return null;
        }

        httpClient.setFollowRedirects(false);
        Request request = httpClient.newRequest(urlBuilder.deviceStatisticsUrl(device.getId()));
        urlBuilder.addDeviceHeader(request, token);
        request.method(HttpMethod.GET);

        ContentResponse response = request.send();
        logAndCheckResponse("device statistics", response);

        return objectMapper.readValue(response.getContentAsString(), DeviceStatistic.class);
    }

    public GruenbeckWebsocket createWebsocket(WebsocketEventListener eventListener) throws Exception {
        logger.debug("create websocket connection");

        // get the websocket access token and url
        httpClient.setFollowRedirects(false);
        Request request = httpClient.newRequest(urlBuilder.websocketNegotiationUrl());
        urlBuilder.addWebsocketNegotiationHeader(request, token);
        request.method(HttpMethod.GET);

        ContentResponse response = request.send();
        logAndCheckResponse("connect websocket", response, LOG_LEVEL_TRACE);
        WebsocketToken websocketToken = objectMapper.readValue(response.getContentAsString(), WebsocketToken.class);

        // get the websocket connectionId
        request = httpClient.newRequest(urlBuilder.getWebsocketIdUrl());
        urlBuilder.addWebsocketIdHeader(request, websocketToken);
        request.method(HttpMethod.POST);

        response = request.send();
        logAndCheckResponse("get websocketId", response, LOG_LEVEL_TRACE);
        JsonNode jsonNode = objectMapper.readTree(response.getContentAsString());
        String connectionId = jsonNode.get("connectionId").asText();

        // create websocket
        GruenbeckWebsocket websocket = new GruenbeckWebsocket(httpClient,
                urlBuilder.websocketUrl(connectionId, websocketToken), objectMapper);
        websocket.addWebsocketEventListener(eventListener);
        websocket.connect();
        return websocket;
    }

    public boolean enterSD(String deviceId) throws ExecutionException, InterruptedException, TimeoutException,
            CommunicationException, AccessDniedException {
        if (token == null) {
            logger.debug("Could not enter realtime for device {}, because token was null!", deviceId);
            return false;
        }

        httpClient.setFollowRedirects(false);
        Request request = httpClient.newRequest(urlBuilder.enterRealtimeUrl(deviceId));
        urlBuilder.addRealtimeHeader(request, token);
        request.method(HttpMethod.POST);

        ContentResponse response = request.send();
        logAndCheckResponse("enter sd (realtime)", response, LOG_LEVEL_TRACE);

        return true;
    }

    public boolean refreshSD(String deviceId) throws ExecutionException, InterruptedException, TimeoutException,
            CommunicationException, AccessDniedException {
        if (token == null) {
            logger.debug("Could not refresh realtime for device {}, because token was null!", deviceId);
            return false;
        }

        httpClient.setFollowRedirects(false);
        Request request = httpClient.newRequest(urlBuilder.refreshRealtimeUrl(deviceId));
        urlBuilder.addRealtimeHeader(request, token);
        request.method(HttpMethod.POST);

        ContentResponse response = request.send();
        logAndCheckResponse("refresh sd (realtime)", response, LOG_LEVEL_TRACE);

        return true;
    }

    public void leaveSD(String deviceId) throws ExecutionException, InterruptedException, TimeoutException,
            CommunicationException, AccessDniedException {
        if (token == null) {
            logger.debug("Could not leave realtime for device {}, because token was null!", deviceId);
            return;
        }

        httpClient.setFollowRedirects(false);
        Request request = httpClient.newRequest(urlBuilder.leaveRealtimeUrl(deviceId));
        urlBuilder.addRealtimeHeader(request, token);
        request.method(HttpMethod.POST);

        ContentResponse response = request.send();
        logAndCheckResponse("leave sd (realtime)", response, LOG_LEVEL_TRACE);
    }

    public void pushParameter(String deviceId, String parameter, Object value) throws JsonProcessingException,
            ExecutionException, InterruptedException, TimeoutException, CommunicationException, AccessDniedException {
        if (token == null) {
            logger.debug("Could not push parameter ({}={}) for device {}, because token was null!", parameter, value,
                    deviceId);
            return;
        }

        httpClient.setFollowRedirects(false);
        Request request = httpClient.newRequest(urlBuilder.pushParameterUrl(deviceId));
        urlBuilder.addPushParameterHeader(request, token);
        request.method(HttpMethod.PATCH);

        String requestBody = objectMapper.writeValueAsString(Map.of(parameter, value));
        request.content(new StringContentProvider(requestBody), "application/json");

        ContentResponse response = request.send();
        logAndCheckResponse("push parameter", response);
    }

    public void requestRegeneration(String deviceId) throws ExecutionException, InterruptedException, TimeoutException,
            CommunicationException, AccessDniedException {
        if (token == null) {
            logger.debug("Could not request regeneration for device {}, because token was null!", deviceId);
            return;
        }

        httpClient.setFollowRedirects(false);
        Request request = httpClient.newRequest(urlBuilder.requestRegenerationUrl(deviceId));
        urlBuilder.addPushParameterHeader(request, token);
        request.method(HttpMethod.POST);
        request.content(new StringContentProvider("{}"), "application/json");

        ContentResponse response = request.send();
        logAndCheckResponse("request regeneration", response);
    }

    public void close() {
        if (httpClient != null) {
            try {
                httpClient.stop();
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Returns the code from a successful login process. This code is needed to retrieve the token for
     * authentication.
     *
     * @param response response from login
     * @return code for token retrieval
     */
    private String getCode(ContentResponse response) {
        String resultString = response.getContentAsString();

        int start, end;
        start = resultString.indexOf("code%3d") + 7;
        end = resultString.indexOf(">here") - 1;
        String code = resultString.substring(start, end);

        logger.debug("login, returned code: {}", code);
        return code;
    }

    private void logAndCheckResponse(String step, ContentResponse response)
            throws CommunicationException, AccessDniedException {
        logAndCheckResponse(step, response, LOG_LEVEL_DEBUG);
    }

    private void logAndCheckResponse(String step, ContentResponse response, int logLevel)
            throws AccessDniedException, CommunicationException {

        if (logLevel == LOG_LEVEL_DEBUG) {
            logger.debug("{}, response status: {}", step, response.getStatus());
            logger.debug("{}, response headers: {}", step, response.getHeaders());
            logger.debug("{}, response body: {}", step, response.getContentAsString());
        }

        if (logLevel == LOG_LEVEL_TRACE) {
            logger.trace("{}, response status: {}", step, response.getStatus());
            logger.trace("{}, response headers: {}", step, response.getHeaders());
            logger.trace("{}, response body: {}", step, response.getContentAsString());
        }

        if (response.getStatus() == 401) {
            throw new AccessDniedException("access to gruenbeck cloud was denied, statusCode=" + response.getStatus()
                    + ", reason=" + response.getReason());
        }

        if (response.getStatus() == 404) {
            throw new CommunicationException("could not execute request, requested element was not found, status code="
                    + response.getStatus() + ", reason=" + response.getReason());
        }

        if (response.getStatus() >= 400) {
            throw new CommunicationException("could not establish communication to gruenbeck cloud, status code="
                    + response.getStatus() + ", reason=" + response.getReason());
        }
    }
}
