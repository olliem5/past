package de.Hero.clickgui;

import java.awt.Color;
import java.util.ArrayList;

import me.ollieobama.past.Past;
import net.minecraft.client.gui.Gui;
import de.Hero.clickgui.elements.ModuleButton;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class Panel {
	public String title;
	public double x;
	public double y;
	private double x2;
	private double y2;
	public double width;
	public double height;
	public boolean dragging;
	public boolean extended;
	public boolean visible;
	public ArrayList<ModuleButton> Elements = new ArrayList<>();
	public ClickGUI clickgui;

	/*
	 * Konstrukor
	 */
	public Panel(String ititle, double ix, double iy, double iwidth, double iheight, boolean iextended, ClickGUI parent) {
		this.title = ititle;
		this.x = ix;
		this.y = iy;
		this.width = iwidth;
		this.height = iheight;
		this.extended = iextended;
		this.dragging = false;
		this.visible = true;
		this.clickgui = parent;
		setup();
	}

	/*
	 * Wird in ClickGUI �berschrieben, sodass auch ModuleButtons hinzugef�gt werden k�nnen :3
	 */
	public void setup() {}

	/*
	 * Rendern des Elements.
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if (!this.visible)
			return;

		if (this.dragging) {
			x = x2 + mouseX;
			y = y2 + mouseY;
		}
		
		Color temp = ColorUtil.getClickGUIColor().darker();
		int outlineColor = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 170).getRGB();

		Gui.drawRect((int)x, (int)y, (int)x + (int)width, (int)y + (int)height, 0xff121212);
		if(Past.settingsManager.getSettingByName("Design").getValString().equalsIgnoreCase("New")){
			Gui.drawRect((int)x, (int)y, (int)x + (int)width, (int)y + (int)height, 0xff121212);
			Gui.drawRect((int)x - 2, (int)y, (int)x, (int)y + (int)height, outlineColor);
			FontUtil.drawStringWithShadow(title, x + 2, y + height / 2 - FontUtil.getFontHeight()/2, 0xffefefef);
		}else if(Past.settingsManager.getSettingByName("Design").getValString().equalsIgnoreCase("JellyLike")){
			Gui.drawRect((int)x, (int)y, (int)x + (int)width, (int)y + (int)height, 0xff121212);
			Gui.drawRect((int)x + 4,			(int)y + 2, (int)x + (int)4.3, 		(int)y + (int)height - 2, 0xffaaaaaa);
			Gui.drawRect((int)x - 4 + (int)width, (int)y + 2, (int)x - (int)4.3 + (int)width, (int)y + (int)height - 2, 0xffaaaaaa);
			FontUtil.drawTotalCenteredStringWithShadow(title, x + width / 2, y + height / 2, 0xffefefef);
		}
		
		if (this.extended && !Elements.isEmpty()) {
			double startY = y + height;
			int epanelcolor = Past.settingsManager.getSettingByName("Design").getValString().equalsIgnoreCase("New") ? 0xff232323 : Past.settingsManager.getSettingByName("Design").getValString().equalsIgnoreCase("JellyLike") ? 0xbb151515 : 0;;
			for (ModuleButton et : Elements) {
				if(Past.settingsManager.getSettingByName("Design").getValString().equalsIgnoreCase("New")){
					Gui.drawRect((int)x - 2, (int)startY, (int)x + (int)width, (int)startY + (int)et.height + 1, outlineColor);
				}
				Gui.drawRect((int)x, (int)startY, (int)x + (int)width, (int)startY + (int)et.height + 1, epanelcolor);
				et.x = x + 2;
				et.y = startY;
				et.width = width - 4;
				et.drawScreen(mouseX, mouseY, partialTicks);
				startY += et.height + 1;
			}
			Gui.drawRect((int)x, (int)startY + 1, (int)x + (int)width, (int)startY + 1, epanelcolor);
		
		}
	}

	/*
	 * Zum Bewegen und Extenden des Panels
	 * usw.
	 */
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (!this.visible) {
			return false;
		}
		if (mouseButton == 0 && isHovered(mouseX, mouseY)) {
			x2 = this.x - mouseX;
			y2 = this.y - mouseY;
			dragging = true;
			return true;
		} else if (mouseButton == 1 && isHovered(mouseX, mouseY)) {
			extended = !extended;
			return true;
		} else if (extended) {
			for (ModuleButton et : Elements) {
				if (et.mouseClicked(mouseX, mouseY, mouseButton)) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * Damit das Panel auch losgelassen werden kann 
	 */
	public void mouseReleased(int mouseX, int mouseY, int state) {
		if (!this.visible) {
			return;
		}
		if (state == 0) {
			this.dragging = false;
		}
	}

	/*
	 * HoverCheck
	 */
	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}
}
