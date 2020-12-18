package me.olliem5.past.module.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.client.MessageUtil;
import me.olliem5.past.util.player.PlayerUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutoBuilder extends Module {
    public AutoBuilder() {
        super("AutoBuilder", "Automatically builds you stuff", Category.MISC);
    }

    Setting buildmode;
    Setting blockspertick;
    Setting infomessages;

    private ArrayList<String> buildmodes;

    @Override
    public void setup() {
        buildmodes = new ArrayList<>();
        buildmodes.add("PP");
        buildmodes.add("Highway");

        Past.settingsManager.registerSetting(buildmode = new Setting("Build", "AutoBuilderBuildMode", this, buildmodes, "PP"));
        Past.settingsManager.registerSetting(blockspertick = new Setting("BPT", "AutoBuilderBlocksPerTick", 1, 1, 10, this));
        Past.settingsManager.registerSetting(infomessages = new Setting("Info Messages", "AutoBuilderInfoMessages", false, this));
    }

    public void onUpdate() {
        if (nullCheck()) return;

        int blocksPlaced = 0;

        if (buildmode.getValueString() == "PP") {
            switch (PlayerUtil.getFacing()) {
                case NORTH:
                    for (Vec3d placePositions : ppBuildNegativeZ) {

                        BlockPos blockPos = new BlockPos(placePositions.add(mc.player.getPositionVector()));

                        if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) {

                            int oldInventorySlot = mc.player.inventory.currentItem;

                            mc.player.inventory.currentItem = PlayerUtil.getAnyBlockInHotbar();

                            if (infomessages.getValBoolean()) {
                                MessageUtil.sendAutoBuilderMessage(ChatFormatting.WHITE + "Placing block");
                            }

                            PlayerUtil.placeBlock(blockPos);
                            mc.player.inventory.currentItem = oldInventorySlot;
                            blocksPlaced++;

                            if (blocksPlaced == blockspertick.getValueInt()) {
                                return;
                            }
                        }
                    }
                    break;
                case EAST:
                    for (Vec3d placePositions : ppBuildPositiveX) {

                        BlockPos blockPos = new BlockPos(placePositions.add(mc.player.getPositionVector()));

                        if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) {

                            int oldInventorySlot = mc.player.inventory.currentItem;

                            mc.player.inventory.currentItem = PlayerUtil.getAnyBlockInHotbar();

                            if (infomessages.getValBoolean()) {
                                MessageUtil.sendAutoBuilderMessage(ChatFormatting.WHITE + "Placing block");
                            }

                            PlayerUtil.placeBlock(blockPos);
                            mc.player.inventory.currentItem = oldInventorySlot;
                            blocksPlaced++;

                            if (blocksPlaced == blockspertick.getValueInt()) {
                                return;
                            }
                        }
                    }
                    break;
                case SOUTH:
                    for (Vec3d placePositions : ppBuildPositiveZ) {

                        BlockPos blockPos = new BlockPos(placePositions.add(mc.player.getPositionVector()));

                        if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) {

                            int oldInventorySlot = mc.player.inventory.currentItem;

                            mc.player.inventory.currentItem = PlayerUtil.getAnyBlockInHotbar();

                            if (infomessages.getValBoolean()) {
                                MessageUtil.sendAutoBuilderMessage(ChatFormatting.WHITE + "Placing block");
                            }

                            PlayerUtil.placeBlock(blockPos);
                            mc.player.inventory.currentItem = oldInventorySlot;
                            blocksPlaced++;

                            if (blocksPlaced == blockspertick.getValueInt()) {
                                return;
                            }
                        }
                    }
                    break;
                case WEST:
                    for (Vec3d placePositions : ppBuildNegativeX) {

                        BlockPos blockPos = new BlockPos(placePositions.add(mc.player.getPositionVector()));

                        if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) {

                            int oldInventorySlot = mc.player.inventory.currentItem;

                            mc.player.inventory.currentItem = PlayerUtil.getAnyBlockInHotbar();

                            if (infomessages.getValBoolean()) {
                                MessageUtil.sendAutoBuilderMessage(ChatFormatting.WHITE + "Placing block");
                            }

                            PlayerUtil.placeBlock(blockPos);
                            mc.player.inventory.currentItem = oldInventorySlot;
                            blocksPlaced++;

                            if (blocksPlaced == blockspertick.getValueInt()) {
                                return;
                            }
                        }
                    }
                    break;
            }
        } else if (buildmode.getValueString() == "Highway") {
            switch (PlayerUtil.getFacing()) {
                case NORTH:
                    for (Vec3d placePositions : highwayBuildNegativeZ) {

                        BlockPos blockPos = new BlockPos(placePositions.add(mc.player.getPositionVector()));

                        if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) {

                            int oldInventorySlot = mc.player.inventory.currentItem;

                            mc.player.inventory.currentItem = PlayerUtil.getAnyBlockInHotbar();

                            if (infomessages.getValBoolean()) {
                                MessageUtil.sendAutoBuilderMessage(ChatFormatting.WHITE + "Placing block");
                            }

                            PlayerUtil.placeBlock(blockPos);
                            mc.player.inventory.currentItem = oldInventorySlot;
                            blocksPlaced++;

                            if (blocksPlaced == blockspertick.getValueInt()) {
                                return;
                            }
                        }
                    }
                    break;
                case EAST:
                    for (Vec3d placePositions : highwayBuildPositiveX) {

                        BlockPos blockPos = new BlockPos(placePositions.add(mc.player.getPositionVector()));

                        if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) {

                            int oldInventorySlot = mc.player.inventory.currentItem;

                            mc.player.inventory.currentItem = PlayerUtil.getAnyBlockInHotbar();

                            if (infomessages.getValBoolean()) {
                                MessageUtil.sendAutoBuilderMessage(ChatFormatting.WHITE + "Placing block");
                            }

                            PlayerUtil.placeBlock(blockPos);
                            mc.player.inventory.currentItem = oldInventorySlot;
                            blocksPlaced++;

                            if (blocksPlaced == blockspertick.getValueInt()) {
                                return;
                            }
                        }
                    }
                    break;
                case SOUTH:
                    for (Vec3d placePositions : highwayBuildPositiveZ) {

                        BlockPos blockPos = new BlockPos(placePositions.add(mc.player.getPositionVector()));

                        if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) {

                            int oldInventorySlot = mc.player.inventory.currentItem;

                            mc.player.inventory.currentItem = PlayerUtil.getAnyBlockInHotbar();

                            if (infomessages.getValBoolean()) {
                                MessageUtil.sendAutoBuilderMessage(ChatFormatting.WHITE + "Placing block");
                            }

                            PlayerUtil.placeBlock(blockPos);
                            mc.player.inventory.currentItem = oldInventorySlot;
                            blocksPlaced++;

                            if (blocksPlaced == blockspertick.getValueInt()) {
                                return;
                            }
                        }
                    }
                    break;
                case WEST:
                    for (Vec3d placePositions : highwayBuildNegativeX) {

                        BlockPos blockPos = new BlockPos(placePositions.add(mc.player.getPositionVector()));

                        if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) {

                            int oldInventorySlot = mc.player.inventory.currentItem;

                            mc.player.inventory.currentItem = PlayerUtil.getAnyBlockInHotbar();

                            if (infomessages.getValBoolean()) {
                                MessageUtil.sendAutoBuilderMessage(ChatFormatting.WHITE + "Placing block");
                            }

                            PlayerUtil.placeBlock(blockPos);
                            mc.player.inventory.currentItem = oldInventorySlot;
                            blocksPlaced++;

                            if (blocksPlaced == blockspertick.getValueInt()) {
                                return;
                            }
                        }
                    }
                    break;
            }
        }
    }

    private final List<Vec3d> ppBuildPositiveX = new ArrayList<>(Arrays.asList(
            new Vec3d(2, 0, 0),
            new Vec3d(2, 0, 1),
            new Vec3d(2, 0, -1),
            new Vec3d(2, 1, 0),
            new Vec3d(2, 2, 0)
    ));

    private final List<Vec3d> ppBuildPositiveZ = new ArrayList<>(Arrays.asList(
            new Vec3d(0, 0, 2),
            new Vec3d(-1, 0, 2),
            new Vec3d(1, 0, 2),
            new Vec3d(0, 1, 2),
            new Vec3d(0, 2, 2)
    ));

    private final List<Vec3d> ppBuildNegativeX = new ArrayList<>(Arrays.asList(
            new Vec3d(-2, 0, 0),
            new Vec3d(-2, 0, -1),
            new Vec3d(-2, 0, 1),
            new Vec3d(-2, 1, 0),
            new Vec3d(-2, 2, 0)
    ));

    private final List<Vec3d> ppBuildNegativeZ = new ArrayList<>(Arrays.asList(
            new Vec3d(0, 0, -2),
            new Vec3d(1, 0, -2),
            new Vec3d(-1, 0, -2),
            new Vec3d(0, 1, -2),
            new Vec3d(0, 2, -2)
    ));

    private final List<Vec3d> highwayBuildPositiveX = new ArrayList<>(Arrays.asList(
            new Vec3d(0, -1, 0),
            new Vec3d(0, -1, 1),
            new Vec3d(0, -1, -1),
            new Vec3d(0, -1, 2),
            new Vec3d(0, -1, -2),
            new Vec3d(0, -1, 3),
            new Vec3d(0, -1, -3),
            new Vec3d(0, 0, 3),
            new Vec3d(0, 0, -3)
    ));

    private final List<Vec3d> highwayBuildPositiveZ = new ArrayList<>(Arrays.asList(
            new Vec3d(0, -1, 0),
            new Vec3d(-1, -1, 0),
            new Vec3d(1, -1, 0),
            new Vec3d(-2, -1, 0),
            new Vec3d(2, -1, 0),
            new Vec3d(-3, -1, 0),
            new Vec3d(3, -1, 0),
            new Vec3d(-3, 0, 0),
            new Vec3d(3, 0, 0)
    ));

    private final List<Vec3d> highwayBuildNegativeX = new ArrayList<>(Arrays.asList(
            new Vec3d(0, -1, 0),
            new Vec3d(0, -1, -1),
            new Vec3d(0, -1, 1),
            new Vec3d(0, -1, -2),
            new Vec3d(0, -1, 2),
            new Vec3d(0, -1, -3),
            new Vec3d(0, -1, 3),
            new Vec3d(0, 0, -3),
            new Vec3d(0, 0, 3)
    ));

    private final List<Vec3d> highwayBuildNegativeZ = new ArrayList<>(Arrays.asList(
            new Vec3d(0, -1, 0),
            new Vec3d(1, -1, 0),
            new Vec3d(-1, -1, 0),
            new Vec3d(2, -1, 0),
            new Vec3d(-2, -1, 0),
            new Vec3d(3, -1, 0),
            new Vec3d(-3, -1, 0),
            new Vec3d(3, 0, 0),
            new Vec3d(-3, 0, 0)
    ));

    public String getArraylistInfo() {
        return ChatFormatting.GRAY + " " + buildmode.getValueString().toUpperCase();
    }
}
