package me.olliem5.past.impl.modules.render;

import me.olliem5.past.Past;
import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.module.ModuleInfo;
import me.olliem5.past.api.setting.Setting;

import java.util.ArrayList;

@ModuleInfo(name = "CrystalCustomize", description = "Allows you to change how end crystals render", category = Category.RENDER)
public class CrystalCustomize extends Module {

    /**
     * TODO: Crystal scale
     */

    Setting crystalmode;
    Setting cube;
    Setting glass;
    Setting base;

    public ArrayList<String> crystalmodes;

    @Override
    public void setup() {
        crystalmodes = new ArrayList<>();
        crystalmodes.add("Vanilla");
        crystalmodes.add("RubiksCube");

        Past.settingsManager.registerSetting(crystalmode = new Setting("Crystal", "CrystalCustomizeCrystalMode", this, crystalmodes, "RubiksCube"));
        Past.settingsManager.registerSetting(cube = new Setting("Cube", "CrystalCustomizeCube", true, this));
        Past.settingsManager.registerSetting(glass = new Setting("Glass", "CrystalCustomizeGlass", true, this));
        Past.settingsManager.registerSetting(base = new Setting("Base", "CrystalCustomizeBase", true, this));
    }
}
