package org.openhab.binding.gruenbeckcloud.internal.communication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The class {@link SignalRMessage} represents the incoming websocket message from singlR. It is
 * needed to decide which type of message we have.
 *
 * @author Mario Aerni - Initial contribution
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignalRMessage {
    private int type;

    public SignalRMessage() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
