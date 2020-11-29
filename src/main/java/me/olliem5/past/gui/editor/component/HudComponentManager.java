package me.olliem5.past.gui.editor.component;

import me.olliem5.past.gui.editor.component.components.*;

import java.util.ArrayList;

public class HudComponentManager {
    public static ArrayList<HudComponent> hudComponents = new ArrayList<>();

    public HudComponentManager() {
        hudComponents.add(new Watermark());
        hudComponents.add(new Welcomer());
        hudComponents.add(new Arraylist());
    }

    public static ArrayList<HudComponent> getHudComponents() {
        return hudComponents;
    }

    public HudComponent getHudComponentByName(String name) {
        return hudComponents.stream().filter(hudComponent -> hudComponent.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
