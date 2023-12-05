package org.openhab.binding.gruenbeckcloud.internal.api.model;

import java.time.LocalDate;

/**
 * The {@link SoftenerData} class is the primary data holder for the persistent storage.
 *
 * @author Mario Aerni - Initial contribution
 */
public class SoftenerData {
    public static final String SOFTENER_DATA = "softenerData";
    private Integer currentCapacityInGram;
    private LocalDate lastAccountedSaltUsageDate;

    public SoftenerData() {
    }

    public Integer getCurrentCapacityInGram() {
        return currentCapacityInGram;
    }

    public void setCurrentCapacityInGram(Integer currentCapacityInGram) {
        this.currentCapacityInGram = currentCapacityInGram;
    }

    public LocalDate getLastAccountedSaltUsageDate() {
        return lastAccountedSaltUsageDate;
    }

    public void setLastAccountedSaltUsageDate(LocalDate lastAccountedSaltUsageDate) {
        this.lastAccountedSaltUsageDate = lastAccountedSaltUsageDate;
    }
}
