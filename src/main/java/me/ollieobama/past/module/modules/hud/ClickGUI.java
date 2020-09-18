package me.ollieobama.past.module.modules.hud;

import me.ollieobama.past.Past;
import me.ollieobama.past.module.Category;
import me.ollieobama.past.module.Module;
import me.ollieobama.past.settings.Setting;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class ClickGUI extends Module {
    public ClickGUI() {
        super ("ClickGUI", "Opens the ClickGUI", Category.HUD);
        setKey(Keyboard.KEY_RSHIFT);
    }

    @Override
    public void setup(){
        ArrayList<String> options = new ArrayList<>();
        options.add("JellyLike");
        options.add("New");
        Past.settingsManager.rSetting(new Setting("Design", this, "New", options));
        Past.settingsManager.rSetting(new Setting("Sound", this, false));
        Past.settingsManager.rSetting(new Setting("GuiRed", this, 255, 0, 255, true));
        Past.settingsManager.rSetting(new Setting("GuiGreen", this, 26, 0, 255, true));
        Past.settingsManager.rSetting(new Setting("GuiBlue", this, 42, 0, 255, true));
    }

    public void onEnable() {
        mc.displayGuiScreen(Past.clickGUI);
        toggle();
    }
}
