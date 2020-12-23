package me.olliem5.past.api.setting;

import me.olliem5.past.Past;
import me.olliem5.past.impl.gui.editor.component.HudComponent;
import me.olliem5.past.api.module.Module;

import java.util.ArrayList;

public class Setting {
    private Module parent;
    private HudComponent hudParent;

    private String name;
    private String id;
    private String type;

    private int min;
    private int start;
    private int max;

    private double dmin;
    private double dstart;
    private double dmax;

    private boolean bval;

    private String sval;
    private ArrayList<String> modes;

    public Setting(String name, String id, int min, int start, int max, Module module) {
        this.parent = module;
        this.name = name;
        this.id = id;
        this.min = min;
        this.start = start;
        this.max = max;
        this.type = "integer";
    }

    public Setting(String name, String id, double dmin, double dstart, double dmax, Module module) {
        this.parent = module;
        this.name = name;
        this.id = id;
        this.dmin = dmin;
        this.dstart = dstart;
        this.dmax = dmax;
        this.type = "double";
    }

    public Setting(String name, String id, boolean bval, Module module) {
        this.parent = module;
        this.name = name;
        this.id = id;
        this.bval = bval;
        this.type = "boolean";
    }

    public Setting(String name, String id, Module module, ArrayList<String> modes, String sval) {
        this.parent = module;
        this.name = name;
        this.id = id;
        this.sval = sval;
        this.modes = modes;
        this.type = "mode";
    }

    public Setting(String name, String id, boolean bval, HudComponent hudComponent) {
        this.hudParent = hudComponent;
        this.name = name;
        this.id = id;
        this.bval = bval;
        this.type = "hudboolean";
    }

    public int getValueInt() {
        return this.start;
    }

    public double getValueDouble() {
        return this.dstart;
    }

    public boolean getValBoolean() {
        return this.bval;
    }

    public String getValueString() {
        return this.sval;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getMin() {
        return min;
    }

    public int getStart() {
        return start;
    }

    public int getMax() {
        return max;
    }

    public double getDmin() {
        return dmin;
    }

    public double getDstart() {
        return dstart;
    }

    public double getDmax() {
        return dmax;
    }

    public String getId() {
        return id;
    }

    public Module getParent() {
        return parent;
    }

    public HudComponent getHudParent() {
        return hudParent;
    }

    public ArrayList<String> getModes() {
        return this.modes;
    }

    public void setValueInt(final int value) {
        this.start = value;

        if (Past.configUtil != null) {
            try {
                Past.configUtil.saveIntegers();
            } catch (Exception e) {}
        }
    }

    public void setValueDouble(final double value) {
        this.dstart = value;

        if (Past.configUtil != null) {
            try {
                Past.configUtil.saveDoubles();
            } catch (Exception e) {}
        }
    }

    public void setValBoolean(boolean value) {
        this.bval = value;

        if (Past.configUtil != null) {
            try {
                Past.configUtil.saveBooleans();
            } catch (Exception e) {}
        }
    }

    public void setValueString(String value) {
        this.sval = value;

        if (Past.configUtil != null) {
            try {
                Past.configUtil.saveModes();
            } catch (Exception e) {}
        }
    }
}
