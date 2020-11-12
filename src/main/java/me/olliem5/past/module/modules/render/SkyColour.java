package me.olliem5.past.module.modules.render;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class SkyColour extends Module {
    public SkyColour() {
        super ("SkyColour", "Changes the colours of the sky, best in nether & low render distance", Category.RENDER);
    }

    Setting red;
    Setting green;
    Setting blue;
    Setting rainbow;
    Setting density;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(red = new Setting("Red", "SkyColourRed", 0, 255, 255, this));
        Past.settingsManager.registerSetting(green = new Setting("Green", "SkyColourGreen", 0, 10, 255, this));
        Past.settingsManager.registerSetting(blue = new Setting("Blue", "SkyColourBlue", 0, 10, 255, this));
        Past.settingsManager.registerSetting(rainbow = new Setting("Rainbow", "SkyColourRainbow", false, this));
        Past.settingsManager.registerSetting(density = new Setting("Density", "SkyColourDensity", 0, 0, 255, this));
    }

    @SubscribeEvent
    public void fogColours(EntityViewRenderEvent.FogColors event) {
        float[] hue = new float[]{(float) (System.currentTimeMillis() % 7500L) / 7500f};
        int rgb = Color.HSBtoRGB(hue[0], 0.8f, 0.8f);
        int r = rgb >> 16 & 255;
        int g = rgb >> 8 & 255;
        int b = rgb & 255;
        if (rainbow.getValBoolean()) {
            event.setRed(r / 255f);
            event.setGreen(g / 255f);
            event.setBlue(b / 255f);
        } else {
            event.setRed(red.getValueInt() / 255f);
            event.setGreen(green.getValueInt() / 255f);
            event.setBlue(blue.getValueInt() / 255f);
        }
    }

    @SubscribeEvent
    public void fogDensity(EntityViewRenderEvent.FogDensity event) {
        event.setDensity(density.getValueInt() / 255f);
        event.setCanceled(true);
    }
}
