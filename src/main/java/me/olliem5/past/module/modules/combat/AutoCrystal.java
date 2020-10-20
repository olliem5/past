package me.olliem5.past.module.modules.combat;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.CooldownUtil;
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
        super ("AutoCrystal", "Breaks and Places crystals", Category.COMBAT);
    }

    CooldownUtil breaktimer = new CooldownUtil();
    CooldownUtil placetimer = new CooldownUtil();

    Setting placedelay;
    Setting breakdelay;
    Setting placerange;
    Setting breakrange;
    Setting placemode;
    Setting autoswitch;
    Setting breakmode;
    Setting swinghand;
    Setting mindamage;
    Setting faceplace;

    private ArrayList<String> placemodes;
    private ArrayList<String> breakmodes;
    private ArrayList<String> swinghands;

    @Override
    public void setup() {
        placemodes = new ArrayList<>();
        placemodes.add("None");
        placemodes.add("Single");
        placemodes.add("Multi");

        breakmodes = new ArrayList<>();
        breakmodes.add("None");
        breakmodes.add("Near");

        swinghands = new ArrayList<>();
        swinghands.add("Main");
        swinghands.add("Offhand");

        Past.settingsManager.registerSetting(placemode = new Setting("Place", this, placemodes, "Single"));
        Past.settingsManager.registerSetting(breakmode = new Setting("Break", this, breakmodes, "Near"));
        Past.settingsManager.registerSetting(swinghand = new Setting("Swing", this, swinghands, "Main"));
        Past.settingsManager.registerSetting(autoswitch = new Setting("AutoSwitch", true, this));
        Past.settingsManager.registerSetting(placedelay = new Setting("Place Delay", 0, 2, 20, this));
        Past.settingsManager.registerSetting(breakdelay = new Setting("Break Delay", 0, 2, 20, this));
        Past.settingsManager.registerSetting(placerange = new Setting("Place Range", 0, 5, 10, this));
        Past.settingsManager.registerSetting(breakrange = new Setting("Break Range", 0, 5, 10, this));
        Past.settingsManager.registerSetting(mindamage = new Setting("Min Damage", 0, 8, 35, this));
        Past.settingsManager.registerSetting(faceplace = new Setting("Faceplace", 0, 8, 35, this));
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.world == null) { return; }
         if (breaktimer.passed(breakdelay.getValueInt() * 50)) {
            EntityEnderCrystal crystal = mc.world.loadedEntityList.stream()
                    .filter(entity -> entity instanceof EntityEnderCrystal)
                    .filter(e -> mc.player.getDistance(e) <= breakrange.getValueInt())
                    .map(entity -> (EntityEnderCrystal) entity)
                    .min(Comparator.comparing(c -> mc.player.getDistance(c)))
                    .orElse(null);
            System.out.println(breakmode.getValueString());
            if (breakmode.getValueString().equalsIgnoreCase("near") && mc.player != null && crystal != null) {
                mc.playerController.attackEntity(mc.player, crystal);
                if (swinghand.getValueString().equalsIgnoreCase("main")) {
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                } else if (swinghand.getValueString().equalsIgnoreCase("offhand")) {
                    mc.player.swingArm(EnumHand.OFF_HAND);
                }
            }
            breaktimer.reset();
        } else {
            //Do stuff.
        }

        if (placetimer.passed(placedelay.getValueInt() * 50)) {
            Entity ent = null;
            Entity lastTarget = null;
            if (placemode.getValueString().equalsIgnoreCase("Multi")) {
                BlockPos finalPos = null;
                final List<BlockPos> blocks = this.findCrystalBlocks();
                final List<Entity> entities = new ArrayList<Entity>();
                entities.addAll((Collection<? extends Entity>) mc.world.playerEntities.stream().collect(Collectors.toList()));
                double damage = 0.5;
                for (final Entity entity : entities) {
                    if (entity != mc.player){
                        if (((EntityLivingBase) entity).getHealth() <= 0.0f) {
                            continue;
                        }
                        for (final BlockPos blockPos : blocks) {

                            final double b = entity.getDistanceSq(blockPos);
                            if (b > 56.2) {
                                continue;
                            }
                            final double d = calculateDamage(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ()+ 0.5, entity);
                            if (d < this.mindamage.getValueInt() && ((EntityLivingBase) entity).getHealth() + ((EntityLivingBase) entity).getAbsorptionAmount() > this.faceplace.getValueInt()) {
                                continue;
                            }
                            if (d <= damage) {
                                continue;
                            }
                            final double self = calculateDamage(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, (Entity) mc.player);
                            damage = d;
                            finalPos = blockPos;
                            ent = entity;
                            lastTarget = entity;
                        }
                    }
                }
                if (damage == 0.5) {
                    return;
                }
                int crystalSlot = (mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) ? mc.player.inventory.currentItem : -1;
                if (crystalSlot == -1) {
                    for (int l = 0; l < 9; ++l) {
                        if (mc.player.inventory.getStackInSlot(l).getItem() == Items.END_CRYSTAL) {
                            crystalSlot = l;
                            break;
                        }
                    }
                }
                boolean offhand = false;
                if (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
                    offhand = true;
                } else if (crystalSlot == -1) {
                    return;
                }
                if (!(mc.player.getHeldItemMainhand().equals(Items.END_CRYSTAL)) && mc.player.inventory.currentItem != crystalSlot) {
                    if (this.autoswitch.getValBoolean()) {
                        mc.player.inventory.currentItem = crystalSlot;
                    }
                    return;
                }
                final RayTraceResult result = mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY +mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(finalPos.getX() + 0.5, finalPos.getY() - 0.5, finalPos.getZ() + 0.5));
                EnumFacing f;
                if (result == null || result.sideHit == null) {
                    f = EnumFacing.UP;
                } else {
                    f = result.sideHit;
                }
                mc.player.connection.sendPacket((Packet) new CPacketPlayerTryUseItemOnBlock(finalPos, f, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
            }
            placetimer.reset();

        } else {
            //in cooldown
        }
    }

    @Override
    public void onToggle() {
        super.onToggle();
    }

    @Override
    public void onRender() {}

    public static float calculateDamage(double posX, double posY, double posZ, Entity entity) {
        float doubleExplosionSize = 12.0F;
        double distancedsize = entity.getDistance(posX, posY, posZ) / (double) doubleExplosionSize;
        Vec3d vec3d = new Vec3d(posX, posY, posZ);
        double blockDensity = (double) entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        double v = (1.0D - distancedsize) * blockDensity;
        float damage = (float) ((int) ((v * v + v) / 2.0D * 7.0D * (double) doubleExplosionSize + 1.0D));
        double finald = 1.0D;
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
            damage *= 1.0F - f / 25.0F;

            if (entity.isPotionActive(Potion.getPotionById(11))) {
                damage = damage - (damage / 4);
            }
            //   damage = Math.max(damage - ep.getAbsorptionAmount(), 0.0F);
            return damage;
        } else {
            damage = CombatRules.getDamageAfterAbsorb(damage, (float) entity.getTotalArmorValue(), (float) entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            return damage;
        }
    }

    private static float getDamageMultiplied(float damage) {
        int diff = mc.world.getDifficulty().getDifficultyId();
        return damage * (diff == 0 ? 0 : (diff == 2 ? 1 : (diff == 1 ? 0.5f : 1.5f)));
    }

    public static float calculateDamage(EntityEnderCrystal crystal, Entity entity) {
        return calculateDamage(crystal.posX, crystal.posY, crystal.posZ, entity);
    }

    private List<BlockPos> findCrystalBlocks() {
        NonNullList positions = NonNullList.create();
        positions.addAll((Collection)this.getSphere(getPlayerPos(), this.placerange.getValueInt(), this.placerange.getValueInt(), false, true, 0).stream().filter(this::canPlaceCrystal).collect(Collectors.toList()));
        return (List<BlockPos>)positions;
    }

    public List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final List<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = loc.getX();
        final int cy = loc.getY();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int)r) : cy; y < (sphere ? (cy + r) : ((float)(cy + h))); ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }

    private boolean canPlaceCrystal(final BlockPos blockPos) {
        final BlockPos boost = blockPos.add(0, 1, 0);
        final BlockPos boost2 = blockPos.add(0, 2, 0);
        return (mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK || mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN) && mc.world.getBlockState(boost).getBlock() == Blocks.AIR && mc.world.getBlockState(boost2).getBlock() == Blocks.AIR && mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost)).isEmpty() && mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost2)).isEmpty();
    }
}
