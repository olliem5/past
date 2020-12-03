package me.olliem5.past.module.modules.core;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", "Opens the client ClickGUI", Category.CORE);
    }

    public void onEnable() {
        mc.displayGuiScreen(Past.clickGUITwo);
        toggle();
    }
}
