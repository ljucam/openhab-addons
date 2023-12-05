package org.openhab.binding.gruenbeckcloud.internal.communication.model;

/**
 * The class {@link InitialWebsocketMessage} represents the static, initial messages for a SignalIR
 * websocket.
 *
 * @author Mario Aerni - Initial contribution
 */
public class InitialWebsocketMessage {

    public InitialWebsocketMessage() {
    }

    public String getProtocol() {
        return "json";
    }

    public int getVersion() {
        return 1;
    }
}
