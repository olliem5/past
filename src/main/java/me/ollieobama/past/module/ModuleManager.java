package me.ollieobama.past.module;

import me.ollieobama.past.module.modules.hud.PastHUD;
import me.ollieobama.past.module.modules.misc.DiscordRPC;
import me.ollieobama.past.module.modules.misc.SettingsTest;
import me.ollieobama.past.module.modules.hud.ClickGUI;

import java.util.ArrayList;

public class ModuleManager {
    private ArrayList<Module> modules = new ArrayList<>();

    public ModuleManager() {
        modules.add(new ClickGUI());
        modules.add(new PastHUD());
        modules.add(new SettingsTest());
        modules.add(new DiscordRPC());
    }

    public ArrayList<Module> getModules() { return modules; }
}