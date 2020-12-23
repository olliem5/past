package me.olliem5.past.impl.modules.player;

import me.olliem5.past.api.module.ModuleInfo;
import me.olliem5.past.impl.events.PacketEvent;
import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

@ModuleInfo(name = "NoRotate", description = "Blocks server side rotations", category = Category.PLAYER)
public class NoRotate extends Module {

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
