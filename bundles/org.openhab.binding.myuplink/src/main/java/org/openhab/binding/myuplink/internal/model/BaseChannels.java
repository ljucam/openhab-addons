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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.openhab.binding.myuplink.internal.model.ScaledChannel.ScaleFactor;
import org.openhab.core.library.unit.MetricPrefix;
import org.openhab.core.library.unit.SIUnits;
import org.openhab.core.library.unit.Units;

/**
 * this class contains all base channels which are used by all heatpump models
 *
 * @author Alexander Friese - initial contribution
 */
public class BaseChannels extends AbstractChannels {

    /**
     * singleton
     */
    private static final BaseChannels INSTANCE = new BaseChannels();

    /**
     * Returns the unique instance of this class.
     *
     * @return the Units instance.
     */
    protected static BaseChannels getInstance() {
        return INSTANCE;
    }

    /**
     * singleton should not be instantiated from outside
     */
    protected BaseChannels() {
    }

    /**
     * returns the matching channel, null if no match was found.
     *
     * @param channelCode the channelCode which identifies the channel
     * @return channel which belongs to the code. might be null if there is no channel found.
     */
    @Override
    public Channel fromCode(String channelCode) {
        Channel channel = super.fromCode(channelCode);

        // also check channels in this class if called from an inherited class
        if (channel == null && this != INSTANCE) {
            return INSTANCE.fromCode(channelCode);
        } else {
            return channel;
        }
    }

    /**
     * returns an unmodifiable set containing all available channels.
     *
     * @return
     */
    @Override
    public Set<Channel> getChannels() {
        Set<Channel> allChannels = new HashSet<>();
        allChannels.addAll(channels);

        // also add channels contained in this class if called from an inherited class
        if (this != INSTANCE) {
            allChannels.addAll(INSTANCE.channels);
        }

        return Collections.unmodifiableSet(allChannels);
    }

    // General
    public static final Channel CH_40004 = INSTANCE.addChannel(new QuantityChannel("40004", // ok
            "BT1 Outdoor Temperature", ChannelGroup.BASE, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_40067 = INSTANCE.addChannel(new QuantityChannel("40067", // ok
            "BT1 Average", ChannelGroup.BASE, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_43005 = INSTANCE.addChannel(new ScaledChannel("43005", // ok
            "Degree Minutes (16 bit)", ChannelGroup.BASE, ScaleFactor.DIV_10, "/Manage/4.9.3", "-?[0-9]+"));
    public static final Channel CH_43009 = INSTANCE.addChannel(new QuantityChannel("43009", // ok
            "Calc. Supply S1", ChannelGroup.BASE, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_40071 = INSTANCE.addChannel(new QuantityChannel("40071", // ok
            "BT25 Ext. Supply", ChannelGroup.BASE, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_40033 = INSTANCE.addChannel(new QuantityChannel("40033", // ok
            "BT50 Room Temp S1", ChannelGroup.BASE, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_43161 = INSTANCE.addChannel(new SwitchChannel("43161", // ok
            "External adjustment activated via input S1", ChannelGroup.BASE));
    public static final Channel CH_40008 = INSTANCE.addChannel(new QuantityChannel("40008", // NOT FOUND
            "BT2 Supply temp S1", ChannelGroup.BASE, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_40012 = INSTANCE.addChannel(new QuantityChannel("40012", // NOT FOUND
            "EB100-EP14-BT3 Return temp", ChannelGroup.BASE, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_40072 = INSTANCE.addChannel(new QuantityChannel("40072", // ok
            "BF1 EP14 Flow", ChannelGroup.BASE, ScaleFactor.DIV_10, Units.LITRE.divide(SIUnits.METRE)));
    public static final Channel CH_43437 = INSTANCE.addChannel(new QuantityChannel("43437", // NOT FOUND
            "Supply Pump Speed EP14", ChannelGroup.BASE, Units.PERCENT));
    public static final Channel CH_40079 = INSTANCE.addChannel(new QuantityChannel("40079", // ok
            "EB100-BE3 Current", ChannelGroup.BASE, ScaleFactor.DIV_10, Units.AMPERE));
    public static final Channel CH_40081 = INSTANCE.addChannel(new QuantityChannel("40081", // ok
            "EB100-BE2 Current", ChannelGroup.BASE, ScaleFactor.DIV_10, Units.AMPERE));
    public static final Channel CH_40083 = INSTANCE.addChannel(new QuantityChannel("40083", // ok
            "EB100-BE1 Current", ChannelGroup.BASE, ScaleFactor.DIV_10, Units.AMPERE));
    public static final Channel CH_10033 = INSTANCE.addChannel(new SwitchChannel("10033", // ok
            "Int. el.add. blocked", ChannelGroup.BASE));

    // additional heater
    public static final Channel CH_43081 = INSTANCE.addChannel(new QuantityChannel("43081", // NOT FOUND
            "Tot. op.time add.", ChannelGroup.BASE, ScaleFactor.DIV_10, Units.HOUR));
    public static final Channel CH_43084 = INSTANCE.addChannel(new QuantityChannel("43084", // NOT FOUND
            "Int. el.add. Power", ChannelGroup.BASE, ScaleFactor.DIV_100, MetricPrefix.KILO(Units.WATT)));
    public static final Channel CH_47212 = INSTANCE.addChannel(new QuantityChannel("47212", // NOT FOUND
            "Max int add. power", ChannelGroup.BASE, ScaleFactor.DIV_100, MetricPrefix.KILO(Units.WATT)));
    public static final Channel CH_48914 = INSTANCE.addChannel(new QuantityChannel("48914", // NOT FOUND
            "Max int add. power, SG Ready", ChannelGroup.BASE, ScaleFactor.DIV_100, MetricPrefix.KILO(Units.WATT)));

    // heat meters
    public static final Channel CH_44308 = INSTANCE.addChannel(new QuantityChannel("44308", // ok
            "Heat Meter - Heat Cpr EP14", ChannelGroup.BASE, ScaleFactor.DIV_10, MetricPrefix.KILO(Units.WATT_HOUR)));
    public static final Channel CH_44304 = INSTANCE.addChannel(new QuantityChannel("44304", // ok
            "Heat Meter - Pool Cpr EP14", ChannelGroup.BASE, ScaleFactor.DIV_10, MetricPrefix.KILO(Units.WATT_HOUR)));
    public static final Channel CH_44300 = INSTANCE.addChannel(new QuantityChannel("44300", // NOT FOUND
            "Heat Meter - Heat Cpr and Add EP14", ChannelGroup.BASE, ScaleFactor.DIV_10,
            MetricPrefix.KILO(Units.WATT_HOUR)));
    public static final Channel CH_48043 = INSTANCE.addChannel(new SwitchChannel("48043", // NOT FOUND
            "Holiday Mode", ChannelGroup.BASE, 0, 10, "/Manage/4.7"));

    // Hotwater
    public static final Channel CH_40013 = INSTANCE.addChannel(new QuantityChannel("40013", // ok
            "BT7 HW Top", ChannelGroup.HOTWATER, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_40014 = INSTANCE.addChannel(new QuantityChannel("40014", // ok
            "BT6 HW Load", ChannelGroup.HOTWATER, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_44306 = INSTANCE.addChannel(new QuantityChannel("44306", // ok
            "Heat Meter - HW Cpr EP14", ChannelGroup.HOTWATER, ScaleFactor.DIV_10, MetricPrefix.KILO(Units.WATT_HOUR)));
    public static final Channel CH_44298 = INSTANCE.addChannel(new QuantityChannel("44298", // NOT FOUND
            "Heat Meter - HW Cpr and Add EP14", ChannelGroup.HOTWATER, ScaleFactor.DIV_10,
            MetricPrefix.KILO(Units.WATT_HOUR)));
    public static final Channel CH_48132 = INSTANCE.addChannel(new Channel("48132", // ok
            "Temporary Lux", ChannelGroup.HOTWATER, "/Manage/2.1", "[01234]"));
    public static final Channel CH_47041 = INSTANCE.addChannel(new Channel("47041", // ok
            "Hot water mode", ChannelGroup.HOTWATER, "/Manage/2.2", "[012]"));
    public static final Channel CH_47045 = INSTANCE.addChannel(new QuantityChannel("47045", // NOT FOUND
            "Start temperature HW Economy", ChannelGroup.HOTWATER, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_47049 = INSTANCE.addChannel(new QuantityChannel("47049", // NOT FOUND
            "Stop temperature HW Economy", ChannelGroup.HOTWATER, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_47044 = INSTANCE.addChannel(new QuantityChannel("47044", // NOT FOUND
            "Start temperature HW Normal", ChannelGroup.HOTWATER, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_47048 = INSTANCE.addChannel(new QuantityChannel("47048", // NOT FOUND
            "Stop temperature HW Normal", ChannelGroup.HOTWATER, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_47043 = INSTANCE.addChannel(new QuantityChannel("47043", // NOT FOUND
            "Start temperature HW Luxury", ChannelGroup.HOTWATER, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_47047 = INSTANCE.addChannel(new QuantityChannel("47047", // NOT FOUND
            "Stop temperature HW Luxury", ChannelGroup.HOTWATER, ScaleFactor.DIV_10, SIUnits.CELSIUS));
    public static final Channel CH_47046 = INSTANCE.addChannel(new QuantityChannel("47046", // NOT FOUND
            "Stop temperature periodic HW", ChannelGroup.HOTWATER, ScaleFactor.DIV_10, SIUnits.CELSIUS));

    // Compressor
    public static final Channel CH_10012 = INSTANCE.addChannel(new SwitchChannel("10012", // NOT FOUND
            "Compressor blocked", ChannelGroup.BASE));
}
