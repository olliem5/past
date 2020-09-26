package me.olliem5.past.module;

import me.olliem5.past.module.modules.chat.Spammer;
import me.olliem5.past.module.modules.combat.AutoTotem;
import me.olliem5.past.module.modules.hud.PastHUD;
import me.olliem5.past.module.modules.chat.ChatSuffix;
import me.olliem5.past.module.modules.misc.DiscordRPC;
import me.olliem5.past.module.modules.hud.ClickGUI;
import me.olliem5.past.module.modules.misc.EntityAlert;
import me.olliem5.past.module.modules.chat.PrefixChat;
import me.olliem5.past.module.modules.misc.FakePlayer;
import me.olliem5.past.module.modules.movement.HoleTP;
import me.olliem5.past.module.modules.movement.Sprint;
import me.olliem5.past.module.modules.player.WeaknessAlert;
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

        /* Player */
        modules.add(new WeaknessAlert());

        /* Render */
        modules.add(new NoWeather());
        modules.add(new EntityESP());
    }

    public ArrayList<Module> getModules() { return modules; }
}