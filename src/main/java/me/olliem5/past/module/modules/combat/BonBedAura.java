package me.olliem5.past.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.client.MessageUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;

import java.util.Comparator;
import java.util.List;

public class BonBedAura extends Module {
    public BonBedAura() {
        super("BonBedAura", "Places and destroys beds, for 1.13+. Made by bon55.", Category.COMBAT);
    }

    //TODO: Mode for 1.12.2. (Probably just AutoBreak).

    /**
     * @author bon55
     * I got permission from him to use this when he made it very early on :)
     */

    Setting place;
    Setting explode;
    Setting autoswitch;
    Setting range;
    Setting placedelay;
    Setting debugMessages;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(place = new Setting("Place", "BedAuraPlace", true, this));
        Past.settingsManager.registerSetting(explode = new Setting("Explode", "BedAuraExplode", true, this));
        Past.settingsManager.registerSetting(autoswitch = new Setting("Auto Switch", "BedAuraAutoSwitch", true, this));
        Past.settingsManager.registerSetting(range = new Setting("Range", "BedAuraRange", 0, 7, 9, this));
        Past.settingsManager.registerSetting(placedelay = new Setting("Place Delay", "BedAuraPlaceDelay", 8, 15, 20, this));
        Past.settingsManager.registerSetting(debugMessages = new Setting("Info Messages", "BedAuraInfoMessages", true, this));
    }

    private int playerHotbarSlot = -1;
    private int lastHotbarSlot = -1;
    private EntityPlayer closestTarget;
    private String lastTickTargetName;
    private int bedSlot = -1;
    private BlockPos placeTarget;
    private float rotVar;
    private int blocksPlaced;
    private double diffXZ;
    private boolean firstRun;
    private boolean nowTop = false;

    @Override
    public void onEnable() {
        if (mc.player == null) {
            this.toggle();
            return;
        }

        MinecraftForge.EVENT_BUS.register(this);
        firstRun = true;
        blocksPlaced = 0;
        playerHotbarSlot = mc.player.inventory.currentItem;
        lastHotbarSlot = -1;
    }

    @Override
    public void onDisable() {
        if (mc.player == null) {
            return;
        }
        if (lastHotbarSlot != playerHotbarSlot && playerHotbarSlot != -1) {
            mc.player.inventory.currentItem = playerHotbarSlot;
        }
        if (debugMessages.getValBoolean()) {
            MessageUtil.sendBedAuraMessage(ChatFormatting.WHITE + "Module has been" + " " + ChatFormatting.RED + "disabled" + ChatFormatting.GRAY + ".");
        }

        MinecraftForge.EVENT_BUS.unregister(this);

        playerHotbarSlot = -1;
        lastHotbarSlot = -1;
        blocksPlaced = 0;
    }

    @Override
    public void onUpdate() {
        if (mc.player == null) {
            return;
        }

        if (mc.player.dimension == 0) {
            MessageUtil.sendBedAuraMessage(ChatFormatting.GRAY + "[" + ChatFormatting.RED + "Error" + ChatFormatting.GRAY + "]" + " " + ChatFormatting.WHITE + "You are in the" + " " + ChatFormatting.GRAY + "Overworld" + ChatFormatting.GRAY + "!" + " " + ChatFormatting.WHITE + "Please go into the" + " " + ChatFormatting.RED + "Nether" + " " + ChatFormatting.WHITE + "to use this feature" + ChatFormatting.GRAY + ".");
            this.toggle();
        }

        try {
            findClosestTarget();
        } catch (NullPointerException npe) {
        }

        if (closestTarget == null && mc.player.dimension != 0) {
            if (firstRun) {
                firstRun = false;
                if (debugMessages.getValBoolean()) {
                    MessageUtil.sendBedAuraMessage(ChatFormatting.WHITE + "Module has been" + " " + ChatFormatting.GREEN + "enabled" + ChatFormatting.GRAY + "," + " " + ChatFormatting.WHITE + "waiting for a target" + ChatFormatting.GRAY + "...");
                }
            }
        }

        if (firstRun && closestTarget != null && mc.player.dimension != 0) {
            firstRun = false;
            lastTickTargetName = closestTarget.getName();
            if (debugMessages.getValBoolean()) {
                MessageUtil.sendBedAuraMessage(ChatFormatting.WHITE + "Module has been" + " " + ChatFormatting.GREEN + "activated" + ChatFormatting.GRAY + "," + " " + ChatFormatting.WHITE + "the target is" + " " + ChatFormatting.AQUA.toString() + lastTickTargetName + ChatFormatting.GRAY + ".");
            }
        }

        if (closestTarget != null && lastTickTargetName != null) {
            if (!lastTickTargetName.equals(closestTarget.getName())) {
                lastTickTargetName = closestTarget.getName();
                if (debugMessages.getValBoolean()) {
                    MessageUtil.sendBedAuraMessage(ChatFormatting.WHITE + "New target" + ChatFormatting.GRAY + " " + "-" + " " + ChatFormatting.AQUA + lastTickTargetName + ChatFormatting.GRAY + ".");
                }
            }
        }

        try {
            diffXZ = mc.player.getPositionVector().distanceTo(closestTarget.getPositionVector());
        } catch (NullPointerException npe) {
        }

        try {
            if (closestTarget != null) {
                placeTarget = new BlockPos(closestTarget.getPositionVector().add(1, 1, 0));
                nowTop = false;
                rotVar = 90;

                BlockPos block1 = placeTarget;
                if (!canPlaceBed(block1)) {
                    placeTarget = new BlockPos(closestTarget.getPositionVector().add(-1, 1, 0));
                    rotVar = -90;
                    nowTop = false;
                }

                BlockPos block2 = placeTarget;
                if (!canPlaceBed(block2)) {
                    placeTarget = new BlockPos(closestTarget.getPositionVector().add(0, 1, 1));
                    rotVar = 180;
                    nowTop = false;
                }

                BlockPos block3 = placeTarget;
                if (!canPlaceBed(block3)) {
                    placeTarget = new BlockPos(closestTarget.getPositionVector().add(0, 1, -1));
                    rotVar = 0;
                    nowTop = false;
                }

                BlockPos block4 = placeTarget;
                if (!canPlaceBed(block4)) {
                    placeTarget = new BlockPos(closestTarget.getPositionVector().add(0, 2, -1));
                    rotVar = 0;
                    nowTop = true;
                }

                BlockPos blockt1 = placeTarget;
                if (nowTop && !canPlaceBed(blockt1)) {
                    placeTarget = new BlockPos(closestTarget.getPositionVector().add(-1, 2, 0));
                    rotVar = -90;
                }

                BlockPos blockt2 = placeTarget;
                if (nowTop && !canPlaceBed(blockt2)) {
                    placeTarget = new BlockPos(closestTarget.getPositionVector().add(0, 2, 1));
                    rotVar = 180;
                }

                BlockPos blockt3 = placeTarget;
                if (nowTop && !canPlaceBed(blockt3)) {
                    placeTarget = new BlockPos(closestTarget.getPositionVector().add(1, 2, 0));
                    rotVar = 90;
                }
            }

            mc.world.loadedTileEntityList.stream()
                    .filter(e -> e instanceof TileEntityBed)
                    .filter(e -> mc.player.getDistance(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ()) <= range.getValueInt())
                    .sorted(Comparator.comparing(e -> mc.player.getDistance(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ())))
                    .forEach(bed -> {
                        if (mc.player.dimension != 0 && explode.getValBoolean()) {
                            mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(bed.getPos(), EnumFacing.UP, EnumHand.OFF_HAND, 0, 0, 0));
                        }
                    });

            if ((mc.player.ticksExisted % placedelay.getValueInt() == 0) && closestTarget != null) {
                this.findBeds();
                mc.player.ticksExisted++;
                this.doDaMagic();
            }
        } catch (NullPointerException npe) {
        }
    }

    private void doDaMagic() {
        if (diffXZ <= range.getValueInt()) {
            for (int i = 0; i < 9; i++) {
                if (bedSlot != -1) {
                    break;
                }
                ItemStack stack = mc.player.inventory.getStackInSlot(i);
                if (stack.getItem() instanceof ItemBed && autoswitch.getValBoolean()) {
                    bedSlot = i;
                    if (i != -1) {
                        mc.player.inventory.currentItem = bedSlot;
                    }
                }
                break;
            }
        }

        bedSlot = -1;
        if (blocksPlaced == 0 && mc.player.inventory.getStackInSlot(mc.player.inventory.currentItem).getItem() instanceof ItemBed) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
            mc.player.connection.sendPacket(new CPacketPlayer.Rotation(rotVar, 0, mc.player.onGround));
            placeBlock(new BlockPos(this.placeTarget), EnumFacing.DOWN);
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            blocksPlaced = 1;
            nowTop = false;
        }
        blocksPlaced = 0;
    }

    private void findBeds() {
        if (mc.currentScreen == null || !(mc.currentScreen instanceof GuiContainer)) {
            if (mc.player.inventory.getStackInSlot(0).getItem() != Items.BED) {
                for (int i = 9; i < 36; ++i) {
                    if (mc.player.inventory.getStackInSlot(i).getItem() == Items.BED) {
                        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, i, 0, ClickType.SWAP, mc.player);
                        break;
                    }
                }
            }
        }
    }

    private void findClosestTarget() {
        List<EntityPlayer> playerList = mc.world.playerEntities;

        closestTarget = null;

        for (EntityPlayer target : playerList) {
            if (target == mc.player) continue;

            if (Past.friendsManager.isFriend(target.getName())) continue;

            if (!isLiving(target)) continue;

            if ((target).getHealth() <= 0) continue;

            if (closestTarget == null) {
                closestTarget = target;
                continue;
            }

            if (mc.player.getDistance(target) < mc.player.getDistance(closestTarget)) {
                closestTarget = target;
            }
        }
    }

    private void placeBlock(BlockPos pos, EnumFacing side) {
        if (place.getValBoolean()) {
            BlockPos neighbour = pos.offset(side);
            EnumFacing opposite = side.getOpposite();
            Vec3d hitVec = new Vec3d(neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
            mc.playerController.processRightClickBlock(mc.player, mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        }
    }

    public static boolean isLiving(Entity e) {
        return e instanceof EntityLivingBase;
    }

    private boolean canPlaceBed(BlockPos pos) {
        return (mc.world.getBlockState(pos).getBlock() == Blocks.AIR || mc.world.getBlockState(pos).getBlock() == Blocks.BED) && mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos)).isEmpty();
    }

    public String getArraylistInfo() {
        if (closestTarget != null) {
            return ChatFormatting.GRAY + " " + closestTarget.getName();
        } else {
            return "";
        }
    }
}
