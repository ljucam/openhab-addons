/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * <p>See the NOTICE file(s) distributed with this work for additional information.
 *
 * <p>This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0
 *
 * <p>SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.gruenbeckcloud.internal.discovery;

import static org.openhab.binding.gruenbeckcloud.internal.GruenbeckCloudBindingConstants.SOFTENER_REPRESENTATION_PROPERTY;
import static org.openhab.binding.gruenbeckcloud.internal.GruenbeckCloudBindingConstants.SUPPORTED_SOFTENER;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.openhab.binding.gruenbeckcloud.internal.GruenbeckCloudBindingConstants;
import org.openhab.binding.gruenbeckcloud.internal.api.model.Device;
import org.openhab.binding.gruenbeckcloud.internal.handler.GruenbeckCloudBridgeHandler;
import org.openhab.core.config.discovery.AbstractDiscoveryService;
import org.openhab.core.config.discovery.DiscoveryResultBuilder;
import org.openhab.core.thing.Bridge;
import org.openhab.core.thing.ThingUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link GruenbeckCloudDiscoveryService} is responsible for starting the discovery procedure
 * that connects to the Gruenbeck Cloud and imports all registered, supported softener devices.
 *
 * @author Mario Aerni - Initial contribution
 */
public class GruenbeckCloudDiscoveryService extends AbstractDiscoveryService {
    private static final int TIMEOUT = 15;

    private final Logger logger = LoggerFactory.getLogger(GruenbeckCloudDiscoveryService.class);
    private final GruenbeckCloudBridgeHandler bridgeHandler;
    private final ThingUID bridgeUID;

    private ScheduledFuture<?> scanTask;

    public GruenbeckCloudDiscoveryService(GruenbeckCloudBridgeHandler bridgeHandler, Bridge bridge) {
        super(GruenbeckCloudBindingConstants.DISCOVERABLE_THING_TYPE_UIDS, TIMEOUT);
        logger.debug("GruenbeckCloud DiscoveryService created");
        this.bridgeHandler = bridgeHandler;
        this.bridgeUID = bridge.getUID();
    }

    @Override
    protected void startScan() {
        logger.debug("start discovery ...");

        if (this.scanTask != null) {
            scanTask.cancel(true);
        }
        this.scanTask = scheduler.schedule(this::findSoftener, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void startBackgroundDiscovery() {
        findSoftener();
    }

    @Override
    protected void stopScan() {
        super.stopScan();

        if (this.scanTask != null) {
            this.scanTask.cancel(true);
            this.scanTask = null;
        }
    }

    private void findSoftener() {
        List<Device> devices = bridgeHandler.getDevices();
        for (Device device : devices) {
            addThing(device);
        }
    }

    private void addThing(Device device) {
        if (!SUPPORTED_SOFTENER.contains(device.getSeries())) {
            logger.warn("device {} with series {} is not supported by this binding and will be ignored.",
                    device.getSerialNumber(), device.getSeries());
            return;
        }

        logger.debug("addThing(): adding device {} to inbox", device.getName());

        Map<String, Object> properties = new HashMap<>();
        properties.put(GruenbeckCloudBindingConstants.SOFTENER_SERIAL_NUMBER, device.getSerialNumber());
        properties.put(GruenbeckCloudBindingConstants.SOFTENER_SERIES, device.getSeries());
        properties.put(GruenbeckCloudBindingConstants.SOFTENER_ID, device.getId());
        properties.put(GruenbeckCloudBindingConstants.SOFTENER_NAME, device.getName());
        properties.put(GruenbeckCloudBindingConstants.SOFTENER_SALT_CAPACITY,
                GruenbeckCloudBindingConstants.SOFTENER_SALT_CAPACITY_DEFAULT);

        ThingUID thingUID = new ThingUID(GruenbeckCloudBindingConstants.THING_TYPE_SOFTENER, bridgeUID,
                device.getSerialNumber());

        thingDiscovered(DiscoveryResultBuilder.create(thingUID)
                .withThingType(GruenbeckCloudBindingConstants.THING_TYPE_SOFTENER).withBridge(bridgeUID)
                .withLabel(device.getName() + " (" + device.getSerialNumber() + ")")
                .withRepresentationProperty(SOFTENER_REPRESENTATION_PROPERTY).withProperties(properties).build());
    }
}
