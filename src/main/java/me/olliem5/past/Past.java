package me.olliem5.past;

import me.olliem5.past.command.CommandManager;
import me.olliem5.past.font.CustomFontRenderer;
import me.olliem5.past.friends.FriendsManager;
import me.olliem5.past.gui.click.ClickGUI;
import me.olliem5.past.gui.editor.component.HudComponent;
import me.olliem5.past.gui.editor.component.HudComponentManager;
import me.olliem5.past.gui.editor.screen.HudEditor;
import me.olliem5.past.module.Module;
import me.olliem5.past.module.ModuleManager;
import me.olliem5.past.settings.SettingsManager;
import me.olliem5.past.util.ConfigUtil;
import me.zero.alpine.EventBus;
import me.zero.alpine.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.awt.*;

@Mod(
        name = Past.name,
        modid = Past.modid,
        version = Past.version
)

public class Past {
    public static final String name = "Past Utility Mod";
    public static final String modid = "past";
    public static final String version = "1.2";
    public static final String author = "olliem5";
    public static final String github = "https://github.com/olliem5/past";
    public static final String appid = "754509326902886411";
    public static String prefix = ".";
    public static String nameversion = name + " " + version;
    public static int prefixchatkey = Keyboard.KEY_PERIOD; //TODO: Sync up with config and the prefix string

    public static EventBus EVENT_BUS = new EventManager();
    public static SettingsManager settingsManager;
    public static ModuleManager moduleManager;
    public static CommandManager commandManager;
    public static FriendsManager friendsManager;
    public static CustomFontRenderer customFontRenderer;
    public static HudComponentManager hudComponentManager;
    public static ClickGUI clickGUI;
    public static HudEditor hudEditor;
    public static ConfigUtil configUtil;

    @Mod.EventHandler
    public void PastPreInitialize(FMLPreInitializationEvent event) { Display.setTitle(nameversion); }

    /* Initializing client */
    @Mod.EventHandler
    public void PastInitialize(FMLInitializationEvent event) {
        System.out.println("[" + nameversion + "]" + " " + "Starting up and initializing!");

        settingsManager = new SettingsManager();
        System.out.println("[" + nameversion + "]" + " " + "Settings Initialized!");

        moduleManager = new ModuleManager();
        System.out.println("[" + nameversion + "]" + " " + "Modules Initialized!");

        commandManager = new CommandManager();
        System.out.println("[" + nameversion + "]" + " " + "Commands Initialized!");

        friendsManager = new FriendsManager();
        System.out.println("[" + nameversion + "]" + " " + "Friends Initialized!");

        customFontRenderer = new CustomFontRenderer(new Font("Arial", 0, 18), true, false);
        System.out.println("[" + nameversion + "]" + " " + "Custom Font Renderer Initialized! (Author 086)");

        hudComponentManager = new HudComponentManager();
        System.out.println("[" + nameversion + "]" + " " + "HUD Components Initialized!");

        clickGUI = new ClickGUI();
        System.out.println("[" + nameversion + "]" + " " + "ClickGUI Initialized!");

        hudEditor = new HudEditor();
        System.out.println("[" + nameversion + "]" + " " + "HUDEditor Initialized!");

        configUtil = new ConfigUtil();
        System.out.println("[" + nameversion + "]" + " " + "Config Initialized!");

        CommandManager.init();
        MinecraftForge.EVENT_BUS.register(new CommandManager());
        MinecraftForge.EVENT_BUS.register(this);

        System.out.println("[" + nameversion + "]" + " " + "Client is ready to go!");
    }

    /* Toggling modules on key press */
    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        for (Module m : moduleManager.getModules()) {
            if (Keyboard.isKeyDown(m.getKey())) {
                m.toggle();
            }
        }
    }

    /* Drawing HUD Componenets */
    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (Minecraft.getMinecraft().player == null || Minecraft.getMinecraft().world == null) {
            return;
        }

        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            for (HudComponent hudComponent : Past.hudComponentManager.getHudComponents()) {
                if (hudComponent.isEnabled()) {
                    hudComponent.render(event.getPartialTicks());
                }
            }
        }
    }
}