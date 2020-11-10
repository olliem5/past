package me.olliem5.past.module.modules.misc;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.ColourUtil;
import me.olliem5.past.util.MessageUtil;
import me.olliem5.past.util.PlayerUtil;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutoBuilder extends Module {
    public AutoBuilder() {
        super ("AutoBuilder", "Automatically builds you stuff", Category.MISC);
    }

    Setting buildmode;
    Setting blockspertick;
    Setting centerplayer;
    Setting infomessages;

    private ArrayList<String> buildmodes;

    private Vec3d center = Vec3d.ZERO;

    @Override
    public void setup() {
        buildmodes = new ArrayList<>();
        buildmodes.add("PP");

        Past.settingsManager.registerSetting(buildmode = new Setting("Build", "AutoBuilderBuildMode", this, buildmodes, "PP"));
        Past.settingsManager.registerSetting(blockspertick = new Setting("BPT", "AutoBuilderBlocksPerTick", 1, 1, 10, this));
        Past.settingsManager.registerSetting(centerplayer = new Setting("Center", "AutoBuilderCenter", true, this));
        Past.settingsManager.registerSetting(infomessages = new Setting("Info Messages", "AutoBuilderInfoMessages", false, this));
    }

    @Override
    public void onEnable() {
        center = getCenter(mc.player.posX, mc.player.posY, mc.player.posZ);

        if (centerplayer.getValBoolean()) {
            mc.player.motionX = 0;
            mc.player.motionZ = 0;

            if (infomessages.getValBoolean()) {
                MessageUtil.sendSurroundMessage(ColourUtil.white + "Centering!");
            }
            mc.player.connection.sendPacket(new CPacketPlayer.Position(center.x, center.y, center.z, true));
            mc.player.setPosition(center.x, center.y, center.z);
        }
    }

    private final List<Vec3d> ppBuildPositiveX = new ArrayList<>(Arrays.asList(
            new Vec3d(1, 0, 0), //Middle Block
            new Vec3d(1, 0, 1), //Right Side
            new Vec3d(1, 0, -1), //Left Side
            new Vec3d(1, 1, 0), //Up
            new Vec3d(1, 2, 0) //Up
    ));

    private final List<Vec3d> ppBuildPositiveZ = new ArrayList<>(Arrays.asList(
            new Vec3d(0, 0, 1), //Middle Block
            new Vec3d(-1, 0, 1), //Right Side
            new Vec3d(1, 0, 1), //Left Side
            new Vec3d(0, 1, 1), //Up
            new Vec3d(0, 2, 1) //Up
    ));

    private final List<Vec3d> ppBuildNegativeX = new ArrayList<>(Arrays.asList(
            new Vec3d(-1, 0, 0), //Middle Block
            new Vec3d(-1, 0, -1), //Right Side
            new Vec3d(-1, 0, 1), //Left Side
            new Vec3d(-1, 1, 0), //Up
            new Vec3d(-1, 2, 0) //Up
    ));

    private final List<Vec3d> ppBuildNegativeZ = new ArrayList<>(Arrays.asList(
            new Vec3d(0, 0, -1), //Middle Block
            new Vec3d(1, 0, -1), //Right Side
            new Vec3d(-1, 0, -1), //Left Side
            new Vec3d(0, 1, -1), //Up
            new Vec3d(0, 2, -1) //Up
    ));

    public void onUpdate() {
        if (nullCheck()) { return; }

        int blocksPlaced = 0;

        if (buildmode.getValueString() == "PP") {
            switch (PlayerUtil.getFacing()) {
                case NORTH:
                    for (Vec3d placePositions : ppBuildNegativeZ) {

                        BlockPos blockPos = new BlockPos(placePositions.add(mc.player.getPositionVector()));

                        if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) {

                            int oldInventorySlot = mc.player.inventory.currentItem;

                            if (infomessages.getValBoolean()) {
                                MessageUtil.sendAutoBuilderMessage(ColourUtil.white + "Switching to" + " " + ColourUtil.aqua + "any block");
                            }

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

                            if (infomessages.getValBoolean()) {
                                MessageUtil.sendAutoBuilderMessage(ColourUtil.white + "Switching to" + " " + ColourUtil.aqua + "any block");
                            }

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

                            if (infomessages.getValBoolean()) {
                                MessageUtil.sendAutoBuilderMessage(ColourUtil.white + "Switching to" + " " + ColourUtil.aqua + "any block");
                            }

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

                            if (infomessages.getValBoolean()) {
                                MessageUtil.sendAutoBuilderMessage(ColourUtil.white + "Switching to" + " " + ColourUtil.aqua + "any block");
                            }

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

    public Vec3d getCenter(double posX, double posY, double posZ) {

        double x = Math.floor(posX) + 0.5D;
        double y = Math.floor(posY);
        double z = Math.floor(posZ) + 0.5D ;

        return new Vec3d(x, y, z);
    }

    public String getArraylistInfo() { return ColourUtil.gray + " " + buildmode.getValueString().toUpperCase(); }
}
