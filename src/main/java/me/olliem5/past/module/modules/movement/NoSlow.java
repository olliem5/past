package me.olliem5.past.module.modules.movement;

import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoSlow extends Module {
    public NoSlow() {
        super("NoSlow","Prevents items from slowing you down", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onInputUpdate(InputUpdateEvent event) {
        if (!mc.player.isRiding() && mc.player.isHandActive()) {
            event.getMovementInput().moveStrafe *= 5;
            event.getMovementInput().moveForward *= 5;
        }
    }
}
