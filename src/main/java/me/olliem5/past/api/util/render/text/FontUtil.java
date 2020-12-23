package me.olliem5.past.api.util.render.text;

import me.olliem5.past.Past;
import net.minecraft.client.Minecraft;

public class FontUtil {

    /**
     * TODO: String width function
     */

    protected static Minecraft mc = Minecraft.getMinecraft();

    public static void drawString(String text, float x, float y, int colour) {
        if (Past.settingsManager.getSettingID("FontFont").getValueString() == "Lato") {
            Past.latoFont.drawString(text, x, y, colour);
        } else if (Past.settingsManager.getSettingID("FontFont").getValueString() == "Verdana") {
            Past.verdanaFont.drawString(text, x, y, colour);
        } else if (Past.settingsManager.getSettingID("FontFont").getValueString() == "Arial") {
            Past.arialFont.drawString(text, x, y, colour);
        } else {
            mc.fontRenderer.drawString(text, (int) x, (int) y, colour);
        }
    }

    public static void drawStringWithShadow(String text, float x, float y, int colour) {
        if (Past.settingsManager.getSettingID("FontFont").getValueString() == "Lato") {
            Past.latoFont.drawStringWithShadow(text, x, y, colour);
        } else if (Past.settingsManager.getSettingID("FontFont").getValueString() == "Verdana") {
            Past.verdanaFont.drawStringWithShadow(text, x, y, colour);
        } else if (Past.settingsManager.getSettingID("FontFont").getValueString() == "Arial") {
            Past.arialFont.drawStringWithShadow(text, x, y, colour);
        } else {
            mc.fontRenderer.drawStringWithShadow(text, (int) x, (int) y, colour);
        }
    }

    public static void drawText(String text, float x, float y, int colour) {
        if (Past.settingsManager.getSettingID("FontShadow").getValBoolean()) {
            drawStringWithShadow(text, x, y, colour);
        } else {
            drawString(text, x, y, colour);
        }
    }

    public static int getFontHeight() {
        if (Past.settingsManager.getSettingID("FontFont").getValueString() == "Lato") {
            return Past.latoFont.getHeight();
        } else if (Past.settingsManager.getSettingID("FontFont").getValueString() == "Verdana") {
            return Past.verdanaFont.getHeight();
        } else if (Past.settingsManager.getSettingID("FontFont").getValueString() == "Arial") {
            return Past.arialFont.getHeight();
        } else {
            return mc.fontRenderer.FONT_HEIGHT;
        }
    }
}
