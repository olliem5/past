package me.olliem5.past.module.modules.movement;

import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "Automatically makes you sprint", Category.MOVEMENT);
    }

    public void onUpdate() {
        if (mc.player != null && mc.world != null) {
            try {
                if (mc.gameSettings.keyBindForward.isKeyDown() && !(mc.player.collidedHorizontally)) {
                    if (!mc.player.isSprinting()) {
                        mc.player.setSprinting(true);
                    }
                }
            } catch (Exception ignored) {
            }
        }
    }
}
