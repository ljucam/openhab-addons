package org.openhab.binding.gruenbeckcloud.internal.communication;

/**
 * The {@link ConnectorEvent} enum declares all possible events, that may occur in the {@link
 * GruenbeckConnector}.
 *
 * @author Mario Aerni - Initial contribution
 */
public enum ConnectorEvent {
    INVALID_CREDENTIALS,
    LOGIN_FAILED,
    LOGIN_SUCCEEDED,
    TOKEN_REFRESH_FAILED,
    TOKEN_REFRESH_SUCCEEDED,
    WEBSOCKET_CREATION_FAILED,
    WEBSOCKET_CREATION_SUCCEEDED,
    WEBSOCKET_CONNECTION_FAILED,
    REQUEST_ACCESS_DENIED,
    REQUEST_FAILED,
}
