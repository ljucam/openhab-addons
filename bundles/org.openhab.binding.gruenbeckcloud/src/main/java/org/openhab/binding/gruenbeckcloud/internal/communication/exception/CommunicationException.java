package org.openhab.binding.gruenbeckcloud.internal.communication.exception;

/**
 * The {@link CommunicationException} is a wrapper for most exceptions that can occur during a
 * request.
 *
 * @author Mario Aerni - Initial contribution
 */
public class CommunicationException extends Exception {
    public CommunicationException(String message) {
        super(message);
    }
}
