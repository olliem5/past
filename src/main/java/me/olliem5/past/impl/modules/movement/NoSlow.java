package me.olliem5.past.impl.modules.movement;

import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.module.ModuleInfo;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraftforge.client.event.InputUpdateEvent;

@ModuleInfo(name = "NoSlow", description = "Prevents items/blocks from slowing you down", category = Category.MOVEMENT)
public class NoSlow extends Module {

    //Check MixinBlockSoulSand

    @EventHandler
    public Listener<InputUpdateEvent> listener = new Listener<>(event -> {
        if (mc.player.isHandActive() && !mc.player.isRiding()) {
            event.getMovementInput().moveStrafe *= 5;
            event.getMovementInput().moveForward *= 5;
        }
    });
}
