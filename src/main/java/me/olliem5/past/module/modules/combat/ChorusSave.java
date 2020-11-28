package me.olliem5.past.module.modules.combat;

import me.olliem5.past.Past;
import me.olliem5.past.event.events.ChorusTeleportEvent;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

public class ChorusSave extends Module {
    public ChorusSave() {
        super("ChorusSave", "Enables surround after you teleport with a chorus fruit", Category.COMBAT);
    }

    /**
     * TODO: Center fix
     */

    @EventHandler
    public Listener<ChorusTeleportEvent> listener = new Listener<>(event -> {
        if (!Past.moduleManager.getModuleByName("Surround").isToggled()) {
            Past.moduleManager.getModuleByName("Surround").toggle();
        }
    });
}
