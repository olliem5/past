package me.olliem5.past.module.modules.combat;

import me.olliem5.past.Past;
import me.olliem5.past.event.events.PacketEvent;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemChorusFruit;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class ChorusSave extends Module {
    public ChorusSave() {
        super("ChorusSave", "Enables surround after you teleport with a chorus fruit", Category.COMBAT);
    }

    int holeBlocks;

    @EventHandler
    public Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketPlayerTryUseItem && mc.player.getHeldItemMainhand().getItem() instanceof ItemChorusFruit) {

            Vec3d[] holeOffset = {
                    mc.player.getPositionVector().add(1.0D, 0.0D, 0.0D),
                    mc.player.getPositionVector().add(-1.0D, 0.0D, 0.0D),
                    mc.player.getPositionVector().add(0.0D, 0.0D, 1.0D),
                    mc.player.getPositionVector().add(0.0D, 0.0D, -1.0D),
                    mc.player.getPositionVector().add(0.0D, -1.0D, 0.0D)
            };

            holeBlocks = 0;

            for (Vec3d vec3d : holeOffset) {
                BlockPos offset = new BlockPos(vec3d.x, vec3d.y, vec3d.z);
                if (mc.world.getBlockState(offset).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(offset).getBlock() == Blocks.BEDROCK) {
                    ++holeBlocks;
                }

                if (holeBlocks != 5) {
                    if (!Past.moduleManager.getModuleByName("Surround").isToggled()) {
                        Past.moduleManager.getModuleByName("Surround").toggle();
                    }
                }
            }
        }
    });
}
