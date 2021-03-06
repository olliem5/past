package me.olliem5.past.impl.modules.core;

import me.olliem5.past.Past;
import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.module.ModuleInfo;
import me.olliem5.past.api.setting.Setting;

import java.util.ArrayList;

@ModuleInfo(name = "Font", description = "Changes the font that Past uses", category = Category.CORE)
public class Font extends Module {

    Setting font;
    Setting shadow;

    public ArrayList<String> fonts;

    @Override
    public void setup() {
        fonts = new ArrayList<>();
        fonts.add("Lato");
        fonts.add("Verdana");
        fonts.add("Arial");
        fonts.add("Minecraft");

        Past.settingsManager.registerSetting(font = new Setting("Font", "FontFont", this, fonts, "Lato"));
        Past.settingsManager.registerSetting(shadow = new Setting("Shadow", "FontShadow", true, this));
    }

    @Override
    public void onEnable() {
        toggle();
    }
}
