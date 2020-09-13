package me.ollieobama.past.module.modules.hud;

import me.ollieobama.past.Past;
import me.ollieobama.past.module.Category;
import me.ollieobama.past.module.Module;
import me.ollieobama.past.settings.Setting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PastHUD extends Module {
    public PastHUD() {
        super ("PastHUD", "Draws the HUD", Category.HUD);
    }

    Setting clientname;
    Setting welcomer;
    Setting arraylist;

    @Override
    public void setup() {
        Past.settingsManager.rSetting(clientname = new Setting("Client Name", this, true));
        Past.settingsManager.rSetting(welcomer  = new Setting("Welcomer", this, true));
        Past.settingsManager.rSetting(arraylist = new Setting("Arraylist", this, true));
    }

    @SubscribeEvent
    public void onGui(RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            if (clientname.getValBoolean()) {
                Past.pastHUD.drawName();
            } if (welcomer.getValBoolean()) {
                Past.pastHUD.drawWelcome();
            } if (arraylist.getValBoolean()) {
                Past.pastHUD.drawModules();
            }
        }
    }
}
