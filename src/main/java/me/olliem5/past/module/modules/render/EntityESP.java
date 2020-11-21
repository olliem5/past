package me.olliem5.past.module.modules.render;

import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class EntityESP extends Module {
    public EntityESP() {
        super("EntityESP", "Highlights loaded entities", Category.RENDER);
    }

    /**
     * TODO: Actually make this good lol
     */

    @EventHandler
    public Listener<RenderWorldLastEvent> listener = new Listener<>(event -> {
        if (mc.world == null) return;

        for (Entity entity : mc.world.getLoadedEntityList()) {
            if (entity != mc.player) {
                entity.setGlowing(true);
            }
        }
    });

    @Override
    public void onDisable() {
        for (Entity entity : mc.world.getLoadedEntityList()) {
            if (entity != mc.player) {
                entity.setGlowing(false);
            }
        }
    }
}
