package de.Hero.clickgui.util;

import java.awt.Color;

//Deine Imports
import me.ollieobama.past.Past;

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class ColorUtil {
	
	public static Color getClickGUIColor(){
		return new Color((int)Past.settingsManager.getSettingByName("GuiRed").getValDouble(), (int) Past.settingsManager.getSettingByName("GuiGreen").getValDouble(), (int)Past.settingsManager.getSettingByName("GuiBlue").getValDouble());
	}
}
