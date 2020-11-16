package me.olliem5.past.mixin.mixins;

import me.olliem5.past.Past;
import me.olliem5.past.util.ColourUtil;
import me.olliem5.past.util.StringUtil;
import net.minecraft.client.gui.Gui;
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
    public void drawText(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        int authorspace = Past.customFontRenderer.getHeight() + 6;
        int githubspace = Past.customFontRenderer.getHeight() + Past.customFontRenderer.getHeight() + 8;

        //This is wack
        int boxwidth = StringUtil.getStringWidthCustomFont(Past.github + "..................");
        int boxheight = Past.customFontRenderer.getHeight() + Past.customFontRenderer.getHeight() + 18;

        Gui.drawRect(2, 2, 4 + boxwidth + 4, boxheight, 0x75101010);

        Past.customFontRenderer.drawStringWithShadow(Past.nameversion, boxheight + 4, 4, ColourUtil.getMultiColour().getRGB());
        Past.customFontRenderer.drawStringWithShadow("Created by" + " " + Past.author, boxheight + 4, authorspace, ColourUtil.getMultiColour().getRGB());
        Past.customFontRenderer.drawStringWithShadow(Past.github, boxheight + 4, githubspace, ColourUtil.getMultiColour().getRGB());

        ResourceLocation resourceLocation = new ResourceLocation("pastclient", "pastlogo.jpg");
        mc.getTextureManager().bindTexture(resourceLocation);
        this.drawModalRectWithCustomSizedTexture(2, 2, 0, 0, boxheight, boxheight - 2, boxheight, boxheight - 2);
    }
}