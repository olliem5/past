package me.olliem5.past.module.modules.core;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;

public class HudEditor extends Module {
    public HudEditor() {
        super("HudEditor", "Edit the HUD", Category.CORE);
    }

    public void onEnable() {
        mc.displayGuiScreen(Past.hudEditor);
        toggle();
    }
}
