package me.olliem5.past.module.modules.combat;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.Cooldown;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.EnumHand;

import java.util.ArrayList;
import java.util.Comparator;

public class AutoCrystal extends Module {
    public AutoCrystal() {
        super("AutoCrystal", "Breaks and Places crystals", Category.COMBAT);
    }
    Cooldown breaktimer = new Cooldown();
    Cooldown placetimer = new Cooldown();

    Setting placedelay;
    Setting breakdelay;
    Setting placerange;
    Setting breakrange;
    Setting placemode;
    Setting autoswitch;
    private ArrayList<String> placemodes;
    private ArrayList<String> breakmodes;

    @Override
    public void setup() {
        placemodes = new ArrayList<>();
        placemodes.add("None");
        placemodes.add("Single");
        placemodes.add("Multi");
        breakmodes = new ArrayList<>();
        breakmodes.add("None");
        breakmodes.add("Near");
        Past.settingsManager.registerSetting(placemode = new Setting("Place", this, placemodes, "Single"));
        Past.settingsManager.registerSetting(placemode = new Setting("Break", this, placemodes, "Single"));
        Past.settingsManager.registerSetting(autoswitch = new Setting("AutoSwitch", true, this));
        Past.settingsManager.registerSetting(placedelay = new Setting("Place Delay", 0, 2, 20, this));
        Past.settingsManager.registerSetting(placerange = new Setting("Place range", 0, 5, 10, this));
        Past.settingsManager.registerSetting(breakdelay = new Setting("Break Delay", 0, 2, 20, this));
    }



    @Override
    public void onUpdate() {
        if(breaktimer.passed(breakdelay.getValueInt() * 50)){
            EntityEnderCrystal crystal = mc.world.loadedEntityList.stream()
                    .filter(entity -> entity instanceof EntityEnderCrystal)
                    .filter(e -> mc.player.getDistance(e) <= placerange.getValueInt())
                    .map(entity -> (EntityEnderCrystal) entity)
                    .min(Comparator.comparing(c -> mc.player.getDistance(c)))
                    .orElse(null);
            mc.playerController.attackEntity(mc.player, crystal);
            mc.player.swingArm(EnumHand.MAIN_HAND);
            breaktimer.reset();

        }else{

        }
        if(placetimer.passed(placedelay.getValueInt() * 50)){
            System.out.println("place");
            placetimer.reset();

        }else{
            //in cooldown
        }
    }

    @Override
    public void onToggle() {
        super.onToggle();
    }

    @Override
    public void onRender() {

    }
}
