package org.openhab.binding.gruenbeckcloud.internal.communication.listener;

import org.openhab.binding.gruenbeckcloud.internal.api.model.CurrSlow;
import org.openhab.binding.gruenbeckcloud.internal.api.model.Current;

/**
 * The {@link DeviceUpdateListener} interface declares all methods to be called on update events.
 *
 * @author Mario Aerni - Initial contribution
 */
public interface DeviceUpdateListener {
    void onCurrentUpdate(Current current);

    void onCurSlowUpdate(CurrSlow currSlow);
}
