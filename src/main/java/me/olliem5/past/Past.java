package me.olliem5.past;

import me.olliem5.past.gui.ingame.PastHUD;
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
    public static final String version = "1.0";
    public static final String appid = "754509326902886411";
    public static String prefix = ".";
    public static int prefixchatkey = Keyboard.KEY_PERIOD; //TODO: Sync up with config and the prefix string
    public static String nameversion = name + " " + version;

    public static ModuleManager moduleManager;
    public static SettingsManager settingsManager;
    public static CommandManager commandManager;
    public static ClickGUI clickGUI;
    public static PastHUD pastHUD;
    public static ConfigUtil configUtil;

    @Mod.EventHandler
    public void PastPreStartup(FMLPreInitializationEvent event) { Display.setTitle(nameversion); }

    /* Initializing client */
    @Mod.EventHandler
    public void PastStartup(FMLInitializationEvent event) {
        System.out.println("[" + nameversion + "]" + " " + "Starting up and initializing!");

        settingsManager = new SettingsManager();
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        clickGUI = new ClickGUI();
        pastHUD = new PastHUD();
        configUtil = new ConfigUtil();

        CommandManager.init();
        MinecraftForge.EVENT_BUS.register(new CommandManager());
        MinecraftForge.EVENT_BUS.register(this);

        System.out.println("[" + nameversion + "]" + " " + "Initialized and ready to go!");
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
//    @Mod.Instance
//    private static Past INSTANCE;
//    public Past() { INSTANCE = this; }
//    public static Past getInstance() { return INSTANCE; }
}