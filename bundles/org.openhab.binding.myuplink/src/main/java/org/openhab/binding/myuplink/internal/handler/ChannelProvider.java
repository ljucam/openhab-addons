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
package org.openhab.binding.myuplink.internal.handler;

import java.util.Set;

import org.openhab.binding.myuplink.internal.model.Channel;

/**
 * this interface provides all methods which deal with channels
 *
 * @author Alexander Friese - initial contribution
 */
public interface ChannelProvider {

    Set<Channel> getChannels();

    Set<Channel> getDeadChannels();

    Channel getSpecificChannel(String channelCode);
}
