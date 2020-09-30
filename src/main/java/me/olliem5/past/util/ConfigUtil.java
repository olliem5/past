package me.olliem5.past.util;

import me.olliem5.past.Past;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.util.ArrayList;

public class ConfigUtil {
    public File MainDirectory;
    public File ModuleFile;

    String separator = " - ";

    public ConfigUtil() {
        MainDirectory = new File(Minecraft.getMinecraft().mcDataDir, "Past Client");
        if (!MainDirectory.exists()) { MainDirectory.mkdir(); }

        ModuleFile = new File(MainDirectory, "Module Info.txt");
        if (!ModuleFile.exists()) { try { ModuleFile.createNewFile(); } catch (IOException e) {} }
    }

    public void saveConfig() {
        ArrayList<String> toSave = new ArrayList<>();

        for (Module module : Past.moduleManager.getModules()) { toSave.add("Module" + separator + module.getName() + separator + module.getKey() + separator + module.isToggled()); }

        for (Setting setting : Past.settingsManager.getSettings()) {
            if (setting.getType() == "boolean") { toSave.add("Setting" + separator + setting.getName() + separator + setting.getParent().getName() + separator + setting.getValBoolean()); }
            if (setting.getType() == "intslider") { toSave.add("Setting" + separator + setting.getName() + separator + setting.getParent().getName() + separator + setting.getValueInt()); }
        }

        try {
            PrintWriter printWriter = new PrintWriter(this.ModuleFile);
            for (String string : toSave) { printWriter.println(string); }
            printWriter.close();
        } catch (FileNotFoundException e) { e.printStackTrace(); }
    }

    public void loadConfig() {
    }
}
