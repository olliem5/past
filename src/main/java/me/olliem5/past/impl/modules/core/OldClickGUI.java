package me.olliem5.past.impl.modules.core;

import me.olliem5.past.Past;
import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.module.ModuleInfo;
import me.olliem5.past.api.setting.Setting;

@ModuleInfo(name = "OldClickGUI", description = "Opens Past's old ClickGUI", category = Category.CORE)
public class OldClickGUI extends Module {

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
