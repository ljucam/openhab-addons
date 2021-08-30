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

import javax.measure.Unit;

import org.openhab.binding.myuplink.internal.handler.ChannelProvider;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.QuantityType;
import org.openhab.core.types.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * transforms the http response into the openhab datamodel (instances of State)
 *
 * @author Alexander Friese - initial contribution
 */
public class DataResponseTransformer {
    private final Logger logger = LoggerFactory.getLogger(DataResponseTransformer.class);

    private final ChannelProvider channelProvider;

    public DataResponseTransformer(ChannelProvider channelProvider) {
        this.channelProvider = channelProvider;
    }

    public Map<Channel, State> transform(DataResponse response) {
        Map<String, Long> source = response.getValues();
        Map<Channel, State> result = new HashMap<>(source.size());

        for (String channelId : source.keySet()) {
            Long value = source.get(channelId);

            Channel channel = channelProvider.getSpecificChannel(channelId);
            if (channel == null) {
                // This should not happen but we want to get informed about it
                logger.warn("Channel not found: {}", channelId);
            } else {
                // prevent NPE in case when value is not available
                if (value == null) {
                    logger.debug("Channel {} has no valid value and will not be set", channel.getFQName());
                    continue;
                }

                if (channel instanceof QuantityChannel) {
                    Unit<?> unit = ((QuantityChannel) channel).getUnit();
                    double factor = ((ScaledChannel) channel).getFactor();
                    logger.debug("Channel {} transformed to QuantityType ({}*{} {})", channel.getFQName(), value,
                            factor, unit.toString());
                    result.put(channel, new QuantityType<>(value * factor, unit));
                } else if (channel instanceof SwitchChannel) {
                    logger.debug("Channel {} transformed to OnOffType ({})", channel.getFQName(), value);
                    OnOffType mapped = ((SwitchChannel) channel).mapValue(value);
                    result.put(channel, mapped);
                } else if (channel instanceof ScaledChannel) {
                    double factor = ((ScaledChannel) channel).getFactor();
                    logger.debug("Channel {} transformed to scaled NumberType ({}*{})", channel.getFQName(), value,
                            factor);
                    result.put(channel, new DecimalType(value * factor));
                } else {
                    logger.debug("Channel {} transformed to NumberType ({})", channel.getFQName(), value);
                    result.put(channel, new DecimalType(value));
                }
            }
        }
        return result;
    }
}
