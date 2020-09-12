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

    @Override
    public void setup() {
        Past.settingsManager.rSetting(blue = new Setting("Blue", this, true));
    }

    public void onEnable() {
        if (blue.getValBoolean()) {
            mc.player.sendChatMessage("BLUEBLUEBLUE");
        } else {
            mc.player.sendChatMessage("Nigga");
        }
    }
}
