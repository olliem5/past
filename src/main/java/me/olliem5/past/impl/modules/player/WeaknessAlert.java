package me.olliem5.past.impl.modules.player;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.olliem5.past.Past;
import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.module.ModuleInfo;
import me.olliem5.past.api.setting.Setting;
import me.olliem5.past.api.util.client.MessageUtil;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;

@ModuleInfo(name = "WeaknessAlert", description = "Notifies you if you get weakness", category = Category.PLAYER)
public class WeaknessAlert extends Module {

    Setting sound;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(sound = new Setting("Sound", "WeaknessAlertSound", true, this));
    }

    private boolean hasAnnounced = false;

    public void onUpdate() {
        if (nullCheck()) return;

        if (mc.player.isPotionActive(MobEffects.WEAKNESS) && !hasAnnounced) {
            hasAnnounced = true;
            MessageUtil.sendWeaknessAlertMessage(ChatFormatting.AQUA + mc.getSession().getUsername() + ChatFormatting.GRAY + " - " + ChatFormatting.WHITE + "You now have " + ChatFormatting.RED + "weakness" + ChatFormatting.GRAY + "!");
            if (sound.getValBoolean()) {
                mc.getSoundHandler().playSound(PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F));
            }
        } else if (!mc.player.isPotionActive(MobEffects.WEAKNESS) && hasAnnounced) {
            hasAnnounced = false;
            MessageUtil.sendWeaknessAlertMessage(ChatFormatting.AQUA + mc.getSession().getUsername() + ChatFormatting.GRAY + " - " + ChatFormatting.WHITE + "You no longer have " + ChatFormatting.RED + "weakness" + ChatFormatting.GRAY + "!");
            if (sound.getValBoolean()) {
                mc.getSoundHandler().playSound(PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F));
            }
        }
    }
}
