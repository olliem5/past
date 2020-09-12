package me.ollieobama.past.module.modules.hud;

import me.ollieobama.past.Past;
import me.ollieobama.past.module.Category;
import me.ollieobama.past.module.Module;
import org.lwjgl.input.Keyboard;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", "Opens the ClickGUI", Category.HUD);
        setKey(Keyboard.KEY_RSHIFT);
    }

    public void onEnable() {
        mc.displayGuiScreen(Past.clickGUI);
        toggle();
    }
}
