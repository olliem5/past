package me.olliem5.past.impl.modules.movement;

import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraftforge.client.event.InputUpdateEvent;

public class NoSlow extends Module {
    public NoSlow() {
        super("NoSlow","Prevents items/blocks from slowing you down", Category.MOVEMENT);
    }

    //Check MixinBlockSoulSand

    @EventHandler
    public Listener<InputUpdateEvent> listener = new Listener<>(event -> {
        if (mc.player.isHandActive() && !mc.player.isRiding()) {
            event.getMovementInput().moveStrafe *= 5;
            event.getMovementInput().moveForward *= 5;
        }
    });
}
