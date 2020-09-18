package me.ollieobama.past.module;

import me.ollieobama.past.module.modules.chat.Spammer;
import me.ollieobama.past.module.modules.combat.AutoTotem;
import me.ollieobama.past.module.modules.hud.PastHUD;
import me.ollieobama.past.module.modules.chat.ChatSuffix;
import me.ollieobama.past.module.modules.misc.DiscordRPC;
import me.ollieobama.past.module.modules.hud.ClickGUI;
import me.ollieobama.past.module.modules.misc.EntityAlert;
import me.ollieobama.past.module.modules.chat.PrefixChat;
import me.ollieobama.past.module.modules.movement.HoleTP;
import me.ollieobama.past.module.modules.movement.Sprint;
import me.ollieobama.past.module.modules.player.WeaknessAlert;
import me.ollieobama.past.module.modules.render.NoWeather;

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

        /* Movement */
        modules.add(new Sprint());
        modules.add(new HoleTP());

        /* Player */
        modules.add(new WeaknessAlert());

        /* Render */
        modules.add(new NoWeather());
    }

    public ArrayList<Module> getModules() { return modules; }
}