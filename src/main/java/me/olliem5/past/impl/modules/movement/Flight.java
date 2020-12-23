package me.olliem5.past.impl.modules.movement;

import me.olliem5.past.Past;
import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.module.ModuleInfo;
import me.olliem5.past.api.setting.Setting;

import java.util.ArrayList;

@ModuleInfo(name = "Flight", description = "Allows you to fly", category = Category.MOVEMENT)
public class Flight extends Module {

    /**
     * TODO: Packet mode!
     */

    Setting flymode;
    Setting flyspeed;

    private ArrayList<String> flymodes;

    @Override
    public void setup() {
        flymodes = new ArrayList<>();
        flymodes.add("Vanilla");

        Past.settingsManager.registerSetting(flymode = new Setting("Mode", "FlyMode", this, flymodes, "Vanilla"));
        Past.settingsManager.registerSetting(flyspeed = new Setting("Speed", "FlySpeed", 0.1, 0.1, 1.0, this));
    }

    public void onUpdate() {
        if (nullCheck()) return;

        if (flymode.getValueString() == "Vanilla") {
            mc.player.capabilities.isFlying = true;
            mc.player.capabilities.setFlySpeed((float) flyspeed.getValueDouble());
        }
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        if (flymode.getValueString() == "Vanilla") {
            mc.player.capabilities.isFlying = false;
        }
    }
}
