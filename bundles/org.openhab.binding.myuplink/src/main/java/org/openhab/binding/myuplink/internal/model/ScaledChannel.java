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

import org.eclipse.jdt.annotation.Nullable;

/**
 * extension of the channel class which adds support of QuantityType
 *
 * @author Alexander Friese - initial contribution
 */
public class ScaledChannel extends Channel {

    static enum ScaleFactor {
        ONE(1),
        DIV_10(0.1),
        DIV_100(0.01),
        MULT_10(10.0),
        MULT_100(100.0);

        private final double factor;

        private ScaleFactor(double factor) {
            this.factor = factor;
        }

        public final double getFactor() {
            return factor;
        }
    }

    private final ScaleFactor factor;

    /**
     * constructor for channels with write access enabled + unit
     *
     * @param id identifier of the channel
     * @param name human readable name
     * @param channelGroup group of the channel
     * @param factor scaling factor
     * @param writeApiUrl API URL for channel updates
     * @param validationExpression expression to validate values before sent to the API
     */
    ScaledChannel(String id, String name, ChannelGroup channelGroup, ScaleFactor factor, @Nullable String writeApiUrl,
            @Nullable String validationExpression) {
        super(id, name, channelGroup, writeApiUrl, validationExpression);
        this.factor = factor;
    }

    /**
     * constructor for channels without write access
     *
     * @param id identifier of the channel
     * @param name human readable name
     * @param channelGroup group of the channel
     * @param factor scaling factor
     */
    ScaledChannel(String id, String name, ChannelGroup channelGroup, ScaleFactor factor) {
        this(id, name, channelGroup, factor, null, null);
    }

    public final double getFactor() {
        return factor.getFactor();
    }
}
