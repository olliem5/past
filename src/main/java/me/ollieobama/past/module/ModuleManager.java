package me.ollieobama.past.module;

import me.ollieobama.past.module.modules.misc.SettingsTest;
import me.ollieobama.past.module.modules.misc.TestClass;

import java.util.ArrayList;

public class ModuleManager {
    private ArrayList<Module> modules = new ArrayList<Module>();

    public ModuleManager() {
        modules.add(new TestClass());
        modules.add(new SettingsTest());
    }

    public ArrayList<Module> getModules() {
        return modules;
    }
}