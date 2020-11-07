package me.olliem5.past.module.modules.combat;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.PlayerUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Surround extends Module {
    public Surround() {
        super ("Surround", "Automatically surrounds you with obsidian", Category.COMBAT);
    }

    /**
     * Refrenced code from https://github.com/Katatje/Dream/blob/master/src/main/java/cat/yoink/dream/impl/module/combat/Surround.java
     * yoink is cool
     */

    Setting placemode;
    Setting disablemode;
    Setting blockspertick;

    private ArrayList<String> placemodes;
    private ArrayList<String> disablemodes;

    private boolean hasPlaced;

    @Override
    public void setup() {
        placemodes = new ArrayList<>();
        placemodes.add("Standard");
        placemodes.add("Full");

        disablemodes = new ArrayList<>();
        disablemodes.add("WhenDone");
        disablemodes.add("OnJump");

        Past.settingsManager.registerSetting(placemode = new Setting("Place", "SurroundPlace", this, placemodes, "Standard"));
        Past.settingsManager.registerSetting(disablemode = new Setting("Disable", "SurroundDisable", this, disablemodes, "WhenDone"));
        Past.settingsManager.registerSetting(blockspertick = new Setting("BPT", "SurroundBlocksPerTick", 1, 1, 10, this));
    }

    @Override
    public void onEnable() {
        hasPlaced = false;
    }

    private final List<Vec3d> standardSurround = new ArrayList<>(Arrays.asList(
            new Vec3d(1, 0, 0),
            new Vec3d(0, 0, 1),
            new Vec3d(-1, 0, 0),
            new Vec3d(0, 0, -1),
            new Vec3d(1, -1, 0),
            new Vec3d(0, -1, 1),
            new Vec3d(-1, -1, 0),
            new Vec3d(0, -1, -1)
    ));

    private final List<Vec3d> fullSurround = new ArrayList<>(Arrays.asList(
            new Vec3d(1, 0, 0),
            new Vec3d(0, 0, 1),
            new Vec3d(-1, 0, 0),
            new Vec3d(0, 0, -1),
            new Vec3d(1, -1, 0),
            new Vec3d(0, -1, 1),
            new Vec3d(-1, -1, 0),
            new Vec3d(0, -1, -1),
            new Vec3d(0, -1, 0)
    ));

    public void onUpdate() {
        if (nullCheck()) { return; }

        if (hasPlaced == true && disablemode.getValueString() == "WhenDone") {
            toggle();
        }

        if (hasPlaced == true && disablemode.getValueString() == "OnJump") {
            if (!mc.player.onGround) {
                toggle();
            }
        }

        int blocksPlaced = 0;

        if (placemode.getValueString() == "Standard") {

            for (Vec3d placePositions : standardSurround) {

                BlockPos blockPos = new BlockPos(placePositions.add(mc.player.getPositionVector()));

                if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) {

                    int oldSlot = mc.player.inventory.currentItem;

                    mc.player.inventory.currentItem = PlayerUtil.getBlockInSlot(Blocks.OBSIDIAN);
                    PlayerUtil.placeBlock(blockPos);
                    mc.player.inventory.currentItem = oldSlot;
                    blocksPlaced++;

                    if (blocksPlaced == blockspertick.getValueInt()) {
                        return;
                    }
                }
            }
        }

        if (placemode.getValueString() == "Full") {

            for (Vec3d placePositions : fullSurround) {

                BlockPos blockPos = new BlockPos(placePositions.add(mc.player.getPositionVector()));

                if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) {

                    int oldSlot = mc.player.inventory.currentItem;

                    mc.player.inventory.currentItem = PlayerUtil.getBlockInSlot(Blocks.OBSIDIAN);

                    PlayerUtil.placeBlock(blockPos);
                    mc.player.inventory.currentItem = oldSlot;
                    blocksPlaced++;

                    if (blocksPlaced == blockspertick.getValueInt()) {
                        return;
                    }
                }
            }
        }
        if (blocksPlaced == 0) {
            hasPlaced = true;
        }
    }
}
