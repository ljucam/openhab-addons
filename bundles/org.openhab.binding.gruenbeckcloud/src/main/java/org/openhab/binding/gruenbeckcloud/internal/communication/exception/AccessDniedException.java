package org.openhab.binding.gruenbeckcloud.internal.communication.exception;

/**
 * The {@link AccessDniedException} class will be thrown if a request suddenly returns 401 -
 * forbidden.
 *
 * @author Mario Aerni - Initial contribution
 */
public class AccessDniedException extends Exception {
    public AccessDniedException(String message) {
        super(message);
    }
}
