package me.olliem5.past.settings;

import me.olliem5.past.Past;
import me.olliem5.past.module.Module;

import java.util.ArrayList;

public class SettingsManager {
    private ArrayList<Setting> settings;

    public SettingsManager() { this.settings = new ArrayList<>(); }

    public void registerSetting(Setting args) { this.settings.add(args); }

    public ArrayList<Setting> getSettings() { return this.settings; }

    public Setting getSettingName(String name) {
        for (Setting setting : getSettings()) {
            if (setting.getName() == name) {
                return setting;
            }
        }
        System.out.println("[" + Past.nameversion + "]" + " " + "Error - Setting" + " " + "'" + name + "'" + "NOT Found!");
        return null;
    }

    public ArrayList<Setting> getSettingsModule(Module module) {
        ArrayList<Setting> list = new ArrayList<>();
        for (Setting setting : getSettings()) {
            if (setting.getParent() == module) {
                list.add(setting);
            }
        }
        return list;
    }
}
