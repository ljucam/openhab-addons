/**
 * Copyright (c) 2010-2019 Contributors to the openHAB project
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
package org.openhab.binding.myuplink.internal.handler;

import java.util.Map;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.binding.myuplink.internal.config.MyUplinkConfiguration;
import org.openhab.binding.myuplink.internal.connector.UplinkWebInterface;
import org.openhab.binding.myuplink.internal.model.Channel;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.types.State;

/**
 * public interface of the {@link UplinkBaseHandler}
 *
 * @author Alexander Friese - initial contribution
 */
@NonNullByDefault
public interface MyUplinkHandler extends ThingHandler, ChannelProvider {
    /**
     * Called from {@link MyUplinkWebInterface#authenticate()} to update
     * the thing status because updateStatus is protected.
     *
     * @param status Bridge status
     * @param statusDetail Bridge status detail
     * @param description Bridge status description
     */
    void setStatusInfo(ThingStatus status, ThingStatusDetail statusDetail, String description);

    /**
     * Provides the web interface object.
     *
     * @return The web interface object
     */
    UplinkWebInterface getWebInterface();

    void updateChannelStatus(Map<Channel, State> values);

    MyUplinkConfiguration getConfiguration();
}
