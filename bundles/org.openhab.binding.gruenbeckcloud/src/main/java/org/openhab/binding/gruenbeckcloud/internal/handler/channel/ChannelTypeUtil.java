package org.openhab.binding.gruenbeckcloud.internal.handler.channel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.openhab.core.library.types.*;
import org.openhab.core.types.State;
import org.openhab.core.types.UnDefType;

/**
 * The {@link ChannelTypeUtil} class holds various channel values conversion methods.
 *
 * @author Mario Aerni - Initial contribution
 */
public class ChannelTypeUtil {
    public static State doubleToState(Double value) {
        return value != null ? new DecimalType(value) : UnDefType.NULL;
    }

    public static State booleanToState(Boolean value) {
        return value != null ? OnOffType.from(value) : UnDefType.NULL;
    }

    public static State intToState(Integer value) {
        return value != null ? new DecimalType(value) : UnDefType.NULL;
    }

    public static State stringToState(String value) {
        return value != null ? new StringType(value) : UnDefType.NULL;
    }

    public static State localDateToState(LocalDate value, ZoneId zoneId) {
        return value != null ? new DateTimeType(value.atStartOfDay(zoneId)) : UnDefType.NULL;
    }

    public static State localDateTimeToState(LocalDateTime value, ZoneId zoneId) {
        return value != null ? new DateTimeType(ZonedDateTime.of(value, zoneId)) : UnDefType.NULL;
    }

    public static State intToPercentageState(Integer value) {
        return value != null ? new PercentType(value) : UnDefType.NULL;
    }
}
