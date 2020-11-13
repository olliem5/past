package me.olliem5.past.module.modules.core;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;

public class ClickGUI extends Module {
    public ClickGUI() {
        super ("ClickGUI", "Opens the ClickGUI", Category.CORE);
    }

    Setting rgb;
    Setting background;
    Setting descriptions;
    Setting customfont;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(rgb = new Setting("RainbowGUI", "ClickGUIRainbow", true, this));
        Past.settingsManager.registerSetting(background = new Setting("Background", "ClickGUIBackground", true, this));
        Past.settingsManager.registerSetting(descriptions = new Setting("Descriptions", "ClickGUIDescriptions", true, this));
        Past.settingsManager.registerSetting(customfont = new Setting("Custom Font", "ClickGUICustomFont", true, this));
    }

    public void onEnable() {
        mc.displayGuiScreen(Past.clickGUI);
        toggle();
    }
}
