package org.openhab.binding.gruenbeckcloud.internal.handler.channel;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@link PowerMode} enum represents the power mode of the softeners.
 *
 * @author Mario Aerni - Initial contribution
 */
public enum PowerMode {
    ECO(1),
    COMFORT(2),
    POWER(3);

    private final int intValue;
    private static final Map<Integer, PowerMode> map = new HashMap<>();

    private PowerMode(int intValue) {
        this.intValue = intValue;
    }

    static {
        for (PowerMode powerMode : PowerMode.values()) {
            map.put(powerMode.intValue, powerMode);
        }
    }

    public static PowerMode valueOf(int powerMode) {
        return map.get(powerMode);
    }

    public int intValue() {
        return intValue;
    }
}
