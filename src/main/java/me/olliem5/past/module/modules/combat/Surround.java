package me.olliem5.past.module.modules.combat;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.colour.ColourUtil;
import me.olliem5.past.util.client.MessageUtil;
import me.olliem5.past.util.player.PlayerUtil;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Surround extends Module {
    public Surround() {
        super("Surround", "Automatically surrounds you with obsidian", Category.COMBAT);
    }

    //TODO: Fix OnJump disable mode with centering

    Setting placemode;
    Setting disablemode;
    Setting centerplayer;
    Setting blockspertick;
    Setting timeout;
    Setting timeoutticks;
    Setting preferobi;
    Setting infomessages;

    private ArrayList<String> placemodes;
    private ArrayList<String> disablemodes;

    private boolean hasPlaced;
    private Vec3d center = Vec3d.ZERO;

    @Override
    public void setup() {
        placemodes = new ArrayList<>();
        placemodes.add("Standard");
        placemodes.add("Full");

        disablemodes = new ArrayList<>();
        disablemodes.add("Finish");
        //disablemodes.add("OnJump");

        Past.settingsManager.registerSetting(placemode = new Setting("Place", "SurroundPlace", this, placemodes, "Standard"));
        Past.settingsManager.registerSetting(disablemode = new Setting("Disable", "SurroundDisable", this, disablemodes, "Finish"));
        Past.settingsManager.registerSetting(centerplayer = new Setting("Center", "SurroundCenter", true, this));
        Past.settingsManager.registerSetting(blockspertick = new Setting("BPT", "SurroundBlocksPerTick", 1, 1, 10, this));
        Past.settingsManager.registerSetting(timeout = new Setting("Timeout", "SurroundTimeout", true, this));
        Past.settingsManager.registerSetting(timeoutticks = new Setting("Timeout Ticks", "SurroundTimeoutTicks", 1, 15, 20, this));
        Past.settingsManager.registerSetting(preferobi = new Setting("Prefer Obi", "SurroundOnlyObsidian", true, this));
        Past.settingsManager.registerSetting(infomessages = new Setting("Info Messages", "SurroundInfoMessages", false, this));
    }

    @Override
    public void onEnable() {
        hasPlaced = false;
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

    private final List<Vec3d> standardSurround = new ArrayList<>(Arrays.asList(
            new Vec3d(0, -1, 0), //Bottom Block
            new Vec3d(1, 0, 0), //X Block
            new Vec3d(-1, 0, 0), //X-Minus Block
            new Vec3d(0, 0, 1), //Z Block
            new Vec3d(0, 0, -1) //Z-Minus Block
    ));

    private final List<Vec3d> fullSurround = new ArrayList<>(Arrays.asList(
            new Vec3d(0, -1, 0), //Bottom Block
            new Vec3d(1, -1, 0), //X-Down Block
            new Vec3d(0, -1, 1), //Z-Down Block
            new Vec3d(-1, -1, 0), //X-Minus-Down Block
            new Vec3d(0, -1, -1), //Z-Minus-Down Block
            new Vec3d(1, 0, 0), //X Block
            new Vec3d(0, 0, 1), //Z Block
            new Vec3d(-1, 0, 0), //X-Minus Block
            new Vec3d(0, 0, -1) //Z-Minus Block
    ));

    public void onUpdate() {
        if (nullCheck()) return;

        if (timeout.getValBoolean()) {
            if (this.isToggled()) {
                if (mc.player.ticksExisted % timeoutticks.getValueInt() == 0) {
                    if (infomessages.getValBoolean()) {
                        MessageUtil.sendSurroundMessage(ColourUtil.white + "Module is" + ColourUtil.red + " " + "disabling" + ColourUtil.gray + " " + "(Timed Out)");
                    }
                    toggle();
                }
            }
        }

        if (hasPlaced == true && disablemode.getValueString() == "Finish") {
            if (infomessages.getValBoolean()) {
                MessageUtil.sendSurroundMessage(ColourUtil.white + "Module is" + ColourUtil.red + " " + "disabling" + ColourUtil.gray + " " + "(Finished)");
            }
            toggle();
        }

//        if (hasPlaced == true && disablemode.getValueString() == "OnJump") {
//            if (!mc.player.onGround) {
//                if (infomessages.getValBoolean()) {
//                    MessageUtil.sendSurroundMessage(ColourUtil.white + "Module is" + ColourUtil.red + " " + "disabling" + ColourUtil.gray + " " + "(OnJump)");
//                }
//                toggle();
//            }
//        }

        int blocksPlaced = 0;

        if (placemode.getValueString() == "Standard") {
            for (Vec3d placePositions : standardSurround) {

                BlockPos blockPos = new BlockPos(placePositions.add(mc.player.getPositionVector()));

                if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) {

                    int oldInventorySlot = mc.player.inventory.currentItem;

                    if (preferobi.getValBoolean()) {
                        if (infomessages.getValBoolean()) {
                            MessageUtil.sendSurroundMessage(ColourUtil.white + "Switching to" + " " + ColourUtil.aqua + "obsidian");
                        }
                        mc.player.inventory.currentItem = PlayerUtil.getBlockInHotbar(Blocks.OBSIDIAN);
                    } else {
                        if (infomessages.getValBoolean()) {
                            MessageUtil.sendSurroundMessage(ColourUtil.white + "Switching to" + " " + ColourUtil.aqua + "any block");
                        }
                        mc.player.inventory.currentItem = PlayerUtil.getAnyBlockInHotbar();
                    }

                    if (infomessages.getValBoolean()) {
                        MessageUtil.sendSurroundMessage(ColourUtil.white + "Placing block");
                    }

                    PlayerUtil.placeBlock(blockPos);
                    mc.player.inventory.currentItem = oldInventorySlot;
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

                    int oldInventorySlot = mc.player.inventory.currentItem;

                    if (preferobi.getValBoolean()) {
                        if (infomessages.getValBoolean()) {
                            MessageUtil.sendSurroundMessage(ColourUtil.white + "Switching to" + " " + ColourUtil.aqua + "obsidian");
                        }
                        mc.player.inventory.currentItem = PlayerUtil.getBlockInHotbar(Blocks.OBSIDIAN);
                    } else {
                        if (infomessages.getValBoolean()) {
                            MessageUtil.sendSurroundMessage(ColourUtil.white + "Switching to" + " " + ColourUtil.aqua + "any block");
                        }
                        mc.player.inventory.currentItem = PlayerUtil.getAnyBlockInHotbar();
                    }

                    if (infomessages.getValBoolean()) {
                        MessageUtil.sendSurroundMessage(ColourUtil.white + "Placing block");
                    }

                    PlayerUtil.placeBlock(blockPos);
                    mc.player.inventory.currentItem = oldInventorySlot;
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

    public Vec3d getCenter(double posX, double posY, double posZ) {

        double x = Math.floor(posX) + 0.5D;
        double y = Math.floor(posY);
        double z = Math.floor(posZ) + 0.5D;

        return new Vec3d(x, y, z);
    }

    public String getArraylistInfo() {
        return ColourUtil.gray + " " + placemode.getValueString().toUpperCase();
    }
}
