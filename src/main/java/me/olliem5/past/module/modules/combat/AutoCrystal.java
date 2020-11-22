package me.olliem5.past.module.modules.combat;

import me.olliem5.past.Past;
import me.olliem5.past.event.events.PacketEvent;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.module.CooldownUtil;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.Explosion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AutoCrystal extends Module {
    static Minecraft mc = Minecraft.getMinecraft();

    public AutoCrystal() {
        super("AutoCrystal", "Places and breaks crystals to kill enemies", Category.COMBAT);
    }

    CooldownUtil breaktimer = new CooldownUtil();
    CooldownUtil placetimer = new CooldownUtil();

    Setting placemode;
    Setting breakmode;
    Setting swinghand;
    Setting rotate;
//    Setting autoswitch;
//    Setting placedelay;
    Setting breakdelay;
//    Setting placerange;
    Setting breakrange;
//    Setting mindamage;
//    Setting faceplace;

    private ArrayList<String> placemodes;
    private ArrayList<String> breakmodes;
    private ArrayList<String> swinghands;

    @Override
    public void setup() {
        placemodes = new ArrayList<>();
        placemodes.add("Single");
        placemodes.add("Multi");
        placemodes.add("None");

        breakmodes = new ArrayList<>();
        breakmodes.add("Nearest");
        breakmodes.add("None");

        swinghands = new ArrayList<>();
        swinghands.add("Mainhand");
        swinghands.add("Offhand");

        Past.settingsManager.registerSetting(placemode = new Setting("Place", "AutoCrystalPlace", this, placemodes, "Single"));
        Past.settingsManager.registerSetting(breakmode = new Setting("Break", "AutoCrystalBreak", this, breakmodes, "Nearest"));
        Past.settingsManager.registerSetting(swinghand = new Setting("Swing", "AutoCrystalSwing", this, swinghands, "Mainhand"));
        Past.settingsManager.registerSetting(rotate = new Setting("Rotate", "AutoCrystalRotate", true, this));
//        Past.settingsManager.registerSetting(autoswitch = new Setting("Auto Switch", "AutoCrystalAutoSwitch", true, this));
//        Past.settingsManager.registerSetting(placedelay = new Setting("Place Delay", "AutoCrystalPlaceDelay", 0, 2, 20, this));
        Past.settingsManager.registerSetting(breakdelay = new Setting("Break Delay", "AutoCrystalBreakDelay", 0, 2, 20, this));
//        Past.settingsManager.registerSetting(placerange = new Setting("Place Range", "AutoCrystalPlaceRange", 0.0, 4.4, 10.0, this));
        Past.settingsManager.registerSetting(breakrange = new Setting("Break Range", "AutoCrystalBreakRange", 0.0, 4.4, 10.0, this));
//        Past.settingsManager.registerSetting(mindamage = new Setting("Min Damage", "AutoCrystalMinDamage", 0, 8, 35, this));
//        Past.settingsManager.registerSetting(faceplace = new Setting("Faceplace", "AutoCrystalFaceplace", 0, 8, 35, this));
    }

    @Override
    public void onUpdate() {
        if (nullCheck()) return;

        /**
         * Breaking Crystals
         */

        if (breaktimer.passed(breakdelay.getValueInt() * 50)) {

            EntityEnderCrystal crystal = mc.world.loadedEntityList.stream()
                    .filter(entity -> entity instanceof EntityEnderCrystal)
                    .filter(e -> mc.player.getDistance(e) <= breakrange.getValueDouble())
                    .map(entity -> (EntityEnderCrystal) entity)
                    .min(Comparator.comparing(c -> mc.player.getDistance(c)))
                    .orElse(null);

            if (breakmode.getValueString() == "Nearest" && mc.player != null && crystal != null) {

                if (rotate.getValBoolean()) {
                    lookAtPacket(crystal.posX, crystal.posY, crystal.posZ, mc.player);
                }

                mc.playerController.attackEntity(mc.player, crystal);

                if (swinghand.getValueString() == "Offhand") {
                    mc.player.swingArm(EnumHand.OFF_HAND);
                } else {
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                }

                if (breakmode.getValueString() == "None") return;
            }
            //Other break modes in the future, OnlyOwn, Smart/MostDamage
            breaktimer.reset();
        }

        /**
         * Placing Crystals
         */
    }

    /**
     * CrystalAura utils made by 086
     *
     * https://github.com/zeroeightysix/KAMI/blob/master/src/main/java/me/zeroeightsix/kami/module/modules/combat/CrystalAura.java
     *
     * legit every client uses them lmao
     *
     * TODO: Move to a util
     */

    private void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
        double[] v = calculateLookAt(px, py, pz, me);
        setYawAndPitch((float) v[0], (float) v[1]);
    }

    private boolean canPlaceCrystal(BlockPos blockPos) {
        BlockPos boost = blockPos.add(0, 1, 0);
        BlockPos boost2 = blockPos.add(0, 2, 0);
        return (mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK
                || mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN)
                && mc.world.getBlockState(boost).getBlock() == Blocks.AIR
                && mc.world.getBlockState(boost2).getBlock() == Blocks.AIR
                && mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost)).isEmpty()
                && mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost2)).isEmpty();
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }

    private List<BlockPos> findCrystalBlocks() {
        NonNullList<BlockPos> positions = NonNullList.create();
        positions.addAll(getSphere(getPlayerPos(), (float) breakrange.getValueDouble(), (int) breakrange.getValueDouble(), false, true, 0).stream().filter(this::canPlaceCrystal).collect(Collectors.toList()));
        return positions;
    }

    public List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        List<BlockPos> circleblocks = new ArrayList<>();
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        for (int x = cx - (int) r; x <= cx + r; x++) {
            for (int z = cz - (int) r; z <= cz + r; z++) {
                for (int y = (sphere ? cy - (int) r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }

    public static float calculateDamage(double posX, double posY, double posZ, Entity entity) {
        float doubleExplosionSize = 6.0F * 2.0F;
        double distancedsize = entity.getDistance(posX, posY, posZ) / (double) doubleExplosionSize;
        Vec3d vec3d = new Vec3d(posX, posY, posZ);
        double blockDensity = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        double v = (1.0D - distancedsize) * blockDensity;
        float damage = (float) ((int) ((v * v + v) / 2.0D * 7.0D * (double) doubleExplosionSize + 1.0D));
        double finald = 1;

        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase) entity, getDamageMultiplied(damage), new Explosion(mc.world, null, posX, posY, posZ, 6F, false, true));
        }

        return (float) finald;
    }

    public static float getBlastReduction(EntityLivingBase entity, float damage, Explosion explosion) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer ep = (EntityPlayer) entity;
            DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            damage = CombatRules.getDamageAfterAbsorb(damage, (float) ep.getTotalArmorValue(), (float) ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());

            int k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            float f = MathHelper.clamp(k, 0.0F, 20.0F);
            damage = damage * (1.0F - f / 25.0F);

            if (entity.isPotionActive(Potion.getPotionById(11))) {
                damage = damage - (damage / 4);
            }

            damage = Math.max(damage - ep.getAbsorptionAmount(), 0.0F);
            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb(damage, (float) entity.getTotalArmorValue(), (float) entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return damage;
    }

    private static float getDamageMultiplied(float damage) {
        int diff = mc.world.getDifficulty().getId();
        return damage * (diff == 0 ? 0 : (diff == 2 ? 1 : (diff == 1 ? 0.5f : 1.5f)));
    }

    public static float calculateDamage(EntityEnderCrystal crystal, Entity entity) {
        return calculateDamage(crystal.posX, crystal.posY, crystal.posZ, entity);
    }

    private static boolean isSpoofingAngles;
    private static double yaw;
    private static double pitch;

    private static void setYawAndPitch(float yaw1, float pitch1) {
        yaw = yaw1;
        pitch = pitch1;
        isSpoofingAngles = true;
    }

    private static void resetRotation() {
        if (isSpoofingAngles) {
            yaw = mc.player.rotationYaw;
            pitch = mc.player.rotationPitch;
            isSpoofingAngles = false;
        }
    }

    public static double[] calculateLookAt(double px, double py, double pz, EntityPlayer me) {
        double dirx = me.posX - px;
        double diry = me.posY - py;
        double dirz = me.posZ - pz;

        double len = Math.sqrt(dirx*dirx + diry*diry + dirz*dirz);

        dirx /= len;
        diry /= len;
        dirz /= len;

        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);

        pitch = pitch * 180.0d / Math.PI;
        yaw = yaw * 180.0d / Math.PI;

        yaw += 90f;

        return new double[]{yaw,pitch};
    }

    @EventHandler
    private Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        Packet packet = event.getPacket();
        if (packet instanceof CPacketPlayer) {
            if (isSpoofingAngles) {
                ((CPacketPlayer) packet).yaw = (float) yaw;
                ((CPacketPlayer) packet).pitch = (float) pitch;
            }
        }
    });

    @Override
    public void onDisable() {
        resetRotation();
    }
}
