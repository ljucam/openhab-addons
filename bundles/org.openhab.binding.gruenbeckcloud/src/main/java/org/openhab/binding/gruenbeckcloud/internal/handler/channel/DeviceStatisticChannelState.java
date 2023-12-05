package org.openhab.binding.gruenbeckcloud.internal.handler.channel;

import java.time.ZoneId;

import org.openhab.binding.gruenbeckcloud.internal.api.model.DeviceStatistic;
import org.openhab.core.types.State;
import org.openhab.core.types.UnDefType;

/**
 * Wrapper for {@link DeviceStatistic} handling the type conversion to {@link State} for directly
 * filling channels.
 *
 * @author Mario Aerni - Initial contribution
 */
public class DeviceStatisticChannelState {
    private final DeviceStatistic deviceStatistic;

    public DeviceStatisticChannelState(DeviceStatistic deviceStatistic) {
        this.deviceStatistic = deviceStatistic != null ? deviceStatistic : new DeviceStatistic();
    }

    public State getHardwareVersionState() {
        return ChannelTypeUtil.stringToState(deviceStatistic.getHardwareVersion());
    }

    public State getSoftwareVersionState() {
        return ChannelTypeUtil.stringToState(deviceStatistic.getSoftwareVersion());
    }

    public State getTimeZoneState() {
        return ChannelTypeUtil.stringToState(deviceStatistic.getTimeZone());
    }

    public State getIdState() {
        return ChannelTypeUtil.stringToState(deviceStatistic.getId());
    }

    public State getSeriesState() {
        return ChannelTypeUtil.stringToState(deviceStatistic.getSeries());
    }

    public State getSerialNumberState() {
        return ChannelTypeUtil.stringToState(deviceStatistic.getSerialNumber());
    }

    public State getNameState() {
        return ChannelTypeUtil.stringToState(deviceStatistic.getName());
    }

    public State getLastServiceState() {
        return ChannelTypeUtil.localDateToState(deviceStatistic.getLastService(), ZoneId.systemDefault());
    }

    public State getStartupState() {
        return ChannelTypeUtil.localDateToState(deviceStatistic.getStartup(), ZoneId.systemDefault());
    }

    public State getNextRegenerationState() {
        return ChannelTypeUtil.localDateTimeToState(deviceStatistic.getNextRegeneration(), ZoneId.systemDefault());
    }

    public State getModeState() {
        return ChannelTypeUtil.intToState(deviceStatistic.getMode());
    }

    public State getUnitState() {
        return ChannelTypeUtil.intToState(deviceStatistic.getUnit());
    }

    public State getTypeState() {
        return ChannelTypeUtil.intToState(deviceStatistic.getType());
    }

    public State getNominalFlowState() {
        return ChannelTypeUtil.doubleToState(deviceStatistic.getNominalFlow());
    }

    public State getRawWaterState() {
        return ChannelTypeUtil.doubleToState(deviceStatistic.getRawWater());
    }

    public State getSoftWaterState() {
        return ChannelTypeUtil.doubleToState(deviceStatistic.getSoftWater());
    }

    public State getHasErrorState() {
        return ChannelTypeUtil.booleanToState(deviceStatistic.isHasError());
    }

    public State getRegisterState() {
        return ChannelTypeUtil.booleanToState(deviceStatistic.isRegister());
    }

    public State getErrorMessageState(int index) {
        if (deviceStatistic.getErrors() == null || deviceStatistic.getErrors().size() <= index) {
            return UnDefType.UNDEF;
        }
        DeviceStatistic.Error error = deviceStatistic.getErrors().get(index);
        return ChannelTypeUtil.stringToState(error.getMessage());
    }

    public State getErrorResolvedState(int index) {
        if (deviceStatistic.getErrors() == null || deviceStatistic.getErrors().size() <= index) {
            return UnDefType.UNDEF;
        }
        DeviceStatistic.Error error = deviceStatistic.getErrors().get(index);
        return ChannelTypeUtil.booleanToState(error.isResolved());
    }

    public State getErrorDateState(int index) {
        if (deviceStatistic.getErrors() == null || deviceStatistic.getErrors().size() <= index) {
            return UnDefType.UNDEF;
        }
        DeviceStatistic.Error error = deviceStatistic.getErrors().get(index);
        return ChannelTypeUtil.localDateToState(error.getDate(), ZoneId.systemDefault());
    }

    public State getErrorTypeState(int index) {
        if (deviceStatistic.getErrors() == null || deviceStatistic.getErrors().size() <= index) {
            return UnDefType.UNDEF;
        }
        DeviceStatistic.Error error = deviceStatistic.getErrors().get(index);
        return ChannelTypeUtil.stringToState(error.getType());
    }

    public State getWaterUsageDateState(int index) {
        if (deviceStatistic.getWater() == null || deviceStatistic.getWater().size() <= index) {
            return UnDefType.UNDEF;
        }
        DeviceStatistic.WaterUsage waterUsage = deviceStatistic.getWater().get(index);
        return ChannelTypeUtil.localDateToState(waterUsage.getDate(), ZoneId.systemDefault());
    }

    public State getWaterUsageValueState(int index) {
        if (deviceStatistic.getWater() == null || deviceStatistic.getWater().size() <= index) {
            return UnDefType.UNDEF;
        }
        DeviceStatistic.WaterUsage waterUsage = deviceStatistic.getWater().get(index);
        return ChannelTypeUtil.intToState(waterUsage.getValue());
    }

    public State getSaltUsageDateState(int index) {
        if (deviceStatistic.getSalt() == null || deviceStatistic.getSalt().size() <= index) {
            return UnDefType.UNDEF;
        }
        DeviceStatistic.SaltUsage saltUsage = deviceStatistic.getSalt().get(index);
        return ChannelTypeUtil.localDateToState(saltUsage.getDate(), ZoneId.systemDefault());
    }

    public State getSaltUsageValueState(int index) {
        if (deviceStatistic.getSalt() == null || deviceStatistic.getSalt().size() <= index) {
            return UnDefType.UNDEF;
        }
        DeviceStatistic.SaltUsage saltUsage = deviceStatistic.getSalt().get(index);
        return ChannelTypeUtil.intToState(saltUsage.getValue());
    }
}
