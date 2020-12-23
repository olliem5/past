package me.olliem5.past.impl.mixins;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenu extends GuiScreen {
    @Inject(method = "drawScreen", at = @At("TAIL"), cancellable = true)
    public void drawText(int mouseX, int mouseY, float partialTicks, CallbackInfo callbackInfo) {
        ResourceLocation resourceLocation = new ResourceLocation("pastclient", "pastbanner.png");
        mc.getTextureManager().bindTexture(resourceLocation);
        this.drawModalRectWithCustomSizedTexture(2, 2, 0, 0, 160, 32, 160, 32);
    }
}