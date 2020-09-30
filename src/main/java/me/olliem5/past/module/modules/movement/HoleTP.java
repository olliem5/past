package me.olliem5.past.module.modules.movement;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;

public class HoleTP extends Module {
    public HoleTP() {
        super ("HoleTP", "Teleports you down quicker", Category.MOVEMENT);
    }

    Setting water;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(water = new Setting("Water Disable", true, this));
    }

    public void onUpdate() {
        if (mc.player != null && mc.world != null) {
            if (water.getValBoolean()) {
                if (!mc.player.isInWater() && mc.player.onGround) { --mc.player.motionY; }
            } else {
                if (mc.player.onGround) { --mc.player.motionY; }
            }
        }
    }
}
