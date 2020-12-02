package me.olliem5.past.module;

import me.olliem5.past.Past;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

public class Module {
    protected Minecraft mc = Minecraft.getMinecraft();

    private String name;
    private String description;
    private Category category;
    private boolean toggled;
    private Integer key;

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
        toggled = false;
        key = Keyboard.KEY_NONE;
        setup();
    }

    public void toggle() {
        toggled = !toggled;
        onToggle();
        if (toggled) {
            onEnableEvent();
        } else {
            onDisableEvent();
        }
    }

    public boolean isToggled() {
        return toggled;
    }

    public void onEnableEvent() {
        MinecraftForge.EVENT_BUS.register(this);
        Past.EVENT_BUS.subscribe(this);

        if (Past.configUtil != null) {
            try {
                Past.configUtil.saveLoadedModules();
            } catch (Exception e) {}
        }

        onEnable();
    }

    public void onDisableEvent() {
        MinecraftForge.EVENT_BUS.unregister(this);
        Past.EVENT_BUS.unsubscribe(this);

        if (Past.configUtil != null) {
            try {
                Past.configUtil.saveLoadedModules();
            } catch (Exception e) {}
        }

        onDisable();
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;

        if (Past.configUtil != null) {
            try {
                Past.configUtil.saveKeybinds();
            } catch (Exception e) {}
        }
    }

    public String getArraylistInfo() {
        return "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }

    public void onToggle() {}

    public void onUpdate() {}

    public void setup() {}

    public void selfSettings() {}

    public void onEnable() {}

    public void onDisable() {}
}