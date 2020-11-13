package me.olliem5.past.module.modules.player;

import me.olliem5.past.event.events.PacketEvent;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.util.ColourUtil;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayer;

import java.util.LinkedList;
import java.util.Queue;

public class Blink extends Module {
    public Blink() {
        super ("Blink", "Hides movement for a short time", Category.PLAYER);
    }

    Queue<CPacketPlayer> packets = new LinkedList<>();

    @EventHandler
    public Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketPlayer) {
            event.cancel();
            packets.add((CPacketPlayer) event.getPacket());
        }
    });

    @Override
    public void onEnable() {
        if (mc.world == null) { return; }
        EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP(mc.world, mc.getSession().getProfile());
        fakePlayer.copyLocationAndAnglesFrom(mc.player);
        fakePlayer.rotationYawHead = mc.player.rotationYawHead;
        mc.world.addEntityToWorld(-100, fakePlayer);
    }

    @Override
    public void onDisable() {
        while (!packets.isEmpty()) {
            mc.player.connection.sendPacket(packets.poll());
        }

        EntityPlayer localPlayer = mc.player;
        if (localPlayer != null) {
            mc.world.removeEntityFromWorld(-100);
        }
    }

    public String getArraylistInfo() { return ColourUtil.gray + " " + packets.size(); }
}
