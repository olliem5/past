package me.olliem5.past.impl.modules.core;

import me.olliem5.past.Past;
import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.setting.Setting;

public class HudEditor extends Module {
    public HudEditor() {
        super("HudEditor", "Edit the HUD", Category.CORE);
    }

    Setting rgb;
    Setting background;
    Setting hoverchange;
    Setting red;
    Setting green;
    Setting blue;
    Setting alpha;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(rgb = new Setting("Rainbow", "HudEditorRainbow", true, this));
        Past.settingsManager.registerSetting(background = new Setting("Background", "HudEditorBackground", true, this));
        Past.settingsManager.registerSetting(hoverchange = new Setting("Hover Change", "HudEditorHoverChange", true, this));
        Past.settingsManager.registerSetting(red = new Setting("Red", "HudEditorRed", 0, 200, 255, this));
        Past.settingsManager.registerSetting(green = new Setting("Green", "HudEditorGreen", 0, 10, 255, this));
        Past.settingsManager.registerSetting(blue = new Setting("Blue", "HudEditorBlue", 0, 10, 255, this));
        Past.settingsManager.registerSetting(alpha = new Setting("Alpha", "HudEditorAlpha", 0, 255, 255, this));
    }

    public void onEnable() {
        mc.displayGuiScreen(Past.hudEditor);
        toggle();
    }
}
