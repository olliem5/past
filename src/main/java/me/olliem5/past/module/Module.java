package me.olliem5.past.module;

import me.olliem5.past.Past;
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
    public void onTick(TickEvent.ClientTickEvent event) {
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

    public void toggle() {
        toggled = !toggled;
        onToggle();
        if (toggled) {
            onEnableEvent();
        } else {
            onDisableEvent();
        }
    }

    public boolean isToggled() { return toggled; }

    public void onEnableEvent() {
        MinecraftForge.EVENT_BUS.register(this);

        if (Past.configUtil != null) { try { Past.configUtil.saveLoadedModules(); } catch (Exception e) {} }

        onEnable();
    }

    public void onDisableEvent() {
        MinecraftForge.EVENT_BUS.unregister(this);

        if (Past.configUtil != null) { try { Past.configUtil.saveLoadedModules(); } catch (Exception e) {} }

        onDisable();
    }

    public Integer getKey() { return key; }

    public void setKey(Integer key) {
        this.key = key;

        if (Past.configUtil != null) { try { Past.configUtil.saveKeybinds(); } catch (Exception e) {} }
    }

    public String getArraylistInfo() { return ""; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public boolean nullCheck() { return mc.player == null || mc.world == null; }

    public void onToggle() {}
    public void onUpdate() {}
    public void onRender() {}
    public void setup() {}
    public void selfSettings() {}
    public void onEnable() {}
    public void onDisable() {}
}