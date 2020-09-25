package me.olliem5.past.module.modules.misc;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.util.ColourManager;
import me.olliem5.past.util.MessageManager;
import me.olliem5.past.settings.Setting;
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
        Past.settingsManager.registerSetting(donkey = new Setting("Donkey", true, this));
        Past.settingsManager.registerSetting(llama = new Setting("Llama", true, this));
        Past.settingsManager.registerSetting(mule = new Setting("Mule", true, this));
        Past.settingsManager.registerSetting(sound = new Setting("Sound", true, this));
    }

    public void onUpdate() {
        if (mc.world != null && mc.player != null) {

            ++donkeyDelay;
            ++llamaDelay;
            ++muleDelay;

            for (Entity entity : mc.world.getLoadedEntityList()) {
                if (entity instanceof EntityDonkey && donkey.getValBoolean() && this.donkeyDelay >= 100) {
                    MessageManager.sendMessagePrefix(ColourManager.gray + "[" + ColourManager.blue + "EntityAlert" + ColourManager.gray + "] " + ColourManager.white + "Found a " + ColourManager.aqua + "donkey " + ColourManager.white + "at " + ColourManager.gray + "[" + ColourManager.white + Math.round(entity.lastTickPosX) + ColourManager.gray + ", " + ColourManager.white + Math.round(entity.lastTickPosY) + ColourManager.gray + ", " + ColourManager.white + Math.round(entity.lastTickPosZ) + ColourManager.gray + "]");
                    if (sound.getValBoolean()) { mc.getSoundHandler().playSound(PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F)); }
                    this.donkeyDelay = -750;
                }
                if (entity instanceof EntityLlama && llama.getValBoolean() && this.llamaDelay >= 100) {
                    MessageManager.sendMessagePrefix(ColourManager.gray + "[" + ColourManager.blue + "EntityAlert" + ColourManager.gray + "] " + ColourManager.white + "Found a " + ColourManager.aqua + "llama " + ColourManager.white + "at " + ColourManager.gray + "[" + ColourManager.white + Math.round(entity.lastTickPosX) + ColourManager.gray + ", " + ColourManager.white + Math.round(entity.lastTickPosY) + ColourManager.gray + ", " + ColourManager.white + Math.round(entity.lastTickPosZ) + ColourManager.gray + "]");
                    if (sound.getValBoolean()) { mc.getSoundHandler().playSound(PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F)); }
                    this.llamaDelay = -750;
                }
                if (entity instanceof EntityMule && mule.getValBoolean() && this.muleDelay >= 100) {
                    MessageManager.sendMessagePrefix(ColourManager.gray + "[" + ColourManager.blue + "EntityAlert" + ColourManager.gray + "] " + ColourManager.white + "Found a " + ColourManager.aqua + "mule " + ColourManager.white + "at " + ColourManager.gray + "[" + ColourManager.white + Math.round(entity.lastTickPosX) + ColourManager.gray + ", " + ColourManager.white + Math.round(entity.lastTickPosY) + ColourManager.gray + ", " + ColourManager.white + Math.round(entity.lastTickPosZ) + ColourManager.gray + "]");
                    if (sound.getValBoolean()) { mc.getSoundHandler().playSound(PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F)); }
                    this.muleDelay = -750;
                }
            }
        }
    }
}
