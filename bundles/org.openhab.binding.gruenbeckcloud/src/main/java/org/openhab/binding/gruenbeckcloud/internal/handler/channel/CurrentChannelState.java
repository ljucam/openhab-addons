package org.openhab.binding.gruenbeckcloud.internal.handler.channel;

import org.openhab.binding.gruenbeckcloud.internal.api.model.Current;
import org.openhab.core.types.State;

/**
 * Wrapper for {@link Current} handling the type conversion to {@link State} for directly filling
 * channels.
 *
 * @author Mario Aerni - Initial contribution
 */
public class CurrentChannelState {
    private final Current current;

    public CurrentChannelState(Current current) {
        this.current = current != null ? current : new Current();
    }

    public State getMregpercent1State() {
        return ChannelTypeUtil.doubleToState(current.getMregpercent1());
    }

    public State getMregpercent2State() {
        return ChannelTypeUtil.doubleToState(current.getMregpercent2());
    }

    public State getMremregstepState() {
        return ChannelTypeUtil.doubleToState(current.getMremregstep());
    }

    public State getMregstatusState() {
        return ChannelTypeUtil.intToState(current.getMregstatus());
    }

    public State getMresidcap1State() {
        return ChannelTypeUtil.doubleToState(current.getMresidcap1());
    }

    public State getMresidcap2State() {
        return ChannelTypeUtil.doubleToState(current.getMresidcap2());
    }

    public State getMrescapa1State() {
        return ChannelTypeUtil.doubleToState(current.getMrescapa1());
    }

    public State getMrescapa2State() {
        return ChannelTypeUtil.doubleToState(current.getMrescapa2());
    }

    public State getMflow1State() {
        return ChannelTypeUtil.doubleToState(current.getMflow1());
    }

    public State getMflow2State() {
        return ChannelTypeUtil.doubleToState(current.getMflow2());
    }

    public State getMflowreg1State() {
        return ChannelTypeUtil.doubleToState(current.getMflowreg1());
    }

    public State getMflowreg2State() {
        return ChannelTypeUtil.doubleToState(current.getMflowreg2());
    }

    public State getMflowblendState() {
        return ChannelTypeUtil.doubleToState(current.getMflowblend());
    }

    public State getMcurrentState() {
        return ChannelTypeUtil.doubleToState(current.getMcurrent());
    }

    public State getMreswatadmodState() {
        return ChannelTypeUtil.doubleToState(current.getMreswatadmod());
    }

    public State getMsaltrangeState() {
        return ChannelTypeUtil.doubleToState(current.getMsaltrange());
    }

    public State getMmaintState() {
        return ChannelTypeUtil.intToState(current.getMmaint());
    }

    public State getMstep1State() {
        return ChannelTypeUtil.intToState(current.getMstep1());
    }

    public State getMstep2State() {
        return ChannelTypeUtil.intToState(current.getMstep2());
    }

    public State getIbuiltindevState() {
        return ChannelTypeUtil.booleanToState(current.isIbuiltindev());
    }

    public State getIsncuState() {
        return ChannelTypeUtil.stringToState(current.getIsncu());
    }
}
