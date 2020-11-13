package me.olliem5.past.module.modules.movement;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.ColourUtil;

import java.util.ArrayList;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "Automatically makes you sprint", Category.MOVEMENT);
    }

    Setting sprintmode;

    private ArrayList<String> sprintmodes;

    @Override
    public void setup() {
        sprintmodes = new ArrayList<>();
        sprintmodes.add("Legit");
        sprintmodes.add("Rage");

        Past.settingsManager.registerSetting(sprintmode = new Setting("Mode", "SprintMode", this, sprintmodes, "Legit"));
    }

    public void onUpdate() {
        if (nullCheck()) {
            return;
        }

        if (sprintmode.getValueString() == "Legit") {
            try {
                if (mc.gameSettings.keyBindForward.isKeyDown() && !(mc.player.collidedHorizontally) && !(mc.player.isSneaking()) && !(mc.player.isHandActive()) && mc.player.getFoodStats().getFoodLevel() > 6f) {
                    mc.player.setSprinting(true);
                }
            } catch (Exception ignored) {
            }
        }
        if (sprintmode.getValueString() == "Rage") {
            try {
                if (!(mc.player.isSneaking()) && !(mc.player.collidedHorizontally) && mc.player.getFoodStats().getFoodLevel() > 6f && mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown()) {
                    mc.player.setSprinting(true);
                }
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public void onDisable() {
        mc.player.setSprinting(false);
    }

    public String getArraylistInfo() {
        return ColourUtil.gray + " " + sprintmode.getValueString().toUpperCase();
    }
}
