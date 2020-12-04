package me.olliem5.past.module.modules.core;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", "Opens the client ClickGUI", Category.CORE);
    }

    Setting pausegame;
    Setting background;
    Setting hoverchange;
    Setting descriptions;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(pausegame = new Setting("No Pause Game", "ClickGUIPauseGame", true, this));
        Past.settingsManager.registerSetting(background = new Setting("Background", "ClickGUIBackground", true, this));
        Past.settingsManager.registerSetting(hoverchange = new Setting("Hover Change", "ClickGUIHoverChange", true, this));
        Past.settingsManager.registerSetting(descriptions = new Setting("Descriptions", "ClickGUIDescriptions", true, this));
    }

    public void onEnable() {
        mc.displayGuiScreen(Past.clickGUITwo);
        toggle();
    }
}
