package org.openhab.binding.gruenbeckcloud.internal.listener;

import org.openhab.binding.gruenbeckcloud.internal.api.model.CurrSlow;
import org.openhab.binding.gruenbeckcloud.internal.api.model.Current;
import org.openhab.binding.gruenbeckcloud.internal.api.model.DeviceInformation;
import org.openhab.binding.gruenbeckcloud.internal.api.model.DeviceStatistic;

/**
 * The {@link DeviceDataChangeListener} interfaces describes methods that are called
 * when device data has changed.
 *
 * @author Mario Aerni - Initial contribution
 */
public interface DeviceDataChangeListener {
    void onStatisticsChange(String serialNumber, DeviceStatistic deviceStatistic);

    void onParameterChange(String serialNumber, DeviceInformation updatedDevice);

    void onCurrentUpdate(Current current);

    void onCurrSlowUpdate(CurrSlow currSlow);
}
