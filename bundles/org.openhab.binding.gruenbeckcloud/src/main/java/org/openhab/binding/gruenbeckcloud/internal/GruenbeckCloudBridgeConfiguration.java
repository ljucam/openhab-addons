/**
 * Copyright (c) 2010-2023 Contributors to the openHAB project
 *
 * <p>See the NOTICE file(s) distributed with this work for additional information.
 *
 * <p>This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0
 *
 * <p>SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.gruenbeckcloud.internal;

/**
 * The {@link GruenbeckCloudBridgeConfiguration} class contains fields mapping thing configuration
 * parameters.
 *
 * @author Mario Aerni - Initial contribution
 */
public class GruenbeckCloudBridgeConfiguration {
    public String email;
    public String password;
    public int refreshPeriod;
}
