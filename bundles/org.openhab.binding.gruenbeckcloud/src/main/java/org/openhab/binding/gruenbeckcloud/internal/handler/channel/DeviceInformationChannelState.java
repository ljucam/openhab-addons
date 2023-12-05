package org.openhab.binding.gruenbeckcloud.internal.handler.channel;

import org.openhab.binding.gruenbeckcloud.internal.api.model.DeviceInformation;
import org.openhab.core.types.State;

/**
 * Wrapper for {@link DeviceInformation} handling the type conversion to {@link State} for directly
 * filling channels.
 *
 * @author Mario Aerni - Initial contribution
 */
public class DeviceInformationChannelState {
    private final DeviceInformation deviceInformation;

    public DeviceInformationChannelState(DeviceInformation deviceInformation) {
        this.deviceInformation = deviceInformation != null ? deviceInformation : new DeviceInformation();
    }

    public State getPrescaplimitState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPrescaplimit());
    }

    public State getPcurrentState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPcurrent());
    }

    public State getPloadState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPload());
    }

    public State getPforcedregdistState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPforcedregdist());
    }

    public State getPmaintintState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPmaintint());
    }

    public State getPfreqregvalveState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPfreqregvalve());
    }

    public State getPfreqblendvalveState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPfreqblendvalve());
    }

    public State getPledbrightState() {
        return ChannelTypeUtil.intToPercentageState(deviceInformation.getPledbright());
    }

    public State getPvolumeState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPvolume());
    }

    public State getPrawhardState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPrawhard());
    }

    public State getPsetsoftState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPsetsoft());
    }

    public State getPpratesoftwaterState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPpratesoftwater());
    }

    public State getPprateblendingState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPprateblending());
    }

    public State getPprateregwaterState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPprateregwater());
    }

    public State getPsetcapmoState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPsetcapmo());
    }

    public State getPsetcaptuState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPsetcaptu());
    }

    public State getPsetcapweState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPsetcapwe());
    }

    public State getPsetcapthState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPsetcapth());
    }

    public State getPsetcapfrState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPsetcapfr());
    }

    public State getPsetcapsaState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPsetcapsa());
    }

    public State getPsetcapsuState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPsetcapsu());
    }

    public State getPnomflowState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPnomflow());
    }

    public State getPpressureregState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPpressurereg());
    }

    public State getPmonregmeterState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPmonregmeter());
    }

    public State getPmonsaltingState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPmonsalting());
    }

    public State getPrinsingState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPrinsing());
    }

    public State getPbackwashState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPbackwash());
    }

    public State getPwashingoutState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPwashingout());
    }

    public State getPminvolmincapState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPminvolmincap());
    }

    public State getPmaxvolmincapState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPmaxvolmincap());
    }

    public State getPminvolmaxcapState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPminvolmaxcap());
    }

    public State getPmaxvolmaxcapState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPmaxvolmaxcap());
    }

    public State getPmaxdurdisinfectState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPmaxdurdisinfect());
    }

    public State getPmaxresdurregState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPmaxresdurreg());
    }

    public State getPmonblendState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPmonblend());
    }

    public State getPoverloadState() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPoverload());
    }

    public State getPfreqregvalve2State() {
        return ChannelTypeUtil.doubleToState(deviceInformation.getPfreqregvalve2());
    }

    public State getPallowemailState() {
        return ChannelTypeUtil.booleanToState(deviceInformation.isPallowemail());
    }

    public State getPallowpushnotificationState() {
        return ChannelTypeUtil.booleanToState(deviceInformation.isPallowpushnotification());
    }

    public State getPdlstautoState() {
        return ChannelTypeUtil.booleanToState(deviceInformation.isPdlstauto());
    }

    public State getPntpsyncState() {
        return ChannelTypeUtil.booleanToState(deviceInformation.isPntpsync());
    }

    public State getPcfcontactState() {
        return ChannelTypeUtil.booleanToState(deviceInformation.isPcfcontact());
    }

    public State getPknxState() {
        return ChannelTypeUtil.booleanToState(deviceInformation.isPknx());
    }

    public State getPmonflowState() {
        return ChannelTypeUtil.booleanToState(deviceInformation.isPmonflow());
    }

    public State getPmondisinfState() {
        return ChannelTypeUtil.booleanToState(deviceInformation.isPmondisinf());
    }

    public State getPledatsaltpreState() {
        return ChannelTypeUtil.booleanToState(deviceInformation.isPledatsaltpre());
    }

    public State getPbuzzerState() {
        return ChannelTypeUtil.booleanToState(deviceInformation.isPbuzzer());
    }

    public State getPmodeState() {
        return ChannelTypeUtil.intToState(deviceInformation.getPmode());
    }

    public State getPmodemoState() {
        return ChannelTypeUtil.intToState(deviceInformation.getPmodemo());
    }

    public State getPmodetuState() {
        return ChannelTypeUtil.intToState(deviceInformation.getPmodetu());
    }

    public State getPmodeweState() {
        return ChannelTypeUtil.intToState(deviceInformation.getPmodewe());
    }

    public State getPmodethState() {
        return ChannelTypeUtil.intToState(deviceInformation.getPmodeth());
    }

    public State getPmodefrState() {
        return ChannelTypeUtil.intToState(deviceInformation.getPmodefr());
    }

    public State getPmodesaState() {
        return ChannelTypeUtil.intToState(deviceInformation.getPmodesa());
    }

    public State getPmodesuState() {
        return ChannelTypeUtil.intToState(deviceInformation.getPmodesu());
    }

    public State getPlanguageState() {
        return ChannelTypeUtil.intToState(deviceInformation.getPlanguage());
    }

    public State getPhunitState() {
        return ChannelTypeUtil.intToState(deviceInformation.getPhunit());
    }

    public State getPregmodeState() {
        return ChannelTypeUtil.intToState(deviceInformation.getPregmode());
    }

    public State getPprogoutState() {
        return ChannelTypeUtil.intToState(deviceInformation.getPprogout());
    }

    public State getPproginState() {
        return ChannelTypeUtil.intToState(deviceInformation.getPprogin());
    }

    public State getPpowerfailState() {
        return ChannelTypeUtil.intToState(deviceInformation.getPpowerfail());
    }

    public State getPmodedesinfState() {
        return ChannelTypeUtil.intToState(deviceInformation.getPmodedesinf());
    }

    public State getPledState() {
        return ChannelTypeUtil.intToState(deviceInformation.getPled());
    }

    public State getPbuzzfromState() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPbuzzfrom());
    }

    public State getPbuzztoState() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPbuzzto());
    }

    public State getPmailadressState() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPmailadress());
    }

    public State getPnameState() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPname());
    }

    public State getPtelnrState() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPtelnr());
    }

    public State getPregmo1State() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPregmo1());
    }

    public State getPregmo2State() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPregmo2());
    }

    public State getPregmo3State() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPregmo3());
    }

    public State getPregtu1State() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPregtu1());
    }

    public State getPregtu2State() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPregtu2());
    }

    public State getPregtu3State() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPregtu3());
    }

    public State getPregwe1State() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPregwe1());
    }

    public State getPregwe2State() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPregwe2());
    }

    public State getPregwe3State() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPregwe3());
    }

    public State getPregth1State() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPregth1());
    }

    public State getPregth2State() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPregth2());
    }

    public State getPregth3State() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPregth3());
    }

    public State getPregfr1State() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPregfr1());
    }

    public State getPregfr2State() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPregfr2());
    }

    public State getPregfr3State() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPregfr3());
    }

    public State getPregsa1State() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPregsa1());
    }

    public State getPregsa2State() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPregsa2());
    }

    public State getPregsa3State() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPregsa3());
    }

    public State getPregsu1State() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPregsu1());
    }

    public State getPregsu2State() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPregsu2());
    }

    public State getPregsu3State() {
        return ChannelTypeUtil.stringToState(deviceInformation.getPregsu3());
    }
}
