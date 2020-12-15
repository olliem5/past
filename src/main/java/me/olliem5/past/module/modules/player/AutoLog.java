package me.olliem5.past.module.modules.player;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.colour.ColourUtil;
import net.minecraft.client.gui.GuiMainMenu;

import java.util.ArrayList;

public class AutoLog extends Module {
    public AutoLog() {
        super("AutoLog", "Logs you out at a certain health", Category.PLAYER);
    }

    Setting logmode;
    Setting health;

    private ArrayList<String> logmodes;

    @Override
    public void setup() {
        logmodes = new ArrayList<>();
        logmodes.add("Disconnect");
        logmodes.add("Kick");

        Past.settingsManager.registerSetting(logmode = new Setting("Mode", "AutoLogMode", this, logmodes, "Disconnect"));
        Past.settingsManager.registerSetting(health = new Setting("Health", "AutoLogHealth", 1, 10, 36, this));
    }

    public void onUpdate() {
        if (nullCheck()) return;

        if (mc.player.getHealth() < health.getValueInt()) {
            doAutoLog();
            toggle();
        }
    }

    private void doAutoLog() {
        if (logmode.getValueString() == "Disconnect") {
            mc.world.sendQuittingDisconnectingPacket();
            mc.loadWorld(null);
            mc.displayGuiScreen(new GuiMainMenu());
        } else {
            mc.player.inventory.currentItem = 69420;
        }
    }

    public String getArraylistInfo() {
        return ColourUtil.gray + " " + logmode.getValueString().toUpperCase();
    }
}
