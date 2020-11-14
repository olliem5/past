package me.olliem5.past.module;

import me.olliem5.past.module.modules.combat.*;
import me.olliem5.past.module.modules.movement.*;
import me.olliem5.past.module.modules.player.*;
import me.olliem5.past.module.modules.render.*;
import me.olliem5.past.module.modules.misc.*;
import me.olliem5.past.module.modules.chat.*;
import me.olliem5.past.module.modules.core.*;

import java.util.ArrayList;

public class ModuleManager {
    public ArrayList<Module> modules = new ArrayList<>();

    public ModuleManager() {
        /* Chat */
        modules.add(new ChatSuffix());
        modules.add(new PrefixChat());
        modules.add(new Spammer());

        /* Combat */
        modules.add(new AutoTotem());
        modules.add(new BonBedAura());
        modules.add(new AutoCrystal());
        modules.add(new Surround());

        /* Misc */
        modules.add(new DiscordRPC());
        modules.add(new EntityAlert());
        modules.add(new FakePlayer());
        modules.add(new AutoBuilder());
        modules.add(new MCF());

        /* Movement */
        modules.add(new Sprint());
        modules.add(new HoleTP());
        modules.add(new Step());
        modules.add(new Flight());
        modules.add(new Timer());
        modules.add(new Velocity());
        modules.add(new NoSlow());

        /* Player */
        modules.add(new WeaknessMsg());
        modules.add(new AutoLog());
        modules.add(new FastUse());
        modules.add(new Blink());
        modules.add(new XCarry());
        modules.add(new PortalGodMode());

        /* Render */
        modules.add(new NoWeather());
        modules.add(new EntityESP());
        modules.add(new ViewModel());
        modules.add(new SkyColour());
        modules.add(new Fullbright());

        /* Core */
        modules.add(new ClickGUI());
        modules.add(new HUD());
        modules.add(new HudEditor());
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}