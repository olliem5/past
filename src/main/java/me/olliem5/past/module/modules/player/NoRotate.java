package me.olliem5.past.module.modules.player;

import me.olliem5.past.event.events.PacketEvent;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

public class NoRotate extends Module {
    public NoRotate() {
        super("NoRotate", "Blocks server side rotations", Category.PLAYER);
    }

    @EventHandler
    public Listener<PacketEvent.Receive> listener = new Listener<>(event -> {
        if (mc.player == null) return;

        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            SPacketPlayerPosLook packet = (SPacketPlayerPosLook) event.getPacket();
            packet.pitch = mc.player.rotationPitch;
            packet.yaw = mc.player.rotationYaw;
        }
    });
}
