package me.olliem5.past;

import me.olliem5.past.gui.editor.HudEditor;
import me.olliem5.past.module.Module;
import me.olliem5.past.module.ModuleManager;
import me.olliem5.past.gui.click.ClickGUI;
import me.olliem5.past.settings.SettingsManager;
import me.olliem5.past.util.ConfigUtil;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import me.olliem5.past.command.CommandManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

@Mod(
        name = Past.name,
        modid = Past.modid,
        version = Past.version
)

public class Past {
    public static final String name = "Past Utility Mod";
    public static final String modid = "past";
    public static final String version = "1.1";
    public static final String author = "olliem5";
    public static final String github = "https://github.com/olliem5/past";
    public static final String appid = "754509326902886411";
    public static String prefix = ".";
    public static String nameversion = name + " " + version;
    public static int prefixchatkey = Keyboard.KEY_PERIOD; //TODO: Sync up with config and the prefix string

    public static SettingsManager settingsManager;
    public static ModuleManager moduleManager;
    public static CommandManager commandManager;
    public static ClickGUI clickGUI;
    public static HudEditor hudEditor;
    public static ConfigUtil configUtil;

    @Mod.EventHandler
    public void PastPreStartup(FMLPreInitializationEvent event) { Display.setTitle(nameversion); }

    /* Initializing client */
    @Mod.EventHandler
    public void PastStartup(FMLInitializationEvent event) {
        System.out.println("[" + nameversion + "]" + " " + "Starting up and initializing!");

        settingsManager = new SettingsManager();
        System.out.println("[" + nameversion + "]" + " " + "Settings Initialized!");

        moduleManager = new ModuleManager();
        System.out.println("[" + nameversion + "]" + " " + "Modules Initialized!");

        commandManager = new CommandManager();
        System.out.println("[" + nameversion + "]" + " " + "Commands Initialized!");

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
}