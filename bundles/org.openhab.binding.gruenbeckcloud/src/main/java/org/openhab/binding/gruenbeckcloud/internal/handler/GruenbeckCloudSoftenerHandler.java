/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * <p>See the NOTICE file(s) distributed with this work for additional information.
 *
 * <p>This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0
 *
 * <p>SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.gruenbeckcloud.internal.handler;

import static org.openhab.binding.gruenbeckcloud.internal.GruenbeckCloudBindingConstants.*;
import static org.openhab.binding.gruenbeckcloud.internal.api.model.SoftenerData.SOFTENER_DATA;

import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;

import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.gruenbeckcloud.internal.GruenbeckCloudSoftenerConfiguration;
import org.openhab.binding.gruenbeckcloud.internal.api.model.*;
import org.openhab.binding.gruenbeckcloud.internal.handler.channel.*;
import org.openhab.binding.gruenbeckcloud.internal.listener.DeviceDataChangeListener;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.StringType;
import org.openhab.core.storage.Storage;
import org.openhab.core.storage.StorageService;
import org.openhab.core.thing.*;
import org.openhab.core.thing.binding.BaseThingHandler;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.types.Command;
import org.openhab.core.types.RefreshType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link GruenbeckCloudSoftenerHandler} is the class to manage gruenbeck softeners.
 *
 * @author Mario Aerni - Initial contribution
 */
public class GruenbeckCloudSoftenerHandler extends BaseThingHandler implements DeviceDataChangeListener {

    private final Logger logger = LoggerFactory.getLogger(GruenbeckCloudSoftenerHandler.class);
    private final Object lock = new Object();

    private StorageService storageService;
    private Device device;
    private GruenbeckCloudBridgeHandler bridgeHandler;

    private volatile DeviceInformation lastDeviceInformation;
    private volatile DeviceStatistic lastDeviceStatistic;
    private volatile Current lastCurrent;
    private volatile CurrSlow lastCurrSlow;
    private volatile SoftenerData softenerData;
    private volatile Storage<SoftenerData> storage;

    public GruenbeckCloudSoftenerHandler(final Thing thing, StorageService storageService) {
        super(thing);
        this.storageService = storageService;
    }

    @Override
    public void initialize() {
        // e.g. gruenbeckcloud:softener:f460bfe9c2:BXXXXXXXXX
        logger.debug("initializing thing {}", getThing().getUID());
        storage = storageService.getStorage(thing.getUID().toString(),
                GruenbeckCloudSoftenerHandler.class.getClassLoader());
        initializeThing(getBridge() == null ? null : getBridge().getStatus());
        bridgeHandler = (GruenbeckCloudBridgeHandler) getBridge().getHandler();
    }

    private void initializeThing(@Nullable final ThingStatus bridgeStatus) {
        logger.info("initializeThing {} bridge status {}", getThing().getUID(), bridgeStatus);
        final GruenbeckCloudSoftenerConfiguration softenerConfiguration = getSoftenerConfiguration();

        if (softenerConfiguration == null) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                    "no configuration found for device id " + getThing().getUID());
            return;
        }

        device = new Device(softenerConfiguration);
        logger.debug("configuration used for thing {}:{}", getThing().getUID(), softenerConfiguration);

        if (!SUPPORTED_SOFTENER.contains(device.getSeries())) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.HANDLER_MISSING_ERROR,
                    "no handler available for softener of series=" + device.getSeries());
            return;
        }

        if (getGruenbeckCloudBridgeHandler() == null) {
            updateStatus(ThingStatus.OFFLINE);
            return;
        }

        if (bridgeStatus == ThingStatus.ONLINE) {
            loadStoredValues();
            updateStatus(ThingStatus.ONLINE);
        } else {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.BRIDGE_OFFLINE);
        }

        logger.trace("found storage service: {}, storage service class is: {}", storageService != null,
                storageService != null ? storageService.getClass() : null);
    }

    private void loadStoredValues() {
        softenerData = storage.get(SOFTENER_DATA);

        if (softenerData == null) {
            logger.trace("no storage found for device {}, create new one.", device.getSerialNumber());
            softenerData = new SoftenerData();
        }

        if (softenerData.getCurrentCapacityInGram() == null) {
            logger.trace("no current capacity for salt found in storage for device {}, reset to default: {}",
                    device.getSerialNumber(), getSoftenerConfiguration().saltCapacity);
            softenerData.setCurrentCapacityInGram(getSoftenerConfiguration().saltCapacity);
        }

        storage.put(SOFTENER_DATA, softenerData);
    }

    private GruenbeckCloudSoftenerConfiguration getSoftenerConfiguration() {
        return getThing().getConfiguration().as(GruenbeckCloudSoftenerConfiguration.class);
    }

    @Override
    public void bridgeStatusChanged(ThingStatusInfo bridgeStatusInfo) {
        logger.trace("bridgeStatusChanged {}", bridgeStatusInfo);
        initializeThing(bridgeStatusInfo.getStatus());
    }

    @Override
    public void handleCommand(final ChannelUID channelUID, final Command command) {
        if (RefreshType.REFRESH.equals(command)) {
            updateState(lastDeviceInformation);
            updateState(lastDeviceStatistic);
            updateState(lastCurrSlow);
            updateState(lastCurrent);
            return;
        }

        String channelId = channelUID.getId();

        if (handleRegenerationRequest(channelId, command)) {
            return;
        }

        if (handleOperationMode(channelId, command)) {
            return;
        }

        if (handleRegenerationTimeMode(channelId, command)) {
            return;
        }

        if (handleRegenerationTimeSetting(channelId, command)) {
            return;
        }

        if (handleResetSaltLevel(channelId, command)) {
            return;
        }
    }

    private boolean handleResetSaltLevel(String channelId, Command command) {
        if (DYN_RESET_SALT_LEVEL.equals(channelId)) {
            if (invalidDataType(OnOffType.class, channelId, command)) {
                return true;
            }

            if (!OnOffType.ON.equals(command)) {
                return true;
            }

            softenerData.setCurrentCapacityInGram(getSoftenerConfiguration().saltCapacity);
            storage.put(SOFTENER_DATA, softenerData);

            updateState(channel(DYN_SALT_LEVEL), ChannelTypeUtil.intToState(softenerData.getCurrentCapacityInGram()));
            updateState(channel(DYN_REQUEST_REGENERATION), OnOffType.OFF);
            return true;
        }

        return false;
    }

    private boolean handleRegenerationTimeSetting(String channelId, Command command) {
        if (REGENERATION_TIME_SETTING_COMMANDS.contains(channelId)) {
            if (invalidDataType(StringType.class, channelId, command)) {
                return true;
            }

            String hoursAndMinutes = command.toFullString();
            Matcher matcher = HOURS_AND_MINUTES_PATTERN.matcher(hoursAndMinutes);
            if (!matcher.matches()) {
                logger.warn("invalid value pattern '{}' for channel={}, valid pattern is: HH:mm", hoursAndMinutes,
                        channelId);
                return true;
            }

            bridgeHandler.handleCommand(device.getId(), channelId, hoursAndMinutes);
            return true;
        }

        return false;
    }

    private boolean handleRegenerationTimeMode(String channelId, Command command) {
        if (DEV_INF_REGENERATION_TIME.equals(channelId)) {
            if (invalidDataType(DecimalType.class, channelId, command)) {
                return true;
            }
            int regenerationMode = ((DecimalType) command).intValue();
            if (!AVAILABLE_REGENERATION_MODES.contains(regenerationMode)) {
                logger.warn("invalid value {} for channel={}, valid values are: 0 (Automatic), 1 (Fixed)",
                        regenerationMode, channelId);

                return true;
            }

            bridgeHandler.handleCommand(device.getId(), channelId, regenerationMode);
            return true;
        }

        return false;
    }

    private boolean handleOperationMode(String channelId, Command command) {
        if (OPERATION_MODE_COMMANDS.contains(channelId)) {
            if (invalidDataType(DecimalType.class, channelId, command)) {
                return true;
            }

            int powerMode = ((DecimalType) command).intValue();
            if (!AVAILABLE_POWER_MODES.contains(powerMode)) {
                logger.warn("invalid value {} for channel={}, valid values are: 1 (Eco), 2 (Comfort), 3 (Power)",
                        powerMode, channelId);

                return true;
            }

            bridgeHandler.handleCommand(device.getId(), channelId, powerMode);
            return true;
        }

        return false;
    }

    private boolean handleRegenerationRequest(String channelId, final Command command) {
        if (DYN_REQUEST_REGENERATION.equals(channelId)) {
            if (invalidDataType(OnOffType.class, channelId, command)) {
                return true;
            }

            if (!OnOffType.ON.equals(command)) {
                return true;
            }

            bridgeHandler.handleRegenerationRequest(device.getId());

            updateState(channel(DYN_REQUEST_REGENERATION), OnOffType.OFF);
            return true;
        }

        return false;
    }

    private boolean invalidDataType(Class<?> type, String channel, Object value) {
        if (type.isInstance(value)) {
            return false;
        }

        logger.warn("expected data type was '{}', but got '{}' for channel={}", type, value.getClass(), channel);

        return true;
    }

    private @Nullable GruenbeckCloudBridgeHandler getGruenbeckCloudBridgeHandler() {
        synchronized (lock) {
            if (this.bridgeHandler == null) {
                final Bridge bridge = getBridge();
                if (bridge == null) {
                    return null;
                }
                final ThingHandler handler = bridge.getHandler();
                if (handler instanceof GruenbeckCloudBridgeHandler) {
                    this.bridgeHandler = (GruenbeckCloudBridgeHandler) handler;
                } else {
                    return null;
                }
            }
        }
        return this.bridgeHandler;
    }

    @Override
    public void dispose() {
        logger.debug("disposing thing: {}", getThing().getUID());
        bridgeHandler = null;
        device = null;
        storageService = null;
        storage = null;
    }

    @Override
    public void onStatisticsChange(String serialNumber, final DeviceStatistic deviceStatistic) {
        if (!device.getSerialNumber().equals(serialNumber)) {
            logger.warn("device {} received statistics for device {}. This statistic won't be processed!",
                    device.getSerialNumber(), serialNumber);
            return;
        }

        lastDeviceStatistic = deviceStatistic;
        updateState(deviceStatistic);
    }

    @Override
    public void onParameterChange(String serialNumber, final DeviceInformation deviceInformation) {
        if (!device.getSerialNumber().equals(serialNumber)) {
            logger.warn("device {} received information for device {}. This information won't be processed!",
                    device.getSerialNumber(), serialNumber);
            return;
        }

        lastDeviceInformation = deviceInformation;
        updateState(deviceInformation);
    }

    @Override
    public void onCurrentUpdate(final Current current) {
        if (!device.getSerialNumber().equals(current.getId())) {
            logger.warn("device {} received current for device {}. This current won't be processed!",
                    device.getSerialNumber(), current.getId());
            return;
        }

        lastCurrent = current;
        updateState(current);
    }

    @Override
    public void onCurrSlowUpdate(final CurrSlow currSlow) {
        if (!device.getSerialNumber().equals(currSlow.getId())) {
            logger.warn("device {} received current for device {}. This current won't be processed!",
                    device.getSerialNumber(), currSlow.getId());
            return;
        }

        lastCurrSlow = currSlow;
        updateState(currSlow);
    }

    private void updateState(Current current) {
        CurrentChannelState currentChannelState = new CurrentChannelState(current);
        updateState(channel(DEV_CURR_PERCENT_TILL_REGISTRATION_1), currentChannelState.getMregpercent1State());
        updateState(channel(DEV_CURR_PERCENT_TILL_REGISTRATION_2), currentChannelState.getMregpercent2State());
        updateState(channel(DEV_CURR_TIME_LEFT_CURRENT_REGISTRATION_STEP), currentChannelState.getMremregstepState());
        updateState(channel(DEV_CURR_REGISTRATION_STEP), currentChannelState.getMregstatusState());
        updateState(channel(DEV_CURR_RESIDUAL_CAPACITY_1), currentChannelState.getMresidcap1State());
        updateState(channel(DEV_CURR_RESIDUAL_CAPACITY_2), currentChannelState.getMresidcap2State());
        updateState(channel(DEV_CURR_VOLUME_SOFT_WATER_EXCHANGER_1), currentChannelState.getMrescapa1State());
        updateState(channel(DEV_CURR_VOLUME_SOFT_WATER_EXCHANGER_2), currentChannelState.getMrescapa2State());
        updateState(channel(DEV_CURR_FLOW_RATE_EXCHANGER_1), currentChannelState.getMflow1State());
        updateState(channel(DEV_CURR_FLOW_RATE_EXCHANGER_2), currentChannelState.getMflow2State());
        updateState(channel(DEV_CURR_REGENERATION_FLOW_RATE_EXCHANGER_1), currentChannelState.getMflowreg1State());
        updateState(channel(DEV_CURR_REGENERATION_FLOW_RATE_EXCHANGER_2), currentChannelState.getMflowreg2State());
        updateState(channel(DEV_CURR_BLENDING_FLOW_RATE), currentChannelState.getMflowblendState());
        updateState(channel(DEV_CURR_CURRENT_CHLORINE), currentChannelState.getMcurrentState());
        updateState(channel(DEV_CURR_ABSORBER_REMAINING_AMOUNT_OF_WATER), currentChannelState.getMreswatadmodState());
        updateState(channel(DEV_CURR_SALT_REACH_IN_DAYS), currentChannelState.getMsaltrangeState());
        updateState(channel(DEV_CURR_DAYS_TILL_NEXT_MAINTENANCE), currentChannelState.getMmaintState());
        updateState(channel(DEV_CURR_REGENERATION_STEP_INDICATION_VALVE_1), currentChannelState.getMstep1State());
        updateState(channel(DEV_CURR_REGENERATION_STEP_INDICATION_VALVE_2), currentChannelState.getMstep2State());
        updateState(channel(DEV_CURR_IBUILTINDEV), currentChannelState.getIbuiltindevState());
        updateState(channel(DEV_CURR_ISNCU), currentChannelState.getIsncuState());

        calculateAndUpdateRegenerationProgress(current);
    }

    private synchronized void calculateAndUpdateRegenerationProgress(Current current) {
        int regStatus = 0;
        int regProgressIdRaw = 0;
        String regProgressDescription = "";
        String regRemaining = "-";
        switch (current.getMregstatus()) {
            case 0:
                regProgressDescription = "In use";
                break;
            case 10:
                regStatus = 20;
                regProgressIdRaw = 1;
                regProgressDescription = "Fill salt tank";
                regRemaining = getRemainingLiter(current.getMremregstep());
                break;
            case 20:
                regStatus = 40;
                regProgressIdRaw = 2;
                regProgressDescription = "Salting";
                regRemaining = getRemainingTime(current.getMremregstep());
                break;
            case 30:
                regStatus = 60;
                regProgressIdRaw = 3;
                regProgressDescription = "Displace";
                regRemaining = getRemainingTime(current.getMremregstep()); // validated, correct
                break;
            case 40:
                regStatus = 80;
                regProgressIdRaw = 4;
                regProgressDescription = "Backwash";
                regRemaining = getRemainingLiter(current.getMremregstep());
                break;
            case 60:
                regStatus = 90;
                regProgressIdRaw = 5;
                regProgressDescription = "Wash out";
                regRemaining = getRemainingLiter(current.getMremregstep());
                break;
            case 50:
                regStatus = 100;
                regProgressIdRaw = 5;
                regProgressDescription = "Wash out";
                regRemaining = getRemainingLiter(current.getMremregstep());
                break;
        }

        /*
         * calc example: liter
         * mregstatus: 10
         * mregstep: 0.6000000000000001
         *
         * Result:
         * regstatus: 20
         * regProgressIdRaw: 1
         * regRemaining: 0.6 l
         *
         *
         * calc example: time
         * mregstatus: 20
         * mregstep: 4480.0
         *
         * Result:
         * regstatus: 40
         * regProgressIdRaw: 2
         * regRemaining: 74min 40s
         *
         */

        logger.trace("calculation for regeneration progress result");
        logger.trace("regeneration active:               {}", current.getMregstatus() != 0);
        logger.trace("regeneration status raw:           {}", current.getMregstatus());
        logger.trace("regeneration step raw:             {}", current.getMremregstep());
        logger.trace("regeneration status:               {}", regStatus);
        logger.trace("regeneration progress raw:         {}", regProgressIdRaw);
        logger.trace("regeneration progress description: {}", regProgressDescription);
        logger.trace("regeneration progress remaining:   {}", regRemaining);

        updateState(channel(DYN_REGENERATION_ACTIVE), ChannelTypeUtil.booleanToState(current.getMregstatus() != 0));
        updateState(channel(DYN_REGENERATION_STATUS), ChannelTypeUtil.intToPercentageState(regStatus));
        updateState(channel(DYN_REGENERATION_PROGRESS_ID_RAW), ChannelTypeUtil.intToState(regProgressIdRaw));
        updateState(channel(DYN_REGENERATION_PROGRESS_DESCRIPTION),
                ChannelTypeUtil.stringToState(regProgressDescription));
        updateState(channel(DYN_REGENERATION_REMAINING_IN_CURRENT_STEP), ChannelTypeUtil.stringToState(regRemaining));
    }

    private String getRemainingLiter(Double mremregstep) {
        return (Math.round(mremregstep * 100) / 100d) + " l";
    }

    private String getRemainingTime(Double mremregstep) {
        int minutes = mremregstep.intValue() / 60;
        int seconds = mremregstep.intValue() % 60;

        // 30s
        if (minutes == 0) {
            return seconds + "s";
        }

        // 10min 2s
        return minutes + "min " + seconds + "s";
    }

    private void updateState(CurrSlow currSlow) {
        CurrSlowChannelState currSlowChannelState = new CurrSlowChannelState(currSlow);
        updateState(channel(DEV_CURRSLOW_REGENERATION_COUNTER), currSlowChannelState.getMcountregState());
        updateState(channel(DEV_CURRSLOW_SOFT_WATER_VOLUME_EXCHANGER_1), currSlowChannelState.getMcountwater1State());
        updateState(channel(DEV_CURRSLOW_SOFT_WATER_VOLUME_EXCHANGER_2), currSlowChannelState.getMcountwater2State());
        updateState(channel(DEV_CURRSLOW_MAKE_UP_WATER_VOLUME), currSlowChannelState.getMcountwatertankState());
        updateState(channel(DEV_CURRSLOW_SALT_CONSUMPTION), currSlowChannelState.getMsaltusageState());
        updateState(channel(DEV_CURRSLOW_FLOW), currSlowChannelState.getMflowexcState());
        updateState(channel(DEV_CURRSLOW_FLOW_EXCHANGER_1), currSlowChannelState.getMflowexc2reg1State());
        updateState(channel(DEV_CURRSLOW_FLOW_EXCHANGER_2), currSlowChannelState.getMflowexc1reg2State());
        updateState(channel(DEV_CURRSLOW_ABSORBER_EXHAUSTED), currSlowChannelState.getMlifeadsorbState());
        updateState(channel(DEV_CURRSLOW_ACTUAL_SOFT_WATER_HARDNESS), currSlowChannelState.getMhardsoftwState());
        updateState(channel(DEV_CURRSLOW_CAPACITY_FIGURE), currSlowChannelState.getMcapacityState());
        updateState(channel(DEV_CURRSLOW_MAVERAGE), currSlowChannelState.getMaverageState());
        updateState(channel(DEV_CURRSLOW_MSTDDEV), currSlowChannelState.getMstddevState());
        updateState(channel(DEV_CURRSLOW_MMAX), currSlowChannelState.getMmaxState());
        updateState(channel(DEV_CURRSLOW_MPRESS), currSlowChannelState.getMpressState());
        updateState(channel(DEV_CURRSLOW_MTEMP), currSlowChannelState.getMtempState());
        updateState(channel(DEV_CURRSLOW_FLOW_RATE_PEAK_VALUE), currSlowChannelState.getMflowmaxState());
        updateState(channel(DEV_CURRSLOW_PEAK_VALUE_EXCHANGER_1), currSlowChannelState.getMflowmax1reg2State());
        updateState(channel(DEV_CURRSLOW_PEAK_VALUE_EXCHANGER_2), currSlowChannelState.getMflowmax2reg1State());
        updateState(channel(DEV_CURRSLOW_LAST_REGENERATION_EXCHANGER_1), currSlowChannelState.getMendreg1State());
        updateState(channel(DEV_CURRSLOW_LAST_REGENERATION_EXCHANGER_2), currSlowChannelState.getMendreg2State());
    }

    private void updateState(DeviceStatistic deviceStatistic) {
        DeviceStatisticChannelState deviceStatisticChannelState = new DeviceStatisticChannelState(deviceStatistic);
        updateState(channel(DEV_STAT_HARDWARE_VERSION), deviceStatisticChannelState.getHardwareVersionState());
        updateState(channel(DEV_STAT_SOFTWARE_VERSION), deviceStatisticChannelState.getSoftwareVersionState());
        updateState(channel(DEV_STAT_TIMEZONE), deviceStatisticChannelState.getTimeZoneState());
        updateState(channel(DEV_STAT_LAST_SERVICE), deviceStatisticChannelState.getLastServiceState());
        updateState(channel(DEV_STAT_STARTUP_DATE), deviceStatisticChannelState.getStartupState());
        updateState(channel(DEV_STAT_NEXT_REGENERATION), deviceStatisticChannelState.getNextRegenerationState());
        updateState(channel(DEV_STAT_OPERATION_MODE), deviceStatisticChannelState.getModeState());
        updateState(channel(DEV_STAT_HARDNESS_UNIT), deviceStatisticChannelState.getUnitState());
        updateState(channel(DEV_STAT_SOFTENER_MODEL_TYPE), deviceStatisticChannelState.getTypeState());
        updateState(channel(DEV_STAT_NOMINAL_FLOW), deviceStatisticChannelState.getNominalFlowState());
        updateState(channel(DEV_STAT_HARDNESS_RAW_WATER), deviceStatisticChannelState.getRawWaterState());
        updateState(channel(DEV_STAT_HARDNESS_SOFT_WATTER), deviceStatisticChannelState.getSoftWaterState());
        updateState(channel(DEV_STAT_HAS_ERROR), deviceStatisticChannelState.getHasErrorState());
        updateState(channel(DEV_STAT_SOFTENER_REGISTERED), deviceStatisticChannelState.getRegisterState());
        updateState(channel(DEV_STAT_LAST_ERROR_MESSAGE), deviceStatisticChannelState.getErrorMessageState(0));
        updateState(channel(DEV_STAT_LAST_ERROR_RESOLVED), deviceStatisticChannelState.getErrorResolvedState(0));
        updateState(channel(DEV_STAT_LAST_ERROR_DATE), deviceStatisticChannelState.getErrorDateState(0));
        updateState(channel(DEV_STAT_LAST_ERROR_TYPE), deviceStatisticChannelState.getErrorTypeState(0));

        updateState(channel(DEV_STAT_USAGE_WATER_DATE_1), deviceStatisticChannelState.getWaterUsageDateState(0));
        updateState(channel(DEV_STAT_USAGE_WATER_VALUE_1), deviceStatisticChannelState.getWaterUsageValueState(0));
        updateState(channel(DEV_STAT_USAGE_WATER_DATE_2), deviceStatisticChannelState.getWaterUsageDateState(1));
        updateState(channel(DEV_STAT_USAGE_WATER_VALUE_2), deviceStatisticChannelState.getWaterUsageValueState(1));
        updateState(channel(DEV_STAT_USAGE_WATER_DATE_3), deviceStatisticChannelState.getWaterUsageDateState(2));
        updateState(channel(DEV_STAT_USAGE_WATER_VALUE_3), deviceStatisticChannelState.getWaterUsageValueState(2));

        updateState(channel(DEV_STAT_USAGE_SALT_DATE_1), deviceStatisticChannelState.getSaltUsageDateState(0));
        updateState(channel(DEV_STAT_USAGE_SALT_VALUE_1), deviceStatisticChannelState.getSaltUsageValueState(0));
        updateState(channel(DEV_STAT_USAGE_SALT_DATE_2), deviceStatisticChannelState.getSaltUsageDateState(1));
        updateState(channel(DEV_STAT_USAGE_SALT_VALUE_2), deviceStatisticChannelState.getSaltUsageValueState(1));
        updateState(channel(DEV_STAT_USAGE_SALT_DATE_3), deviceStatisticChannelState.getSaltUsageDateState(2));
        updateState(channel(DEV_STAT_USAGE_SALT_VALUE_3), deviceStatisticChannelState.getSaltUsageValueState(2));

        calculateAndUpdateSaltLevelState(deviceStatistic);
    }

    private synchronized void calculateAndUpdateSaltLevelState(final DeviceStatistic deviceStatistic) {

        /*
         * only process salt usage once, therefore check the salt usage date
         * with the last processed date.
         *
         * As the day changes, salt usages changes, but the initial salt usage (of the new day)
         * may be 0 at the beginning. So we have to ensure to take this in consideration during
         * salt usage calculation.
         *
         * If we have an accounted salt usage of the current day with 0, we will check if the
         * new value is > zero and take this in our calculation. Once the accounted salt usage
         * is > 0, we will consider it.
         */

        /*
         * We have a salt usage > 0, so we check if we already considered it, otherwise we
         * move on. This way we circumvent the fact, that initial salt usage (of the day) is
         * always 0
         */

        /*
         * We also want to consider the fact, that the binding may have been shut down
         * for a few days, so let's take this in our calculation.
         *
         * We have to reverse the list, since the last entry is the oldest one.
         */
        final List<DeviceStatistic.SaltUsage> reversedSaltUsage = deviceStatistic.getSalt();
        Collections.reverse(reversedSaltUsage);
        for (DeviceStatistic.SaltUsage saltUsage : reversedSaltUsage) {

            /*
             * We have a salt usage > 0, so we check if we already considered it, otherwise we
             * move on. This way we circumvent the fact, that initial salt usage (of the day) is
             * always 0.
             *
             * If the value is greater than 0, we check if the day is after the already processed
             * values, so we ensure not to account it twice.
             */
            if (saltUsage.getValue() > 0 && (softenerData.getLastAccountedSaltUsageDate() == null
                    || saltUsage.getDate().isAfter(softenerData.getLastAccountedSaltUsageDate()))) {
                logger.trace("current salt level is {}", softenerData.getCurrentCapacityInGram());
                logger.trace("calculate new salt level for date {} and salt usage of {}", saltUsage.getDate(),
                        saltUsage.getValue());

                final int newSaltLevelInGram = softenerData.getCurrentCapacityInGram() - saltUsage.getValue();
                softenerData.setLastAccountedSaltUsageDate(saltUsage.getDate());
                softenerData.setCurrentCapacityInGram(newSaltLevelInGram);
                storage.put(SOFTENER_DATA, softenerData);

                logger.trace("new salt level is {}", softenerData.getCurrentCapacityInGram());
            } else if (saltUsage.getValue() == 0) {
                logger.trace("salt usage for date {} was reported with 0 and will be ignored.", saltUsage.getDate());
            } else {
                logger.trace("salt usage for date {} was reported with {} and is already accounted for.",
                        saltUsage.getDate(), saltUsage.getValue());
            }
        }

        updateState(channel(DYN_SALT_LEVEL), ChannelTypeUtil.intToState(softenerData.getCurrentCapacityInGram()));
        updateState(channel(DYN_LAST_ACCOUNTED_SALT_USAGE_DATE),
                ChannelTypeUtil.localDateToState(softenerData.getLastAccountedSaltUsageDate(), ZoneId.systemDefault()));
    }

    private void updateState(DeviceInformation deviceInformation) {
        DeviceInformationChannelState deviceInformationChannelState = new DeviceInformationChannelState(
                deviceInformation);
        updateState(channel(DEV_INF_RESIDUAL_CAPACITY_LIMIT), deviceInformationChannelState.getPrescaplimitState());
        updateState(channel(DEV_INF_SET_POINT_CURRENT), deviceInformationChannelState.getPcurrentState());
        updateState(channel(DEV_INF_CHARGE), deviceInformationChannelState.getPloadState());
        updateState(channel(DEV_INF_FORCED_REGENERATION_INTERVAL),
                deviceInformationChannelState.getPforcedregdistState());
        updateState(channel(DEV_INF_MAINTENANCE_INTERVAL), deviceInformationChannelState.getPmaintintState());
        updateState(channel(DEV_INF_REGENERATION_VALVE_END_FREQUENCY),
                deviceInformationChannelState.getPfreqregvalveState());
        updateState(channel(DEV_INF_BLENDING_VALVE_END_FREQUENCY),
                deviceInformationChannelState.getPfreqblendvalveState());
        updateState(channel(DEV_INF_LED_BRIGHTNESS), deviceInformationChannelState.getPledbrightState());
        updateState(channel(DEV_INF_ABSORBER_TREATMENT_VOLUME), deviceInformationChannelState.getPvolumeState());
        updateState(channel(DEV_INF_RAW_WATER_HARDNESS), deviceInformationChannelState.getPrawhardState());
        updateState(channel(DEV_INF_SOFT_WATER_HARDNESS), deviceInformationChannelState.getPsetsoftState());
        updateState(channel(DEV_INF_SOFT_WATER_METER_PULSE_RATE),
                deviceInformationChannelState.getPpratesoftwaterState());
        updateState(channel(DEV_INF_BLENDING_WATER_METER_PULSE_RATE),
                deviceInformationChannelState.getPprateblendingState());
        updateState(channel(DEV_INF_REGENERATION_WATER_METER_PULSE_RATE),
                deviceInformationChannelState.getPprateregwaterState());
        updateState(channel(DEV_INF_CAPACITY_FIGURE_MONDAY), deviceInformationChannelState.getPsetcapmoState());
        updateState(channel(DEV_INF_CAPACITY_FIGURE_TUESDAY), deviceInformationChannelState.getPsetcaptuState());
        updateState(channel(DEV_INF_CAPACITY_FIGURE_WEDNESDAY), deviceInformationChannelState.getPsetcapweState());
        updateState(channel(DEV_INF_CAPACITY_FIGURE_THURSDAY), deviceInformationChannelState.getPsetcapthState());
        updateState(channel(DEV_INF_CAPACITY_FIGURE_FRIDAY), deviceInformationChannelState.getPsetcapfrState());
        updateState(channel(DEV_INF_CAPACITY_FIGURE_SATURDAY), deviceInformationChannelState.getPsetcapsaState());
        updateState(channel(DEV_INF_CAPACITY_FIGURE_SUNDAY), deviceInformationChannelState.getPsetcapsuState());
        updateState(channel(DEV_INF_NOMINAL_FLOW_RATE), deviceInformationChannelState.getPnomflowState());
        updateState(channel(DEV_INF_PPRESSUREREG), deviceInformationChannelState.getPpressureregState());
        updateState(channel(DEV_INF_REGENERATION_MONITORING_TIME),
                deviceInformationChannelState.getPmonregmeterState());
        updateState(channel(DEV_INF_SALTING_MONITORING_TIME), deviceInformationChannelState.getPmonsaltingState());
        updateState(channel(DEV_INF_SLOW_RINSE), deviceInformationChannelState.getPrinsingState());
        updateState(channel(DEV_INF_BACKWASH), deviceInformationChannelState.getPbackwashState());
        updateState(channel(DEV_INF_WASHING_OUT), deviceInformationChannelState.getPwashingoutState());
        updateState(channel(DEV_INF_FILLING_VOLUME_SMALLEST_CAP_MIN),
                deviceInformationChannelState.getPminvolmincapState());
        updateState(channel(DEV_INF_FILLING_VOLUME_SMALLEST_CAP_MAX),
                deviceInformationChannelState.getPmaxvolmincapState());
        updateState(channel(DEV_INF_FILLING_VOLUME_LARGEST_CAP_MIN),
                deviceInformationChannelState.getPminvolmaxcapState());
        updateState(channel(DEV_INF_FILLING_VOLUME_LARGEST_CAP_MAX),
                deviceInformationChannelState.getPmaxvolmaxcapState());
        updateState(channel(DEV_INF_DISINFECT_MAX_DURATION), deviceInformationChannelState.getPmaxdurdisinfectState());
        updateState(channel(DEV_INF_REGENERATION_REMAINING_TIME_MAX),
                deviceInformationChannelState.getPmaxresdurregState());
        updateState(channel(DEV_INF_MONITOR_BLENDING), deviceInformationChannelState.getPmonblendState());
        updateState(channel(DEV_INF_SYSTEM_OVERLOADED), deviceInformationChannelState.getPoverloadState());
        updateState(channel(DEV_INF_REGENERATION_VALVE_2_END_FREQUENCY),
                deviceInformationChannelState.getPfreqregvalve2State());
        updateState(channel(DEV_INF_ALLOW_EMAIL_NOTIFICATION), deviceInformationChannelState.getPallowemailState());
        updateState(channel(DEV_INF_ALLOW_PUSH_NOTIFICATION),
                deviceInformationChannelState.getPallowpushnotificationState());
        updateState(channel(DEV_INF_DLST_AUTO), deviceInformationChannelState.getPdlstautoState());
        updateState(channel(DEV_INF_NTP_SYNC), deviceInformationChannelState.getPntpsyncState());
        updateState(channel(DEV_INF_FUNCTION_FAULT_SIGNAL_CONTACT), deviceInformationChannelState.getPcfcontactState());
        updateState(channel(DEV_INF_KNX), deviceInformationChannelState.getPknxState());
        updateState(channel(DEV_INF_MONITOR_NOMINAL_FLOW), deviceInformationChannelState.getPmonflowState());
        updateState(channel(DEV_INF_MONITOR_DISINFECTION), deviceInformationChannelState.getPmondisinfState());
        updateState(channel(DEV_INF_PRE_ALARM_SALT_SUPPLY_LED), deviceInformationChannelState.getPledatsaltpreState());
        updateState(channel(DEV_INF_AUDIO_SIGNAL), deviceInformationChannelState.getPbuzzerState());
        updateState(channel(DEV_INF_OPERATION_MODE), deviceInformationChannelState.getPmodeState());
        updateState(channel(DEV_INF_OPERATION_MODE_MONDAY), deviceInformationChannelState.getPmodemoState());
        updateState(channel(DEV_INF_OPERATION_MODE_TUESDAY), deviceInformationChannelState.getPmodetuState());
        updateState(channel(DEV_INF_OPERATION_MODE_WEDNESDAY), deviceInformationChannelState.getPmodeweState());
        updateState(channel(DEV_INF_OPERATION_MODE_THURSDAY), deviceInformationChannelState.getPmodethState());
        updateState(channel(DEV_INF_OPERATION_MODE_FRIDAY), deviceInformationChannelState.getPmodefrState());
        updateState(channel(DEV_INF_OPERATION_MODE_SATURDAY), deviceInformationChannelState.getPmodesaState());
        updateState(channel(DEV_INF_OPERATION_MODE_SUNDAY), deviceInformationChannelState.getPmodesuState());
        updateState(channel(DEV_INF_LANGUAGE), deviceInformationChannelState.getPlanguageState());
        updateState(channel(DEV_INF_HARDNESS_UNIT), deviceInformationChannelState.getPhunitState());
        updateState(channel(DEV_INF_REGENERATION_TIME), deviceInformationChannelState.getPregmodeState());
        updateState(channel(DEV_INF_PROGRAMMABLE_OUTPUT_FUNCTION), deviceInformationChannelState.getPprogoutState());
        updateState(channel(DEV_INF_PROGRAMMABLE_INPUT_FUNCTION), deviceInformationChannelState.getPproginState());
        updateState(channel(DEV_INF_POWER_FAILURE_REACTION), deviceInformationChannelState.getPpowerfailState());
        updateState(channel(DEV_INF_DISINFECTION_STATE), deviceInformationChannelState.getPmodedesinfState());
        updateState(channel(DEV_INF_LED_STATE), deviceInformationChannelState.getPledState());
        updateState(channel(DEV_INF_AUDION_SIGNAL_RELEASE_FROM), deviceInformationChannelState.getPbuzzfromState());
        updateState(channel(DEV_INF_AUDION_SIGNAL_RELEASE_TO), deviceInformationChannelState.getPbuzztoState());
        updateState(channel(DEV_INF_MAINTENANCE_COMPANY_EMAIL), deviceInformationChannelState.getPmailadressState());
        updateState(channel(DEV_INF_MAINTENANCE_COMPANY_NAME), deviceInformationChannelState.getPnameState());
        updateState(channel(DEV_INF_MAINTENANCE_COMPANY_PHONE), deviceInformationChannelState.getPtelnrState());
        updateState(channel(DEV_INF_REGENERATION_TIME_MONDAY_1), deviceInformationChannelState.getPregmo1State());
        updateState(channel(DEV_INF_REGENERATION_TIME_MONDAY_2), deviceInformationChannelState.getPregmo2State());
        updateState(channel(DEV_INF_REGENERATION_TIME_MONDAY_3), deviceInformationChannelState.getPregmo3State());
        updateState(channel(DEV_INF_REGENERATION_TIME_TUESDAY_1), deviceInformationChannelState.getPregtu1State());
        updateState(channel(DEV_INF_REGENERATION_TIME_TUESDAY_2), deviceInformationChannelState.getPregtu2State());
        updateState(channel(DEV_INF_REGENERATION_TIME_TUESDAY_3), deviceInformationChannelState.getPregtu3State());
        updateState(channel(DEV_INF_REGENERATION_TIME_WEDNESDAY_1), deviceInformationChannelState.getPregwe1State());
        updateState(channel(DEV_INF_REGENERATION_TIME_WEDNESDAY_2), deviceInformationChannelState.getPregwe2State());
        updateState(channel(DEV_INF_REGENERATION_TIME_WEDNESDAY_3), deviceInformationChannelState.getPregwe3State());
        updateState(channel(DEV_INF_REGENERATION_TIME_THURSDAY_1), deviceInformationChannelState.getPregth1State());
        updateState(channel(DEV_INF_REGENERATION_TIME_THURSDAY_2), deviceInformationChannelState.getPregth2State());
        updateState(channel(DEV_INF_REGENERATION_TIME_THURSDAY_3), deviceInformationChannelState.getPregth3State());
        updateState(channel(DEV_INF_REGENERATION_TIME_FRIDAY_1), deviceInformationChannelState.getPregfr1State());
        updateState(channel(DEV_INF_REGENERATION_TIME_FRIDAY_2), deviceInformationChannelState.getPregfr2State());
        updateState(channel(DEV_INF_REGENERATION_TIME_FRIDAY_3), deviceInformationChannelState.getPregfr3State());
        updateState(channel(DEV_INF_REGENERATION_TIME_SATURDAY_1), deviceInformationChannelState.getPregsa1State());
        updateState(channel(DEV_INF_REGENERATION_TIME_SATURDAY_2), deviceInformationChannelState.getPregsa2State());
        updateState(channel(DEV_INF_REGENERATION_TIME_SATURDAY_3), deviceInformationChannelState.getPregsa3State());
        updateState(channel(DEV_INF_REGENERATION_TIME_SUNDAY_1), deviceInformationChannelState.getPregsu1State());
        updateState(channel(DEV_INF_REGENERATION_TIME_SUNDAY_2), deviceInformationChannelState.getPregsu2State());
        updateState(channel(DEV_INF_REGENERATION_TIME_SUNDAY_3), deviceInformationChannelState.getPregsu3State());
    }

    /**
     * Creates a {@link ChannelUID} from the given name.
     *
     * @param name channel name
     * @return {@link ChannelUID}
     */
    private ChannelUID channel(String name) {
        return new ChannelUID(getThing().getUID(), name);
    }
}
