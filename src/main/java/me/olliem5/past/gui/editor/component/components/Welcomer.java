package me.olliem5.past.gui.editor.component.components;

import me.olliem5.past.Past;
import me.olliem5.past.gui.editor.component.HudComponent;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.colour.ColourUtil;
import me.olliem5.past.util.text.StringUtil;

import java.util.Calendar;

public class Welcomer extends HudComponent {
    public Welcomer() {
        super("Welcomer");
        setWidth(boxWidth);
        setHeight(9);
    }

    Setting lowercase;
    Setting rainbow;
    Setting customfont;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(lowercase = new Setting("Lowercase", "WelcomerLowercase", false, this));
        Past.settingsManager.registerSetting(rainbow = new Setting("Rainbow", "WelcomerRainbow", true, this));
        Past.settingsManager.registerSetting(customfont = new Setting("Custom Font", "WelcomerCustomFont", true, this));
    }

    private static String renderText = getWelcomerMessage() + mc.getSession().getUsername();

    private static int boxWidth = StringUtil.getStringWidth(renderText);

    private static String getWelcomerMessage() {
        final int timeOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (timeOfDay < 12) {
            return "Good Morning" + "," + " ";
        } else if (timeOfDay < 16) {
            return "Good Afternoon" + "," + " ";
        } else if (timeOfDay < 21) {
            return "Good Evening" + "," + " ";
        } else {
            return "Good Night" + "," + " ";
        }
    }

    public void render(float ticks) {
        if (!lowercase.getValBoolean()) {
            if (customfont.getValBoolean()) {
                Past.customFontRenderer.drawStringWithShadow(renderText, getX(), getY(), rainbow.getValBoolean() ? ColourUtil.getMultiColour().getRGB() : -1);
            } else {
                mc.fontRenderer.drawStringWithShadow(renderText, getX(), getY(), rainbow.getValBoolean() ? ColourUtil.getMultiColour().getRGB() : -1);
            }
        } else {
            if (customfont.getValBoolean()) {
                Past.customFontRenderer.drawStringWithShadow(renderText.toLowerCase(), getX(), getY(), rainbow.getValBoolean() ? ColourUtil.getMultiColour().getRGB() : -1);
            } else {
                mc.fontRenderer.drawStringWithShadow(renderText.toLowerCase(), getX(), getY(), rainbow.getValBoolean() ? ColourUtil.getMultiColour().getRGB() : -1);
            }
        }
    }
}
