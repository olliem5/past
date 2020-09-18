package me.ollieobama.past.module.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ollieobama.past.Past;
import me.ollieobama.past.managers.MessageManager;
import me.ollieobama.past.module.Category;
import me.ollieobama.past.module.Module;
import me.ollieobama.past.settings.Setting;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.init.SoundEvents;

public class EntityAlert extends Module {
    public EntityAlert() {
        super ("EntityAlert", "Alerts you when selected entities enter render distance", Category.MISC);
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
        Past.settingsManager.rSetting(donkey = new Setting("Donkey", this, false));
        Past.settingsManager.rSetting(llama = new Setting("Llama", this, false));
        Past.settingsManager.rSetting(mule = new Setting("Mule", this, false));
        Past.settingsManager.rSetting(sound = new Setting("Sound", this, false));
    }

    public void onUpdate() {
        if (mc.world != null && mc.player != null) {

            ++donkeyDelay;
            ++llamaDelay;
            ++muleDelay;

            for (Entity entity : mc.world.getLoadedEntityList()) {
                if (entity instanceof EntityDonkey && donkey.getValBoolean() && this.donkeyDelay >= 100) {
                    MessageManager.sendMessagePrefix(ChatFormatting.GRAY + "[" + ChatFormatting.BLUE + "EntityAlert" + ChatFormatting.GRAY + "] " + ChatFormatting.WHITE + "Found a " + ChatFormatting.AQUA + "donkey " + ChatFormatting.WHITE + "at " + ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + Math.round(entity.lastTickPosX) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.lastTickPosY) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.lastTickPosZ) + ChatFormatting.GRAY + "]");
                    if (sound.getValBoolean()) { mc.getSoundHandler().playSound(PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F)); }
                    this.donkeyDelay = -750;
                }
                if (entity instanceof EntityLlama && llama.getValBoolean() && this.llamaDelay >= 100) {
                    MessageManager.sendMessagePrefix(ChatFormatting.GRAY + "[" + ChatFormatting.BLUE + "EntityAlert" + ChatFormatting.GRAY + "] " + ChatFormatting.WHITE + "Found a " + ChatFormatting.AQUA + "llama " + ChatFormatting.WHITE + "at " + ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + Math.round(entity.lastTickPosX) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.lastTickPosY) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.lastTickPosZ) + ChatFormatting.GRAY + "]");
                    if (sound.getValBoolean()) { mc.getSoundHandler().playSound(PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F)); }
                    this.llamaDelay = -750;
                }
                if (entity instanceof EntityMule && mule.getValBoolean() && this.muleDelay >= 100) {
                    MessageManager.sendMessagePrefix(ChatFormatting.GRAY + "[" + ChatFormatting.BLUE + "EntityAlert" + ChatFormatting.GRAY + "] " + ChatFormatting.WHITE + "Found a " + ChatFormatting.AQUA + "mule " + ChatFormatting.WHITE + "at " + ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + Math.round(entity.lastTickPosX) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.lastTickPosY) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.lastTickPosZ) + ChatFormatting.GRAY + "]");
                    if (sound.getValBoolean()) { mc.getSoundHandler().playSound(PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F)); }
                    this.muleDelay = -750;
                }
            }
        }
    }
}
