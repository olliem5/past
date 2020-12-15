package me.olliem5.past.module.modules.core;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;

public class OldClickGUI extends Module {
    public OldClickGUI() {
        super("OldClickGUI", "Opens the old ClickGUI", Category.CORE);
    }

    Setting rgb;
    Setting background;
    Setting descriptions;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(rgb = new Setting("RainbowGUI", "OldClickGUIRainbow", true, this));
        Past.settingsManager.registerSetting(background = new Setting("Background", "OldClickGUIBackground", true, this));
        Past.settingsManager.registerSetting(descriptions = new Setting("Descriptions", "OldClickGUIDescriptions", true, this));
    }

    public void onEnable() {
        mc.displayGuiScreen(Past.clickGUIOne);
        toggle();
    }
}
