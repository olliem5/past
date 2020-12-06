package me.olliem5.past.module.modules.chat;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.client.MessageUtil;
import me.olliem5.past.util.colour.ColourUtil;
import me.olliem5.past.util.module.CooldownUtil;
import me.olliem5.past.util.module.DurabilityUtil;
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
                                mc.player.sendChatMessage("/msg" + " " + entity.getName() + " " + "Boi, your" + " " + getArmourPiece(itemStack) + " " + "getting low on durability" + "!");
                            } else if (sendmode.getValueString() == "Public") {
                                mc.player.sendChatMessage(entity.getName() + "'s" + " " + getArmourPiece(itemStack) + " " + "getting low on durability" + "!");
                            } else if (sendmode.getValueString() == "Client") {
                                MessageUtil.sendDurabilityWarnMessage(ColourUtil.green + entity.getName() + "'s" + " " + ColourUtil.aqua + getArmourPiece(itemStack) + " " + ColourUtil.white + "getting low on durability" + "!");
                            }
                        }
                    }
                }
            }
        }
    }

    private String getArmourPiece(ItemStack stack) {
        if (stack.getItem() == Items.DIAMOND_HELMET || stack.getItem() == Items.GOLDEN_HELMET || stack.getItem() == Items.IRON_HELMET || stack.getItem() == Items.CHAINMAIL_HELMET || stack.getItem() == Items.LEATHER_HELMET) {
            return "helmet is";
        } else if (stack.getItem() == Items.DIAMOND_CHESTPLATE || stack.getItem() == Items.GOLDEN_CHESTPLATE || stack.getItem() == Items.IRON_CHESTPLATE || stack.getItem() == Items.CHAINMAIL_CHESTPLATE || stack.getItem() == Items.LEATHER_CHESTPLATE) {
            return "chestplate is";
        } else if (stack.getItem() == Items.DIAMOND_LEGGINGS || stack.getItem() == Items.GOLDEN_LEGGINGS || stack.getItem() == Items.IRON_LEGGINGS || stack.getItem() == Items.CHAINMAIL_LEGGINGS || stack.getItem() == Items.LEATHER_LEGGINGS) {
            return "leggings are";
        } else if (stack.getItem() == Items.DIAMOND_BOOTS || stack.getItem() == Items.GOLDEN_BOOTS || stack.getItem() == Items.IRON_BOOTS || stack.getItem() == Items.CHAINMAIL_BOOTS || stack.getItem() == Items.LEATHER_BOOTS) {
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
