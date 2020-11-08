package me.olliem5.past.module.modules.movement;

import me.olliem5.past.Past;
import me.olliem5.past.mixin.accessors.MinecraftAccessor;
import me.olliem5.past.mixin.accessors.TimerAccessor;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;

public class Timer extends Module {
    public Timer() {
        super ("Timer", "Changes your tick speed", Category.MOVEMENT);
    }

    Setting speed;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(speed = new Setting("Speed", "TimerSpeed", 0, 20, 100, this));
    }

    public void onUpdate() {
        ((TimerAccessor) ((MinecraftAccessor) mc).getTimer()).setTickLength(50f / (speed.getValueInt() / 10f));
    }

    @Override
    public void onDisable() {
        ((TimerAccessor) ((MinecraftAccessor) mc).getTimer()).setTickLength(50f);
    }
}
