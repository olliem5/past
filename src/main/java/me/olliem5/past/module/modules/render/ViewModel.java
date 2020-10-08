package me.olliem5.past.module.modules.render;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ViewModel extends Module {
    public ViewModel() {
        super ("ViewModel", "Changes the way your player looks", Category.RENDER);
    }

    //Arm Yaw is disabled atom, because when you pop a totem the values get fucked and change.

    Setting itemfov;
    Setting armpitch;
//    Setting armyaw;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(itemfov = new Setting("Item FOV", 110, 130, 170, this));
        Past.settingsManager.registerSetting(armpitch = new Setting("Arm Pitch", -360, 90, 360, this));
//        Past.settingsManager.registerSetting(armyaw = new Setting("Arm Yaw", -360, 220, 1080, this));
    }

    public void onUpdate() {
        mc.player.renderArmPitch = armpitch.getValueInt();
//        mc.player.renderArmYaw = armyaw.getValueInt();
    }

    @SubscribeEvent
    public void fovEvent(EntityViewRenderEvent.FOVModifier m) { m.setFOV(itemfov.getValueInt()); }
}
