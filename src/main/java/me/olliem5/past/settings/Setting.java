package me.olliem5.past.settings;

import me.olliem5.past.Past;
import me.olliem5.past.module.Module;

import java.util.ArrayList;

public class Setting {
    private Module parent;
    private String name;
    private String type;
    private int min;
    private int start;
    private int max;
    private boolean bval;

    public Setting(String name, int min, int start, int max, Module module) {
        this.parent = module;
        this.name = name;
        this.min = min;
        this.start = start;
        this.max = max;
        this.type = "intslider";
    }

    public Setting(String name, boolean bval, Module module) {
        this.parent = module;
        this.name = name;
        this.bval = bval;
        this.type = "boolean";
    }

    private String value;
    private ArrayList<String> modes;
    public Setting(String name, Module module, ArrayList<String> modes, String value) {
        this.parent = module;
        this.name = name;
        this.value = value;
        this.modes = modes;
        this.type = "mode";
    }

    public int getValueInt() { return this.start; }
    public boolean getValBoolean() { return this.bval; }
    public String getValueString() { return this.value; }

    public String getType() { return type; }
    public String getName() { return name; }

    public int getMin() { return min; }
    public int getStart() { return start; }
    public int getMax() { return max; }

    public Module getParent() { return parent; }

    public ArrayList<String> getModes() { return this.modes; }

    public void setValueInt(final int value) {
        this.start = value;

        if (Past.configUtil != null) {
            try { Past.configUtil.saveConfig(); } catch (Exception e) {}
        }
    }

    public void setValBoolean(boolean value) {
        this.bval = value;

        if (Past.configUtil != null) {
            try { Past.configUtil.saveConfig(); } catch (Exception e) {}
        }
    }

    public void setValueString(String value) { this.value = value; }
}
