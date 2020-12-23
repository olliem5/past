package me.olliem5.past.impl.modules.render;

import me.olliem5.past.Past;
import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.setting.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraftforge.client.event.EntityViewRenderEvent;

public class ViewModel extends Module {
    public ViewModel() {
        super("ViewModel", "Changes the way your player looks in first person", Category.RENDER);
    }

    Setting itemfov;
    Setting armpitch;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(itemfov = new Setting("Item FOV", "ViewModelItemFOV", 110, 130, 170, this));
        Past.settingsManager.registerSetting(armpitch = new Setting("Arm Pitch", "ViewModelArmPitch", -360, 90, 360, this));
    }

    public void onUpdate() {
        if (nullCheck()) return;

        mc.player.renderArmPitch = armpitch.getValueInt();
    }

    @EventHandler
    public Listener<EntityViewRenderEvent.FOVModifier> listener = new Listener<>(event -> {
        event.setFOV(itemfov.getValueInt());
    });
}
