package me.olliem5.past.module.modules.player;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.olliem5.past.Past;
import me.olliem5.past.managers.MessageManager;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
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
        Past.settingsManager.registerSetting(sound = new Setting("Sound", true, this));
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
