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
package org.openhab.binding.myuplink.internal.command;

import static org.openhab.binding.myuplink.internal.MyUplinkBindingConstants.*;

import java.nio.charset.StandardCharsets;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Result;
import org.eclipse.jetty.client.util.FormContentProvider;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.util.Fields;
import org.openhab.binding.myuplink.internal.callback.AbstractUplinkCommandCallback;
import org.openhab.binding.myuplink.internal.connector.StatusUpdateListener;
import org.openhab.binding.myuplink.internal.handler.MyUplinkHandler;
import org.openhab.binding.myuplink.internal.model.Channel;
import org.openhab.binding.myuplink.internal.model.DataResponse;
import org.openhab.binding.myuplink.internal.model.DataResponseTransformer;
import org.openhab.binding.myuplink.internal.model.GenericDataResponse;
import org.openhab.binding.myuplink.internal.model.VVM320Channels;

/**
 * generic command that retrieves status values for all channels defined in {@link VVM320Channels}
 *
 * @author Alexander Friese - initial contribution
 */
public class GenericStatusUpdate extends AbstractUplinkCommandCallback implements MyUplinkCommand {
    private final MyUplinkHandler handler;
    private final DataResponseTransformer transformer;
    private int retries = 0;

    public GenericStatusUpdate(MyUplinkHandler handler) {
        super(handler.getConfiguration());
        this.handler = handler;
        this.transformer = new DataResponseTransformer(handler);
    }

    @Override
    protected Request prepareRequest(Request requestToPrepare) {

        Fields fields = new Fields();
        fields.add(DATA_API_FIELD_LAST_DATE, DATA_API_FIELD_LAST_DATE_DEFAULT_VALUE);
        fields.add(DATA_API_FIELD_ID, config.getNibeId());

        for (Channel channel : handler.getChannels()) {
            if (!handler.getDeadChannels().contains(channel)) {
                if (!channel.getChannelCode().equals("0")) {
                    fields.add(DATA_API_FIELD_DATA, channel.getChannelCode());
                }
            }
        }

        fields.add(DATA_API_FIELD_DATA, DATA_API_FIELD_DATA_DEFAULT_VALUE);
        FormContentProvider cp = new FormContentProvider(fields);

        requestToPrepare.header(HttpHeader.ACCEPT, "application/json");
        requestToPrepare.header(HttpHeader.ACCEPT_ENCODING, StandardCharsets.UTF_8.name());
        requestToPrepare.content(cp);
        requestToPrepare.followRedirects(false);
        requestToPrepare.method(HttpMethod.POST);

        return requestToPrepare;
    }

    @Override
    protected String getURL() {
        return DATA_API_URL;
    }

    @Override
    public void onComplete(@Nullable Result result) {
        logger.debug("onComplete()");

        if (!HttpStatus.Code.OK.equals(getCommunicationStatus().getHttpCode()) && retries++ < MAX_RETRIES) {
            StatusUpdateListener listener = getListener();
            if (listener != null) {
                listener.update(getCommunicationStatus());
            }
            handler.getWebInterface().enqueueCommand(this);

        } else {

            String json = getContentAsString(StandardCharsets.UTF_8);
            if (json != null) {
                logger.debug("JSON String: {}", json);
                DataResponse jsonObject = gson.fromJson(json, GenericDataResponse.class);
                handler.updateChannelStatus(transformer.transform(jsonObject));
            }
        }
    }
}
