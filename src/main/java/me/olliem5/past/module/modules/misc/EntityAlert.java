package me.olliem5.past.module.modules.misc;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.client.MessageUtil;
import me.olliem5.past.util.colour.ColourUtil;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.init.SoundEvents;

public class EntityAlert extends Module {
    public EntityAlert() {
        super("EntityAlert", "Alerts you when selected entities enter render distance", Category.MISC);
    }

    Setting donkey;
    Setting llama;
    Setting mule;
    Setting sound;

    private int donkeyDelay;
    private int llamaDelay;
    private int muleDelay;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(donkey = new Setting("Donkey", "EntityAlertDonkey", true, this));
        Past.settingsManager.registerSetting(llama = new Setting("Llama", "EntityAlertLlama", true, this));
        Past.settingsManager.registerSetting(mule = new Setting("Mule", "EntityAlertMule", true, this));
        Past.settingsManager.registerSetting(sound = new Setting("Sound", "EntityAlertSound", true, this));
    }

    public void onUpdate() {
        if (nullCheck()) return;

        ++donkeyDelay;
        ++llamaDelay;
        ++muleDelay;

        for (Entity entity : mc.world.getLoadedEntityList()) {
            if (entity instanceof EntityDonkey && donkey.getValBoolean() && this.donkeyDelay >= 100) {
                MessageUtil.sendEntityAlertMessage(ColourUtil.white + "Found a " + ColourUtil.aqua + "donkey " + ColourUtil.white + "at " + ColourUtil.gray + "[" + ColourUtil.white + Math.round(entity.lastTickPosX) + ColourUtil.gray + ", " + ColourUtil.white + Math.round(entity.lastTickPosY) + ColourUtil.gray + ", " + ColourUtil.white + Math.round(entity.lastTickPosZ) + ColourUtil.gray + "]");
                if (sound.getValBoolean()) {
                    mc.getSoundHandler().playSound(PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F));
                }
                this.donkeyDelay = -750;
            } else if (entity instanceof EntityLlama && llama.getValBoolean() && this.llamaDelay >= 100) {
                MessageUtil.sendEntityAlertMessage(ColourUtil.white + "Found a " + ColourUtil.aqua + "llama " + ColourUtil.white + "at " + ColourUtil.gray + "[" + ColourUtil.white + Math.round(entity.lastTickPosX) + ColourUtil.gray + ", " + ColourUtil.white + Math.round(entity.lastTickPosY) + ColourUtil.gray + ", " + ColourUtil.white + Math.round(entity.lastTickPosZ) + ColourUtil.gray + "]");
                if (sound.getValBoolean()) {
                    mc.getSoundHandler().playSound(PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F));
                }
                this.llamaDelay = -750;
            } else if (entity instanceof EntityMule && mule.getValBoolean() && this.muleDelay >= 100) {
                MessageUtil.sendEntityAlertMessage(ColourUtil.white + "Found a " + ColourUtil.aqua + "mule " + ColourUtil.white + "at " + ColourUtil.gray + "[" + ColourUtil.white + Math.round(entity.lastTickPosX) + ColourUtil.gray + ", " + ColourUtil.white + Math.round(entity.lastTickPosY) + ColourUtil.gray + ", " + ColourUtil.white + Math.round(entity.lastTickPosZ) + ColourUtil.gray + "]");
                if (sound.getValBoolean()) {
                    mc.getSoundHandler().playSound(PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F));
                }
                this.muleDelay = -750;
            }
        }
    }
}
