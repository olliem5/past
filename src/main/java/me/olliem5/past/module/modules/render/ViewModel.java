package me.olliem5.past.module.modules.render;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraftforge.client.event.EntityViewRenderEvent;

public class ViewModel extends Module {
    public ViewModel() {
        super("ViewModel", "Changes the way your player looks", Category.RENDER);
    }

    //Arm Yaw is disabled atm, because when you pop a totem the values change.

    Setting itemfov;
    Setting armpitch;
//    Setting armyaw;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(itemfov = new Setting("Item FOV", "ViewModelItemFOV", 110, 130, 170, this));
        Past.settingsManager.registerSetting(armpitch = new Setting("Arm Pitch", "ViewModelArmPitch", -360, 90, 360, this));
//        Past.settingsManager.registerSetting(armyaw = new Setting("Arm Yaw", -360, 220, 1080, this));
    }

    public void onUpdate() {
        if (nullCheck()) {
            return;
        }
        mc.player.renderArmPitch = armpitch.getValueInt();
//        mc.player.renderArmYaw = armyaw.getValueInt();
    }

    @EventHandler
    public Listener<EntityViewRenderEvent.FOVModifier> listener = new Listener<>(event -> {
        event.setFOV(itemfov.getValueInt());
    });
}
