package org.openhab.binding.gruenbeckcloud.internal.communication;

import java.net.URI;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.eclipse.jetty.websocket.api.WriteCallback;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.eclipse.jetty.websocket.common.WebSocketSession;
import org.openhab.binding.gruenbeckcloud.internal.api.model.CurrSlow;
import org.openhab.binding.gruenbeckcloud.internal.api.model.Current;
import org.openhab.binding.gruenbeckcloud.internal.communication.listener.WebsocketEventListener;
import org.openhab.binding.gruenbeckcloud.internal.communication.model.InitialWebsocketMessage;
import org.openhab.binding.gruenbeckcloud.internal.communication.model.SignalRInvocationMessage;
import org.openhab.binding.gruenbeckcloud.internal.communication.model.SignalRMessage;
import org.openhab.binding.gruenbeckcloud.internal.communication.model.SignalRProtocolConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The {@link GruenbeckWebsocket} class receives all softener data during
 * realtime engagement (enterSD / leaveSD).
 *
 * @author Mario Aerni - Initial contribution
 */
@WebSocket()
public class GruenbeckWebsocket {
    private static final String TYPE_CUR_SLOW = "CurrSlow";
    private static final String TYPE_CURRENT = "Current";
    private static final char SIGNAL_JSON_TERMINATION = 0x1e;
    private final Logger logger = LoggerFactory.getLogger(GruenbeckWebsocket.class);
    private final Set<WebsocketEventListener> websocketEventListeners = ConcurrentHashMap.newKeySet();
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;
    private final String websocketUrl;
    private WebSocketClient client;
    private WebSocketSession session;
    private Session wsSession;
    private boolean closing;

    public GruenbeckWebsocket(final HttpClient httpClient, final String websocketUrl, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.websocketUrl = websocketUrl;
        this.objectMapper = objectMapper;
    }

    public void addWebsocketEventListener(WebsocketEventListener websocketEventListener) {
        websocketEventListeners.add(websocketEventListener);
    }

    private void onWebsocketError() {
        websocketEventListeners.forEach(WebsocketEventListener::obWebsocketError);
    }

    private void onCurrentUpdate(final Current current) {
        websocketEventListeners.forEach(websocketEventListener -> websocketEventListener.onCurrentUpdate(current));
    }

    private void onCurSlowUpdate(final CurrSlow currSlow) {
        websocketEventListeners.forEach(websocketEventListener -> websocketEventListener.onCurSlowUpdate(currSlow));
    }

    public boolean isRunning() {
        return session != null && session.isOpen();
    }

    public void connect() throws Exception {
        logger.info("connecting to gruenbeck websocket");
        client = new WebSocketClient(httpClient);
        client.getExtensionFactory().unregister("permessage-deflate");
        client.start();

        session = (WebSocketSession) client.connect(this, URI.create(websocketUrl)).get();
    }

    public synchronized void close() {
        logger.debug("closing gruenbeck websocket");
        closing = true;

        websocketEventListeners.clear();

        if (isRunning()) {
            try {
                session.close();
            } catch (Exception ignored) {
            }
        }
        session = null;

        if (wsSession != null && wsSession.isOpen()) {
            try {
                wsSession.close();
            } catch (Exception ignored) {
            }
        }
        wsSession = null;

        if (client != null) {
            try {
                client.stop();
            } catch (Exception ignored) {
            }
            client = null;
        }
    }

    @OnWebSocketConnect
    public void onConnect(final Session wsSession) {
        this.wsSession = wsSession;
        logger.info("websocket: connection succeeded");
        logger.debug("websocket session: {}", wsSession);
        closing = false;

        try {
            wsSession.getRemote().sendString(
                    objectMapper.writeValueAsString(new InitialWebsocketMessage()) + SIGNAL_JSON_TERMINATION,
                    new WriteCallback() {
                        @Override
                        public void writeFailed(Throwable x) {
                            logger.error("could not write initial websocket message", x);
                            onWebsocketError();
                        }

                        @Override
                        public void writeSuccess() {
                            logger.debug("initial websocket message successfully written");
                        }
                    });

        } catch (Exception e) {
            logger.warn("an error occurred during final websocket initialization", e);
            onWebsocketError();
        }
    }

    @OnWebSocketClose
    public void onClose(final int statusCode, final String reason) {
        // closing was demanded
        if (closing) {
            logger.debug("websocket: connection closed normally during closing event. (code: {}). Reason: {}",
                    statusCode, reason);
        }
        // closing was not demanded
        else {
            if (statusCode == StatusCode.NORMAL) {
                logger.debug("websocket: connection closed normally. (code: {}). Reason: {}", statusCode, reason);
                // probably right???
            } else {
                logger.debug("websocket: connection closed abnormally. (code: {}). Reason: {}", statusCode, reason);
                onWebsocketError();
            }
        }
    }

    @OnWebSocketError
    public void onError(final Throwable cause) {
        if (!closing) {
            logger.error("websocket: error: {}", cause.getMessage());
            onWebsocketError();
        }
    }

    @OnWebSocketMessage
    public void onMessage(final String message) {
        final String clearedMessage = message.replaceAll(String.valueOf(SIGNAL_JSON_TERMINATION), "");
        logger.debug("websocket: message: {}", clearedMessage);

        try {
            SignalRMessage signalRMessage = objectMapper.readValue(clearedMessage, SignalRMessage.class);

            if (signalRMessage.getType() == SignalRProtocolConstants.PingMessageType) {
                logger.debug("ping received by gruenbeck signalR server");
                return;
            }

            if (signalRMessage.getType() == SignalRProtocolConstants.InvocationMessageType) {
                processInvocationMessage(clearedMessage);
                return;
            }

            logger.trace("received unhandled websocket message of type {}: {}", signalRMessage.getType(),
                    clearedMessage);
        } catch (Exception e) {
            logger.warn("an exception occurred during websocket message processing, incoming message was: {}", message,
                    e);
        }
    }

    private void processInvocationMessage(String message) {
        logger.debug("processing invocation message");
        try {
            SignalRInvocationMessage signalRInvocationMessage = objectMapper.readValue(message,
                    SignalRInvocationMessage.class);

            for (JsonNode argument : signalRInvocationMessage.getArguments()) {
                try {
                    JsonNode typeNode = argument.get("type");

                    if (typeNode != null) {
                        String type = typeNode.textValue();

                        if (TYPE_CUR_SLOW.equals(type)) {
                            CurrSlow currSlow = objectMapper.treeToValue(argument, CurrSlow.class);
                            onCurSlowUpdate(currSlow);
                        } else if (TYPE_CURRENT.equals(type)) {
                            Current current = objectMapper.treeToValue(argument, Current.class);
                            onCurrentUpdate(current);
                        } else {
                            logger.warn("unknown type '{}' found in argument: {}", type, argument);
                        }
                    } else {
                        logger.warn("argument didn't have a 'type' attribute: {}", argument);
                    }
                } catch (Exception e) {
                    logger.warn("could not process argument: {}", argument, e);
                }
            }
        } catch (Exception e) {
            logger.warn("could not parse invocation message: {}", message, e);
        }
    }
}
