package me.olliem5.past.impl.modules.core;

import me.olliem5.past.Past;
import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.module.ModuleInfo;
import me.olliem5.past.api.setting.Setting;

@ModuleInfo(name = "Render", description = "Changes values for client rendering", category = Category.CORE)
public class Render extends Module {

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
