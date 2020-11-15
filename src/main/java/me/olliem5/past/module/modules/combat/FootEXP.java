package me.olliem5.past.module.modules.combat;

import me.olliem5.past.event.events.PacketEvent;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;

public class FootEXP extends Module {
    public FootEXP() {
        super("FootEXP", "Makes you look down server side while using EXP bottles", Category.COMBAT);
    }

    @EventHandler
    public Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketPlayerTryUseItem && mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle) {
            mc.player.connection.sendPacket(new CPacketPlayer.Rotation(mc.player.rotationYaw, 90.0f, mc.player.onGround));
        }
    });
}
