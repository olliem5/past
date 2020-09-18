package me.ollieobama.past.module.modules.player;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ollieobama.past.Past;
import me.ollieobama.past.managers.MessageManager;
import me.ollieobama.past.module.Category;
import me.ollieobama.past.module.Module;
import me.ollieobama.past.settings.Setting;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;

public class WeaknessAlert extends Module {
    public WeaknessAlert() {
        super ("WeaknessAlert", "Notifies you if you get weakness", Category.PLAYER);
    }

    Setting sound;

    @Override
    public void setup() {
        Past.settingsManager.rSetting(sound = new Setting("Sound", this, false));
    }

    private boolean hasAnnounced = false;

    public void onUpdate() {
        if (mc.world != null && mc.player != null) {
            if (mc.player.isPotionActive(MobEffects.WEAKNESS) && !hasAnnounced) {
                hasAnnounced = true;
                MessageManager.sendMessagePrefix(ChatFormatting.GRAY + "[" + ChatFormatting.LIGHT_PURPLE + "WeaknessDetect" + ChatFormatting.GRAY + "] " + ChatFormatting.AQUA + mc.getSession().getUsername() + ChatFormatting.GRAY + " - " + ChatFormatting.WHITE + "You now have " + ChatFormatting.RED + "weakness" + ChatFormatting.WHITE + "!");
                if (sound.getValBoolean()) { mc.getSoundHandler().playSound(PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F)); }
            }
            if (!mc.player.isPotionActive(MobEffects.WEAKNESS) && hasAnnounced) {
                hasAnnounced = false;
                MessageManager.sendMessagePrefix(ChatFormatting.GRAY + "[" + ChatFormatting.LIGHT_PURPLE + "WeaknessDetect" + ChatFormatting.GRAY + "] " + ChatFormatting.AQUA + mc.getSession().getUsername() + ChatFormatting.GRAY + " - " + ChatFormatting.WHITE + "You no longer have " + ChatFormatting.RED + "weakness" + ChatFormatting.WHITE + "!");
                if (sound.getValBoolean()) { mc.getSoundHandler().playSound(PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F)); }
            }
        }
    }
}
