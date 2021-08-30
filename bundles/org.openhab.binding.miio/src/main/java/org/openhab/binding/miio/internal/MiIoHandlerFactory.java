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
package org.openhab.binding.miio.internal;

import static org.openhab.binding.miio.internal.MiIoBindingConstants.*;

import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.miio.internal.basic.BasicChannelTypeProvider;
import org.openhab.binding.miio.internal.basic.MiIoDatabaseWatchService;
import org.openhab.binding.miio.internal.cloud.CloudConnector;
import org.openhab.binding.miio.internal.handler.MiIoBasicHandler;
import org.openhab.binding.miio.internal.handler.MiIoGenericHandler;
import org.openhab.binding.miio.internal.handler.MiIoUnsupportedHandler;
import org.openhab.binding.miio.internal.handler.MiIoVacuumHandler;
import org.openhab.core.common.ThreadPoolManager;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.binding.BaseThingHandlerFactory;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.thing.binding.ThingHandlerFactory;
import org.openhab.core.thing.type.ChannelTypeRegistry;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link MiIoHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Marcel Verpaalen - Initial contribution
 */
@Component(service = ThingHandlerFactory.class, configurationPid = "binding.miio")
@NonNullByDefault
public class MiIoHandlerFactory extends BaseThingHandlerFactory {
    private static final String THING_HANDLER_THREADPOOL_NAME = "thingHandler";
    protected final ScheduledExecutorService scheduler = ThreadPoolManager
            .getScheduledPool(THING_HANDLER_THREADPOOL_NAME);
    private MiIoDatabaseWatchService miIoDatabaseWatchService;
    private CloudConnector cloudConnector;
    private ChannelTypeRegistry channelTypeRegistry;
    private BasicChannelTypeProvider basicChannelTypeProvider;
    private @Nullable Future<Boolean> scheduledTask;
    private final Logger logger = LoggerFactory.getLogger(MiIoHandlerFactory.class);

    @Activate
    public MiIoHandlerFactory(@Reference ChannelTypeRegistry channelTypeRegistry,
            @Reference MiIoDatabaseWatchService miIoDatabaseWatchService, @Reference CloudConnector cloudConnector,
            @Reference BasicChannelTypeProvider basicChannelTypeProvider, Map<String, Object> properties) {
        this.miIoDatabaseWatchService = miIoDatabaseWatchService;
        this.channelTypeRegistry = channelTypeRegistry;
        this.basicChannelTypeProvider = basicChannelTypeProvider;
        this.cloudConnector = cloudConnector;
        @Nullable
        String username = (String) properties.get("username");
        @Nullable
        String password = (String) properties.get("password");
        @Nullable
        String country = (String) properties.get("country");
        cloudConnector.setCredentials(username, password, country);
        try {
            if (!scheduler.isShutdown()) {
                scheduledTask = scheduler.submit(() -> cloudConnector.isConnected());
            } else {
                logger.debug("Unexpected: ScheduledExecutorService is shutdown.");
            }
        } catch (RejectedExecutionException e) {
            logger.debug("Unexpected: ScheduledExecutorService task rejected.", e);
        }
    }

    @Deactivate
    public void dispose() {
        final Future<Boolean> scheduledTask = this.scheduledTask;
        if (scheduledTask != null && !scheduledTask.isDone()) {
            scheduledTask.cancel(true);
        }
    }

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();
        if (thingTypeUID.equals(THING_TYPE_MIIO)) {
            return new MiIoGenericHandler(thing, miIoDatabaseWatchService, cloudConnector);
        }
        if (thingTypeUID.equals(THING_TYPE_BASIC)) {
            return new MiIoBasicHandler(thing, miIoDatabaseWatchService, cloudConnector, channelTypeRegistry,
                    basicChannelTypeProvider);
        }
        if (thingTypeUID.equals(THING_TYPE_VACUUM)) {
            return new MiIoVacuumHandler(thing, miIoDatabaseWatchService, cloudConnector, channelTypeRegistry);
        }
        return new MiIoUnsupportedHandler(thing, miIoDatabaseWatchService, cloudConnector);
    }
}
