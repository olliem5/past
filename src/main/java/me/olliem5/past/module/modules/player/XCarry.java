package me.olliem5.past.module.modules.player;

import me.olliem5.past.event.events.PacketEvent;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketCloseWindow;

public class XCarry extends Module {
    public XCarry() {
        super("XCarry", "Allows you to store items in crafting slots", Category.PLAYER);
    }

    @EventHandler
    public Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketCloseWindow) {
            event.cancel();
        }
    });
}
