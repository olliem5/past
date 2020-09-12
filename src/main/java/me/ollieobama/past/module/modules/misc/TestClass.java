package me.ollieobama.past.module.modules.misc;

import me.ollieobama.past.Past;
import me.ollieobama.past.module.Category;
import me.ollieobama.past.module.Module;

public class TestClass extends Module {
    public TestClass() {
        super("TestClass", "Tests Classes", Category.MISC);
    }

    public void onEnable() {
        mc.displayGuiScreen(Past.clickGUI);
        mc.player.sendChatMessage("Working!");
    }
}
