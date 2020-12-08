package me.olliem5.past.mixin.mixins;

import me.olliem5.past.Past;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {
    @Inject(method = "hurtCameraEffect", at = @At("HEAD"), cancellable = true)
    public void hurtCameraEffect(float ticks, CallbackInfo callbackInfo) {
        if (Past.moduleManager.getModuleByName("NoRender").isToggled() && Past.settingsManager.getSettingID("NoRenderHurtCam").getValBoolean()) {
            callbackInfo.cancel();
        }
    }
}