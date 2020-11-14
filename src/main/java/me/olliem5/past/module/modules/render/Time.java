package me.olliem5.past.module.modules.render;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;

import java.util.ArrayList;

public class Time extends Module {
    public Time() {
        super("Time", "Changes the time in your world", Category.RENDER);
    }

    Setting timemode;
    //Setting usecustom;
    Setting customtimemode;

    private ArrayList<String> timemodes;

    @Override
    public void setup() {
        timemodes = new ArrayList<>();
        timemodes.add("Day");
        timemodes.add("Noon");
        timemodes.add("Sunset");
        timemodes.add("Night");
        timemodes.add("Midnight");
        timemodes.add("Sunrise");
        timemodes.add("Custom");

        Past.settingsManager.registerSetting(timemode = new Setting("Time", "TimeMode", this, timemodes, "Day"));
        //Past.settingsManager.registerSetting(usecustom = new Setting("Use Custom", "TimeUseCustom", false, this));
        Past.settingsManager.registerSetting(customtimemode = new Setting("Custom", "TimeCustom", 0, 18000, 24000, this));
    }

    public void onUpdate() {
        if (mc.world == null) {
            return;
        }

        if (timemode.getValueString() == "Day") {
            mc.world.setWorldTime(1000);
        }

        if (timemode.getValueString() == "Noon") {
            mc.world.setWorldTime(6000);
        }

        if (timemode.getValueString() == "Sunset") {
            mc.world.setWorldTime(12500);
        }

        if (timemode.getValueString() == "Night") {
            mc.world.setWorldTime(13000);
        }

        if (timemode.getValueString() == "Midnight") {
            mc.world.setWorldTime(18000);
        }

        if (timemode.getValueString() == "Sunrise") {
            mc.world.setWorldTime(23500);
        }

        if (timemode.getValueString() == "Custom") {
            mc.world.setWorldTime(customtimemode.getValueInt());
        }
    }
}
