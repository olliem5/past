package me.olliem5.past.module;

import me.olliem5.past.module.modules.chat.Spammer;
import me.olliem5.past.module.modules.combat.AutoCrystal;
import me.olliem5.past.module.modules.combat.AutoTotem;
import me.olliem5.past.module.modules.combat.BonBedAura;
import me.olliem5.past.module.modules.hud.HudEditor;
import me.olliem5.past.module.modules.hud.HUD;
import me.olliem5.past.module.modules.chat.ChatSuffix;
import me.olliem5.past.module.modules.misc.DiscordRPC;
import me.olliem5.past.module.modules.hud.ClickGUI;
import me.olliem5.past.module.modules.misc.EntityAlert;
import me.olliem5.past.module.modules.chat.PrefixChat;
import me.olliem5.past.module.modules.misc.FakePlayer;
import me.olliem5.past.module.modules.movement.HoleTP;
import me.olliem5.past.module.modules.movement.Sprint;
import me.olliem5.past.module.modules.movement.Step;
import me.olliem5.past.module.modules.player.AutoLog;
import me.olliem5.past.module.modules.player.WeaknessMsg;
import me.olliem5.past.module.modules.render.*;

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

        /* HUD */
        modules.add(new ClickGUI());
        modules.add(new HUD());
        modules.add(new HudEditor());

        /* Misc */
        modules.add(new DiscordRPC());
        modules.add(new EntityAlert());
        modules.add(new FakePlayer());

        /* Movement */
        modules.add(new Sprint());
        modules.add(new HoleTP());
        modules.add(new Step());

        /* Player */
        modules.add(new WeaknessMsg());
        modules.add(new AutoLog());

        /* Render */
        modules.add(new NoWeather());
        modules.add(new EntityESP());
        modules.add(new ViewModel());
        modules.add(new SkyColour());
        modules.add(new Fullbright());
    }

    public ArrayList<Module> getModules() { return modules; }

    public Module getModuleByName(String name) { return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null); }
}