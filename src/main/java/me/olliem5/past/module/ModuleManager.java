package me.olliem5.past.module;

import me.olliem5.past.module.modules.chat.Spammer;
import me.olliem5.past.module.modules.combat.AutoTotem;
import me.olliem5.past.module.modules.combat.BedAura;
import me.olliem5.past.module.modules.hud.PastHUD;
import me.olliem5.past.module.modules.chat.ChatSuffix;
import me.olliem5.past.module.modules.misc.DiscordRPC;
import me.olliem5.past.module.modules.hud.ClickGUI;
import me.olliem5.past.module.modules.misc.EntityAlert;
import me.olliem5.past.module.modules.chat.PrefixChat;
import me.olliem5.past.module.modules.misc.FakePlayer;
import me.olliem5.past.module.modules.movement.HoleTP;
import me.olliem5.past.module.modules.movement.Sprint;
import me.olliem5.past.module.modules.movement.Step;
import me.olliem5.past.module.modules.player.WeaknessMsg;
import me.olliem5.past.module.modules.render.EntityESP;
import me.olliem5.past.module.modules.render.NoWeather;

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
        modules.add(new BedAura());

        /* HUD */
        modules.add(new ClickGUI());
        modules.add(new PastHUD());

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

        /* Render */
        modules.add(new NoWeather());
        modules.add(new EntityESP());
    }

    public ArrayList<Module> getModules() { return modules; }
}