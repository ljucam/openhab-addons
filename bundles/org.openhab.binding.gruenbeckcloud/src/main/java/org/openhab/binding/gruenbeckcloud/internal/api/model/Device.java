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
package org.openhab.binding.gruenbeckcloud.internal.api.model;

import org.openhab.binding.gruenbeckcloud.internal.GruenbeckCloudSoftenerConfiguration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The {@link Device} class holds regular device data retrieved by the gruenbeck cloud.
 *
 * @author Mario Aerni - Initial contribution
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Device {
    /*
     * {
     * "type":21,
     * "hasError":false,
     * "id":"softliQ.D/BSxxxxxxxx",
     * "series":"softliQ.D",
     * "serialNumber":"BSxxxxxxxx",
     * "name":"SoftliQ SD21",
     * "register":true
     * }
     */

    private String id;
    private String series;
    private String serialNumber;
    private boolean error;
    private String name;
    private Integer type;
    private boolean register;

    public Device() {
    }

    public Device(GruenbeckCloudSoftenerConfiguration config) {
        this.id = config.id;
        this.series = config.series;
        this.serialNumber = config.serialNumber;
        this.error = config.error;
        this.name = config.name;
        this.type = config.type;
        this.register = config.register;
    }

    public String getId() {
        return id;
    }

    public boolean hasError() {
        return error;
    }

    public String getName() {
        return name;
    }

    public Integer getType() {
        return type;
    }

    public String getSeries() {
        return series;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public boolean isError() {
        return error;
    }

    @JsonProperty("has_error")
    public void setError(boolean error) {
        this.error = error;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }
}
