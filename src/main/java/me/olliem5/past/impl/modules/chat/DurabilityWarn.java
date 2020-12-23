package me.olliem5.past.impl.modules.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.olliem5.past.Past;
import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.setting.Setting;
import me.olliem5.past.api.util.client.MessageUtil;
import me.olliem5.past.api.util.client.CooldownUtil;
import me.olliem5.past.api.util.player.DurabilityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DurabilityWarn extends Module {
    public DurabilityWarn() {
        super("DurabilityWarn", "Warns others when their armour durability is getting low", Category.CHAT);
    }

    Setting sendmode;
    Setting durability;
    Setting delay;

    private ArrayList<String> sendmodes;

    CooldownUtil sendtimer = new CooldownUtil();

    @Override
    public void setup() {
        sendmodes = new ArrayList<>();
        sendmodes.add("Whisper");
        sendmodes.add("Public");
        sendmodes.add("Client");

        Past.settingsManager.registerSetting(sendmode = new Setting("Send", "DurabilityWarnSendMode", this, sendmodes, "Whisper"));
        Past.settingsManager.registerSetting(durability = new Setting("Durability %", "DurabilityWarnDurability", 1.0, 20.0, 100.0, this));
        Past.settingsManager.registerSetting(delay = new Setting("Delay MS", "DurabilityWarnDelay", 0, 8000, 10000, this));
    }

    public void onUpdate() {
        if (nullCheck()) return;

        List<Entity> entities = mc.world.loadedEntityList.stream()
                .filter(entity -> entity != mc.player)
                .filter(entity -> !entity.isDead)
                .filter(entity -> sendCheck(entity))
                .sorted(Comparator.comparing(e -> mc.player.getDistance(e)))
                .collect(Collectors.toList());

        for (Entity entity : entities) {
            for (ItemStack itemStack : ((EntityPlayer) entity).inventory.armorInventory) {
                if (itemStack != ItemStack.EMPTY) {
                    int armourPercent = DurabilityUtil.getRoundedDamage(itemStack);
                    if (armourPercent <= durability.getValueDouble()) {
                        if (sendtimer.passed(delay.getValueInt())) {
                            sendtimer.reset();
                            if (sendmode.getValueString() == "Whisper") {
                                mc.player.sendChatMessage("/msg" + " " + entity.getName() + " " + "Hey, your" + " " + getArmourPiece(itemStack) + " " + "getting low on durability" + "!");
                            } else if (sendmode.getValueString() == "Public") {
                                mc.player.sendChatMessage(entity.getName() + "'s" + " " + getArmourPiece(itemStack) + " " + "getting low on durability" + "!");
                            } else if (sendmode.getValueString() == "Client") {
                                MessageUtil.sendDurabilityWarnMessage(ChatFormatting.GREEN + entity.getName() + "'s" + " " + ChatFormatting.AQUA + getArmourPiece(itemStack) + " " + ChatFormatting.WHITE + "getting low on durability" + "!");
                            }
                        }
                    }
                }
            }
        }
    }

    private String getArmourPiece(ItemStack itemStack) {
        if (itemStack.getItem() == Items.DIAMOND_HELMET || itemStack.getItem() == Items.GOLDEN_HELMET || itemStack.getItem() == Items.IRON_HELMET || itemStack.getItem() == Items.CHAINMAIL_HELMET || itemStack.getItem() == Items.LEATHER_HELMET) {
            return "helmet is";
        } else if (itemStack.getItem() == Items.DIAMOND_CHESTPLATE || itemStack.getItem() == Items.GOLDEN_CHESTPLATE || itemStack.getItem() == Items.IRON_CHESTPLATE || itemStack.getItem() == Items.CHAINMAIL_CHESTPLATE || itemStack.getItem() == Items.LEATHER_CHESTPLATE) {
            return "chestplate is";
        } else if (itemStack.getItem() == Items.DIAMOND_LEGGINGS || itemStack.getItem() == Items.GOLDEN_LEGGINGS || itemStack.getItem() == Items.IRON_LEGGINGS || itemStack.getItem() == Items.CHAINMAIL_LEGGINGS || itemStack.getItem() == Items.LEATHER_LEGGINGS) {
            return "leggings are";
        } else if (itemStack.getItem() == Items.DIAMOND_BOOTS || itemStack.getItem() == Items.GOLDEN_BOOTS || itemStack.getItem() == Items.IRON_BOOTS || itemStack.getItem() == Items.CHAINMAIL_BOOTS || itemStack.getItem() == Items.LEATHER_BOOTS) {
            return "boots are";
        } else {
            return "";
        }
    }

    public boolean sendCheck(Entity entity) {
        if (entity instanceof EntityPlayer) {
            if (((EntityPlayer) entity).getHealth() > 0) {
                return true;
            }
        }
        return false;
    }
}
