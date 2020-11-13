package me.olliem5.past.util;

import me.olliem5.past.Past;
import net.minecraft.client.Minecraft;

import java.util.regex.Pattern;

public class StringUtil {
    public static final Pattern COLOR_CODE_PATTERN = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");
    public static String simpleTranslateColors(String string) { return string.replace("&", "\u00A7"); }
    public static String capitalizeFirstLetter(String string) { return string.substring(0, 1).toUpperCase() + string.substring(1); }
    public static int getStringWidth(String text) { return Minecraft.getMinecraft().fontRenderer.getStringWidth(stripColors(text)); }
    public static int getStringWidthCustomFont(String text) { return Past.customFontRenderer.getStringWidth(stripColors(text)); }
    public static String stripColors(String string) { return COLOR_CODE_PATTERN.matcher(simpleTranslateColors(string)).replaceAll(""); }
}
