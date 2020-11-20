package me.olliem5.past.module.modules.core;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;

public class Render extends Module {
    public Render() {
        super("Render", "Changes values for client rendering", Category.CORE);
    }

    /**
     * Just to store values for RenderUtil.java
     */

    Setting linewidth;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(linewidth = new Setting("Line Width", "RenderLineWidth", 1, 3, 5, this));
    }

    @Override
    public void onEnable() {
        toggle();
    }
}
