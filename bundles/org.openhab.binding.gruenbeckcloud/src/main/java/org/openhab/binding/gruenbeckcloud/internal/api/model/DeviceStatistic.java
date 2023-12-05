package org.openhab.binding.gruenbeckcloud.internal.api.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The {@link GruenbeckMessage} class is a model class containing device statistics retrieved from
 * gruenbeck cloud.
 *
 * @author Mario Aerni - Initial contribution
 */
@JsonIgnoreProperties(ignoreUnknown = false)
public class DeviceStatistic {

    /*
     * {
     * "hardwareVersion":"00000004",
     * "lastService":"2023-11-15",
     * "mode":2,
     * "nextRegeneration":"2023-11-25T02:00:00",
     * "nominalFlow":2.1,
     * "rawWater":14.6067,
     * "softWater":5.618,
     * "softwareVersion":"0003.0030",
     * "errors": [
     * {
     * "isResolved": true,
     * "date": "2020-06-02T02:03:38.612",
     * "message": "Antriebsstörung Steuerventil Regeneration!",
     * "type": "warning"
     * },
     * {
     * "isResolved": true,
     * "date": "2020-05-28T01:30:51.527",
     * "message": "Antriebsstörung Steuerventil Regeneration!",
     * "type": "warning"
     * }
     * ],
     * "salt":[
     * {"date":"2023-11-23","value":0},
     * {"date":"2023-11-22","value":0},
     * {"date":"2023-11-21","value":447}
     * ],
     * "timeZone":"+01:00",
     * "water":[
     * {"date":"2023-11-23","value":216},
     * {"date":"2023-11-22","value":250},
     * {"date":"2023-11-21","value":257}
     * ],
     * "unit":2,
     * "startup":"2023-11-15",
     * "type":21,
     * "hasError":false,
     * "id":"softliQ.D/BSxxxxxxxx",
     * "series":"softliQ.D",
     * "serialNumber":"BSxxxxxxxx",
     * "name":"SoftliQ SD21",
     * "register":true
     * }
     */

    private String hardwareVersion;
    private LocalDate lastService;
    private Integer mode;
    private LocalDateTime nextRegeneration;
    private Double nominalFlow;
    private Double rawWater;
    private Double softWater;
    private String softwareVersion;
    private String timeZone;
    private Integer unit;
    private LocalDate startup;
    private Integer type;
    private Boolean hasError;
    private String id;
    private String series;
    private String serialNumber;
    private String name;
    private Boolean register;
    private List<Error> errors = new ArrayList<>();
    private List<SaltUsage> salt = new ArrayList<>();
    private List<WaterUsage> water = new ArrayList<>();

    public DeviceStatistic() {
    }

    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    public LocalDate getLastService() {
        return lastService;
    }

    public void setLastService(LocalDate lastService) {
        this.lastService = lastService;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public LocalDateTime getNextRegeneration() {
        return nextRegeneration;
    }

    public void setNextRegeneration(LocalDateTime nextRegeneration) {
        this.nextRegeneration = nextRegeneration;
    }

    public Double getNominalFlow() {
        return nominalFlow;
    }

    public void setNominalFlow(Double nominalFlow) {
        this.nominalFlow = nominalFlow;
    }

    public Double getRawWater() {
        return rawWater;
    }

    public void setRawWater(Double rawWater) {
        this.rawWater = rawWater;
    }

    public Double getSoftWater() {
        return softWater;
    }

    public void setSoftWater(Double softWater) {
        this.softWater = softWater;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public LocalDate getStartup() {
        return startup;
    }

    public void setStartup(LocalDate startup) {
        this.startup = startup;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean isHasError() {
        return hasError;
    }

    public void setHasError(Boolean hasError) {
        this.hasError = hasError;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeries() {
        return series;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isRegister() {
        return register;
    }

    public void setRegister(Boolean register) {
        this.register = register;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public List<SaltUsage> getSalt() {
        return salt;
    }

    public void setSalt(List<SaltUsage> salt) {
        this.salt = salt;
    }

    public List<WaterUsage> getWater() {
        return water;
    }

    public void setWater(List<WaterUsage> water) {
        this.water = water;
    }

    public static class SaltUsage {
        private Integer value;
        private LocalDate date;

        public SaltUsage() {
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }
    }

    public static class WaterUsage {
        private Integer value;
        private LocalDate date;

        public WaterUsage() {
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }
    }

    public static class Error {
        private Boolean resolved;
        private LocalDate date;
        private String message;
        private String type;

        public Error() {
        }

        public Boolean isResolved() {
            return resolved;
        }

        @JsonProperty("isResolved")
        public void setResolved(Boolean resolved) {
            this.resolved = resolved;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
