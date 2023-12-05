/**
 * Copyright (c) 2010-2023 Contributors to the openHAB project
 *
 * <p>See the NOTICE file(s) distributed with this work for additional information.
 *
 * <p>This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0
 *
 * <p>SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.gruenbeckcloud.internal;

import java.util.*;
import java.util.regex.Pattern;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.binding.gruenbeckcloud.internal.handler.channel.PowerMode;
import org.openhab.binding.gruenbeckcloud.internal.handler.channel.RegenerationMode;
import org.openhab.core.thing.ThingTypeUID;

/**
 * The {@link GruenbeckCloudBindingConstants} class defines common constants, which are used across
 * the whole binding.
 *
 * @author Mario Aerni - Initial contribution
 */
@NonNullByDefault
public class GruenbeckCloudBindingConstants {

    public static final int SOFTENER_SALT_CAPACITY_DEFAULT = 25000;
    private static final String BINDING_ID = "gruenbeckcloud";

    public static final String SOFTENER_REPRESENTATION_PROPERTY = "serialNumber";

    // List of all Thing Type UIDs
    public static final ThingTypeUID THING_TYPE_SOFTENER = new ThingTypeUID(BINDING_ID, "softener");
    public static final ThingTypeUID THING_TYPE_BRIDGE = new ThingTypeUID(BINDING_ID, "bridge");
    public static final Set<ThingTypeUID> DISCOVERABLE_THING_TYPE_UIDS = Collections.singleton(THING_TYPE_SOFTENER);

    // List of all supported softener series
    public static final String SOFTENER_SOFTLI_Q_D_SERIES = "softliQ.D";
    public static final List<String> SUPPORTED_SOFTENER = List.of(SOFTENER_SOFTLI_Q_D_SERIES);

    // List of all configuration properties
    public static final String SOFTENER_ID = "id";
    public static final String SOFTENER_SERIAL_NUMBER = "serialNumber";
    public static final String SOFTENER_SERIES = "series";
    public static final String SOFTENER_NAME = "name";
    public static final String SOFTENER_SALT_CAPACITY = "saltCapacity";

    // List of all Channel ids

    // device information
    public static final String DEV_INF_ALLOW_EMAIL_NOTIFICATION = "pallowemail";
    public static final String DEV_INF_ALLOW_PUSH_NOTIFICATION = "pallowpushnotification";
    public static final String DEV_INF_DLST_AUTO = "pdlstauto";
    public static final String DEV_INF_NTP_SYNC = "pntpsync";
    public static final String DEV_INF_FUNCTION_FAULT_SIGNAL_CONTACT = "pcfcontact";
    public static final String DEV_INF_KNX = "pknx";
    public static final String DEV_INF_MONITOR_NOMINAL_FLOW = "pmonflow";
    public static final String DEV_INF_MONITOR_DISINFECTION = "pmondisinf";
    public static final String DEV_INF_PRE_ALARM_SALT_SUPPLY_LED = "pledatsaltpre";
    public static final String DEV_INF_AUDIO_SIGNAL = "pbuzzer";
    public static final String DEV_INF_RESIDUAL_CAPACITY_LIMIT = "prescaplimit";
    public static final String DEV_INF_SET_POINT_CURRENT = "pcurrent";
    public static final String DEV_INF_CHARGE = "pload";
    public static final String DEV_INF_FORCED_REGENERATION_INTERVAL = "pforcedregdist";
    public static final String DEV_INF_MAINTENANCE_INTERVAL = "pmaintint";
    public static final String DEV_INF_REGENERATION_VALVE_END_FREQUENCY = "pfreqregvalve";
    public static final String DEV_INF_BLENDING_VALVE_END_FREQUENCY = "pfreqblendvalve";
    public static final String DEV_INF_LED_BRIGHTNESS = "pledbright";
    public static final String DEV_INF_ABSORBER_TREATMENT_VOLUME = "pvolume";
    public static final String DEV_INF_RAW_WATER_HARDNESS = "prawhard";
    public static final String DEV_INF_SOFT_WATER_HARDNESS = "psetsoft";
    public static final String DEV_INF_SOFT_WATER_METER_PULSE_RATE = "ppratesoftwater";
    public static final String DEV_INF_BLENDING_WATER_METER_PULSE_RATE = "pprateblending";
    public static final String DEV_INF_REGENERATION_WATER_METER_PULSE_RATE = "pprateregwater";
    public static final String DEV_INF_CAPACITY_FIGURE_MONDAY = "psetcapmo";
    public static final String DEV_INF_CAPACITY_FIGURE_TUESDAY = "psetcaptu";
    public static final String DEV_INF_CAPACITY_FIGURE_WEDNESDAY = "psetcapwe";
    public static final String DEV_INF_CAPACITY_FIGURE_THURSDAY = "psetcapth";
    public static final String DEV_INF_CAPACITY_FIGURE_FRIDAY = "psetcapfr";
    public static final String DEV_INF_CAPACITY_FIGURE_SATURDAY = "psetcapsa";
    public static final String DEV_INF_CAPACITY_FIGURE_SUNDAY = "psetcapsu";
    public static final String DEV_INF_NOMINAL_FLOW_RATE = "pnomflow";
    public static final String DEV_INF_PPRESSUREREG = "ppressurereg";
    public static final String DEV_INF_REGENERATION_MONITORING_TIME = "pmonregmeter";
    public static final String DEV_INF_SALTING_MONITORING_TIME = "pmonsalting";
    public static final String DEV_INF_SLOW_RINSE = "prinsing";
    public static final String DEV_INF_BACKWASH = "pbackwash";
    public static final String DEV_INF_WASHING_OUT = "pwashingout";
    public static final String DEV_INF_FILLING_VOLUME_SMALLEST_CAP_MIN = "pminvolmincap";
    public static final String DEV_INF_FILLING_VOLUME_SMALLEST_CAP_MAX = "pmaxvolmincap";
    public static final String DEV_INF_FILLING_VOLUME_LARGEST_CAP_MIN = "pminvolmaxcap";
    public static final String DEV_INF_FILLING_VOLUME_LARGEST_CAP_MAX = "pmaxvolmaxcap";
    public static final String DEV_INF_DISINFECT_MAX_DURATION = "pmaxdurdisinfect";
    public static final String DEV_INF_REGENERATION_REMAINING_TIME_MAX = "pmaxresdurreg";
    public static final String DEV_INF_AUDION_SIGNAL_RELEASE_FROM = "pbuzzfrom";
    public static final String DEV_INF_AUDION_SIGNAL_RELEASE_TO = "pbuzzto";
    public static final String DEV_INF_MAINTENANCE_COMPANY_EMAIL = "pmailadress";
    public static final String DEV_INF_MAINTENANCE_COMPANY_NAME = "pname";
    public static final String DEV_INF_MAINTENANCE_COMPANY_PHONE = "ptelnr";
    public static final String DEV_INF_OPERATION_MODE = "pmode";
    public static final String DEV_INF_OPERATION_MODE_MONDAY = "pmodemo";
    public static final String DEV_INF_OPERATION_MODE_TUESDAY = "pmodetu";
    public static final String DEV_INF_OPERATION_MODE_WEDNESDAY = "pmodewe";
    public static final String DEV_INF_OPERATION_MODE_THURSDAY = "pmodeth";
    public static final String DEV_INF_OPERATION_MODE_FRIDAY = "pmodefr";
    public static final String DEV_INF_OPERATION_MODE_SATURDAY = "pmodesa";
    public static final String DEV_INF_OPERATION_MODE_SUNDAY = "pmodesu";
    public static final String DEV_INF_LANGUAGE = "planguage";
    public static final String DEV_INF_HARDNESS_UNIT = "phunit";
    public static final String DEV_INF_REGENERATION_TIME = "pregmode";
    public static final String DEV_INF_PROGRAMMABLE_OUTPUT_FUNCTION = "pprogout";
    public static final String DEV_INF_PROGRAMMABLE_INPUT_FUNCTION = "pprogin";
    public static final String DEV_INF_POWER_FAILURE_REACTION = "ppowerfail";
    public static final String DEV_INF_DISINFECTION_STATE = "pmodedesinf";
    public static final String DEV_INF_LED_STATE = "pled";
    public static final String DEV_INF_REGENERATION_TIME_MONDAY_1 = "pregmo1";
    public static final String DEV_INF_REGENERATION_TIME_MONDAY_2 = "pregmo2";
    public static final String DEV_INF_REGENERATION_TIME_MONDAY_3 = "pregmo3";
    public static final String DEV_INF_REGENERATION_TIME_TUESDAY_1 = "pregtu1";
    public static final String DEV_INF_REGENERATION_TIME_TUESDAY_2 = "pregtu2";
    public static final String DEV_INF_REGENERATION_TIME_TUESDAY_3 = "pregtu3";
    public static final String DEV_INF_REGENERATION_TIME_WEDNESDAY_1 = "pregwe1";
    public static final String DEV_INF_REGENERATION_TIME_WEDNESDAY_2 = "pregwe2";
    public static final String DEV_INF_REGENERATION_TIME_WEDNESDAY_3 = "pregwe3";
    public static final String DEV_INF_REGENERATION_TIME_THURSDAY_1 = "pregth1";
    public static final String DEV_INF_REGENERATION_TIME_THURSDAY_2 = "pregth2";
    public static final String DEV_INF_REGENERATION_TIME_THURSDAY_3 = "pregth3";
    public static final String DEV_INF_REGENERATION_TIME_FRIDAY_1 = "pregfr1";
    public static final String DEV_INF_REGENERATION_TIME_FRIDAY_2 = "pregfr2";
    public static final String DEV_INF_REGENERATION_TIME_FRIDAY_3 = "pregfr3";
    public static final String DEV_INF_REGENERATION_TIME_SATURDAY_1 = "pregsa1";
    public static final String DEV_INF_REGENERATION_TIME_SATURDAY_2 = "pregsa2";
    public static final String DEV_INF_REGENERATION_TIME_SATURDAY_3 = "pregsa3";
    public static final String DEV_INF_REGENERATION_TIME_SUNDAY_1 = "pregsu1";
    public static final String DEV_INF_REGENERATION_TIME_SUNDAY_2 = "pregsu2";
    public static final String DEV_INF_REGENERATION_TIME_SUNDAY_3 = "pregsu3";
    public static final String DEV_INF_MONITOR_BLENDING = "pmonblend";
    public static final String DEV_INF_SYSTEM_OVERLOADED = "poverload";
    public static final String DEV_INF_REGENERATION_VALVE_2_END_FREQUENCY = "pfreqregvalve2";
    // end device information

    // device statistics
    public static final String DEV_STAT_HARDWARE_VERSION = "hardwareVersion";
    public static final String DEV_STAT_LAST_SERVICE = "lastService";
    public static final String DEV_STAT_OPERATION_MODE = "mode";
    public static final String DEV_STAT_NEXT_REGENERATION = "nextRegeneration";
    public static final String DEV_STAT_NOMINAL_FLOW = "nominalFlow";
    public static final String DEV_STAT_HARDNESS_RAW_WATER = "rawWater";
    public static final String DEV_STAT_HARDNESS_SOFT_WATTER = "softWater";
    public static final String DEV_STAT_SOFTWARE_VERSION = "softwareVersion";
    public static final String DEV_STAT_TIMEZONE = "timeZone";
    public static final String DEV_STAT_HARDNESS_UNIT = "unit";
    public static final String DEV_STAT_STARTUP_DATE = "startup";
    public static final String DEV_STAT_SOFTENER_MODEL_TYPE = "type";
    public static final String DEV_STAT_HAS_ERROR = "hasError";
    public static final String DEV_STAT_SOFTENER_REGISTERED = "register";

    public static final String DEV_STAT_LAST_ERROR_MESSAGE = "last_error_message";
    public static final String DEV_STAT_LAST_ERROR_RESOLVED = "last_error_resolved";
    public static final String DEV_STAT_LAST_ERROR_DATE = "last_error_date";
    public static final String DEV_STAT_LAST_ERROR_TYPE = "last_error_type";

    public static final String DEV_STAT_USAGE_SALT_DATE_1 = "usage_date_salt_1";
    public static final String DEV_STAT_USAGE_SALT_VALUE_1 = "usage_value_salt_1";
    public static final String DEV_STAT_USAGE_SALT_DATE_2 = "usage_date_salt_2";
    public static final String DEV_STAT_USAGE_SALT_VALUE_2 = "usage_value_salt_2";
    public static final String DEV_STAT_USAGE_SALT_DATE_3 = "usage_date_salt_3";
    public static final String DEV_STAT_USAGE_SALT_VALUE_3 = "usage_value_salt_3";

    public static final String DEV_STAT_USAGE_WATER_DATE_1 = "usage_date_water_1";
    public static final String DEV_STAT_USAGE_WATER_VALUE_1 = "usage_value_water_1";
    public static final String DEV_STAT_USAGE_WATER_DATE_2 = "usage_date_water_2";
    public static final String DEV_STAT_USAGE_WATER_VALUE_2 = "usage_value_water_2";
    public static final String DEV_STAT_USAGE_WATER_DATE_3 = "usage_date_water_3";
    public static final String DEV_STAT_USAGE_WATER_VALUE_3 = "usage_value_water_3";
    // end device statistics

    // device current
    public static final String DEV_CURR_IBUILTINDEV = "ibuiltindev";
    public static final String DEV_CURR_ISNCU = "isncu";
    public static final String DEV_CURR_PERCENT_TILL_REGISTRATION_1 = "mregpercent1";
    public static final String DEV_CURR_PERCENT_TILL_REGISTRATION_2 = "mregpercent2";
    public static final String DEV_CURR_TIME_LEFT_CURRENT_REGISTRATION_STEP = "mremregstep";
    public static final String DEV_CURR_REGISTRATION_STEP = "mregstatus";
    public static final String DEV_CURR_RESIDUAL_CAPACITY_1 = "mresidcap1";
    public static final String DEV_CURR_RESIDUAL_CAPACITY_2 = "mresidcap2";
    public static final String DEV_CURR_VOLUME_SOFT_WATER_EXCHANGER_1 = "mrescapa1";
    public static final String DEV_CURR_VOLUME_SOFT_WATER_EXCHANGER_2 = "mrescapa2";
    public static final String DEV_CURR_DAYS_TILL_NEXT_MAINTENANCE = "mmaint";
    public static final String DEV_CURR_FLOW_RATE_EXCHANGER_1 = "mflow1";
    public static final String DEV_CURR_FLOW_RATE_EXCHANGER_2 = "mflow2";
    public static final String DEV_CURR_REGENERATION_FLOW_RATE_EXCHANGER_1 = "mflowreg1";
    public static final String DEV_CURR_REGENERATION_FLOW_RATE_EXCHANGER_2 = "mflowreg2";
    public static final String DEV_CURR_BLENDING_FLOW_RATE = "mflowblend";
    public static final String DEV_CURR_REGENERATION_STEP_INDICATION_VALVE_1 = "mstep1";
    public static final String DEV_CURR_REGENERATION_STEP_INDICATION_VALVE_2 = "mstep2";
    public static final String DEV_CURR_CURRENT_CHLORINE = "mcurrent";
    public static final String DEV_CURR_ABSORBER_REMAINING_AMOUNT_OF_WATER = "mreswatadmod";
    public static final String DEV_CURR_SALT_REACH_IN_DAYS = "msaltrange";
    // end device current

    // device currSlow
    public static final String DEV_CURRSLOW_REGENERATION_COUNTER = "mcountreg";
    public static final String DEV_CURRSLOW_SOFT_WATER_VOLUME_EXCHANGER_1 = "mcountwater1";
    public static final String DEV_CURRSLOW_SOFT_WATER_VOLUME_EXCHANGER_2 = "mcountwater2";
    public static final String DEV_CURRSLOW_MAKE_UP_WATER_VOLUME = "mcountwatertank";
    public static final String DEV_CURRSLOW_SALT_CONSUMPTION = "msaltusage";
    public static final String DEV_CURRSLOW_FLOW = "mflowexc";
    public static final String DEV_CURRSLOW_FLOW_EXCHANGER_1 = "mflowexc1reg2";
    public static final String DEV_CURRSLOW_FLOW_EXCHANGER_2 = "mflowexc2reg1";
    public static final String DEV_CURRSLOW_ABSORBER_EXHAUSTED = "mlifeadsorb";
    public static final String DEV_CURRSLOW_ACTUAL_SOFT_WATER_HARDNESS = "mhardsoftw";
    public static final String DEV_CURRSLOW_CAPACITY_FIGURE = "mcapacity";
    public static final String DEV_CURRSLOW_MAVERAGE = "maverage";
    public static final String DEV_CURRSLOW_MSTDDEV = "mstddev";
    public static final String DEV_CURRSLOW_MMAX = "mmax";
    public static final String DEV_CURRSLOW_MPRESS = "mpress";
    public static final String DEV_CURRSLOW_MTEMP = "mtemp";
    public static final String DEV_CURRSLOW_FLOW_RATE_PEAK_VALUE = "mflowmax";
    public static final String DEV_CURRSLOW_PEAK_VALUE_EXCHANGER_1 = "mflowmax1reg2";
    public static final String DEV_CURRSLOW_PEAK_VALUE_EXCHANGER_2 = "mflowmax2reg1";
    public static final String DEV_CURRSLOW_LAST_REGENERATION_EXCHANGER_1 = "mendreg1";
    public static final String DEV_CURRSLOW_LAST_REGENERATION_EXCHANGER_2 = "mendreg2";
    // end device currSlow

    // binding relevant
    public static final String DYN_REQUEST_REGENERATION = "requestRegeneration";

    /** Regeneration progress % */
    public static final String DYN_REGENERATION_STATUS = "regenerationStatus";

    public static final String DYN_REGENERATION_PROGRESS_ID_RAW = "regenerationProgressIdRaw";
    public static final String DYN_REGENERATION_PROGRESS_DESCRIPTION = "regenerationProgressDescription";

    /** Regeneration remaining in the current regeneration step */
    public static final String DYN_REGENERATION_REMAINING_IN_CURRENT_STEP = "regenerationRemainingInCurrentStep";

    public static final String DYN_SALT_LEVEL = "saltLevel";
    public static final String DYN_LAST_ACCOUNTED_SALT_USAGE_DATE = "lastAccountedSaltUsageDate";
    public static final String DYN_RESET_SALT_LEVEL = "resetSaltLevel";
    public static final String DYN_REGENERATION_ACTIVE = "regenerationActive";
    // end binding relevant

    // channel constants
    // ----
    public static final List<String> OPERATION_MODE_COMMANDS = List.of(DEV_INF_OPERATION_MODE,
            DEV_INF_OPERATION_MODE_MONDAY, DEV_INF_OPERATION_MODE_TUESDAY, DEV_INF_OPERATION_MODE_WEDNESDAY,
            DEV_INF_OPERATION_MODE_THURSDAY, DEV_INF_OPERATION_MODE_FRIDAY, DEV_INF_OPERATION_MODE_SATURDAY,
            DEV_INF_OPERATION_MODE_SUNDAY);

    public static final List<String> REGENERATION_TIME_SETTING_COMMANDS = List.of(DEV_INF_REGENERATION_TIME_MONDAY_1,
            DEV_INF_REGENERATION_TIME_MONDAY_2, DEV_INF_REGENERATION_TIME_MONDAY_3, DEV_INF_REGENERATION_TIME_TUESDAY_1,
            DEV_INF_REGENERATION_TIME_TUESDAY_2, DEV_INF_REGENERATION_TIME_TUESDAY_3,
            DEV_INF_REGENERATION_TIME_WEDNESDAY_1, DEV_INF_REGENERATION_TIME_WEDNESDAY_2,
            DEV_INF_REGENERATION_TIME_WEDNESDAY_3, DEV_INF_REGENERATION_TIME_THURSDAY_1,
            DEV_INF_REGENERATION_TIME_THURSDAY_2, DEV_INF_REGENERATION_TIME_THURSDAY_3,
            DEV_INF_REGENERATION_TIME_FRIDAY_1, DEV_INF_REGENERATION_TIME_FRIDAY_2, DEV_INF_REGENERATION_TIME_FRIDAY_3,
            DEV_INF_REGENERATION_TIME_SATURDAY_1, DEV_INF_REGENERATION_TIME_SATURDAY_2,
            DEV_INF_REGENERATION_TIME_SATURDAY_3, DEV_INF_REGENERATION_TIME_SUNDAY_1,
            DEV_INF_REGENERATION_TIME_SUNDAY_2, DEV_INF_REGENERATION_TIME_SUNDAY_3);

    public static final List<Integer> AVAILABLE_POWER_MODES = List.of(PowerMode.ECO.intValue(),
            PowerMode.COMFORT.intValue(), PowerMode.POWER.intValue());
    public static final List<Integer> AVAILABLE_REGENERATION_MODES = List.of(RegenerationMode.AUTOMATIC.intValue(),
            RegenerationMode.FIXED.intValue());
    public static final Pattern HOURS_AND_MINUTES_PATTERN = Pattern.compile("(?:[01]?[0-9]|2[0-3]):[0-5]?[0-9]?$");
    // ----
}
