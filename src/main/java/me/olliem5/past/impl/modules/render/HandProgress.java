package me.olliem5.past.impl.modules.render;

import me.olliem5.past.Past;
import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.setting.Setting;

public class HandProgress extends Module {
    public HandProgress() {
        super("HandProgress", "Changes your hand progress", Category.RENDER);
    }

    Setting mainhand;
    Setting offhand;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(mainhand = new Setting("Main Hand", "HandProgressMainHand", 0.0, 1.0, 1.0, this));
        Past.settingsManager.registerSetting(offhand = new Setting("Off Hand", "HandProgressOffHand", 0.0, 1.0, 1.0, this));
    }

    public void onUpdate() {
        if (nullCheck()) return;

        mc.entityRenderer.itemRenderer.equippedProgressMainHand = (float) mainhand.getValueDouble();
        mc.entityRenderer.itemRenderer.equippedProgressOffHand = (float) offhand.getValueDouble();
    }
}
