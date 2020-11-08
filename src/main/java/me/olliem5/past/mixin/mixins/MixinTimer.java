package me.olliem5.past.mixin.mixins;

import me.olliem5.past.mixin.accessors.TimerAccessor;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Timer.class)
public class MixinTimer implements TimerAccessor {

    @Shadow
    private float tickLength;

    @Override
    public float getTickLength() {
        return tickLength;
    }

    @Override
    public void setTickLength(float tickLength) {
        this.tickLength = tickLength;
    }
}
