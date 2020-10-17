package me.olliem5.past.util;

import me.olliem5.past.Past;
import me.olliem5.past.gui.click.ClickGUI;
import me.olliem5.past.gui.click.Panel;
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

    public void saveIntegers() {
        try {
            File file = new File(MainDirectory, "IntegerValues.txt");
            ArrayList<String> integersToSave = new ArrayList<>();

            for (Setting setting : Past.settingsManager.getSettings()) { if (setting.getType() == "intslider") { integersToSave.add(setting.getParent().getName() + ":" + setting.getName() + ":" + setting.getValueInt()); } }

            try {
                PrintWriter printWriter = new PrintWriter(file);
                for (String string : integersToSave) { printWriter.println(string); }
                printWriter.close();
            } catch (FileNotFoundException e) {}
        } catch (Exception e) {}
    }

    public void saveModes() {
        try {
            File file = new File(MainDirectory, "ModeValues.txt");
            ArrayList<String> modesToSave = new ArrayList<>();

            for (Setting setting : Past.settingsManager.getSettings()) { if (setting.getType() == "mode") { modesToSave.add(setting.getParent().getName() + ":" + setting.getName() + ":" + setting.getValueString()); } }

            try {
                PrintWriter printWriter = new PrintWriter(file);
                for (String string : modesToSave) { printWriter.println(string); }
                printWriter.close();
            } catch (FileNotFoundException e) {}
        } catch (Exception e) {}
    }

    public void saveGuiPanels() {
        try {
            File file = new File(MainDirectory, "GuiPanels.txt");
            ArrayList<String> panelsToSave = new ArrayList<>();

            for (Panel panel : ClickGUI.panels) {
                panelsToSave.add(panel.getCat() + ":" + "x" + ":" + panel.getX());
                panelsToSave.add(panel.getCat() + ":" + "y" + ":" + panel.getY());
            }

            try {
                PrintWriter printWriter = new PrintWriter(file);
                for (String string : panelsToSave) { printWriter.println(string); }
                printWriter.close();
            } catch (FileNotFoundException e) {}
        } catch (Exception e) {}
    }

    public void loadConfig() {}
}