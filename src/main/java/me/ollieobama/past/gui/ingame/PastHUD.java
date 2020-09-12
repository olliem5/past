package me.ollieobama.past.gui.ingame;

import me.ollieobama.past.Past;
import me.ollieobama.past.module.Module;
import net.minecraft.client.Minecraft;

import java.util.Calendar;

public class PastHUD {
    protected Minecraft mc = Minecraft.getMinecraft();

    public void Draw() {
        float modulespacing = mc.fontRenderer.FONT_HEIGHT + mc.fontRenderer.FONT_HEIGHT + 2;
        float welcomespacing = mc.fontRenderer.FONT_HEIGHT + 2;

        /* Client Name */
        mc.fontRenderer.drawStringWithShadow(Past.nameversion, 2, 2, -1);

        /* Enabled Modules */
        for (Module module : Past.moduleManager.getModules()) {
            if (module.isToggled()) {
                mc.fontRenderer.drawStringWithShadow(module.getName(), 2, modulespacing, -1);
                modulespacing += mc.fontRenderer.FONT_HEIGHT;
            }
        }

        /* Welcome Message */
        mc.fontRenderer.drawStringWithShadow(WelcomeMessage() + mc.getSession().getUsername(), 2, welcomespacing, -1);
    }

    private String WelcomeMessage() {
        final int timeOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (timeOfDay < 12) {
            return "Good Morning, ";
        } else if (timeOfDay < 16) {
            return "Good Afternoon, ";
        } else if (timeOfDay < 21) {
            return "Good Evening, ";
        } else {
            return "Good Night, ";
        }
    }
}
