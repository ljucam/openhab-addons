package org.openhab.binding.gruenbeckcloud.internal.api.model;

/**
 * The {@link Current} class holds data retrieved by the websocket.
 *
 * @author Mario Aerni - Initial contribution
 */
public class Current extends GruenbeckMessage {
    /*
     * {
     * "id":"BSxxxxxxxx",
     * "type":"Current",
     * "ibuiltindev":true,
     * "isncu":"202111171126",
     * "mregpercent1":52,
     * "mregpercent2":0,
     * "mremregstep":0,
     * "mregstatus":0,
     * "mresidcap1":73,
     * "mresidcap2":0,
     * "mrescapa1":1,
     * "mrescapa2":0,
     * "mmaint":356,
     * "mflow1":0,
     * "mflow2":0,
     * "mflowreg1":0,
     * "mflowreg2":0,
     * "mflowblend":0,
     * "mstep1":4950,
     * "mstep2":0,
     * "mcurrent":0,
     * "mreswatadmod":0,
     * "msaltrange":99
     * }
     */

    private Double mregpercent1;
    private Double mregpercent2;
    private Double mremregstep;
    private Integer mregstatus;
    private Double mresidcap1;
    private Double mresidcap2;
    private Double mrescapa1;
    private Double mrescapa2;
    private Integer mmaint;
    private Double mflow1;
    private Double mflow2;
    private Double mflowreg1;
    private Double mflowreg2;
    private Double mflowblend;
    private Integer mstep1;
    private Integer mstep2;
    private Double mcurrent;
    private Double mreswatadmod;
    private Double msaltrange;

    public Current() {
    }

    public Double getMregpercent1() {
        return mregpercent1;
    }

    public void setMregpercent1(Double mregpercent1) {
        this.mregpercent1 = mregpercent1;
    }

    public Double getMregpercent2() {
        return mregpercent2;
    }

    public void setMregpercent2(Double mregpercent2) {
        this.mregpercent2 = mregpercent2;
    }

    public Double getMremregstep() {
        return mremregstep;
    }

    public void setMremregstep(Double mremregstep) {
        this.mremregstep = mremregstep;
    }

    public Integer getMregstatus() {
        return mregstatus;
    }

    public void setMregstatus(Integer mregstatus) {
        this.mregstatus = mregstatus;
    }

    public Double getMresidcap1() {
        return mresidcap1;
    }

    public void setMresidcap1(Double mresidcap1) {
        this.mresidcap1 = mresidcap1;
    }

    public Double getMresidcap2() {
        return mresidcap2;
    }

    public void setMresidcap2(Double mresidcap2) {
        this.mresidcap2 = mresidcap2;
    }

    public Double getMrescapa1() {
        return mrescapa1;
    }

    public void setMrescapa1(Double mrescapa1) {
        this.mrescapa1 = mrescapa1;
    }

    public Double getMrescapa2() {
        return mrescapa2;
    }

    public void setMrescapa2(Double mrescapa2) {
        this.mrescapa2 = mrescapa2;
    }

    public Integer getMmaint() {
        return mmaint;
    }

    public void setMmaint(Integer mmaint) {
        this.mmaint = mmaint;
    }

    public Double getMflow1() {
        return mflow1;
    }

    public void setMflow1(Double mflow1) {
        this.mflow1 = mflow1;
    }

    public Double getMflow2() {
        return mflow2;
    }

    public void setMflow2(Double mflow2) {
        this.mflow2 = mflow2;
    }

    public Double getMflowreg1() {
        return mflowreg1;
    }

    public void setMflowreg1(Double mflowreg1) {
        this.mflowreg1 = mflowreg1;
    }

    public Double getMflowreg2() {
        return mflowreg2;
    }

    public void setMflowreg2(Double mflowreg2) {
        this.mflowreg2 = mflowreg2;
    }

    public Double getMflowblend() {
        return mflowblend;
    }

    public void setMflowblend(Double mflowblend) {
        this.mflowblend = mflowblend;
    }

    public Integer getMstep1() {
        return mstep1;
    }

    public void setMstep1(Integer mstep1) {
        this.mstep1 = mstep1;
    }

    public Integer getMstep2() {
        return mstep2;
    }

    public void setMstep2(Integer mstep2) {
        this.mstep2 = mstep2;
    }

    public Double getMcurrent() {
        return mcurrent;
    }

    public void setMcurrent(Double mcurrent) {
        this.mcurrent = mcurrent;
    }

    public Double getMreswatadmod() {
        return mreswatadmod;
    }

    public void setMreswatadmod(Double mreswatadmod) {
        this.mreswatadmod = mreswatadmod;
    }

    public Double getMsaltrange() {
        return msaltrange;
    }

    public void setMsaltrange(Double msaltrange) {
        this.msaltrange = msaltrange;
    }
}
