package me.olliem5.past.impl.modules.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.olliem5.past.Past;
import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.setting.Setting;
import me.olliem5.past.api.util.client.MessageUtil;
import me.olliem5.past.api.util.client.CooldownUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AutoInsult extends Module {
    public AutoInsult() {
        super("AutoInsult", "Automatically insults players", Category.CHAT);
    }

    Setting sendmode;
    Setting range;
    Setting delay;
    Setting bowspammsg;

    private ArrayList<String> sendmodes;

    CooldownUtil sendtimer = new CooldownUtil();

    @Override
    public void setup() {
        sendmodes = new ArrayList<>();
        sendmodes.add("Whisper");
        sendmodes.add("Public");
        sendmodes.add("Client");

        Past.settingsManager.registerSetting(sendmode = new Setting("Send", "AutoInsultSendMode", this, sendmodes, "Whisper"));
        Past.settingsManager.registerSetting(range = new Setting("Range", "AutoInsultRange", 1.0, 25.0, 50.0, this));
        Past.settingsManager.registerSetting(delay = new Setting("Delay MS", "AutoInsultDelay", 0, 8000, 10000, this));
        Past.settingsManager.registerSetting(bowspammsg = new Setting("Bowspam Msg", "AutoInsultBowspamMsg", true, this));
    }

    /**
     * TODO: More modes for this
     * * Phase mode
     * * Sword mode
     */

    public void onUpdate() {
        if (nullCheck()) return;

        List<Entity> entities = mc.world.loadedEntityList.stream()
                .filter(entity -> entity != mc.player)
                .filter(entity -> mc.player.getDistance(entity) <= range.getValueDouble())
                .filter(entity -> !entity.isDead)
                .filter(entity -> sendCheck(entity))
                .sorted(Comparator.comparing(e -> mc.player.getDistance(e)))
                .collect(Collectors.toList());

        for (Entity entity : entities) {
            if (bowspammsg.getValBoolean()) {
                if (((EntityPlayer) entity).getHeldItemMainhand().getItem() instanceof ItemBow || ((EntityPlayer) entity).getHeldItemOffhand().getItem() instanceof ItemBow) {
                    if (sendtimer.passed(delay.getValueInt())) {
                        sendtimer.reset();
                        if (sendmode.getValueString() == "Whisper") {
                            mc.player.sendChatMessage("/msg" + " " + entity.getName() + " " + "Hey, bowspammer, no one likes you!");
                        } else if (sendmode.getValueString() == "Public") {
                            mc.player.sendChatMessage(entity.getName() + "," + " " + "Nasty bowspammer, fight like a real man!");
                        } else {
                            MessageUtil.sendAutoInsultMessage(ChatFormatting.AQUA + entity.getName() + " " + ChatFormatting.WHITE + "is a dumb bowspammer!");
                        }
                    }
                }
            }
        }
    }

    public boolean sendCheck(Entity entity) {
        if (entity instanceof EntityPlayer && !Past.friendsManager.isFriend(entity.getName())) {
            if (((EntityPlayer) entity).getHealth() > 0) {
                return true;
            }
        }
        return false;
    }
}
