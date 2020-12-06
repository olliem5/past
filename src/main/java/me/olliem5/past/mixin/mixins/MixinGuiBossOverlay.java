package me.olliem5.past.mixin.mixins;

import me.olliem5.past.Past;
import net.minecraft.client.gui.GuiBossOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiBossOverlay.class)
public class MixinGuiBossOverlay {
    @Inject(method = "renderBossHealth", at = @At("HEAD"), cancellable = true)
    private void renderBossHealth(CallbackInfo callbackInfo) {
        if (Past.moduleManager.getModuleByName("NoRender").isToggled() && Past.settingsManager.getSettingID("NoRenderBossBar").getValBoolean()) {
            callbackInfo.cancel();
        }
    }
}

