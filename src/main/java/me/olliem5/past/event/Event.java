package me.olliem5.past.event;

import me.olliem5.past.util.client.WrapperUtil;
import me.zero.alpine.type.Cancellable;

public class Event extends Cancellable {
    private Era era = Era.PRE;
    private final float partialTicks;

    public Event() {
        partialTicks = WrapperUtil.getMinecraft().getRenderPartialTicks();
    }

    public Era getEra() {
        return era;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public enum Era {
        PRE, PERI, POST
    }
}
