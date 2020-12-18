package me.olliem5.past.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.render.RenderUtil;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.awt.*;
import java.util.ArrayList;

public class AutoCreeper extends Module {
    public AutoCreeper() {
        super("AutoCreeper", "Automatically attacks with creeper eggs, for anarchypvp.pw", Category.COMBAT);
    }

    Setting mode;
    Setting playerrange;

    public ArrayList<String> creepermodes;

    @Override
    public void setup() {
        creepermodes = new ArrayList<>();
        creepermodes.add("Hole");

        Past.settingsManager.registerSetting(mode = new Setting("Mode", "AutoCreeperMode", this, creepermodes, "Hole"));
        Past.settingsManager.registerSetting(playerrange = new Setting("Player Range", "AutoCreeperPlayerRange", 1.0, 25.0, 50.0, this));
    }

    private BlockPos placePosition = null;
    private EntityPlayer target = null;
    private boolean offhand = false;

    int holeBlocks;

    public void onDisable() {
        placePosition = null;
        target = null;
    }

    public void onUpdate() {
        if (nullCheck()) return;

        findPlacePosition();
        placeCreeperEggs();
    }

    @EventHandler
    public Listener<RenderWorldLastEvent> listener = new Listener<>(event -> {
        if (nullCheck()) return;

        renderPlacementBlock();
    });

    private void findPlacePosition() {
        for (EntityPlayer player : mc.world.playerEntities) {
            if (player != null && player != mc.player && !player.isDead && player.getDistanceSq(mc.player) < playerrange.getValueDouble()) {
                if (mode.getValueString() == "Hole") {

                    Vec3d[] hole = {
                            player.getPositionVector().add(1.0D, 0.0D, 0.0D),
                            player.getPositionVector().add(-1.0D, 0.0D, 0.0D),
                            player.getPositionVector().add(0.0D, 0.0D, 1.0D),
                            player.getPositionVector().add(0.0D, 0.0D, -1.0D),
                            player.getPositionVector().add(0.0D, -1.0D, 0.0D)
                    };

                    Vec3d[] bottomHoleBlock = {
                            player.getPositionVector().add(0.0D, -1.0D, 0.0D)
                    };

                    holeBlocks = 0;

                    for (Vec3d vec3d : hole) {
                        BlockPos offset = new BlockPos(vec3d.x, vec3d.y, vec3d.z);

                        if (mc.world.getBlockState(offset).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(offset).getBlock() == Blocks.BEDROCK) {
                            ++holeBlocks;
                        }

                        if (holeBlocks == 5) {
                            target = player;

                            for (Vec3d vec3d1 : bottomHoleBlock) {
                                BlockPos offset1 = new BlockPos(vec3d1.x, vec3d1.y, vec3d1.z);

                                if (!(mc.player.getDistanceSq(target) <= 1)) {
                                    placePosition = offset1;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void placeCreeperEggs() {
        if (mc.player.getHeldItemOffhand().getItem() == Items.SPAWN_EGG) {
            offhand = true;
        } else {
            offhand = false;
        }

        if (placePosition != null) {
            if (mc.player.getHeldItemMainhand().getItem() == Items.SPAWN_EGG || mc.player.getHeldItemOffhand().getItem() == Items.SPAWN_EGG) {
                mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(placePosition, EnumFacing.UP, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0, 0, 0));
            }
        }
    }

    private void renderPlacementBlock() {
        if (placePosition != null) {
            float[] hue = new float[]{(float) (System.currentTimeMillis() % 7500L) / 7500f};
            int rgb = Color.HSBtoRGB(hue[0], 0.8f, 0.8f);
            int r = rgb >> 16 & 255;
            int g = rgb >> 8 & 255;
            int b = rgb & 255;

            RenderUtil.drawBox(RenderUtil.generateBB(placePosition.getX(), placePosition.getY(), placePosition.getZ()), r / 255f, g / 255f, b / 255f, 100);
        }
    }

    public String getArraylistInfo() {
        if (target != null) {
            return ChatFormatting.GRAY + " " + target.getName();
        } else {
            return "";
        }
    }
}