package me.olliem5.past.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.module.HoleUtil;
import me.olliem5.past.util.render.RenderUtil;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AutoCreeper extends Module {
    public AutoCreeper() {
        super("AutoCreeper", "Automatically attacks with creeper eggs, for anarchypvp.pw", Category.COMBAT);
    }

    Setting mode;
    Setting playerrange;
    Setting renderplace;
    Setting rendermode;
    Setting red;
    Setting green;
    Setting blue;
    Setting alpha;
    Setting rainbow;

    public ArrayList<String> creepermodes;
    public ArrayList<String> rendermodes;

    @Override
    public void setup() {
        creepermodes = new ArrayList<>();
        creepermodes.add("Hole");
        creepermodes.add("Always");

        Past.settingsManager.registerSetting(mode = new Setting("Mode", "AutoCreeperMode", this, creepermodes, "Hole"));
        Past.settingsManager.registerSetting(playerrange = new Setting("Player Range", "AutoCreeperPlayerRange", 1.0, 25.0, 50.0, this));
        Past.settingsManager.registerSetting(renderplace = new Setting("Render Place", "AutoCrystalRenderPlace", true, this));
        Past.settingsManager.registerSetting(rendermode = new Setting("Mode", "AutoCreeperRenderMode", this, rendermodes, "FullFrame"));
        Past.settingsManager.registerSetting(red = new Setting("Red", "AutoCreeperRed", 0, 100, 255, this));
        Past.settingsManager.registerSetting(green = new Setting("Green", "AutoCreeperGreen", 0, 100, 255, this));
        Past.settingsManager.registerSetting(blue = new Setting("Blue", "AutoCreeperBlue", 0, 100, 255, this));
        Past.settingsManager.registerSetting(alpha = new Setting("Alpha", "AutoCreeperAlpha", 0, 100, 255, this));
        Past.settingsManager.registerSetting(rainbow = new Setting("Rainbow", "AutoCreeperRainbow", true, this));
    }

    private EntityPlayer target = null;
    private BlockPos placePosition = null;

    private boolean offhand = false;

    public void onDisable() {
        target = null;
        placePosition = null;
    }

    public void onUpdate() {
        if (nullCheck()) return;

        if (mc.player.getHeldItemOffhand().getItem() == Items.SPAWN_EGG) {
            offhand = true;
        } else {
            offhand = false;
        }

        mc.world.playerEntities.stream()
                .filter(target -> target != null)
                .filter(target -> target != mc.player)
                .filter(target -> mc.player.getDistance(target) <= playerrange.getValueDouble())
                .filter(target -> !target.isDead)
                .filter(target -> target.getHealth() > 0)
                .filter(target -> HoleUtil.isPlayerInHole(target) || mode.getValueString() == "Always")
                .forEach(target -> {
                    placePosition = new BlockPos(target.posX, target.posY -1, target.posZ);
                    placeCreeperEggs();
                });
    }

    private void placeCreeperEggs() {
        if (placePosition != null) {
            if (mc.player.getHeldItemMainhand().getItem() == Items.SPAWN_EGG || mc.player.getHeldItemOffhand().getItem() == Items.SPAWN_EGG) {
                mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(placePosition, EnumFacing.UP, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0, 0, 0));
            }
        }
    }

    private void renderPlacementBlock() {
        if (placePosition != null) {

            float[] hue = new float[] {(float) (System.currentTimeMillis() % 7500L) / 7500f};
            int rgb = Color.HSBtoRGB(hue[0], 0.8f, 0.8f);
            int rgbred = rgb >> 16 & 255;
            int rgbgreen = rgb >> 8 & 255;
            int rgbblue = rgb & 255;

            if (!rainbow.getValBoolean()) {
                if (rendermode.getValueString() == "Full") {
                    RenderUtil.drawBox(RenderUtil.generateBB(placePosition.getX(), placePosition.getY(), placePosition.getZ()), red.getValueInt() / 255f, green.getValueInt() / 255f, blue.getValueInt() / 255f, alpha.getValueInt() / 255f);
                } else if (rendermode.getValueString() == "FullFrame") {
                    RenderUtil.drawBoxOutline(RenderUtil.generateBB(placePosition.getX(), placePosition.getY(), placePosition.getZ()), red.getValueInt() / 255f, green.getValueInt() / 255f, blue.getValueInt() / 255f, alpha.getValueInt() / 255f);
                } else {
                    RenderUtil.drawOutline(RenderUtil.generateBB(placePosition.getX(), placePosition.getY(), placePosition.getZ()), red.getValueInt() / 255f, green.getValueInt() / 255f, blue.getValueInt() / 255f, alpha.getValueInt() / 255f);
                }
            } else {
                if (rendermode.getValueString() == "Full") {
                    RenderUtil.drawBox(RenderUtil.generateBB(placePosition.getX(), placePosition.getY(), placePosition.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, alpha.getValueInt() / 255f);
                } else if (rendermode.getValueString() == "FullFrame") {
                    RenderUtil.drawBoxOutline(RenderUtil.generateBB(placePosition.getX(), placePosition.getY(), placePosition.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, alpha.getValueInt() / 255f);
                } else {
                    RenderUtil.drawOutline(RenderUtil.generateBB(placePosition.getX(), placePosition.getY(), placePosition.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, alpha.getValueInt() / 255f);
                }
            }
        }
    }

    @EventHandler
    public Listener<RenderWorldLastEvent> listener = new Listener<>(event -> {
        if (nullCheck()) return;

        renderPlacementBlock();
    });

    public String getArraylistInfo() {
        if (target != null) {
            return ChatFormatting.GRAY + " " + target.getName();
        } else {
            return "";
        }
    }
}