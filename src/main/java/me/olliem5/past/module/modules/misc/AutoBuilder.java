package me.olliem5.past.module.modules.misc;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.colour.ColourUtil;
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

    private final List<Vec3d> ppBuildPositiveX = new ArrayList<>(Arrays.asList(
            new Vec3d(2, 0, 0), //Middle Block
            new Vec3d(2, 0, 1), //Right Side
            new Vec3d(2, 0, -1), //Left Side
            new Vec3d(2, 1, 0), //Up
            new Vec3d(2, 2, 0) //Up
    ));

    private final List<Vec3d> ppBuildPositiveZ = new ArrayList<>(Arrays.asList(
            new Vec3d(0, 0, 2), //Middle Block
            new Vec3d(-1, 0, 2), //Right Side
            new Vec3d(1, 0, 2), //Left Side
            new Vec3d(0, 1, 2), //Up
            new Vec3d(0, 2, 2) //Up
    ));

    private final List<Vec3d> ppBuildNegativeX = new ArrayList<>(Arrays.asList(
            new Vec3d(-2, 0, 0), //Middle Block
            new Vec3d(-2, 0, -1), //Right Side
            new Vec3d(-2, 0, 1), //Left Side
            new Vec3d(-2, 1, 0), //Up
            new Vec3d(-2, 2, 0) //Up
    ));

    private final List<Vec3d> ppBuildNegativeZ = new ArrayList<>(Arrays.asList(
            new Vec3d(0, 0, -2), //Middle Block
            new Vec3d(1, 0, -2), //Right Side
            new Vec3d(-1, 0, -2), //Left Side
            new Vec3d(0, 1, -2), //Up
            new Vec3d(0, 2, -2) //Up
    ));

    private final List<Vec3d> highwayBuildPositiveX = new ArrayList<>(Arrays.asList(
            new Vec3d(0, -1, 0), //Center
            new Vec3d(0, -1, 1), //Right
            new Vec3d(0, -1, -1), //Left
            new Vec3d(0, -1, 2), //Right Right
            new Vec3d(0, -1, -2), //Left Left
            new Vec3d(0, -1, 3), //Right Right Right
            new Vec3d(0, -1, -3), //Left Left Left
            new Vec3d(0, 0, 3), //Right Wall
            new Vec3d(0, 0, -3) //Left Wall
    ));

    private final List<Vec3d> highwayBuildPositiveZ = new ArrayList<>(Arrays.asList(
            new Vec3d(0, -1, 0), //Center
            new Vec3d(-1, -1, 0), //Right
            new Vec3d(1, -1, 0), //Left
            new Vec3d(-2, -1, 0), //Right Right
            new Vec3d(2, -1, 0), //Left Left
            new Vec3d(-3, -1, 0), //Right Right Right
            new Vec3d(3, -1, 0), //Left Left Left
            new Vec3d(-3, 0, 0), //Right Wall
            new Vec3d(3, 0, 0) //Left Wall
    ));

    private final List<Vec3d> highwayBuildNegativeX = new ArrayList<>(Arrays.asList(
            new Vec3d(0, -1, 0), //Center
            new Vec3d(0, -1, -1), //Right
            new Vec3d(0, -1, 1), //Left
            new Vec3d(0, -1, -2), //Right Right
            new Vec3d(0, -1, 2), //Left Left
            new Vec3d(0, -1, -3), //Right Right Right
            new Vec3d(0, -1, 3), //Left Left Left
            new Vec3d(0, 0, -3), //Right Wall
            new Vec3d(0, 0, 3) //Left Wall
    ));

    private final List<Vec3d> highwayBuildNegativeZ = new ArrayList<>(Arrays.asList(
            new Vec3d(0, -1, 0), //Center
            new Vec3d(1, -1, 0), //Right
            new Vec3d(-1, -1, 0), //Left
            new Vec3d(2, -1, 0), //Right Right
            new Vec3d(-2, -1, 0), //Left Left
            new Vec3d(3, -1, 0), //Right Right Right
            new Vec3d(-3, -1, 0), //Left Left Left
            new Vec3d(3, 0, 0), //Right Wall
            new Vec3d(-3, 0, 0) //Left Wall
    ));

    public void onUpdate() {
        if (nullCheck()) {
            return;
        }

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
                                MessageUtil.sendAutoBuilderMessage(ColourUtil.white + "Placing block");
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
                                MessageUtil.sendAutoBuilderMessage(ColourUtil.white + "Placing block");
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
                                MessageUtil.sendAutoBuilderMessage(ColourUtil.white + "Placing block");
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
                                MessageUtil.sendAutoBuilderMessage(ColourUtil.white + "Placing block");
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
        if (buildmode.getValueString() == "Highway") {
            switch (PlayerUtil.getFacing()) {
                case NORTH:
                    for (Vec3d placePositions : highwayBuildNegativeZ) {

                        BlockPos blockPos = new BlockPos(placePositions.add(mc.player.getPositionVector()));

                        if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) {

                            int oldInventorySlot = mc.player.inventory.currentItem;

                            mc.player.inventory.currentItem = PlayerUtil.getAnyBlockInHotbar();

                            if (infomessages.getValBoolean()) {
                                MessageUtil.sendAutoBuilderMessage(ColourUtil.white + "Placing block");
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
                                MessageUtil.sendAutoBuilderMessage(ColourUtil.white + "Placing block");
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
                                MessageUtil.sendAutoBuilderMessage(ColourUtil.white + "Placing block");
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
                                MessageUtil.sendAutoBuilderMessage(ColourUtil.white + "Placing block");
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

    public String getArraylistInfo() {
        return ColourUtil.gray + " " + buildmode.getValueString().toUpperCase();
    }
}
