package me.olliem5.past.impl.modules.player;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.olliem5.past.Past;
import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.module.ModuleInfo;
import me.olliem5.past.api.setting.Setting;
import net.minecraft.client.gui.GuiMainMenu;

import java.util.ArrayList;

@ModuleInfo(name = "AutoLog", description = "Leaves the current server at a certain health", category = Category.PLAYER)
public class AutoLog extends Module {

    /**
     * TODO: Packet kick mode!
     */

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
        return ChatFormatting.GRAY + " " + logmode.getValueString().toUpperCase();
    }
}
