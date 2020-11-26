package me.olliem5.past.module.modules.combat;

import me.olliem5.past.Past;
import me.olliem5.past.event.events.PacketEvent;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.client.MessageUtil;
import me.olliem5.past.util.colour.ColourUtil;
import me.olliem5.past.util.module.CooldownUtil;
import me.olliem5.past.util.module.CrystalUtil;
import me.olliem5.past.util.render.RenderUtil;
import me.olliem5.past.util.text.RenderText;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AutoCrystalRewrite extends Module {
    public AutoCrystalRewrite() {
        super("NewAutoCrystal", "AutoCrystal, but more poggers", Category.COMBAT);
    }

    private static CrystalUtil crystalUtil = new CrystalUtil();

    CooldownUtil breaktimer = new CooldownUtil();
    CooldownUtil placetimer = new CooldownUtil();

    Setting place;
    Setting explode; //stupid java thinkin i wanna break when i name dis break
    Setting breakhand;
    Setting rotate;
    Setting raytrace;
    Setting nodesync;
    Setting placedelay;
    Setting breakdelay;
    Setting placerange;
    Setting breakrange;
    Setting enemyrange;
    Setting mindamage;
    Setting faceplace;
    Setting maxselfdamage;
    Setting customfont;
    Setting renderdamage;
    Setting renderplace;
    Setting rendermode;
    Setting red;
    Setting green;
    Setting blue;
    Setting opacity;
    Setting rainbow;

    private ArrayList<String> breakhands;
    private ArrayList<String> rendermodes;

    @Override
    public void setup() {
        breakhands = new ArrayList<>();
        breakhands.add("Mainhand");
        breakhands.add("Offhand");
        breakhands.add("Both");

        rendermodes = new ArrayList<>();
        rendermodes.add("Full");
        rendermodes.add("FullFrame");
        rendermodes.add("Frame");

        Past.settingsManager.registerSetting(place = new Setting("Place", "AutoCrystalRewritePlace", true, this));
        Past.settingsManager.registerSetting(explode = new Setting("Break", "AutoCrystalRewriteExplode", true, this));
        Past.settingsManager.registerSetting(breakhand = new Setting("Swing", "AutoCrystalRewriteBreakHand", this, breakhands, "Mainhand"));
        Past.settingsManager.registerSetting(rotate = new Setting("Rotate", "AutoCrystalRewriteRotate", true, this));
        Past.settingsManager.registerSetting(raytrace = new Setting("Raytrace", "AutoCrystalRewriteRaytrace", true, this));
        Past.settingsManager.registerSetting(nodesync = new Setting("No Desync", "AutoCrystalRewriteNoDesync", true, this));
        Past.settingsManager.registerSetting(placedelay = new Setting("Place Delay (ms)", "AutoCrystalRewritePlaceDelay", 0.0, 200.0, 1000.0, this));
        Past.settingsManager.registerSetting(breakdelay = new Setting("Break Delay (ms)", "AutoCrystalRewriteBreakDelay", 0.0, 200.0, 1000.0, this));
        Past.settingsManager.registerSetting(placerange = new Setting("Place Range", "AutoCrystalRewritePlaceRange", 0.0, 4.4, 10.0, this));
        Past.settingsManager.registerSetting(breakrange = new Setting("Break Range", "AutoCrystalRewriteBreakRange", 0.0, 4.4, 10.0, this));
        Past.settingsManager.registerSetting(enemyrange = new Setting("Enemy Range", "AutoCrystalRewriteEnemyRange", 0.0, 15.0, 50.0, this));
        Past.settingsManager.registerSetting(mindamage = new Setting("Min Damage", "AutoCrystalRewriteMinDamage", 0.0, 6.0, 36.0, this));
        Past.settingsManager.registerSetting(faceplace = new Setting("Faceplace HP", "AutoCrystalRewriteFaceplace", 0.0, 8.0, 36.0, this));
        Past.settingsManager.registerSetting(maxselfdamage = new Setting("Max Self Dmg", "AutoCrystalRewriteMaxSelfDamage", 0.0, 8.0, 36.0, this));
        Past.settingsManager.registerSetting(customfont = new Setting("Custom Font", "AutoCrystalRewriteCustomFont", true, this));
        Past.settingsManager.registerSetting(renderdamage = new Setting("Render Damage", "AutoCrystalRewriteRenderDamage", true, this));
        Past.settingsManager.registerSetting(renderplace = new Setting("Render Place", "AutoCrystalRewriteRenderPlace", true, this));
        Past.settingsManager.registerSetting(rendermode = new Setting("Mode", "AutoCrystalRewriteRenderMode", this, rendermodes, "FullFrame"));
        Past.settingsManager.registerSetting(red = new Setting("Red", "AutoCrystalRewriteRed", 0, 100, 255, this));
        Past.settingsManager.registerSetting(green = new Setting("Green", "AutoCrystalRewriteGreen", 0, 100, 255, this));
        Past.settingsManager.registerSetting(blue = new Setting("Blue", "AutoCrystalRewriteBlue", 0, 100, 255, this));
        Past.settingsManager.registerSetting(opacity = new Setting("Opacity", "AutoCrystalRewriteOpacity", 0, 100, 255, this));
        Past.settingsManager.registerSetting(rainbow = new Setting("Rainbow", "AutoCrystalRewriteRainbow", true, this));
    }

    BlockPos bPos = null;
    private BlockPos renderBlock;
    private EnumFacing enumFacing;
    private Entity renderEnt;

    private boolean offhand = false;
    private static boolean togglePitch = false;

    private double renderDamageText;

    @Override
    public void onDisable() {
        renderBlock = null;
        renderEnt = null;
        crystalUtil.resetRotation();
    }

    public void onUpdate() {
        if (nullCheck()) return;

        placeCrystal();
        breakCrystal();
    }

    private void placeCrystal() {
        if (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            offhand = true;
        } else {
            offhand = false;
        }

        double damage = 0.5D;

        List<BlockPos> blocks = crystalUtil.findCrystalBlocksRewrite();
        List<Entity> entities = new ArrayList<>();
        entities.addAll(mc.world.playerEntities.stream().filter(entityPlayer -> !Past.friendsManager.isFriend(entityPlayer.getName())).collect(Collectors.toList()));

        for (Entity entity : entities) {
            if (entity == mc.player || ((EntityLivingBase) entity).getHealth() <= 0 || mc.player.getDistance(entity) > enemyrange.getValueDouble()) continue;

            for (BlockPos blockPos : blocks) {
//                double d = crystalUtil.calculateDamage(blockPos.getX() + 0.5D, blockPos.getY() + 1, blockPos.getZ() + 0.5D, entity);
//                double self = crystalUtil.calculateDamage(blockPos.getX() + 0.5D, blockPos.getY() + 1, blockPos.getZ() + 0.5D, mc.player);
//
//                if (d < mindamage.getValueDouble() && entity.getHealth() + entity.getAbsorptionAmount() > faceplace.getValueDouble() || maxselfdamage.getValueDouble() <= self || d < damage) continue;
//
//                damage = d;
//                bPos = blockPos;

                double d = crystalUtil.calculateDamage(blockPos.getX() + 0.5D, blockPos.getY() + 1, blockPos.getZ() + 0.5D, entity);

                if (d < mindamage.getValueDouble() && ((EntityLivingBase) entity).getHealth() + ((EntityLivingBase) entity).getAbsorptionAmount() > faceplace.getValueDouble()) continue;

                if (d > damage) {
                    double self = crystalUtil.calculateDamage(blockPos.getX() + 0.5D, blockPos.getY() + 1, blockPos.getZ() + 0.5D, mc.player);

                    if ((self > d && !(d < ((EntityLivingBase) entity).getHealth())) || self - 0.5D > mc.player.getHealth()) continue;

                    if (self > maxselfdamage.getValueDouble()) continue;

                    damage = d;
                    bPos = blockPos;
                    renderEnt = entity;
                    renderDamageText = damage;
                }
            }
        }

        if (damage == 0.5D) {
            renderBlock = null;
            renderEnt = null;
            crystalUtil.resetRotation();
            return;
        }

        renderBlock = bPos;

        if (placetimer.passed(placedelay.getValueDouble()) && place.getValBoolean()) {
            if (rotate.getValBoolean()) {
                crystalUtil.lookAtPacket(bPos.getX() + 0.5D, bPos.getY() - 0.5D, bPos.getZ() + 0.5D, mc.player);
            }

            RayTraceResult result = mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(bPos.getX() + 0.5D, bPos.getY() - 0.5D, bPos.getZ() + 0.5D));

            if (raytrace.getValBoolean()) {
                if (result == null || result.sideHit == null) {
                    enumFacing = null;
                    renderBlock = null;
                    crystalUtil.resetRotation();
                    return;
                } else {
                    enumFacing = result.sideHit;
                }
            }

            if (bPos != null) {
                if (raytrace.getValBoolean() && enumFacing != null) {
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(bPos, enumFacing, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0, 0, 0));
                } else if (bPos.getY() == 255) {
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(bPos, EnumFacing.DOWN, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0, 0, 0));
                } else {
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(bPos, EnumFacing.UP, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0, 0, 0));
                }
            }

            if (crystalUtil.isSpoofingAngles) {
                if (togglePitch) {
                    mc.player.rotationPitch += 0.0004;
                    togglePitch = false;
                } else {
                    mc.player.rotationPitch -= 0.0004;
                    togglePitch = true;
                }
            }

            placetimer.reset();
        }
    }

    private void breakCrystal() {
        final EntityEnderCrystal crystal = (EntityEnderCrystal) mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).min(Comparator.comparing(c -> mc.player.getDistance(c))).orElse(null);

        if (explode.getValBoolean() && crystal != null && mc.player.getDistance(crystal) <= breakrange.getValueDouble() && breaktimer.passed(breakdelay.getValueDouble())) {
            if (rotate.getValBoolean()) {
                crystalUtil.lookAtPacket(crystal.posX, crystal.posY, crystal.posZ, mc.player);
            }

            mc.playerController.attackEntity(mc.player, crystal);

            if (breakhand.getValueString() == "Mainhand") {
                mc.player.swingArm(EnumHand.MAIN_HAND);
            } else if (breakhand.getValueString() == "Offhand") {
                mc.player.swingArm(EnumHand.OFF_HAND);
            } else {
                mc.player.swingArm(EnumHand.MAIN_HAND);
                mc.player.swingArm(EnumHand.OFF_HAND);
            }
            breaktimer.reset();
        } else {
            crystalUtil.resetRotation();
        }
    }

    @EventHandler
    private Listener<PacketEvent.Receive> packetReceiveListener = new Listener<>(event -> {
        if (event.getPacket() instanceof SPacketSoundEffect && nodesync.getValBoolean()) {
            final SPacketSoundEffect packet = (SPacketSoundEffect) event.getPacket();
            if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                for (Entity e : Minecraft.getMinecraft().world.loadedEntityList) {
                    if (e instanceof EntityEnderCrystal) {
                        if (e.getDistance(packet.getX(), packet.getY(), packet.getZ()) <= 6.0f) {
                            e.setDead();
                        }
                    }
                }
            }
        }
    });

    @EventHandler
    private Listener<PacketEvent.Send> packetSendListener = new Listener<>(event -> {
        Packet packet = event.getPacket();
        if (packet instanceof CPacketPlayer) {
            if (crystalUtil.isSpoofingAngles) {
                ((CPacketPlayer) packet).yaw = (float) crystalUtil.yaw;
                ((CPacketPlayer) packet).pitch = (float) crystalUtil.pitch;
            }
        }
    });

    private void renderACPlacement() {
        if (renderBlock != null) {

            float[] hue = new float[] {(float) (System.currentTimeMillis() % 7500L) / 7500f};
            int rgb = Color.HSBtoRGB(hue[0], 0.8f, 0.8f);
            int rgbred = rgb >> 16 & 255;
            int rgbgreen = rgb >> 8 & 255;
            int rgbblue = rgb & 255;

            if (!rainbow.getValBoolean()) {
                if (rendermode.getValueString() == "Full") {
                    RenderUtil.drawBox(RenderUtil.generateBB(renderBlock.getX(), renderBlock.getY(), renderBlock.getZ()), red.getValueInt(), green.getValueInt(), blue.getValueInt(), opacity.getValueInt());
                }

                if (rendermode.getValueString() == "FullFrame") {
                    RenderUtil.drawBoxOutline(RenderUtil.generateBB(renderBlock.getX(), renderBlock.getY(), renderBlock.getZ()), red.getValueInt(), green.getValueInt(), blue.getValueInt(), opacity.getValueInt());
                }

                if (rendermode.getValueString() == "Frame") {
                    RenderUtil.drawOutline(RenderUtil.generateBB(renderBlock.getX(), renderBlock.getY(), renderBlock.getZ()), red.getValueInt(), green.getValueInt(), blue.getValueInt(), opacity.getValueInt());
                }
            } else {
                if (rendermode.getValueString() == "Full") {
                    RenderUtil.drawBox(RenderUtil.generateBB(renderBlock.getX(), renderBlock.getY(), renderBlock.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, opacity.getValueInt());
                }

                if (rendermode.getValueString() == "FullFrame") {
                    RenderUtil.drawBoxOutline(RenderUtil.generateBB(renderBlock.getX(), renderBlock.getY(), renderBlock.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, opacity.getValueInt());
                }

                if (rendermode.getValueString() == "Frame") {
                    RenderUtil.drawOutline(RenderUtil.generateBB(renderBlock.getX(), renderBlock.getY(), renderBlock.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, opacity.getValueInt());
                }
            }
        }
    }

    private void renderACDamage() {
        if (renderBlock != null && renderEnt != null) {
            String renderDamageText3dp = String.format ("%.3f", renderDamageText);
            if (customfont.getValBoolean()) {
                RenderText.drawTextCustomFont(renderBlock, renderDamageText3dp + "");
            } else {
                RenderText.drawTextMCFont(renderBlock, renderDamageText3dp + "");
            }
        }
    }

    @EventHandler
    public Listener<RenderWorldLastEvent> renderWorldLastEventListener = new Listener<>(event -> {
        if (nullCheck()) return;

        if (renderplace.getValBoolean()) {
            renderACPlacement();
        }

        if (renderdamage.getValBoolean()) {
            renderACDamage();
        }
    });

    public String getArraylistInfo() {
        if (renderEnt != null) {
            return ColourUtil.gray + " " + renderEnt.getName();
        } else {
            return "";
        }
    }
}
