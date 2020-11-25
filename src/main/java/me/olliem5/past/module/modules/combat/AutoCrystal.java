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
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AutoCrystal extends Module {
    static Minecraft mc = Minecraft.getMinecraft();

    public AutoCrystal() {
        super("AutoCrystal", "Places and breaks crystals to kill enemies", Category.COMBAT);
    }

    /**
     * TODO: AutoSwitch
     * TODO: AntiWeakness
     * TODO: MultiPlace
     * TODO: Timeout when a certain ammount of hits are performed on one crystal
     * TODO: Info Messages on certain events, e.g. "FACEPLACING" with x health & tidy up formatting of existing messages
     * TODO: Make the CA check if the name is the same as yours, so it does not crystal the fake player from blink
     * TODO: better calc for place to stop sometimes multiplacing
     * TODO: better delay system
     */

    CooldownUtil breaktimer = new CooldownUtil();
    CooldownUtil placetimer = new CooldownUtil();

    Setting logicmode;
    Setting placemode;
    Setting breakmode;
    Setting swinghand;
    Setting rotate;
    Setting raytrace;
    Setting nodesync;
    Setting enemyrange;
    Setting antisuicide;
    Setting antisuicidevalue;
    Setting placedelay;
    Setting breakdelay;
    Setting wallsrange;
    Setting placerange;
    Setting breakrange;
    Setting mindamage;
    Setting maxselfdamage;
    Setting faceplace;
    Setting infomessages;
    Setting customfont;
    Setting renderdamage;
    Setting renderplace;
    Setting rendermode;
    Setting red;
    Setting green;
    Setting blue;
    Setting opacity;
    Setting rainbow;

    private ArrayList<String> logicmodes;
    private ArrayList<String> placemodes;
    private ArrayList<String> breakmodes;
    private ArrayList<String> swinghands;
    private ArrayList<String> rendermodes;

    @Override
    public void setup() {
        logicmodes = new ArrayList<>();
        logicmodes.add("PlBreak");
        logicmodes.add("BrPlace");

        placemodes = new ArrayList<>();
        placemodes.add("Single");
        placemodes.add("None");

        breakmodes = new ArrayList<>();
        breakmodes.add("Nearest");
        breakmodes.add("None");

        swinghands = new ArrayList<>();
        swinghands.add("Mainhand");
        swinghands.add("Offhand");

        rendermodes = new ArrayList<>();
        rendermodes.add("Full");
        rendermodes.add("FullFrame");
        rendermodes.add("Frame");

        Past.settingsManager.registerSetting(logicmode = new Setting("Logic", "AutoCrystalLogic", this, logicmodes, "PlBreak"));
        Past.settingsManager.registerSetting(placemode = new Setting("Place", "AutoCrystalPlace", this, placemodes, "Single"));
        Past.settingsManager.registerSetting(breakmode = new Setting("Break", "AutoCrystalBreak", this, breakmodes, "Nearest"));
        Past.settingsManager.registerSetting(swinghand = new Setting("Swing", "AutoCrystalSwing", this, swinghands, "Mainhand"));
        Past.settingsManager.registerSetting(rotate = new Setting("Rotate", "AutoCrystalRotate", true, this));
        Past.settingsManager.registerSetting(raytrace = new Setting("Raytrace", "AutoCrystalRaytrace", true, this));
        Past.settingsManager.registerSetting(nodesync = new Setting("No Desync", "AutoCrystalNoDesync", true, this));
        Past.settingsManager.registerSetting(enemyrange = new Setting("Enemy Rng", "AutoCrystalEnemyRange", 1.0, 15.0, 50.0, this));
        Past.settingsManager.registerSetting(antisuicide = new Setting("Anti Suicide", "AutoCrystalAntiSuicide", true, this));
        Past.settingsManager.registerSetting(antisuicidevalue = new Setting("Min HP", "AutoCrystalAntiSuicideHealth", 1.0, 15.0, 36.0, this));
        Past.settingsManager.registerSetting(placedelay = new Setting("Place Delay", "AutoCrystalPlaceDelay", 0, 2, 20, this));
        Past.settingsManager.registerSetting(breakdelay = new Setting("Break Delay", "AutoCrystalBreakDelay", 0, 2, 20, this));
        Past.settingsManager.registerSetting(wallsrange = new Setting("Walls Range", "AutoCrystalWallsRange", 0.0, 3.5, 10.0, this));
        Past.settingsManager.registerSetting(placerange = new Setting("Place Range", "AutoCrystalPlaceRange", 0.0, 4.4, 10.0, this));
        Past.settingsManager.registerSetting(breakrange = new Setting("Break Range", "AutoCrystalBreakRange", 0.0, 4.4, 10.0, this));
        Past.settingsManager.registerSetting(mindamage = new Setting("Min Damage", "AutoCrystalMinDamage", 0.0, 6.0, 36.0, this));
        Past.settingsManager.registerSetting(maxselfdamage = new Setting("Max Self Dmg", "AutoCrystalMaxSelfDamage", 0.0, 8.0, 36.0, this));
        Past.settingsManager.registerSetting(faceplace = new Setting("Faceplace HP", "AutoCrystalFaceplace", 0.0, 8.0, 36.0, this));
        Past.settingsManager.registerSetting(infomessages = new Setting("Info Messages", "AutoCrystalInfoMessages", false, this));
        Past.settingsManager.registerSetting(customfont = new Setting("Custom Font", "AutoCrystalCustomFont", true, this));
        Past.settingsManager.registerSetting(renderdamage = new Setting("Render Damage", "AutoCrystalRenderDamage", true, this));
        Past.settingsManager.registerSetting(renderplace = new Setting("Render Place", "AutoCrystalRenderPlace", true, this));
        Past.settingsManager.registerSetting(rendermode = new Setting("Mode", "AutoCrystalRenderMode", this, rendermodes, "FullFrame"));
        Past.settingsManager.registerSetting(red = new Setting("Red", "AutoCrystalRed", 0, 100, 255, this));
        Past.settingsManager.registerSetting(green = new Setting("Green", "AutoCrystalGreen", 0, 100, 255, this));
        Past.settingsManager.registerSetting(blue = new Setting("Blue", "AutoCrystalBlue", 0, 100, 255, this));
        Past.settingsManager.registerSetting(opacity = new Setting("Opacity", "AutoCrystalOpacity", 0, 100, 255, this));
        Past.settingsManager.registerSetting(rainbow = new Setting("Rainbow", "AutoCrystalRainbow", true, this));
    }

    private static CrystalUtil crystalUtil = new CrystalUtil();

    private BlockPos renderBlock;
    private EnumFacing enumFacing;
    private Entity renderEnt;

    private boolean offhand = false;
//    public static boolean acBreaking = false;
//    public static boolean acPlacing = false;
    private static boolean togglePitch = false;

    private double renderDamageText;

    @Override
    public void onEnable() {
//        acBreaking = false;
//        acPlacing = false;
    }

    @Override
    public void onDisable() {
        renderBlock = null;
        renderEnt = null;
        crystalUtil.resetRotation();
//        acBreaking = false;
//        acPlacing = false;
    }

    public void onUpdate() {
        if (nullCheck()) return;

        implementLogic();
    }

    private void implementLogic() {
        if (logicmode.getValueString() == "PlBreak") {
            placeCrystal();
            breakCrystal();
        } else {
            breakCrystal();
            placeCrystal();
        }
    }

    private void breakCrystal() {
        if (breaktimer.passed(breakdelay.getValueInt() * 50)) {

            if (antisuicide.getValBoolean() && (mc.player.getHealth() + mc.player.getAbsorptionAmount()) <= antisuicidevalue.getValueDouble()) return;

            EntityEnderCrystal crystal = mc.world.loadedEntityList.stream()
                    .filter(entity -> entity instanceof EntityEnderCrystal)
                    .filter(e -> mc.player.getDistance(e) <= breakrange.getValueDouble())
                    .map(entity -> (EntityEnderCrystal) entity)
                    .min(Comparator.comparing(c -> mc.player.getDistance(c)))
                    .orElse(null);

            if (crystal != null) {
                if (breakmode.getValueString() == "Nearest") {

                    if (!mc.player.canEntityBeSeen(crystal) && mc.player.getDistance(crystal) > wallsrange.getValueDouble()) return;

                    if (rotate.getValBoolean()) {
                        if (infomessages.getValBoolean()) {
                            MessageUtil.sendAutoCrystalMessage(ColourUtil.white + "Rotating to" + ColourUtil.aqua + " " + "crystal" + " " + ColourUtil.white + crystal.posX + ", " + crystal.posY + ", " + crystal.posZ);
                        }
                        crystalUtil.lookAtPacket(crystal.posX, crystal.posY, crystal.posZ, mc.player);
                    }

                    mc.playerController.attackEntity(mc.player, crystal);

                    if (swinghand.getValueString() == "Offhand") {
                        if (infomessages.getValBoolean()) {
                            MessageUtil.sendAutoCrystalMessage(ColourUtil.white + "Swinging the" + " " + ColourUtil.aqua + "off hand" + ColourUtil.white + " " + "at a crystal");
                        }
                        mc.player.swingArm(EnumHand.OFF_HAND);
                    } else {
                        if (infomessages.getValBoolean()) {
                            MessageUtil.sendAutoCrystalMessage(ColourUtil.white + "Swinging the" + " " + ColourUtil.aqua + "main hand" + ColourUtil.white + " " + "at a crystal");
                        }
                        mc.player.swingArm(EnumHand.MAIN_HAND);
                    }

                }
                if (breakmode.getValueString() == "None") return;
                //Other break modes in the future, OnlyOwn, Smart/MostDamage
                breaktimer.reset();
            } else {
                crystalUtil.resetRotation();
            }
        }
    }

    private void placeCrystal() {
        if (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            offhand = true;
        } else {
            offhand = false;
        }

        List<BlockPos> blocks = crystalUtil.findCrystalBlocks();
        List<Entity> entities = new ArrayList<>();

        entities.addAll(mc.world.playerEntities.stream().filter(entityPlayer -> !Past.friendsManager.isFriend(entityPlayer.getName())).collect(Collectors.toList()));

        BlockPos bPos = null;
        double damage = 0.5D;

        for (Entity entity : entities) {
            if (entity == mc.player || ((EntityLivingBase) entity).getHealth() <= 0) continue;

            for (BlockPos blockPos : blocks) {
                double b = entity.getDistanceSq(blockPos);

                if (b >= Math.pow(enemyrange.getValueDouble(), 2)) continue;

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

        if (placetimer.passed(placedelay.getValueInt() * 50)) {
            if (placemode.getValueString() == "Single") {

                if (rotate.getValBoolean()) {
                    if (infomessages.getValBoolean()) {
                        MessageUtil.sendAutoCrystalMessage(ColourUtil.white + "Rotating to" + " " + ColourUtil.aqua + " " + "block" + ColourUtil.white + bPos.getX() + 0.5D + ", " + (bPos.getY() - 0.5D) + ", " + bPos.getZ() + 0.5D);
                    }
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
                        if (infomessages.getValBoolean()) {
                            MessageUtil.sendAutoCrystalMessage(ColourUtil.white + "Place packet sent" + ColourUtil.aqua + " " + "raytrace");
                        }
                    } else if (bPos.getY() == 255) {
                        mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(bPos, EnumFacing.DOWN, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0, 0, 0));
                        if (infomessages.getValBoolean()) {
                            MessageUtil.sendAutoCrystalMessage(ColourUtil.white + "Place packet sent" + ColourUtil.aqua + " " + "above y255 place");
                        }
                    } else {
                        mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(bPos, EnumFacing.UP, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0, 0, 0));
                        if (infomessages.getValBoolean()) {
                            MessageUtil.sendAutoCrystalMessage(ColourUtil.white + "Place packet sent" + ColourUtil.aqua + " " + "normal");
                        }
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
            }
            if (placemode.getValueString() == "None") return;
            placetimer.reset();
        }
    }

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

    @EventHandler
    private Listener<PacketEvent.Receive> packetReceiveListener = new Listener<>(event -> {
        if (event.getPacket() instanceof SPacketSoundEffect && nodesync.getValBoolean()) {
            final SPacketSoundEffect packet = (SPacketSoundEffect) event.getPacket();
            if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                for (Entity e : Minecraft.getMinecraft().world.loadedEntityList) {
                    if (e instanceof EntityEnderCrystal) {
                        if (e.getDistance(packet.getX(), packet.getY(), packet.getZ()) <= 6.0f) {
                            if (infomessages.getValBoolean()) {
                                MessageUtil.sendAutoCrystalMessage(ColourUtil.white + "Desync crystal" + " " + ColourUtil.red + "removed");
                            }
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
                if (infomessages.getValBoolean()) {
                    MessageUtil.sendAutoCrystalMessage(ColourUtil.white + "Setting" + " " + ColourUtil.aqua + "yaw" + " " + ColourUtil.white + "to" + " " + crystalUtil.yaw);
                }
                ((CPacketPlayer) packet).pitch = (float) crystalUtil.pitch;
                if (infomessages.getValBoolean()) {
                    MessageUtil.sendAutoCrystalMessage(ColourUtil.white + "Setting" + " " + ColourUtil.aqua + "pitch" + " " + ColourUtil.white + "to" + " " + crystalUtil.pitch);
                }
            }
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