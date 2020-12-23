package me.olliem5.past.impl.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.olliem5.past.Past;
import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.module.ModuleInfo;
import me.olliem5.past.api.setting.Setting;
import me.olliem5.past.api.util.client.MessageUtil;
import me.olliem5.past.api.util.player.PlayerUtil;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ModuleInfo(name = "Surround", description = "Automatically surrounds you", category = Category.COMBAT)
public class Surround extends Module {

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

    private int blocksPlaced;

    @Override
    public void setup() {
        placemodes = new ArrayList<>();
        placemodes.add("Standard");
        placemodes.add("Full");
        placemodes.add("AntiCity");

        disablemodes = new ArrayList<>();
        disablemodes.add("Finish");
        disablemodes.add("Never");
        disablemodes.add("OnJump");

        Past.settingsManager.registerSetting(placemode = new Setting("Place", "SurroundPlace", this, placemodes, "Full"));
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
                MessageUtil.sendSurroundMessage(ChatFormatting.WHITE + "Centering!");
            }

            mc.player.connection.sendPacket(new CPacketPlayer.Position(center.x, center.y, center.z, true));
            mc.player.setPosition(center.x, center.y, center.z);
        }
    }

    public void onUpdate() {
        if (nullCheck()) return;

        if (timeout.getValBoolean()) {
            if (this.isToggled() && disablemode.getValueString() != "Never") {
                if (mc.player.ticksExisted % timeoutticks.getValueInt() == 0) {
                    if (infomessages.getValBoolean()) {
                        MessageUtil.sendSurroundMessage(ChatFormatting.WHITE + "Module is" + ChatFormatting.RED + " " + "disabling" + ChatFormatting.GRAY + " " + "(Timed Out)");
                    }
                    toggle();
                }
            }
        } else if (hasPlaced == true && disablemode.getValueString() == "Finish") {
            if (infomessages.getValBoolean()) {
                MessageUtil.sendSurroundMessage(ChatFormatting.WHITE + "Module is" + ChatFormatting.RED + " " + "disabling" + ChatFormatting.GRAY + " " + "(Finished)");
            }
            toggle();
        } else {
            if (!mc.player.onGround) {
                if (infomessages.getValBoolean()) {
                    MessageUtil.sendSurroundMessage(ChatFormatting.WHITE + "Module is" + ChatFormatting.RED + " " + "disabling" + ChatFormatting.GRAY + " " + "(OnJump)");
                }
                toggle();
            }
        }

        blocksPlaced = 0;

        for (Vec3d placePositions : getSurroundType()) {
            BlockPos blockPos = new BlockPos(placePositions.add(mc.player.getPositionVector()));

            if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) {
                int oldInventorySlot = mc.player.inventory.currentItem;

                if (preferobi.getValBoolean()) {
                    if (infomessages.getValBoolean()) {
                        MessageUtil.sendSurroundMessage(ChatFormatting.WHITE + "Switching to" + " " + ChatFormatting.AQUA + "obsidian");
                    }
                    mc.player.inventory.currentItem = PlayerUtil.getBlockInHotbar(Blocks.OBSIDIAN);
                } else {
                    if (infomessages.getValBoolean()) {
                        MessageUtil.sendSurroundMessage(ChatFormatting.WHITE + "Switching to" + " " + ChatFormatting.AQUA + "any block");
                    }
                    mc.player.inventory.currentItem = PlayerUtil.getAnyBlockInHotbar();
                }

                if (infomessages.getValBoolean()) {
                    MessageUtil.sendSurroundMessage(ChatFormatting.WHITE + "Placing block");
                }

                PlayerUtil.placeBlock(blockPos);
                mc.player.inventory.currentItem = oldInventorySlot;
                blocksPlaced++;

                if (blocksPlaced == blockspertick.getValueInt() && disablemode.getValueString() != "Never") return;
            }
        }

        if (blocksPlaced == 0) {
            hasPlaced = true;
        }
    }

    private List<Vec3d> getSurroundType() {
        if (placemode.getValueString() == "Standard") {
            return standardSurround;
        } else if (placemode.getValueString() == "Full") {
            return fullSurround;
        }
        return antiCitySurround;
    }

    private final List<Vec3d> standardSurround = new ArrayList<>(Arrays.asList(
            new Vec3d(0, -1, 0),
            new Vec3d(1, 0, 0),
            new Vec3d(-1, 0, 0),
            new Vec3d(0, 0, 1),
            new Vec3d(0, 0, -1)
    ));

    private final List<Vec3d> fullSurround = new ArrayList<>(Arrays.asList(
            new Vec3d(0, -1, 0),
            new Vec3d(1, -1, 0),
            new Vec3d(0, -1, 1),
            new Vec3d(-1, -1, 0),
            new Vec3d(0, -1, -1),
            new Vec3d(1, 0, 0),
            new Vec3d(0, 0, 1),
            new Vec3d(-1, 0, 0),
            new Vec3d(0, 0, -1)
    ));

    private final List<Vec3d> antiCitySurround = new ArrayList<>(Arrays.asList(
            new Vec3d(0, -1, 0),
            new Vec3d(1, 0, 0),
            new Vec3d(-1, 0, 0),
            new Vec3d(0, 0, 1),
            new Vec3d(0, 0, -1),
            new Vec3d(2, 0, 0),
            new Vec3d(-2, 0, 0),
            new Vec3d(0, 0, 2),
            new Vec3d(0, 0, -2),
            new Vec3d(3, 0, 0),
            new Vec3d(-3, 0, 0),
            new Vec3d(0, 0, 3),
            new Vec3d(0, 0, -3)
    ));

    public Vec3d getCenter(double posX, double posY, double posZ) {
        double x = Math.floor(posX) + 0.5D;
        double y = Math.floor(posY);
        double z = Math.floor(posZ) + 0.5D;

        return new Vec3d(x, y, z);
    }

    public String getArraylistInfo() {
        return ChatFormatting.GRAY + " " + placemode.getValueString().toUpperCase();
    }
}
