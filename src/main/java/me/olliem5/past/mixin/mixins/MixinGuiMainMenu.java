package me.olliem5.past.mixin.mixins;

import me.olliem5.past.Past;
import me.olliem5.past.util.ColourUtil;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenu extends GuiScreen {

    @Inject(method = "drawScreen", at = @At("TAIL"), cancellable = true)
    public void drawText(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        mc.fontRenderer.drawStringWithShadow(Past.nameversion, 2, 2, ColourUtil.getMultiColour().getRGB());
    }
}

