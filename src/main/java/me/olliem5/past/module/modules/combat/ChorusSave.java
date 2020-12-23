package me.olliem5.past.module.modules.combat;

import me.olliem5.past.Past;
import me.olliem5.past.event.events.PacketEvent;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.util.module.HoleUtil;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.item.ItemChorusFruit;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;

public class ChorusSave extends Module {
    public ChorusSave() {
        super("ChorusSave", "Surrounds you while eating chorus fruit", Category.COMBAT);
    }

    /**
     * TODO: ChorusTeleportEvent, on teleport, delay, surround
     */

    @EventHandler
    public Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketPlayerTryUseItem && mc.player.getHeldItemMainhand().getItem() instanceof ItemChorusFruit) {
            if (!HoleUtil.isPlayerInHole(mc.player)) {
                if (!Past.moduleManager.getModuleByName("Surround").isToggled()) {
                    Past.moduleManager.getModuleByName("Surround").toggle();
                }
            }
        }
    });
}
