package me.olliem5.past.util;

import me.olliem5.past.Past;
import me.olliem5.past.friends.Friend;
import me.olliem5.past.friends.FriendsManager;
import me.olliem5.past.gui.click.ClickGUI;
import me.olliem5.past.gui.click.Panel;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ConfigUtil {
    public File MainDirectory;

    public ConfigUtil() {
        MainDirectory = new File(Minecraft.getMinecraft().gameDir, "Past Client");
        if (!MainDirectory.exists()) {
            MainDirectory.mkdir();
        }

        loadSavedModules();
        loadKeybinds();
        loadFriends();
        loadGuiPanels();
    }

    public void saveLoadedModules() {
        try {
            File file = new File(MainDirectory, "ToggledModules.txt");
            ArrayList<String> modulesToSave = new ArrayList<>();

            for (Module module : Past.moduleManager.getModules()) {
                if (module.isToggled()) {
                    modulesToSave.add(module.getName());
                }
            }

            try {
                PrintWriter printWriter = new PrintWriter(file);
                for (String string : modulesToSave) {
                    printWriter.println(string);
                }
                printWriter.close();
            } catch (FileNotFoundException e) {}
        } catch (Exception e) {}
    }

    public void saveKeybinds() {
        try {
            File file = new File(MainDirectory, "Keybinds.txt");
            ArrayList<String> bindsToSave = new ArrayList<>();

            for (Module module : Past.moduleManager.getModules()) {
                bindsToSave.add(module.getName() + ":" + module.getKey());
            }

            try {
                PrintWriter printWriter = new PrintWriter(file);
                for (String string : bindsToSave) {
                    printWriter.println(string);
                }
                printWriter.close();
            } catch (FileNotFoundException e) {}
        } catch (Exception e) {}
    }

    public void saveBooleans() {
        try {
            File file = new File(MainDirectory, "BooleanValues.txt");
            ArrayList<String> booleansToSave = new ArrayList<>();

            for (Setting setting : Past.settingsManager.getSettings()) {
                if (setting.getType() == "boolean") {
                    booleansToSave.add(setting.getParent().getName() + ":" + setting.getId() + ":" + setting.getValBoolean());
                }
            }

            try {
                PrintWriter printWriter = new PrintWriter(file);
                for (String string : booleansToSave) {
                    printWriter.println(string);
                }
                printWriter.close();
            } catch (FileNotFoundException e) {}
        } catch (Exception e) {}
    }

    public void saveIntegers() {
        try {
            File file = new File(MainDirectory, "IntegerValues.txt");
            ArrayList<String> integersToSave = new ArrayList<>();

            for (Setting setting : Past.settingsManager.getSettings()) {
                if (setting.getType() == "intslider") {
                    integersToSave.add(setting.getParent().getName() + ":" + setting.getId() + ":" + setting.getValueInt());
                }
            }

            try {
                PrintWriter printWriter = new PrintWriter(file);
                for (String string : integersToSave) {
                    printWriter.println(string);
                }
                printWriter.close();
            } catch (FileNotFoundException e) {}
        } catch (Exception e) {}
    }

    public void saveModes() {
        try {
            File file = new File(MainDirectory, "ModeValues.txt");
            ArrayList<String> modesToSave = new ArrayList<>();

            for (Setting setting : Past.settingsManager.getSettings()) {
                if (setting.getType() == "mode") {
                    modesToSave.add(setting.getParent().getName() + ":" + setting.getId() + ":" + setting.getValueString());
                }
            }

            try {
                PrintWriter printWriter = new PrintWriter(file);
                for (String string : modesToSave) {
                    printWriter.println(string);
                }
                printWriter.close();
            } catch (FileNotFoundException e) {}
        } catch (Exception e) {}
    }

    public void saveGuiPanels() {
        try {
            File file = new File(MainDirectory, "GuiPanels.txt");
            ArrayList<String> panelsToSave = new ArrayList<>();

            for (Panel panel : ClickGUI.panels) {
                panelsToSave.add(panel.getCategory() + ":" + panel.getX() + ":" + panel.getY() + ":" + panel.isOpen());
            }

            try {
                PrintWriter printWriter = new PrintWriter(file);
                for (String string : panelsToSave) {
                    printWriter.println(string);
                }
                printWriter.close();
            } catch (FileNotFoundException e) {}
        } catch (Exception e) {}
    }

    public void saveFriends() {
        try {
            File file = new File(MainDirectory, "Friends.txt");
            ArrayList<String> friendsToSave = new ArrayList<>();

            for (Friend friend : FriendsManager.friends) {
                friendsToSave.add(friend.getName());
            }

            try {
                PrintWriter printWriter = new PrintWriter(file);
                for (String string : friendsToSave) {
                    printWriter.println(string);
                }
                printWriter.close();
            } catch (FileNotFoundException e) {}
        } catch (Exception e) {}
    }

    public void loadSavedModules() {
        try {
            File file = new File(MainDirectory, "ToggledModules.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = br.readLine()) != null) {
                Iterator var6 = Past.moduleManager.getModules().iterator();

                while (var6.hasNext()) {
                    Module m = (Module) var6.next();
                    if (m.getName().equals(line)) {
                        m.toggle();
                    }
                }
            }
            br.close();
        } catch (Exception e) {}
    }

    public void loadKeybinds() {
        try {
            File file = new File(MainDirectory, "Keybinds.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = br.readLine()) != null) {
                String curLine = line.trim();
                String name = curLine.split(":")[0];
                String bind = curLine.split(":")[1];
                for (Module m : Past.moduleManager.getModules()) {
                    if (m != null && m.getName().equalsIgnoreCase(name)) {
                        m.setKey(Integer.parseInt(bind));
                    }
                }
            }
            br.close();
        } catch (Exception var11) {}
    }

    public void loadFriends() {
        try {
            File file = new File(MainDirectory, "Friends.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            FriendsManager.friends.clear();
            String line;
            while((line = br.readLine()) != null) {
                Past.friendsManager.addFriend(line);
            }

            br.close();
        } catch (Exception e) {}
    }

    public void loadGuiPanels() {
        try {
            File file = new File(MainDirectory, "GuiPanels.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while((line = br.readLine()) != null) {
                String curLine = line.trim();
                String name = curLine.split(":")[0];
                String x = curLine.split(":")[1];
                String y = curLine.split(":")[2];
                String open = curLine.split(":")[3];
                int x1 = Integer.parseInt(x);
                int y1 = Integer.parseInt(y);
                boolean opened = Boolean.parseBoolean(open);
                Panel p = ClickGUI.getPanelByName(name);
                if (p != null) {
                    p.x = x1;
                    p.y = y1;
                    p.setOpen(opened);
                }
            }

            br.close();
        } catch (Exception e) {}
    }
}