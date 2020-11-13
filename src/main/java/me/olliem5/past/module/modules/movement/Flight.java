package me.olliem5.past.module.modules.movement;

import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;

public class Flight extends Module {
    public Flight() {
        super ("Flight", "Allows you to fly", Category.MOVEMENT);
    }

//TODO: Float GUI Slider to do fly speed, 1 is fast.

//    Setting flyspeed;
//
//    @Override
//    public void setup() {
//        Past.settingsManager.registerSetting(flyspeed = new Setting("Speed", "FlySpeed", 0, 10, 100, this));
//    }

    public void onUpdate() {
        if (nullCheck()) { return; }
        mc.player.capabilities.isFlying = true;
        //mc.player.capabilities.setFlySpeed((float) flyspeed.getValueInt());
    }

    @Override
    public void onDisable() {
        if (nullCheck()) { return; }
        mc.player.capabilities.isFlying = false;
    }
}
