package me.ollieobama.past.module.modules.movement;

import me.ollieobama.past.module.Category;
import me.ollieobama.past.module.Module;

public class HoleTP extends Module {
    public HoleTP() {
        super ("HoleTP", "Teleports you down quicker", Category.MOVEMENT);
    }

    public void onUpdate() {
        if (mc.player.onGround) {
            --mc.player.motionY;
        }
    }
}
