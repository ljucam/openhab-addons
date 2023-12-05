package org.openhab.binding.gruenbeckcloud.internal.api.model;

/**
 * The {@link CurrSlow} class holds data retrieved by the websocket.
 *
 * @author Mario Aerni - Initial contribution
 */
public class CurrSlow extends GruenbeckMessage {

    /*
     * {
     * "id":"BSxxxxxxxx",
     * "type":"CurrSlow",
     * "ibuiltindev":true,
     * "isncu":"202111171126",
     * "mcountreg":4,
     * "mcountwater1":2696,
     * "mcountwater2":0,
     * "mcountwatertank":3,
     * "msaltusage":1.3765,
     * "mflowexc":0,
     * "mflowexc2reg1":0,
     * "mflowexc1reg2":0,
     * "mlifeadsorb":0,
     * "mhardsoftw":5,
     * "mcapacity":20,
     * "maverage":99,
     * "mstddev":0,
     * "mmax":0,
     * "mpress":0,
     * "mtemp":0,
     * "mflowmax":2.06,
     * "mflowmax1reg2":0,
     * "mflowmax2reg1":0,
     * "mendreg1":"01:44",
     * "mendreg2":"00:00"
     * }
     */

    private Double mcountreg;
    private Double mcountwater1;
    private Double mcountwater2;
    private Double mcountwatertank;
    private Double msaltusage;
    private Double mflowexc;
    private Double mflowexc2reg1;
    private Double mflowexc1reg2;
    private Double mlifeadsorb;
    private Double mhardsoftw;
    private Double mcapacity;
    private Double maverage;
    private Double mstddev;
    private Double mmax;
    private Double mpress;
    private Double mtemp;
    private Double mflowmax;
    private Double mflowmax1reg2;
    private Double mflowmax2reg1;
    private String mendreg1;
    private String mendreg2;

    public CurrSlow() {
    }

    public Double getMcountreg() {
        return mcountreg;
    }

    public void setMcountreg(Double mcountreg) {
        this.mcountreg = mcountreg;
    }

    public Double getMcountwater1() {
        return mcountwater1;
    }

    public void setMcountwater1(Double mcountwater1) {
        this.mcountwater1 = mcountwater1;
    }

    public Double getMcountwater2() {
        return mcountwater2;
    }

    public void setMcountwater2(Double mcountwater2) {
        this.mcountwater2 = mcountwater2;
    }

    public Double getMcountwatertank() {
        return mcountwatertank;
    }

    public void setMcountwatertank(Double mcountwatertank) {
        this.mcountwatertank = mcountwatertank;
    }

    public Double getMsaltusage() {
        return msaltusage;
    }

    public void setMsaltusage(Double msaltusage) {
        this.msaltusage = msaltusage;
    }

    public Double getMflowexc() {
        return mflowexc;
    }

    public void setMflowexc(Double mflowexc) {
        this.mflowexc = mflowexc;
    }

    public Double getMflowexc2reg1() {
        return mflowexc2reg1;
    }

    public void setMflowexc2reg1(Double mflowexc2reg1) {
        this.mflowexc2reg1 = mflowexc2reg1;
    }

    public Double getMflowexc1reg2() {
        return mflowexc1reg2;
    }

    public void setMflowexc1reg2(Double mflowexc1reg2) {
        this.mflowexc1reg2 = mflowexc1reg2;
    }

    public Double getMlifeadsorb() {
        return mlifeadsorb;
    }

    public void setMlifeadsorb(Double mlifeadsorb) {
        this.mlifeadsorb = mlifeadsorb;
    }

    public Double getMhardsoftw() {
        return mhardsoftw;
    }

    public void setMhardsoftw(Double mhardsoftw) {
        this.mhardsoftw = mhardsoftw;
    }

    public Double getMcapacity() {
        return mcapacity;
    }

    public void setMcapacity(Double mcapacity) {
        this.mcapacity = mcapacity;
    }

    public Double getMaverage() {
        return maverage;
    }

    public void setMaverage(Double maverage) {
        this.maverage = maverage;
    }

    public Double getMstddev() {
        return mstddev;
    }

    public void setMstddev(Double mstddev) {
        this.mstddev = mstddev;
    }

    public Double getMmax() {
        return mmax;
    }

    public void setMmax(Double mmax) {
        this.mmax = mmax;
    }

    public Double getMpress() {
        return mpress;
    }

    public void setMpress(Double mpress) {
        this.mpress = mpress;
    }

    public Double getMtemp() {
        return mtemp;
    }

    public void setMtemp(Double mtemp) {
        this.mtemp = mtemp;
    }

    public Double getMflowmax() {
        return mflowmax;
    }

    public void setMflowmax(Double mflowmax) {
        this.mflowmax = mflowmax;
    }

    public Double getMflowmax1reg2() {
        return mflowmax1reg2;
    }

    public void setMflowmax1reg2(Double mflowmax1reg2) {
        this.mflowmax1reg2 = mflowmax1reg2;
    }

    public Double getMflowmax2reg1() {
        return mflowmax2reg1;
    }

    public void setMflowmax2reg1(Double mflowmax2reg1) {
        this.mflowmax2reg1 = mflowmax2reg1;
    }

    public String getMendreg1() {
        return mendreg1;
    }

    public void setMendreg1(String mendreg1) {
        this.mendreg1 = mendreg1;
    }

    public String getMendreg2() {
        return mendreg2;
    }

    public void setMendreg2(String mendreg2) {
        this.mendreg2 = mendreg2;
    }
}
