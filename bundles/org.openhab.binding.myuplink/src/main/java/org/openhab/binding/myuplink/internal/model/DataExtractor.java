/**
 * Copyright (c) 2010-2021 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.myuplink.internal.model;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openhab.binding.myuplink.internal.model.GenericDataResponse.Value;
import org.openhab.binding.myuplink.internal.model.ScaledChannel.ScaleFactor;

/**
 * data extractor - failsafe if response data isn't already interpreted
 * 
 * @author Trophy
 *
 */
public class DataExtractor {
    private static final Pattern NUMBER_PATTERN;
    private static final Map<String, ScaleFactor> FACTOR_MAP;
    private static final Map<String, Long> VALUE_MAP;

    static {
        NUMBER_PATTERN = Pattern.compile("(?<number>[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?)(?<unit>.*)");

        FACTOR_MAP = new HashMap<>();
        FACTOR_MAP.put("°c", ScaleFactor.MULT_10);
        FACTOR_MAP.put("hz", ScaleFactor.MULT_10);
        FACTOR_MAP.put("kw", ScaleFactor.MULT_100);
        FACTOR_MAP.put("a", ScaleFactor.MULT_10);
        FACTOR_MAP.put("bar", ScaleFactor.MULT_10);
        FACTOR_MAP.put("kwh", ScaleFactor.MULT_10);
        FACTOR_MAP.put("h", ScaleFactor.ONE);
        FACTOR_MAP.put("gm", ScaleFactor.MULT_10);
        FACTOR_MAP.put("%", ScaleFactor.ONE);
        FACTOR_MAP.put("rpm", ScaleFactor.MULT_10);

        VALUE_MAP = new HashMap<>();
        VALUE_MAP.put("nein", 0L);
        VALUE_MAP.put("inaktiv", 0L);
        VALUE_MAP.put("aus", 0L);
        VALUE_MAP.put("eco", 0L);

        VALUE_MAP.put("ein", 1L);
        VALUE_MAP.put("aktiv", 1L);
        VALUE_MAP.put("ja", 1L);
        VALUE_MAP.put("normal", 1L);

        VALUE_MAP.put("luxus", 2L);
        VALUE_MAP.put("lux", 2L);
    }

    public static Long getCurrentIntValue(Value value) {
        if (value.currentIntValue != null) {
            return value.currentIntValue;
        }

        if (value.currentValue == null) {
            return null;
        }

        Matcher matcher = NUMBER_PATTERN.matcher(value.currentValue);
        Long currentIntValue = null;

        // got a number (evtl. with a unit)
        if (matcher.matches()) {
            double doubleValue = Double.parseDouble(matcher.group("number"));
            String unit = matcher.group("unit").toLowerCase();
            currentIntValue = (long) doubleValue; // no multipl. in case of
                                                  // number without unit

            if (unit != null && FACTOR_MAP.containsKey(unit)) {
                currentIntValue = (long) (FACTOR_MAP.get(unit).getFactor() * doubleValue);
            }

            return currentIntValue;
        } else {
            return VALUE_MAP.get(value.currentValue.toLowerCase());
        }
    }
}
