package org.openhab.binding.gruenbeckcloud.internal.handler.channel;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@link RegenerationMode} enum represents the regeneration mode of the softeners.
 *
 * @author Mario Aerni - Initial contribution
 */
public enum RegenerationMode {
    AUTOMATIC(0),
    FIXED(1);

    private final int intValue;
    private static final Map<Integer, RegenerationMode> map = new HashMap<>();

    private RegenerationMode(int intValue) {
        this.intValue = intValue;
    }

    static {
        for (RegenerationMode regenerationMode : RegenerationMode.values()) {
            map.put(regenerationMode.intValue, regenerationMode);
        }
    }

    public static RegenerationMode valueOf(int regenerationMode) {
        return map.get(regenerationMode);
    }

    public int intValue() {
        return intValue;
    }
}
