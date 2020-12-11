package me.olliem5.past.module;

import me.olliem5.past.module.modules.chat.*;
import me.olliem5.past.module.modules.combat.*;
import me.olliem5.past.module.modules.core.*;
import me.olliem5.past.module.modules.exploit.*;
import me.olliem5.past.module.modules.misc.*;
import me.olliem5.past.module.modules.movement.*;
import me.olliem5.past.module.modules.player.*;
import me.olliem5.past.module.modules.render.*;

import java.util.ArrayList;

public class ModuleManager {
    public ArrayList<Module> modules = new ArrayList<>();

    public ModuleManager() {
        /* Chat */
        modules.add(new ChatSuffix());
        modules.add(new AutoInsult());
        modules.add(new DurabilityWarn());

        /* Combat */
        modules.add(new AutoTotem());
        modules.add(new BonBedAura());
        modules.add(new AutoCrystal());
        modules.add(new Surround());
        modules.add(new FootEXP());
        modules.add(new Criticals());
        modules.add(new KillAura());
        modules.add(new ChorusSave());
        modules.add(new AutoTrap());

        /* Core */
        modules.add(new OldClickGUI());
        modules.add(new HUD());
        modules.add(new HudEditor());
        modules.add(new Render());
        modules.add(new Capes());
        modules.add(new ClickGUI());

        /* Exploit */
        modules.add(new Blink());
        modules.add(new PortalGodMode());
        modules.add(new Timer());
        modules.add(new XCarry());
        modules.add(new BowExploit());
        modules.add(new PacketMine());

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
        modules.add(new Velocity());
        modules.add(new NoSlow());
        modules.add(new ElytraFly());

        /* Player */
        modules.add(new WeaknessAlert());
        modules.add(new AutoLog());
        modules.add(new FastUse());
        modules.add(new NoRotate());
        modules.add(new Burrow());

        /* Render */
        modules.add(new NoWeather());
        modules.add(new EntityESP());
        modules.add(new ViewModel());
        modules.add(new SkyColour());
        modules.add(new Fullbright());
        modules.add(new Time());
        modules.add(new HoleESP());
        modules.add(new BlockHighlight());
        modules.add(new NoRender());
        modules.add(new Trajectories());
        modules.add(new HandProgress());
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}