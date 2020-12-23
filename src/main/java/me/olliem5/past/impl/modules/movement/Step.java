package me.olliem5.past.impl.modules.movement;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.olliem5.past.Past;
import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.module.ModuleInfo;
import me.olliem5.past.api.setting.Setting;

import java.util.ArrayList;

@ModuleInfo(name = "Step", description = "Allows you to step up blocks", category = Category.MOVEMENT)
public class Step extends Module {

    /**
     * TODO: Packet mode!
     */

    Setting mode;
    Setting height;

    private ArrayList<String> stepmodes;

    @Override
    public void setup() {
        stepmodes = new ArrayList<>();
        stepmodes.add("Vanilla");
        stepmodes.add("Reverse");

        Past.settingsManager.registerSetting(mode = new Setting("Mode", "StepMode", this, stepmodes, "Vanilla"));
        Past.settingsManager.registerSetting(height = new Setting("Height", "StepHeight", 1, 1, 10, this));
    }

    public void onUpdate() {
        if (nullCheck()) return;

        if (mc.player.onGround && !mc.player.isInWater() && !mc.player.isInLava() && !mc.player.isOnLadder()) {
            if (mode.getValueString() == "Vanilla") {
                if (mc.player.collidedHorizontally) {
                    mc.player.stepHeight = height.getValueInt();
                    mc.player.jump();
                }
            } else {
                --mc.player.motionY;
            }
        }
    }

    @Override
    public void onDisable() {
        if (mode.getValueString() == "Vanilla") {
            mc.player.stepHeight = 0.5f;
        }
    }

    public String getArraylistInfo() {
        return ChatFormatting.GRAY + " " + mode.getValueString().toUpperCase();
    }
}
