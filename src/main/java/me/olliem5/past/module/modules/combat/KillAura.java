package me.olliem5.past.module.modules.combat;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.colour.ColourUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KillAura extends Module {
    public KillAura() {
        super("KillAura", "Automatically attacks entities in range", Category.COMBAT);
    }

    Setting range;
    Setting players;
    Setting mobs;
    Setting animals;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(range = new Setting("Range", "KillAuraRange", 0.0, 5.0, 10.0, this));
        Past.settingsManager.registerSetting(players = new Setting("Players", "KillAuraPlayers", true, this));
        Past.settingsManager.registerSetting(mobs = new Setting("Mobs", "KillAuraMobs", false, this));
        Past.settingsManager.registerSetting(animals = new Setting("Animals", "KillAuraAnimals", false, this));
    }

    private Entity target = null;

    public void onUpdate() {
        if (nullCheck()) return;

        List<Entity> targets = mc.world.loadedEntityList.stream()
                .filter(entity -> entity != mc.player)
                .filter(entity -> mc.player.getDistance(entity) <= range.getValueDouble())
                .filter(entity -> !entity.isDead)
                .filter(entity -> attackCheck(entity))
                .sorted(Comparator.comparing(e -> mc.player.getDistance(e)))
                .collect(Collectors.toList());

        targets.forEach(target -> {
            attack(target);
        });
    }

    public void attack(Entity entity) {
        if (mc.player.getCooledAttackStrength(0) >= 1) {
            mc.playerController.attackEntity(mc.player, entity);
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
        target = entity;
    }

    public boolean attackCheck(Entity entity) {
        if (players.getValBoolean() && entity instanceof EntityPlayer && !Past.friendsManager.isFriend(entity.getName())) {
            if (((EntityPlayer) entity).getHealth() > 0) {
                return true;
            }
        } else if (mobs.getValBoolean() && entity instanceof EntityMob) {
            if (((EntityMob) entity).getHealth() > 0) {
                return true;
            }
        } else if (animals.getValBoolean() && entity instanceof EntityAnimal) {
            if (((EntityAnimal) entity).getHealth() > 0) {
                return true;
            }
        }
        return false;
    }

    public String getArraylistInfo() {
        if (target != null) {
            return ColourUtil.gray + " " + target.getName();
        } else {
            return "";
        }
    }
}
