package me.olliem5.past.impl.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.olliem5.past.Past;
import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.setting.Setting;
import me.olliem5.past.api.util.client.MessageUtil;
import me.olliem5.past.api.util.client.CooldownUtil;
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
    Setting donkeydelay;
    Setting llamadelay;
    Setting muledelay;
    Setting sound;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(donkey = new Setting("Donkey", "EntityAlertDonkey", true, this));
        Past.settingsManager.registerSetting(llama = new Setting("Llama", "EntityAlertLlama", true, this));
        Past.settingsManager.registerSetting(mule = new Setting("Mule", "EntityAlertMule", true, this));
        Past.settingsManager.registerSetting(donkeydelay = new Setting("D Delay", "EntityAlertDonkeyDelay", 0, 3000, 10000, this));
        Past.settingsManager.registerSetting(llamadelay = new Setting("L Delay", "EntityAlertLlamaDelay", 0, 3000, 10000, this));
        Past.settingsManager.registerSetting(muledelay = new Setting("M Delay", "EntityAlertMuleDelay", 0, 3000, 10000, this));
        Past.settingsManager.registerSetting(sound = new Setting("Sound", "EntityAlertSound", true, this));
    }

    CooldownUtil donkeyCooldown = new CooldownUtil();
    CooldownUtil llamaCooldown = new CooldownUtil();
    CooldownUtil muleCooldown = new CooldownUtil();

    public void onUpdate() {
        if (nullCheck()) return;

        for (Entity entity : mc.world.getLoadedEntityList()) {
            if (entity instanceof EntityDonkey && donkey.getValBoolean() && donkeyCooldown.passed(donkeydelay.getValueInt())) {
                donkeyCooldown.reset();
                MessageUtil.sendEntityAlertMessage(ChatFormatting.WHITE + "Found a " + ChatFormatting.AQUA + "donkey " + ChatFormatting.WHITE + "at " + ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + Math.round(entity.lastTickPosX) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.lastTickPosY) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.lastTickPosZ) + ChatFormatting.GRAY + "]");
                if (sound.getValBoolean()) {
                    mc.getSoundHandler().playSound(PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F));
                }
            } else if (entity instanceof EntityLlama && llama.getValBoolean() && llamaCooldown.passed(llamadelay.getValueInt())) {
                llamaCooldown.reset();
                MessageUtil.sendEntityAlertMessage(ChatFormatting.WHITE + "Found a " + ChatFormatting.AQUA + "llama " + ChatFormatting.WHITE + "at " + ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + Math.round(entity.lastTickPosX) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.lastTickPosY) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.lastTickPosZ) + ChatFormatting.GRAY + "]");
                if (sound.getValBoolean()) {
                    mc.getSoundHandler().playSound(PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F));
                }
            } else if (entity instanceof EntityMule && mule.getValBoolean() && muleCooldown.passed(muledelay.getValueInt())) {
                muleCooldown.reset();
                MessageUtil.sendEntityAlertMessage(ChatFormatting.WHITE + "Found a " + ChatFormatting.AQUA + "mule " + ChatFormatting.WHITE + "at " + ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + Math.round(entity.lastTickPosX) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.lastTickPosY) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.lastTickPosZ) + ChatFormatting.GRAY + "]");
                if (sound.getValBoolean()) {
                    mc.getSoundHandler().playSound(PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F));
                }
            }
        }
    }
}
