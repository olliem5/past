package me.olliem5.past.impl.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.olliem5.past.Past;
import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.setting.Setting;
import me.olliem5.past.api.util.player.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutoTrap extends Module {
    public AutoTrap() {
        super("AutoTrap", "Automatically traps players in range", Category.COMBAT);
    }

    Setting disable;
    Setting playerrange;
    Setting blockspertick;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(disable = new Setting("Disable", "AutoTrapDisable", false, this));
        Past.settingsManager.registerSetting(playerrange = new Setting("Player Range", "AutoTrapPlayerRange", 1.0, 4.4, 10.0, this));
        Past.settingsManager.registerSetting(blockspertick = new Setting("BPT", "AutoTrapBlocksPerTick", 1, 1, 10, this));
    }

    private boolean hasPlaced;

    @Override
    public void onEnable() {
        hasPlaced = false;
    }

    public void onUpdate() {
        if (nullCheck()) return;

        if (hasPlaced && disable.getValBoolean()) {
            toggle();
        }

        int blocksPlaced = 0;

        for (Vec3d autoTrapBox : autoTrap) {
            final EntityPlayer target = getClosestPlayer();

            if (target != null) {
                BlockPos blockPos = new BlockPos(autoTrapBox.add(getClosestPlayer().getPositionVector()));

                if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) {
                    int oldInventorySlot = mc.player.inventory.currentItem;
                    mc.player.inventory.currentItem = PlayerUtil.getBlockInHotbar(Blocks.OBSIDIAN);
                    PlayerUtil.placeBlock(blockPos);
                    mc.player.inventory.currentItem = oldInventorySlot;
                    blocksPlaced++;

                    if (blocksPlaced == blockspertick.getValueInt()) return;
                }
            }
        }
        if (blocksPlaced == 0) {
            hasPlaced = true;
        }
    }

    private EntityPlayer getClosestPlayer() {
        EntityPlayer closestPlayer = null;

        for (final EntityPlayer entityPlayer : mc.world.playerEntities) {
            if (!(entityPlayer == mc.player) && !Past.friendsManager.isFriend(entityPlayer.getName())) {
                final double distance = mc.player.getDistance(entityPlayer);

                if (distance < playerrange.getValueDouble()) {
                    closestPlayer = entityPlayer;
                }
            }
        }
        return closestPlayer;
    }

    private final List<Vec3d> autoTrap = new ArrayList<>(Arrays.asList(
            new Vec3d(0, -1, -1),
            new Vec3d(1, -1, 0),
            new Vec3d(0, -1, 1),
            new Vec3d(-1, -1, 0),
            new Vec3d(0, 0, -1),
            new Vec3d(1, 0, 0),
            new Vec3d(0, 0, 1),
            new Vec3d(-1, 0, 0),
            new Vec3d(0, 1, -1),
            new Vec3d(1, 1, 0),
            new Vec3d(0, 1, 1),
            new Vec3d(-1, 1, 0),
            new Vec3d(0, 2, -1),
            new Vec3d(0, 2, 1),
            new Vec3d(0, 2, 0)
    ));

    public String getArraylistInfo() {
        if (getClosestPlayer() != null) {
            return ChatFormatting.GRAY + " " + getClosestPlayer().getName();
        } else {
            return "";
        }
    }
}
