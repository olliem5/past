package me.olliem5.past.module.modules.player;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.colour.ColourUtil;
import me.olliem5.past.util.client.MessageUtil;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;

public class WeaknessMsg extends Module {
    public WeaknessMsg() {
        super("WeaknessMsg", "Notifies you if you get weakness", Category.PLAYER);
    }

    Setting sound;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(sound = new Setting("Sound", "WeaknessMsgSound", true, this));
    }

    private boolean hasAnnounced = false;

    public void onUpdate() {
        if (nullCheck()) {
            return;
        }

        if (mc.player.isPotionActive(MobEffects.WEAKNESS) && !hasAnnounced) {
            hasAnnounced = true;
            MessageUtil.sendWeaknessAlertMessage(ColourUtil.aqua + mc.getSession().getUsername() + ColourUtil.gray + " - " + ColourUtil.white + "You now have " + ColourUtil.red + "weakness" + ColourUtil.gray + "!");
            if (sound.getValBoolean()) {
                mc.getSoundHandler().playSound(PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F));
            }
        }
        if (!mc.player.isPotionActive(MobEffects.WEAKNESS) && hasAnnounced) {
            hasAnnounced = false;
            MessageUtil.sendWeaknessAlertMessage(ColourUtil.aqua + mc.getSession().getUsername() + ColourUtil.gray + " - " + ColourUtil.white + "You no longer have " + ColourUtil.red + "weakness" + ColourUtil.gray + "!");
            if (sound.getValBoolean()) {
                mc.getSoundHandler().playSound(PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F));
            }
        }
    }
}
