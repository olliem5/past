package me.olliem5.past.gui.ingame;

import me.olliem5.past.module.Module;
import me.olliem5.past.Past;
import me.olliem5.past.util.ColourUtil;
import me.olliem5.past.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.util.Calendar;

public class PastHUD {
    protected Minecraft mc = Minecraft.getMinecraft();

    public void drawName() {
        mc.fontRenderer.drawStringWithShadow(Past.name + " " + Past.version, 2, 2, ColourUtil.getMultiColour().getRGB());
    }

    public void drawWelcome() {
        float welcomespacing = mc.fontRenderer.FONT_HEIGHT + 2;
        mc.fontRenderer.drawStringWithShadow(WelcomeMessage() + mc.getSession().getUsername(), 2, welcomespacing, ColourUtil.getMultiColour().getRGB());
    }

    //Old Arraylist
//    public void drawModules() {
//        float modulespacing = mc.fontRenderer.FONT_HEIGHT + mc.fontRenderer.FONT_HEIGHT + mc.fontRenderer.FONT_HEIGHT + 2;
//        for (Module module : Past.moduleManager.getModules()) {
//            if (module.isToggled()) {
//                mc.fontRenderer.drawStringWithShadow(module.getName(), 2, modulespacing, -1);
//                modulespacing += mc.fontRenderer.FONT_HEIGHT;
//            }
//        }
//    }

    FontRenderer fr = mc.fontRenderer;
    ScaledResolution sr = new ScaledResolution(mc);

    public void drawModules() {
        Past.moduleManager.modules.sort((module1, module2) -> StringUtil.getStringWidth(StringUtil.capitalizeFirstLetter(module2.getName())) - StringUtil.getStringWidth(StringUtil.capitalizeFirstLetter(module1.getName())));
        int count = 0;
        for (Module module : Past.moduleManager.getModules()) {
            if (module.isToggled()) {
                int offset = count * (fr.FONT_HEIGHT + 6);
                Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(module.getName()) - 10, offset, sr.getScaledWidth(),6 + fr.FONT_HEIGHT + offset, 0xFF222222);
                Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(module.getName()) -10, offset, sr.getScaledWidth() - fr.getStringWidth(module.getName()) -8, 6 + fr.FONT_HEIGHT + offset, ColourUtil.getMultiColour().getRGB());
                fr.drawString(module.getName(), sr.getScaledWidth() - fr.getStringWidth(module.getName()) -4, 4 + offset, -1);
                count++;
            }
        }
    }

    private String WelcomeMessage() {
        final int timeOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (timeOfDay < 12) {
            return "Good Morning" + "," + " ";
        } else if (timeOfDay < 16) {
            return "Good Afternoon" + "," + " ";
        } else if (timeOfDay < 21) {
            return "Good Evening" + "," + " ";
        } else {
            return "Good Night" + "," + " ";
        }
    }
}
