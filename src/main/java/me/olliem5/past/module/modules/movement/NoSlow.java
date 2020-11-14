package me.olliem5.past.module.modules.movement;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoSlow extends Module {
    public NoSlow() {
        super("NoSlow","Prevents items/blocks from slowing you down", Category.MOVEMENT);
    }

    Setting soulsand;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(soulsand = new Setting("Soul Sand", "NoSlowSoulSand", true, this));
    }

    @SubscribeEvent
    public void onInputUpdate(InputUpdateEvent event) {
        if (!mc.player.isRiding() && mc.player.isHandActive()) {
            event.getMovementInput().moveStrafe *= 5;
            event.getMovementInput().moveForward *= 5;
        }
    }
}
