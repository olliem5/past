package me.olliem5.past.module.modules.core;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import net.minecraft.util.ResourceLocation;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", "Opens the client ClickGUI", Category.CORE);
    }

    Setting scrollspeed;
    Setting rainbow;
    Setting customfont;
    Setting descriptions;
    Setting hoverchange;
    Setting background;
    Setting pausegame;
    Setting red;
    Setting green;
    Setting blue;
    Setting alpha;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(scrollspeed = new Setting("Scroll Speed", "ClickGUIScrollSpeed", 0.0, 10.0, 20.0, this));
        Past.settingsManager.registerSetting(rainbow = new Setting("Rainbow", "ClickGUIRainbow", true, this));
        Past.settingsManager.registerSetting(customfont = new Setting("Custom Font","ClickGUICustomFont", true, this));
        Past.settingsManager.registerSetting(descriptions = new Setting("Descriptions", "ClickGUIDescriptions", true, this));
        Past.settingsManager.registerSetting(hoverchange = new Setting("Hover Change", "ClickGUIHoverChange", true, this));
        Past.settingsManager.registerSetting(background = new Setting("Background", "ClickGUIBackground", true, this));
        Past.settingsManager.registerSetting(pausegame = new Setting("No Pause Game", "ClickGUIPauseGame", true, this));
        Past.settingsManager.registerSetting(red = new Setting("Red", "ClickGUIRed", 0, 200, 255, this));
        Past.settingsManager.registerSetting(green = new Setting("Green", "ClickGUIGreen", 0, 10, 255, this));
        Past.settingsManager.registerSetting(blue = new Setting("Blue", "ClickGUIBlue", 0, 10, 255, this));
        Past.settingsManager.registerSetting(alpha = new Setting("Alpha", "ClickGUIAlpha", 0, 255, 255, this));
    }

    public void onEnable() {
        mc.displayGuiScreen(Past.clickGUITwo);
        toggle();
    }
}
