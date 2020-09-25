package me.olliem5.past.settings;

import me.olliem5.past.module.Module;

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

    public int getValueInt() { return this.start; }
    public void setValueInt(final int value) { this.start = value; }

    public boolean getValBoolean() { return this.bval; }
    public void setValBoolean(boolean value) { this.bval = value; }

    public String getType() { return type; }
    public String getName() { return name; }

    public int getMin() { return min; }
    public int getStart() { return start; }
    public int getMax() { return max; }

    public Module getParent() { return parent; }
}
