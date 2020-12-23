package me.olliem5.past.impl.modules.combat;

import me.olliem5.past.Past;
import me.olliem5.past.api.module.ModuleInfo;
import me.olliem5.past.impl.events.PacketEvent;
import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.util.world.HoleUtil;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.item.ItemChorusFruit;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;

@ModuleInfo(name = "ChorusSave", description = "Surrounds you while eating chorus fruit", category = Category.COMBAT)
public class ChorusSave extends Module {

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
