package me.olliem5.past.gui.ingame;

import me.olliem5.past.module.Module;
import me.olliem5.past.Past;
import me.olliem5.past.util.ColourManager;
import net.minecraft.client.Minecraft;

import java.util.Calendar;

public class PastHUD {
    protected Minecraft mc = Minecraft.getMinecraft();

    public void drawName() {
        mc.fontRenderer.drawStringWithShadow(ColourManager.red + Past.name + " " + ColourManager.gray + Past.version, 2, 2, -1);
    }

    public void drawWelcome() {
        float welcomespacing = mc.fontRenderer.FONT_HEIGHT + 2;
        mc.fontRenderer.drawStringWithShadow(WelcomeMessage() + ColourManager.red + mc.getSession().getUsername(), 2, welcomespacing, -1);
    }

    public void drawModules() {
        float modulespacing = mc.fontRenderer.FONT_HEIGHT + mc.fontRenderer.FONT_HEIGHT + mc.fontRenderer.FONT_HEIGHT + 2;
        for (Module module : Past.moduleManager.getModules()) {
            if (module.isToggled()) {
                mc.fontRenderer.drawStringWithShadow(module.getName(), 2, modulespacing, -1);
                modulespacing += mc.fontRenderer.FONT_HEIGHT;
            }
        }
    }

    private String WelcomeMessage() {
        final int timeOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (timeOfDay < 12) {
            return "Good Morning" + ColourManager.gray +", ";
        } else if (timeOfDay < 16) {
            return "Good Afternoon" + ColourManager.gray +", ";
        } else if (timeOfDay < 21) {
            return "Good Evening" + ColourManager.gray +", ";
        } else {
            return "Good Night" + ColourManager.gray +", ";
        }
    }
}
