package org.openhab.binding.gruenbeckcloud.internal.communication.model;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * The class {@link SignalRInvocationMessage} represents the actual message from signalR caring data
 * for our gruenbeck devices.
 *
 * @author Mario Aerni - Initial contribution
 */
public class SignalRInvocationMessage extends SignalRMessage {
    private String target;
    private List<JsonNode> arguments;

    public SignalRInvocationMessage() {
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * due to polymorphic behaviour (the list can contain either CurSlow or Current items), we use a
     * list of {@link JsonNode}s here.
     */
    public List<JsonNode> getArguments() {
        return arguments;
    }

    public void setArguments(List<JsonNode> arguments) {
        this.arguments = arguments;
    }
}
