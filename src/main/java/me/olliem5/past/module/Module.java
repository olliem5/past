package me.olliem5.past.module;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import static me.olliem5.past.Past.moduleManager;

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

    @SubscribeEvent
    public void gameTickEvent(TickEvent event) {
        for (Module module : moduleManager.getModules()) {
            if (module.isToggled()) {
                onUpdate();
            }
        }
    }

    @SubscribeEvent
    public void onRender(RenderHandEvent event) {
        for (Module module : moduleManager.getModules()) {
            if (module.isToggled()) {
                onRender();
            }
        }
    }

//    @SubscribeEvent
//    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
//        if (event.type == TickEvent.Type.PLAYER) {
//            for (Module module : moduleManager.getModules()) {
//                if (module.isToggled()) {
//                    onPlayerTick();
//                }
//            }
//        }
//    }

    public void toggle() {
        toggled = !toggled;
        onToggle();
        if (toggled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public boolean isToggled() { return toggled; }

    public void onEnable() { MinecraftForge.EVENT_BUS.register(this); }
    public void onDisable() { MinecraftForge.EVENT_BUS.unregister(this); }

    public Integer getKey() { return key; }
    public void setKey(Integer key) { this.key = key; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; } //TODO: Render description in ClickGUI
    public void setDescription(String description) { this.description = description; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public void onToggle() {}
    public void onUpdate() {}
    public void onRender() {}
//  public void onPlayerTick() {}
    public void setup() {}
    public void selfSettings() {}
}