package org.openhab.binding.gruenbeckcloud.internal.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The {@link GruenbeckMessage} class is the base model class for all websocket messages of the
 * gruenbeck cloud.
 *
 * @author Mario Aerni - Initial contribution
 */
@JsonIgnoreProperties(ignoreUnknown = false)
public abstract class GruenbeckMessage {
    private String id;

    private String type;

    private Boolean ibuiltindev;

    private String isncu;

    protected GruenbeckMessage() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isIbuiltindev() {
        return ibuiltindev;
    }

    public void setIbuiltindev(Boolean ibuiltindev) {
        this.ibuiltindev = ibuiltindev;
    }

    public String getIsncu() {
        return isncu;
    }

    public void setIsncu(String isncu) {
        this.isncu = isncu;
    }
}
