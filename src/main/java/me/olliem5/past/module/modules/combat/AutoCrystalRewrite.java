package me.olliem5.past.module.modules.combat;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.module.CooldownUtil;
import me.olliem5.past.util.module.CrystalUtil;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.EnumHand;

import java.util.ArrayList;
import java.util.Comparator;

public class AutoCrystalRewrite extends Module {
    public AutoCrystalRewrite() {
        super("NewAutoCrystal", "AutoCrystal, but more poggers", Category.COMBAT);
    }

    private static CrystalUtil crystalUtil = new CrystalUtil();

    CooldownUtil breaktimer = new CooldownUtil();
    CooldownUtil placetimer = new CooldownUtil();

    Setting place;
    Setting explode; //stupid java thinkin i wanna break when i name dis break
    Setting breakhand;
    Setting rotate;
    Setting placedelay;
    Setting breakdelay;
    Setting placerange;
    Setting breakrange;

    private ArrayList<String> breakhands;

    @Override
    public void setup() {
        breakhands = new ArrayList<>();
        breakhands.add("Mainhand");
        breakhands.add("Offhand");
        breakhands.add("Both");

        Past.settingsManager.registerSetting(place = new Setting("Place", "AutoCrystalRewritePlace", true, this));
        Past.settingsManager.registerSetting(explode = new Setting("Break", "AutoCrystalRewriteExplode", true, this));
        Past.settingsManager.registerSetting(breakhand = new Setting("Swing", "AutoCrystalRewriteBreakHand", this, breakhands, "Mainhand"));
        Past.settingsManager.registerSetting(rotate = new Setting("Rotate", "AutoCrystalRewriteRotate", true, this));
        Past.settingsManager.registerSetting(placedelay = new Setting("Place Delay (ms)", "AutoCrystalRewritePlaceDelay", 0.0, 200.0, 1000.0, this));
        Past.settingsManager.registerSetting(breakdelay = new Setting("Break Delay (ms)", "AutoCrystalRewriteBreakDelay", 0.0, 200.0, 1000.0, this));
        Past.settingsManager.registerSetting(placerange = new Setting("Place Range", "AutoCrystalRewritePlaceRange", 0.0, 4.4, 10.0, this));
        Past.settingsManager.registerSetting(breakrange = new Setting("Break Range", "AutoCrystalRewriteBreakRange", 0.0, 4.4, 10.0, this));
    }

    public void onUpdate() {
        if (nullCheck()) return;

        breakCrystal();
    }

    private void breakCrystal() {
        final EntityEnderCrystal crystal = (EntityEnderCrystal) mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).min(Comparator.comparing(c -> mc.player.getDistance(c))).orElse(null);

        if (explode.getValBoolean() && crystal != null && mc.player.getDistance(crystal) <= breakrange.getValueDouble()) {
            if (breaktimer.passed(breakdelay.getValueDouble())) {

                if (rotate.getValBoolean()) {
                    crystalUtil.lookAtPacket(crystal.posX, crystal.posY, crystal.posZ, mc.player);
                }

                mc.playerController.attackEntity(mc.player, crystal);

                if (breakhand.getValueString() == "Mainhand") {
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                } else if (breakhand.getValueString() == "Offhand") {
                    mc.player.swingArm(EnumHand.OFF_HAND);
                } else {
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    mc.player.swingArm(EnumHand.OFF_HAND);
                }
                breaktimer.reset();
            }
        }
    }
}
