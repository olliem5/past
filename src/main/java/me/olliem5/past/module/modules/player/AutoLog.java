package me.olliem5.past.module.modules.player;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import net.minecraft.client.gui.GuiMainMenu;

public class AutoLog extends Module {
    public AutoLog() {
        super ("AutoLog", "Logs you out at a certain health", Category.PLAYER);
    }

    Setting health;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(health = new Setting("Health", "AutoLogHealth", 0, 10, 36, this));
    }

    public void onUpdate() {
        if (nullCheck()) { return; }

        if (mc.player.getHealth() < health.getValueInt()) {
            toggle();
            mc.world.sendQuittingDisconnectingPacket();
            mc.loadWorld(null);
            mc.displayGuiScreen(new GuiMainMenu());
        }
    }
}
