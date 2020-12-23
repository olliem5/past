package me.olliem5.past.impl.modules.render;

import me.olliem5.past.Past;
import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.module.ModuleInfo;
import me.olliem5.past.api.setting.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;

@ModuleInfo(name = "ESP", description = "Highlights loaded entities", category = Category.RENDER)
public class ESP extends Module {

    /**
     * TODO: Actually make this good lol
     */

    Setting players;
    Setting animals;
    Setting mobs;
    Setting crystals;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(players = new Setting("Players", "ESPPlayers", true, this));
        Past.settingsManager.registerSetting(animals = new Setting("Animals", "ESPAnimals", false, this));
        Past.settingsManager.registerSetting(mobs = new Setting("Mobs", "ESPMobs", false, this));
        Past.settingsManager.registerSetting(crystals = new Setting("Crystals", "ESPCrystals", false, this));
    }

    @EventHandler
    public Listener<RenderWorldLastEvent> listener = new Listener<>(event -> {
        if (mc.world == null) return;

        for (Entity entity : mc.world.getLoadedEntityList()) {
            if (entity != mc.player) {
                if (entity instanceof EntityPlayer && players.getValBoolean()) {
                    entity.setGlowing(true);
                } else if (entity instanceof EntityAnimal && animals.getValBoolean()) {
                    entity.setGlowing(true);
                } else if (entity instanceof EntityMob && mobs.getValBoolean()) {
                    entity.setGlowing(true);
                } else if (entity instanceof EntityEnderCrystal && crystals.getValBoolean()) {
                    entity.setGlowing(true);
                }
            }
        }
    });

    @Override
    public void onDisable() {
        for (Entity entity : mc.world.getLoadedEntityList()) {
            if (entity != mc.player) {
                if (entity instanceof EntityPlayer && players.getValBoolean()) {
                    entity.setGlowing(false);
                } else if (entity instanceof EntityAnimal && animals.getValBoolean()) {
                    entity.setGlowing(false);
                } else if (entity instanceof EntityMob && mobs.getValBoolean()) {
                    entity.setGlowing(false);
                } else if (entity instanceof EntityEnderCrystal && crystals.getValBoolean()) {
                    entity.setGlowing(false);
                }
            }
        }
    }
}
