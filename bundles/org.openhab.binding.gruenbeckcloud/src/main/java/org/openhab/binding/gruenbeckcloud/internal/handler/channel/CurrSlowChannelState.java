package org.openhab.binding.gruenbeckcloud.internal.handler.channel;

import org.openhab.binding.gruenbeckcloud.internal.api.model.CurrSlow;
import org.openhab.core.types.State;

/**
 * Wrapper for {@link CurrSlow} handling the type conversion to {@link State} for directly filling
 * channels.
 *
 * @author Mario Aerni - Initial contribution
 */
public class CurrSlowChannelState {

    private final CurrSlow currSlow;

    public CurrSlowChannelState(CurrSlow currSlow) {
        this.currSlow = currSlow != null ? currSlow : new CurrSlow();
    }

    public State getMcountregState() {
        return ChannelTypeUtil.doubleToState(currSlow.getMcountreg());
    }

    public State getMcountwater1State() {
        return ChannelTypeUtil.doubleToState(currSlow.getMcountwater1());
    }

    public State getMcountwater2State() {
        return ChannelTypeUtil.doubleToState(currSlow.getMcountwater2());
    }

    public State getMcountwatertankState() {
        return ChannelTypeUtil.doubleToState(currSlow.getMcountwatertank());
    }

    public State getMsaltusageState() {
        return ChannelTypeUtil.doubleToState(currSlow.getMsaltusage());
    }

    public State getMflowexcState() {
        return ChannelTypeUtil.doubleToState(currSlow.getMflowexc());
    }

    public State getMflowexc2reg1State() {
        return ChannelTypeUtil.doubleToState(currSlow.getMflowexc2reg1());
    }

    public State getMflowexc1reg2State() {
        return ChannelTypeUtil.doubleToState(currSlow.getMflowexc1reg2());
    }

    public State getMlifeadsorbState() {
        return ChannelTypeUtil.doubleToState(currSlow.getMlifeadsorb());
    }

    public State getMhardsoftwState() {
        return ChannelTypeUtil.doubleToState(currSlow.getMhardsoftw());
    }

    public State getMcapacityState() {
        return ChannelTypeUtil.doubleToState(currSlow.getMcapacity());
    }

    public State getMaverageState() {
        return ChannelTypeUtil.doubleToState(currSlow.getMaverage());
    }

    public State getMstddevState() {
        return ChannelTypeUtil.doubleToState(currSlow.getMstddev());
    }

    public State getMmaxState() {
        return ChannelTypeUtil.doubleToState(currSlow.getMmax());
    }

    public State getMpressState() {
        return ChannelTypeUtil.doubleToState(currSlow.getMpress());
    }

    public State getMtempState() {
        return ChannelTypeUtil.doubleToState(currSlow.getMtemp());
    }

    public State getMflowmaxState() {
        return ChannelTypeUtil.doubleToState(currSlow.getMflowmax());
    }

    public State getMflowmax1reg2State() {
        return ChannelTypeUtil.doubleToState(currSlow.getMflowmax1reg2());
    }

    public State getMflowmax2reg1State() {
        return ChannelTypeUtil.doubleToState(currSlow.getMflowmax2reg1());
    }

    public State getMendreg1State() {
        return ChannelTypeUtil.stringToState(currSlow.getMendreg1());
    }

    public State getMendreg2State() {
        return ChannelTypeUtil.stringToState(currSlow.getMendreg2());
    }
}
