package me.olliem5.past.module.modules.player;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;

public class FastUse extends Module {
    public FastUse() {
        super("FastUse", "Allows you to use items faster", Category.PLAYER);
    }

    Setting bow;
    Setting exp;
    Setting crystals;
    Setting blocks;
    Setting other;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(bow = new Setting("Bow", "FastUseBow", true, this));
        Past.settingsManager.registerSetting(exp = new Setting("Exp Bottles", "FastUseExpBottles", true, this));
        Past.settingsManager.registerSetting(crystals = new Setting("End Crystals", "FastUseEndCrystals", true, this));
        Past.settingsManager.registerSetting(blocks = new Setting("Blocks", "FastUseBlocks", false, this));
        Past.settingsManager.registerSetting(other = new Setting("Other", "FastUseOther", false, this));
    }

    public void onUpdate() {
        if (nullCheck()) return;

        if (mc.player.getHeldItemMainhand().getItem() instanceof ItemBow) {
            if (mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= 3) {
                if (bow.getValBoolean()) {
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
                    mc.player.stopActiveHand();
                }
            }
        } else if (mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle) {
            if (exp.getValBoolean()) {
                mc.rightClickDelayTimer = 0;
            }
        } else if (mc.player.getHeldItemMainhand().getItem() instanceof ItemEndCrystal) {
            if (crystals.getValBoolean()) {
                mc.rightClickDelayTimer = 0;
            }
        } else if (Block.getBlockFromItem(mc.player.getHeldItemMainhand().getItem()).getDefaultState().isFullBlock()) {
            if (blocks.getValBoolean()) {
                mc.rightClickDelayTimer = 0;
            }
        } else if (!(mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock)) {
            if (other.getValBoolean()) {
                mc.rightClickDelayTimer = 0;
            }
        }
    }
}
