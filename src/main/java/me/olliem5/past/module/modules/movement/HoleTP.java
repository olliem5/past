package me.olliem5.past.module.modules.movement;

import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;

public class HoleTP extends Module {
    public HoleTP() {
        super("HoleTP", "Teleports you down quicker", Category.MOVEMENT);
    }

    public void onUpdate() {
        if (nullCheck()) {
            return;
        }

        if (mc.player.onGround && !mc.player.isInLava() && !mc.player.isInWater() && !mc.player.isOnLadder()) {
            --mc.player.motionY;
        }
    }
}
