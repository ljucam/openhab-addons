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

import org.openhab.binding.myuplink.internal.command.GenericStatusUpdate;
import org.openhab.binding.myuplink.internal.command.MyUplinkCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Polling worker class. This is responsible for periodic polling of status values.
 *
 * @author Alexander Friese - initial contribution
 */
public class UplinkPolling implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Handler for delegation to callbacks.
     */
    private final MyUplinkHandler handler;

    /**
     * Constructor.
     *
     * @param handler instance of the thing handler
     */
    public UplinkPolling(MyUplinkHandler handler) {
        this.handler = handler;
    }

    /**
     * Poll the MyUplink webservice one time.
     */
    @Override
    public void run() {
        logger.debug("polling MyUplink {}", handler.getConfiguration());

        MyUplinkCommand command = new GenericStatusUpdate(handler);
        handler.getWebInterface().enqueueCommand(command);
    }
}
