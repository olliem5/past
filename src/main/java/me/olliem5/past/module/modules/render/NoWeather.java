package me.olliem5.past.module.modules.render;

import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;

public class NoWeather extends Module {
    public NoWeather() {
        super("NoWeather", "Removes weather from the world", Category.RENDER);
    }

    public void onUpdate() {
        if (mc.world == null) {
            return;
        }
        if (mc.world.isRaining()) {
            mc.world.setRainStrength(0);
        }
    }
}
