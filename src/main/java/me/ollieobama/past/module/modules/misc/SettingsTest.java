package me.ollieobama.past.module.modules.misc;

import me.ollieobama.past.Past;
import me.ollieobama.past.module.Module;
import me.ollieobama.past.module.Category;
import me.ollieobama.past.settings.Setting;

public class SettingsTest extends Module {
    public SettingsTest() {
        super ("SettingsTest", "Test", Category.MISC);
    }

    Setting blue;
    Setting slider;

    @Override
    public void setup() {
        Past.settingsManager.rSetting(blue = new Setting("Blue", this, true));
        Past.settingsManager.rSetting(slider = new Setting("GuiBlue", this, 1, 1, 255, true));
    }

    public void onEnable() {
        if (blue.getValBoolean()) {
            mc.player.sendChatMessage("BLUEBLUEBLUE" + slider.getValString());
        } else {
            mc.player.sendChatMessage("Nigga" + slider.getValString());
        }
    }
}
