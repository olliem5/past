package me.olliem5.past.module.modules.combat;

import me.olliem5.past.Past;
import me.olliem5.past.event.events.PacketEvent;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.player.PlayerUtil;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.init.Items;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;

public class FootEXP extends Module {
    public FootEXP() {
        super("FootEXP", "Makes you look down server side while using EXP bottles", Category.COMBAT);
    }

//    Setting autoswitch;
    Setting usecustompitch;
    Setting custompitch;

    @Override
    public void setup() {
//        Past.settingsManager.registerSetting(autoswitch = new Setting("Auto Switch", "FootEXPAutoSwitch", false, this));
        Past.settingsManager.registerSetting(usecustompitch = new Setting("Use Custom Pitch", "FootEXPUseCustomPitch", false, this));
        Past.settingsManager.registerSetting(custompitch = new Setting("Custom Pitch", "FootEXPCustomPitch", -90, 90, 90, this));
    }

//    private int oldInventorySlot;

    @Override
    public void onEnable() {
//        if (autoswitch.getValBoolean()) {
//            oldInventorySlot = mc.player.inventory.currentItem;
//        }
    }

    @Override
    public void onDisable() {
//        if (autoswitch.getValBoolean()) {
//            mc.player.inventory.currentItem = oldInventorySlot;
//        }
    }

    @Override
    public void onUpdate() {
//        if (nullCheck()) return;
//
//        if (autoswitch.getValBoolean()) {
//            mc.player.inventory.currentItem = PlayerUtil.getItemInHotbar(Items.EXPERIENCE_BOTTLE);
//        }
    }

    @EventHandler
    public Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketPlayerTryUseItem && mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle) {
            if (usecustompitch.getValBoolean()) {
                mc.player.connection.sendPacket(new CPacketPlayer.Rotation(mc.player.rotationYaw, custompitch.getValueInt(), mc.player.onGround));
            } else {
                mc.player.connection.sendPacket(new CPacketPlayer.Rotation(mc.player.rotationYaw, 90.0f, mc.player.onGround));
            }
        }
    });
}
