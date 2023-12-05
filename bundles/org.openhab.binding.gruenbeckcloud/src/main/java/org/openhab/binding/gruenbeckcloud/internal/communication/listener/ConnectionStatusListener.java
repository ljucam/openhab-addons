package org.openhab.binding.gruenbeckcloud.internal.communication.listener;

import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;

/**
 * The {@link ConnectionStatusListener} interface declares all methods to be called on a connection
 * status update.
 *
 * @author Mario Aerni - Initial contribution
 */
public interface ConnectionStatusListener {
    void updateConnectionStatus(ThingStatus status, ThingStatusDetail thingStatusDetail);
}
