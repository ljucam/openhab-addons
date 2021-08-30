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

import org.openhab.binding.myuplink.internal.model.ScaledChannel.ScaleFactor;
import org.openhab.core.library.unit.MetricPrefix;
import org.openhab.core.library.unit.SIUnits;
import org.openhab.core.library.unit.Units;

/**
 * list of all available channels
 *
 * @author Alexander Friese - initial contribution
 */
public final class NPCS40Channels extends BaseChannels {

    /**
     * singleton
     */
    private static final NPCS40Channels INSTANCE = new NPCS40Channels();

    /**
     * Returns the unique instance of this class.
     *
     * @return the Units instance.
     */
    public static NPCS40Channels getInstance() {
        return INSTANCE;
    }

    /**
     * singleton should not be instantiated from outside
     */
    private NPCS40Channels() {
    }

    // General
    public static final Channel CH_44302 = INSTANCE.addChannel(new QuantityChannel("44302", // ok ok
            "Heat Meter - Cooling Cpr EP14", ChannelGroup.GENERAL, ScaleFactor.DIV_10,
            MetricPrefix.KILO(Units.WATT_HOUR)));
    public static final Channel CH_47011 = INSTANCE.addChannel(new Channel("47011", // ok ok
            "Heat Offset S1", ChannelGroup.GENERAL, "/Manage/1.9.1.1-S1", "[-1]*[0-9]"));

    public static final Channel CH_47276 = INSTANCE.addChannel(new SwitchChannel("47276", // ok - new ok
            "Floor Drying", ChannelGroup.BASE));
    public static final Channel CH_40152 = INSTANCE.addChannel(new QuantityChannel("40152", // ok - new ok
            "Return Flow Temp BT71", ChannelGroup.BASE, ScaleFactor.DIV_10, SIUnits.CELSIUS));

    // additional heater
    public static final Channel CH_47214 = INSTANCE.addChannel(new QuantityChannel("47214", // ok - new ok
            "Additional Heater Fuse Size", ChannelGroup.BASE, ScaleFactor.DIV_10, Units.AMPERE));
    public static final Channel CH_47613 = INSTANCE.addChannel(new Channel("47613", // ok - new ok
            "Additional Heater Level", ChannelGroup.BASE));
    public static final Channel CH_43091 = INSTANCE.addChannel(new Channel("43091", // ok - new ok
            "Additional Heater State", ChannelGroup.BASE));

    // heat meters
    public static final Channel CH_40771 = INSTANCE.addChannel(new QuantityChannel("40771", // ok - new
            "Heat Meter - Pool Cpr 2", ChannelGroup.BASE, ScaleFactor.DIV_10, MetricPrefix.KILO(Units.WATT_HOUR)));

    // Compressor
    public static final Channel CH_44362 = INSTANCE.addChannel(new QuantityChannel("44362", // ok ok
            "EB101-EP14-BT28 Outdoor Temp", ChannelGroup.COMPRESSOR, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_44396 = INSTANCE.addChannel(new QuantityChannel("44396", // ok ok
            "EB101 Speed charge pump", ChannelGroup.COMPRESSOR, Units.PERCENT));
    public static final Channel CH_44703 = INSTANCE.addChannel(new Channel("44703", // ok ok
            "EB101-EP14 Defrosting Outdoor Unit", ChannelGroup.COMPRESSOR));
    public static final Channel CH_44073 = INSTANCE.addChannel(new QuantityChannel("44073", // ok ok
            "EB101-EP14 Tot. HW op.time compr", ChannelGroup.COMPRESSOR, Units.HOUR));
    public static final Channel CH_40737 = INSTANCE.addChannel(new QuantityChannel("40737", // ok ok
            "EB101-EP14 Tot. Cooling op.time compr", ChannelGroup.COMPRESSOR, Units.HOUR));
    public static final Channel CH_44071 = INSTANCE.addChannel(new QuantityChannel("44071", // ok ok
            "EB101-EP14 Tot. op.time compr", ChannelGroup.COMPRESSOR, Units.HOUR));
    public static final Channel CH_44069 = INSTANCE.addChannel(new Channel("44069", // ok ok
            "EB101-EP14 Compressor starts", ChannelGroup.COMPRESSOR));
    public static final Channel CH_44061 = INSTANCE.addChannel(new QuantityChannel("44061", // ok ok
            "EB101-EP14-BT17 Suction", ChannelGroup.COMPRESSOR, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_44060 = INSTANCE.addChannel(new QuantityChannel("44060", // ok ok
            "EB101-EP14-BT15 Liquid Line", ChannelGroup.COMPRESSOR, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_44059 = INSTANCE.addChannel(new QuantityChannel("44059", // ok ok
            "EB101-EP14-BT14 Hot Gas Temp", ChannelGroup.COMPRESSOR, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_44058 = INSTANCE.addChannel(new QuantityChannel("44058", // ok ok
            "EB101-EP14-BT12 Condensor Out", ChannelGroup.COMPRESSOR, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_44055 = INSTANCE.addChannel(new QuantityChannel("44055", // ok ok
            "EB101-EP14-BT3 Return Temp.", ChannelGroup.COMPRESSOR, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_44363 = INSTANCE.addChannel(new QuantityChannel("44363", // ok ok
            "EB101-EP14-BT16 Evaporator", ChannelGroup.COMPRESSOR, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_44699 = INSTANCE.addChannel(new QuantityChannel("44699", // ok ok
            "EB101-EP14-BP4 Pressure Sensor", ChannelGroup.COMPRESSOR, ScaleFactor.DIV_10, Units.BAR));
    public static final Channel CH_40782 = INSTANCE.addChannel(new QuantityChannel("40782", // ok ok
            "EB101 Cpr Frequency Desired F2040", ChannelGroup.COMPRESSOR, Units.HERTZ));
    public static final Channel CH_44701 = INSTANCE.addChannel(new QuantityChannel("44701", // ok ok
            "EB101-EP14 Actual Cpr Frequency Outdoor Unit", ChannelGroup.COMPRESSOR, ScaleFactor.DIV_10, Units.HERTZ));
    public static final Channel CH_44700 = INSTANCE.addChannel(new QuantityChannel("44700", // NOT FOUND - but sounds
                                                                                            // logic ok
            "EB101-EP14 Low Pressure Sensor Outdoor Unit", ChannelGroup.COMPRESSOR, ScaleFactor.DIV_10, Units.BAR));
    public static final Channel CH_44457 = INSTANCE.addChannel(new Channel("44457", // NOT FOUND - but sounds logic ok
            "EB101-EP14 Compressor State", ChannelGroup.COMPRESSOR));

    public static final Channel CH_10014 = INSTANCE.addChannel(new SwitchChannel("10014", // ok - new ok
            "Compressor blocked", ChannelGroup.COMPRESSOR));
    public static final Channel CH_44866 = INSTANCE.addChannel(new QuantityChannel("44866", // ok - new ok
            "EB101 Current", ChannelGroup.COMPRESSOR, ScaleFactor.DIV_10, Units.AMPERE));
    public static final Channel CH_41164 = INSTANCE.addChannel(new QuantityChannel("41164", // ok - new ok
            "EB101-BT81 Compressor Injection", ChannelGroup.COMPRESSOR, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_41163 = INSTANCE.addChannel(new QuantityChannel("41163", // ok - new ok
            "EB101-BP9 High Pressure", ChannelGroup.COMPRESSOR, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_41162 = INSTANCE.addChannel(new QuantityChannel("41162", // ok - new ok
            "EB101-BP8 Low Pressure", ChannelGroup.COMPRESSOR, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_41167 = INSTANCE.addChannel(new QuantityChannel("41167", // ok - new ok
            "EB101-BT84 Evaporator", ChannelGroup.COMPRESSOR, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_41002 = INSTANCE.addChannel(new ScaledChannel("41002", // ok - new ok
            "EB101 Fan Speed", ChannelGroup.COMPRESSOR, ScaleFactor.DIV_10));
}
