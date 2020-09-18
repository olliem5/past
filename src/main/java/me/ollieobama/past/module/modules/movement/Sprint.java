package me.ollieobama.past.module.modules.movement;

import me.ollieobama.past.module.Category;
import me.ollieobama.past.module.Module;

public class Sprint extends Module {
    public Sprint() {
        super ("Sprint", "Automatically makes you sprint", Category.MOVEMENT);
    }

    public void onUpdate() {
        try {
            if (mc.gameSettings.keyBindForward.isKeyDown() && !(mc.player.collidedHorizontally)) {
                if (!mc.player.isSprinting()) {
                    mc.player.setSprinting(true);
                }
            }
        } catch (Exception ignored) {}
    }
}
