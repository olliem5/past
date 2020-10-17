package me.olliem5.past.util;

import me.olliem5.past.Past;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.util.ArrayList;

public class ConfigUtil {
    public File MainDirectory;

    public ConfigUtil() {
        MainDirectory = new File(Minecraft.getMinecraft().mcDataDir, "Past Client");
        if (!MainDirectory.exists()) { MainDirectory.mkdir(); }
    }

    public void saveLoadedModules() {
        try {
            File file = new File(MainDirectory, "ToggledModules.txt");
            ArrayList<String> modulesToSave = new ArrayList<>();

            for (Module module : Past.moduleManager.getModules()) { if (module.isToggled()) { modulesToSave.add(module.getName()); } }

            try {
                PrintWriter printWriter = new PrintWriter(file);
                for (String string : modulesToSave) { printWriter.println(string); }
                printWriter.close();
            } catch (FileNotFoundException e) {}
        } catch (Exception e) {}
    }

    public void saveKeybinds() {
        try {
            File file = new File(MainDirectory, "Keybinds.txt");
            ArrayList<String> bindsToSave = new ArrayList<>();

            for (Module module : Past.moduleManager.getModules()) { bindsToSave.add(module.getName() + ":" + module.getKey()); }

            try {
                PrintWriter printWriter = new PrintWriter(file);
                for (String string : bindsToSave) { printWriter.println(string); }
                printWriter.close();
            } catch (FileNotFoundException e) {}
        } catch (Exception e) {}
    }

    public void saveBooleans() {
        try {
            File file = new File(MainDirectory, "BooleanValues.txt");
            ArrayList<String> booleansToSave = new ArrayList<>();

            for (Setting setting : Past.settingsManager.getSettings()) { if (setting.getType() == "boolean") { booleansToSave.add(setting.getParent().getName() + ":" + setting.getName() + ":" + setting.getValBoolean()); } }

            try {
                PrintWriter printWriter = new PrintWriter(file);
                for (String string : booleansToSave) { printWriter.println(string); }
                printWriter.close();
            } catch (FileNotFoundException e) {}
        } catch (Exception e) {}
    }

    public void loadConfig() {}

    //For later
//        for (Setting setting : Past.settingsManager.getSettings()) {
//            if (setting.getType() == "boolean") { toSave.add("Setting" + separator + setting.getName() + separator + setting.getParent().getName() + separator + setting.getValBoolean()); }
//            if (setting.getType() == "intslider") { toSave.add("Setting" + separator + setting.getName() + separator + setting.getParent().getName() + separator + setting.getValueInt()); }
//        }
}