package me.olliem5.past.module.modules.core;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;

public class HudEditor extends Module {
    public HudEditor() {
        super("HudEditor", "Edit the HUD", Category.CORE);
    }

    Setting rgb;
    Setting background;
    Setting customfont;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(rgb = new Setting("RainbowGUI", "HudEditorRainbow", true, this));
        Past.settingsManager.registerSetting(background = new Setting("Background", "HudEditorBackground", true, this));
        Past.settingsManager.registerSetting(customfont = new Setting("Custom Font", "HudEditorCustomFont", true, this));
    }

    public void onEnable() {
        mc.displayGuiScreen(Past.hudEditor);
        toggle();
    }
}
