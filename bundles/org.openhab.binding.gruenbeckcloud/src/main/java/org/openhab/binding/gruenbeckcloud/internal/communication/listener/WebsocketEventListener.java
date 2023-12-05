package org.openhab.binding.gruenbeckcloud.internal.communication.listener;

import org.openhab.binding.gruenbeckcloud.internal.api.model.CurrSlow;
import org.openhab.binding.gruenbeckcloud.internal.api.model.Current;

/**
 * The {@link WebsocketEventListener} interface declares all methods for a gruenbeck cloud websocket
 * listener.
 *
 * @author Mario Aerni - Initial contribution
 */
public interface WebsocketEventListener {
    void obWebsocketError();

    void onCurrentUpdate(Current current);

    void onCurSlowUpdate(CurrSlow currSlow);
}
