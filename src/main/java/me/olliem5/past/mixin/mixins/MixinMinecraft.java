package me.olliem5.past.mixin.mixins;

import me.olliem5.past.mixin.accessors.MinecraftAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Minecraft.class)
public class MixinMinecraft implements MinecraftAccessor {

    @Final
    @Shadow
    private Timer timer;

    @Override
    public Timer getTimer() {
        return timer;
    }
}
