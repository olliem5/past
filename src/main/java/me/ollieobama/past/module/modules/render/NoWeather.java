package me.ollieobama.past.module.modules.render;

import me.ollieobama.past.module.Category;
import me.ollieobama.past.module.Module;

public class NoWeather extends Module {
    public NoWeather() {
        super ("NoWeather", "Removes weather from the world", Category.RENDER);
    }

    public void onUpdate() {
        if (mc.world == null) { return; }
        if (mc.world.isRaining()) {
            mc.world.setRainStrength(0);
        }
    }
}
