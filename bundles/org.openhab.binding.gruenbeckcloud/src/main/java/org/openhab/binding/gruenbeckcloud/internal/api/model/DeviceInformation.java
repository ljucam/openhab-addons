package org.openhab.binding.gruenbeckcloud.internal.api.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * The {@link DeviceInformation} class is a model class containing device information retrieved from gruenbeck cloud.
 *
 * @author Mario Aerni - Initial contribution
 */
@JsonIgnoreProperties(ignoreUnknown = false)
public class DeviceInformation {
    /*
     * {
     * "$pending":[],
     * "pallowemail":true,
     * "pallowpushnotification":true,
     * "pdlstauto":true,
     * "pntpsync":true,
     * "pcfcontact":true,
     * "pknx":false,
     * "pmonflow":false,
     * "pmondisinf":true,
     * "pledatsaltpre":true,
     * "pbuzzer":false,
     * "prescaplimit":50,
     * "pcurrent":700,
     * "pload":2000,
     * "pforcedregdist":4,
     * "pmaintint":365,
     * "pfreqregvalve":140,
     * "pfreqblendvalve":300,
     * "pledbright":100,
     * "pvolume":0,
     * "prawhard":14.6067,
     * "psetsoft":5.618,
     * "ppratesoftwater":0.0043,
     * "pprateblending":0.004,
     * "pprateregwater":0.0039000000000000003,
     * "psetcapmo":20,
     * "psetcaptu":20,
     * "psetcapwe":20,
     * "psetcapth":20,
     * "psetcapfr":20,
     * "psetcapsa":20,
     * "psetcapsu":20,
     * "pnomflow":2.1,
     * "ppressurereg":0,
     * "pmonregmeter":20,
     * "pmonsalting":75,
     * "prinsing":9.5,
     * "pbackwash":12,
     * "pwashingout":4,
     * "pminvolmincap":0.17,
     * "pmaxvolmincap":0.49,
     * "pminvolmaxcap":0.8200000000000001,
     * "pmaxvolmaxcap":2.15,
     * "pmaxdurdisinfect":4,
     * "pmaxresdurreg":0,
     * "pbuzzfrom":"08:00",
     * "pbuzzto":"18:00",
     * "pmailadress":"test@testus.ch",
     * "pname":"A secret organization",
     * "ptelnr":"012 345 67 89",
     * "pmode":2,
     * "pmodemo":2,
     * "pmodetu":1,
     * "pmodewe":1,
     * "pmodeth":1,
     * "pmodefr":2,
     * "pmodesa":3,
     * "pmodesu":3,
     * "planguage":1,
     * "phunit":2,
     * "pregmode":0,
     * "pprogout":1,
     * "pprogin":0,
     * "ppowerfail":0,
     * "pmodedesinf":1,
     * "pled":4,
     * "pregmo1":"07:00",
     * "pregmo2":"07:00",
     * "pregmo3":"07:00",
     * "pregtu1":"07:00",
     * "pregtu2":"07:00",
     * "pregtu3":"07:00",
     * "pregwe1":"07:00",
     * "pregwe2":"07:00",
     * "pregwe3":"07:00",
     * "pregth1":"07:00",
     * "pregth2":"07:00",
     * "pregth3":"07:00",
     * "pregfr1":"07:00",
     * "pregfr2":"07:00",
     * "pregfr3":"07:00",
     * "pregsa1":"--:--",
     * "pregsa2":"--:--",
     * "pregsa3":"--:--",
     * "pregsu1":"--:--",
     * "pregsu2":"--:--",
     * "pregsu3":"--:--",
     * "pmonblend":0,
     * "poverload":0,
     * "pfreqregvalve2":200
     * }
     */

    private List<JsonNode> pending = new ArrayList<>();
    private Boolean pallowemail;
    private Boolean pallowpushnotification;
    private Boolean pdlstauto;
    private Boolean pntpsync;
    private Boolean pcfcontact;
    private Boolean pknx;
    private Boolean pmonflow;
    private Boolean pmondisinf;
    private Boolean pledatsaltpre;
    private Boolean pbuzzer;
    private Double prescaplimit;
    private Double pcurrent;
    private Double pload;
    private Double pforcedregdist;
    private Double pmaintint;
    private Double pfreqregvalve;
    private Double pfreqblendvalve;
    private Integer pledbright;
    private Double pvolume;
    private Double prawhard;
    private Double psetsoft;
    private Double ppratesoftwater;
    private Double pprateblending;
    private Double pprateregwater;
    private Double psetcapmo;
    private Double psetcaptu;
    private Double psetcapwe;
    private Double psetcapth;
    private Double psetcapfr;
    private Double psetcapsa;
    private Double psetcapsu;
    private Double pnomflow;
    private Double ppressurereg;
    private Double pmonregmeter;
    private Double pmonsalting;
    private Double prinsing;
    private Double pbackwash;
    private Double pwashingout;
    private Double pminvolmincap;
    private Double pmaxvolmincap;
    private Double pminvolmaxcap;
    private Double pmaxvolmaxcap;
    private Double pmaxdurdisinfect;
    private Double pmaxresdurreg;
    private String pbuzzfrom;
    private String pbuzzto;
    private String pmailadress;
    private String pname;
    private String ptelnr;
    private Integer pmode;
    private Integer pmodemo;
    private Integer pmodetu;
    private Integer pmodewe;
    private Integer pmodeth;
    private Integer pmodefr;
    private Integer pmodesa;
    private Integer pmodesu;
    private Integer planguage;
    private Integer phunit;
    private Integer pregmode;
    private Integer pprogout;
    private Integer pprogin;
    private Integer ppowerfail;
    private Integer pmodedesinf;
    private Integer pled;
    private String pregmo1;
    private String pregmo2;
    private String pregmo3;
    private String pregtu1;
    private String pregtu2;
    private String pregtu3;
    private String pregwe1;
    private String pregwe2;
    private String pregwe3;
    private String pregth1;
    private String pregth2;
    private String pregth3;
    private String pregfr1;
    private String pregfr2;
    private String pregfr3;
    private String pregsa1;
    private String pregsa2;
    private String pregsa3;
    private String pregsu1;
    private String pregsu2;
    private String pregsu3;
    private Double pmonblend;
    private Double poverload;
    private Double pfreqregvalve2;

    public DeviceInformation() {
    }

    /*
     * Pending only appears to be present in the response,
     * after a patch request (sending a parameter) was
     * sent. It is a list of strings containing the
     * corresponding parameters.
     */
    public List<JsonNode> getPending() {
        return pending;
    }

    @JsonProperty("$pending")
    public void setPending(List<JsonNode> pending) {
        this.pending = pending;
    }

    public Boolean isPallowemail() {
        return pallowemail;
    }

    public void setPallowemail(Boolean pallowemail) {
        this.pallowemail = pallowemail;
    }

    public Boolean isPallowpushnotification() {
        return pallowpushnotification;
    }

    public void setPallowpushnotification(Boolean pallowpushnotification) {
        this.pallowpushnotification = pallowpushnotification;
    }

    public Boolean isPdlstauto() {
        return pdlstauto;
    }

    public void setPdlstauto(Boolean pdlstauto) {
        this.pdlstauto = pdlstauto;
    }

    public Boolean isPntpsync() {
        return pntpsync;
    }

    public void setPntpsync(Boolean pntpsync) {
        this.pntpsync = pntpsync;
    }

    public Boolean isPcfcontact() {
        return pcfcontact;
    }

    public void setPcfcontact(Boolean pcfcontact) {
        this.pcfcontact = pcfcontact;
    }

    public Boolean isPknx() {
        return pknx;
    }

    public void setPknx(Boolean pknx) {
        this.pknx = pknx;
    }

    public Boolean isPmonflow() {
        return pmonflow;
    }

    public void setPmonflow(Boolean pmonflow) {
        this.pmonflow = pmonflow;
    }

    public Boolean isPmondisinf() {
        return pmondisinf;
    }

    public void setPmondisinf(Boolean pmondisinf) {
        this.pmondisinf = pmondisinf;
    }

    public Boolean isPledatsaltpre() {
        return pledatsaltpre;
    }

    public void setPledatsaltpre(Boolean pledatsaltpre) {
        this.pledatsaltpre = pledatsaltpre;
    }

    public Boolean isPbuzzer() {
        return pbuzzer;
    }

    public void setPbuzzer(Boolean pbuzzer) {
        this.pbuzzer = pbuzzer;
    }

    public Double getPrescaplimit() {
        return prescaplimit;
    }

    public void setPrescaplimit(Double prescaplimit) {
        this.prescaplimit = prescaplimit;
    }

    public Double getPcurrent() {
        return pcurrent;
    }

    public void setPcurrent(Double pcurrent) {
        this.pcurrent = pcurrent;
    }

    public Double getPload() {
        return pload;
    }

    public void setPload(Double pload) {
        this.pload = pload;
    }

    public Double getPforcedregdist() {
        return pforcedregdist;
    }

    public void setPforcedregdist(Double pforcedregdist) {
        this.pforcedregdist = pforcedregdist;
    }

    public Double getPmaintint() {
        return pmaintint;
    }

    public void setPmaintint(Double pmaintint) {
        this.pmaintint = pmaintint;
    }

    public Double getPfreqregvalve() {
        return pfreqregvalve;
    }

    public void setPfreqregvalve(Double pfreqregvalve) {
        this.pfreqregvalve = pfreqregvalve;
    }

    public Double getPfreqblendvalve() {
        return pfreqblendvalve;
    }

    public void setPfreqblendvalve(Double pfreqblendvalve) {
        this.pfreqblendvalve = pfreqblendvalve;
    }

    public Integer getPledbright() {
        return pledbright;
    }

    public void setPledbright(Integer pledbright) {
        this.pledbright = pledbright;
    }

    public Double getPvolume() {
        return pvolume;
    }

    public void setPvolume(Double pvolume) {
        this.pvolume = pvolume;
    }

    public Double getPrawhard() {
        return prawhard;
    }

    public void setPrawhard(Double prawhard) {
        this.prawhard = prawhard;
    }

    public Double getPsetsoft() {
        return psetsoft;
    }

    public void setPsetsoft(Double psetsoft) {
        this.psetsoft = psetsoft;
    }

    public Double getPpratesoftwater() {
        return ppratesoftwater;
    }

    public void setPpratesoftwater(Double ppratesoftwater) {
        this.ppratesoftwater = ppratesoftwater;
    }

    public Double getPprateblending() {
        return pprateblending;
    }

    public void setPprateblending(Double pprateblending) {
        this.pprateblending = pprateblending;
    }

    public Double getPprateregwater() {
        return pprateregwater;
    }

    public void setPprateregwater(Double pprateregwater) {
        this.pprateregwater = pprateregwater;
    }

    public Double getPsetcapmo() {
        return psetcapmo;
    }

    public void setPsetcapmo(Double psetcapmo) {
        this.psetcapmo = psetcapmo;
    }

    public Double getPsetcaptu() {
        return psetcaptu;
    }

    public void setPsetcaptu(Double psetcaptu) {
        this.psetcaptu = psetcaptu;
    }

    public Double getPsetcapwe() {
        return psetcapwe;
    }

    public void setPsetcapwe(Double psetcapwe) {
        this.psetcapwe = psetcapwe;
    }

    public Double getPsetcapth() {
        return psetcapth;
    }

    public void setPsetcapth(Double psetcapth) {
        this.psetcapth = psetcapth;
    }

    public Double getPsetcapfr() {
        return psetcapfr;
    }

    public void setPsetcapfr(Double psetcapfr) {
        this.psetcapfr = psetcapfr;
    }

    public Double getPsetcapsa() {
        return psetcapsa;
    }

    public void setPsetcapsa(Double psetcapsa) {
        this.psetcapsa = psetcapsa;
    }

    public Double getPsetcapsu() {
        return psetcapsu;
    }

    public void setPsetcapsu(Double psetcapsu) {
        this.psetcapsu = psetcapsu;
    }

    public Double getPnomflow() {
        return pnomflow;
    }

    public void setPnomflow(Double pnomflow) {
        this.pnomflow = pnomflow;
    }

    public Double getPpressurereg() {
        return ppressurereg;
    }

    public void setPpressurereg(Double ppressurereg) {
        this.ppressurereg = ppressurereg;
    }

    public Double getPmonregmeter() {
        return pmonregmeter;
    }

    public void setPmonregmeter(Double pmonregmeter) {
        this.pmonregmeter = pmonregmeter;
    }

    public Double getPmonsalting() {
        return pmonsalting;
    }

    public void setPmonsalting(Double pmonsalting) {
        this.pmonsalting = pmonsalting;
    }

    public Double getPrinsing() {
        return prinsing;
    }

    public void setPrinsing(Double prinsing) {
        this.prinsing = prinsing;
    }

    public Double getPbackwash() {
        return pbackwash;
    }

    public void setPbackwash(Double pbackwash) {
        this.pbackwash = pbackwash;
    }

    public Double getPwashingout() {
        return pwashingout;
    }

    public void setPwashingout(Double pwashingout) {
        this.pwashingout = pwashingout;
    }

    public Double getPminvolmincap() {
        return pminvolmincap;
    }

    public void setPminvolmincap(Double pminvolmincap) {
        this.pminvolmincap = pminvolmincap;
    }

    public Double getPmaxvolmincap() {
        return pmaxvolmincap;
    }

    public void setPmaxvolmincap(Double pmaxvolmincap) {
        this.pmaxvolmincap = pmaxvolmincap;
    }

    public Double getPminvolmaxcap() {
        return pminvolmaxcap;
    }

    public void setPminvolmaxcap(Double pminvolmaxcap) {
        this.pminvolmaxcap = pminvolmaxcap;
    }

    public Double getPmaxvolmaxcap() {
        return pmaxvolmaxcap;
    }

    public void setPmaxvolmaxcap(Double pmaxvolmaxcap) {
        this.pmaxvolmaxcap = pmaxvolmaxcap;
    }

    public Double getPmaxdurdisinfect() {
        return pmaxdurdisinfect;
    }

    public void setPmaxdurdisinfect(Double pmaxdurdisinfect) {
        this.pmaxdurdisinfect = pmaxdurdisinfect;
    }

    public Double getPmaxresdurreg() {
        return pmaxresdurreg;
    }

    public void setPmaxresdurreg(Double pmaxresdurreg) {
        this.pmaxresdurreg = pmaxresdurreg;
    }

    public String getPbuzzfrom() {
        return pbuzzfrom;
    }

    public void setPbuzzfrom(String pbuzzfrom) {
        this.pbuzzfrom = pbuzzfrom;
    }

    public String getPbuzzto() {
        return pbuzzto;
    }

    public void setPbuzzto(String pbuzzto) {
        this.pbuzzto = pbuzzto;
    }

    public String getPmailadress() {
        return pmailadress;
    }

    public void setPmailadress(String pmailadress) {
        this.pmailadress = pmailadress;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPtelnr() {
        return ptelnr;
    }

    public void setPtelnr(String ptelnr) {
        this.ptelnr = ptelnr;
    }

    public Integer getPmode() {
        return pmode;
    }

    public void setPmode(Integer pmode) {
        this.pmode = pmode;
    }

    public Integer getPmodemo() {
        return pmodemo;
    }

    public void setPmodemo(Integer pmodemo) {
        this.pmodemo = pmodemo;
    }

    public Integer getPmodetu() {
        return pmodetu;
    }

    public void setPmodetu(Integer pmodetu) {
        this.pmodetu = pmodetu;
    }

    public Integer getPmodewe() {
        return pmodewe;
    }

    public void setPmodewe(Integer pmodewe) {
        this.pmodewe = pmodewe;
    }

    public Integer getPmodeth() {
        return pmodeth;
    }

    public void setPmodeth(Integer pmodeth) {
        this.pmodeth = pmodeth;
    }

    public Integer getPmodefr() {
        return pmodefr;
    }

    public void setPmodefr(Integer pmodefr) {
        this.pmodefr = pmodefr;
    }

    public Integer getPmodesa() {
        return pmodesa;
    }

    public void setPmodesa(Integer pmodesa) {
        this.pmodesa = pmodesa;
    }

    public Integer getPmodesu() {
        return pmodesu;
    }

    public void setPmodesu(Integer pmodesu) {
        this.pmodesu = pmodesu;
    }

    public Integer getPlanguage() {
        return planguage;
    }

    public void setPlanguage(Integer planguage) {
        this.planguage = planguage;
    }

    public Integer getPhunit() {
        return phunit;
    }

    public void setPhunit(Integer phunit) {
        this.phunit = phunit;
    }

    public Integer getPregmode() {
        return pregmode;
    }

    public void setPregmode(Integer pregmode) {
        this.pregmode = pregmode;
    }

    public Integer getPprogout() {
        return pprogout;
    }

    public void setPprogout(Integer pprogout) {
        this.pprogout = pprogout;
    }

    public Integer getPprogin() {
        return pprogin;
    }

    public void setPprogin(Integer pprogin) {
        this.pprogin = pprogin;
    }

    public Integer getPpowerfail() {
        return ppowerfail;
    }

    public void setPpowerfail(Integer ppowerfail) {
        this.ppowerfail = ppowerfail;
    }

    public Integer getPmodedesinf() {
        return pmodedesinf;
    }

    public void setPmodedesinf(Integer pmodedesinf) {
        this.pmodedesinf = pmodedesinf;
    }

    public Integer getPled() {
        return pled;
    }

    public void setPled(Integer pled) {
        this.pled = pled;
    }

    public String getPregmo1() {
        return pregmo1;
    }

    public void setPregmo1(String pregmo1) {
        this.pregmo1 = pregmo1;
    }

    public String getPregmo2() {
        return pregmo2;
    }

    public void setPregmo2(String pregmo2) {
        this.pregmo2 = pregmo2;
    }

    public String getPregmo3() {
        return pregmo3;
    }

    public void setPregmo3(String pregmo3) {
        this.pregmo3 = pregmo3;
    }

    public String getPregtu1() {
        return pregtu1;
    }

    public void setPregtu1(String pregtu1) {
        this.pregtu1 = pregtu1;
    }

    public String getPregtu2() {
        return pregtu2;
    }

    public void setPregtu2(String pregtu2) {
        this.pregtu2 = pregtu2;
    }

    public String getPregtu3() {
        return pregtu3;
    }

    public void setPregtu3(String pregtu3) {
        this.pregtu3 = pregtu3;
    }

    public String getPregwe1() {
        return pregwe1;
    }

    public void setPregwe1(String pregwe1) {
        this.pregwe1 = pregwe1;
    }

    public String getPregwe2() {
        return pregwe2;
    }

    public void setPregwe2(String pregwe2) {
        this.pregwe2 = pregwe2;
    }

    public String getPregwe3() {
        return pregwe3;
    }

    public void setPregwe3(String pregwe3) {
        this.pregwe3 = pregwe3;
    }

    public String getPregth1() {
        return pregth1;
    }

    public void setPregth1(String pregth1) {
        this.pregth1 = pregth1;
    }

    public String getPregth2() {
        return pregth2;
    }

    public void setPregth2(String pregth2) {
        this.pregth2 = pregth2;
    }

    public String getPregth3() {
        return pregth3;
    }

    public void setPregth3(String pregth3) {
        this.pregth3 = pregth3;
    }

    public String getPregfr1() {
        return pregfr1;
    }

    public void setPregfr1(String pregfr1) {
        this.pregfr1 = pregfr1;
    }

    public String getPregfr2() {
        return pregfr2;
    }

    public void setPregfr2(String pregfr2) {
        this.pregfr2 = pregfr2;
    }

    public String getPregfr3() {
        return pregfr3;
    }

    public void setPregfr3(String pregfr3) {
        this.pregfr3 = pregfr3;
    }

    public String getPregsa1() {
        return pregsa1;
    }

    public void setPregsa1(String pregsa1) {
        this.pregsa1 = pregsa1;
    }

    public String getPregsa2() {
        return pregsa2;
    }

    public void setPregsa2(String pregsa2) {
        this.pregsa2 = pregsa2;
    }

    public String getPregsa3() {
        return pregsa3;
    }

    public void setPregsa3(String pregsa3) {
        this.pregsa3 = pregsa3;
    }

    public String getPregsu1() {
        return pregsu1;
    }

    public void setPregsu1(String pregsu1) {
        this.pregsu1 = pregsu1;
    }

    public String getPregsu2() {
        return pregsu2;
    }

    public void setPregsu2(String pregsu2) {
        this.pregsu2 = pregsu2;
    }

    public String getPregsu3() {
        return pregsu3;
    }

    public void setPregsu3(String pregsu3) {
        this.pregsu3 = pregsu3;
    }

    public Double getPmonblend() {
        return pmonblend;
    }

    public void setPmonblend(Double pmonblend) {
        this.pmonblend = pmonblend;
    }

    public Double getPoverload() {
        return poverload;
    }

    public void setPoverload(Double poverload) {
        this.poverload = poverload;
    }

    public Double getPfreqregvalve2() {
        return pfreqregvalve2;
    }

    public void setPfreqregvalve2(Double pfreqregvalve2) {
        this.pfreqregvalve2 = pfreqregvalve2;
    }
}
