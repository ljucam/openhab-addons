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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.jetty.client.HttpClient;
import org.openhab.binding.myuplink.internal.AtomicReferenceTrait;
import org.openhab.binding.myuplink.internal.command.UpdateSetting;
import org.openhab.binding.myuplink.internal.config.MyUplinkConfiguration;
import org.openhab.binding.myuplink.internal.connector.UplinkWebInterface;
import org.openhab.binding.myuplink.internal.model.Channel;
import org.openhab.binding.myuplink.internal.model.CustomChannels;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;
import org.openhab.core.thing.binding.BaseThingHandler;
import org.openhab.core.types.Command;
import org.openhab.core.types.RefreshType;
import org.openhab.core.types.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link UplinkBaseHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Alexander Friese - initial contribution
 */
public abstract class UplinkBaseHandler extends BaseThingHandler implements MyUplinkHandler, AtomicReferenceTrait {
    private final Logger logger = LoggerFactory.getLogger(UplinkBaseHandler.class);

    private final long POLLING_INITIAL_DELAY = 30;
    private final long HOUSE_KEEPING_INITIAL_DELAY = 300;

    private Set<Channel> deadChannels = new HashSet<>(100);

    /**
     * Interface object for querying the MyUplink web interface
     */
    private UplinkWebInterface webInterface;

    /**
     * Schedule for polling
     */
    private final AtomicReference<Future<?>> pollingJobReference;

    /**
     * Schedule for periodic cleaning dead channel list
     */
    private final AtomicReference<Future<?>> deadChannelHouseKeepingReference;

    public UplinkBaseHandler(Thing thing, HttpClient httpClient) {
        super(thing);
        this.webInterface = new UplinkWebInterface(scheduler, this, httpClient);
        this.pollingJobReference = new AtomicReference<Future<?>>(null);
        this.deadChannelHouseKeepingReference = new AtomicReference<Future<?>>(null);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (!(command instanceof RefreshType)) {
            logger.debug("command for {}: {}", channelUID.getIdWithoutGroup(), command.toString());
            Channel channel = getSpecificChannel(channelUID.getIdWithoutGroup());
            if (channel != null && !channel.isReadOnly()) {
                webInterface.enqueueCommand(new UpdateSetting(this, channel, command));
            }
        }
    }

    @Override
    public void initialize() {
        logger.debug("About to initialize MyUplink");
        MyUplinkConfiguration config = getConfiguration();

        logger.debug("MyUplink initialized with configuration: {}", config);

        setupCustomChannels(config);

        startPolling();
        webInterface.start();
        updateStatus(ThingStatus.UNKNOWN, ThingStatusDetail.NONE, "waiting for web api login");
    }

    /**
     * initialize the custom channels out of the configuration
     *
     * @param config the active configuration
     */
    private void setupCustomChannels(MyUplinkConfiguration config) {
        CustomChannels.CH_CH01.setCode(config.getCustomChannel01());
        CustomChannels.CH_CH02.setCode(config.getCustomChannel02());
        CustomChannels.CH_CH03.setCode(config.getCustomChannel03());
        CustomChannels.CH_CH04.setCode(config.getCustomChannel04());
        CustomChannels.CH_CH05.setCode(config.getCustomChannel05());
        CustomChannels.CH_CH06.setCode(config.getCustomChannel06());
        CustomChannels.CH_CH07.setCode(config.getCustomChannel07());
        CustomChannels.CH_CH08.setCode(config.getCustomChannel08());
    }

    /**
     * Start the polling.
     */
    private void startPolling() {
        updateJobReference(pollingJobReference, scheduler.scheduleWithFixedDelay(new UplinkPolling(this),
                POLLING_INITIAL_DELAY, getConfiguration().getPollingInterval(), TimeUnit.SECONDS));
        updateJobReference(deadChannelHouseKeepingReference, scheduler.scheduleWithFixedDelay(deadChannels::clear,
                HOUSE_KEEPING_INITIAL_DELAY, getConfiguration().getHouseKeepingInterval(), TimeUnit.SECONDS));
    }

    /**
     * Disposes the bridge.
     */
    @Override
    public void dispose() {
        logger.debug("Handler disposed.");

        cancelJobReference(pollingJobReference);
        cancelJobReference(deadChannelHouseKeepingReference);

        // the webinterface also makes use of the scheduler and must stop it's jobs
        webInterface.dispose();
    }

    @Override
    public UplinkWebInterface getWebInterface() {
        return webInterface;
    }

    /**
     * will update all channels provided in the map
     *
     * @param values map containing the data updates
     */
    @Override
    public void updateChannelStatus(Map<Channel, State> values) {
        logger.debug("Handling channel update. ({} Channels)", values.size());

        for (Channel channel : values.keySet()) {
            if (getChannels().contains(channel)) {
                State value = values.get(channel);
                if (value == null) {
                    continue;
                }
                logger.debug("Channel is to be updated: {}: {}", channel.getFQName(), value);
                updateState(channel.getFQName(), value);
            } else {
                logger.debug("Could not identify channel: {} for model {}", channel.getFQName(),
                        getThing().getThingTypeUID().getAsString());
            }
        }
    }

    @Override
    public Set<Channel> getDeadChannels() {
        return deadChannels;
    }

    @Override
    public void setStatusInfo(ThingStatus status, ThingStatusDetail statusDetail, String description) {
        super.updateStatus(status, statusDetail, description);
    }

    @Override
    public MyUplinkConfiguration getConfiguration() {
        return this.getConfigAs(MyUplinkConfiguration.class);
    }
}
