package me.olliem5.past.module.modules.render;

import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import net.minecraft.entity.Entity;

public class EntityESP extends Module {
    public EntityESP() {
        super ("EntityESP", "Highlights loaded entities", Category.RENDER);
    }

    public void onRender() {
        if (mc.world == null) { return; }
        for (Entity entity : mc.world.getLoadedEntityList()) {
            if (entity != mc.player) {
                entity.setGlowing(true);
            }
        }
    }

    @Override
    public void onDisable() {
        for (Entity entity : mc.world.getLoadedEntityList()) {
            if (entity != mc.player) {
                entity.setGlowing(false);
            }
        }
    }
}
