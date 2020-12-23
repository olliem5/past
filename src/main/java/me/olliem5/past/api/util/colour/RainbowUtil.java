package me.olliem5.past.api.util.colour;

import java.awt.*;

public class RainbowUtil {
    public static Color getMultiColour() {
        return Color.getHSBColor((float) (System.currentTimeMillis() % 7500L) / 7500f, 0.8f, 0.8f);
    }
}
