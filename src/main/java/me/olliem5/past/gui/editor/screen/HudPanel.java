package me.olliem5.past.gui.editor.screen;

import me.olliem5.past.Past;
import me.olliem5.past.gui.editor.component.HudComponent;
import me.olliem5.past.gui.editor.screen.element.HudButton;
import me.olliem5.past.util.colour.ColourUtil;
import me.olliem5.past.util.module.GUIColourUtil;
import me.olliem5.past.util.text.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;

public class HudPanel {
    protected Minecraft mc = Minecraft.getMinecraft();

    public ArrayList<Element> elements;
    public String title;
    public int x;
    public int y;
    public int width;
    public int height;
    public boolean isSettingOpen;
    private boolean isDragging;
    private boolean open;
    public int dragX;
    public int dragY;
    public int tY;

    public HudPanel(String title, int x, int y, int width, int height) {
        this.elements = new ArrayList<>();
        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dragX = 0;
        this.isSettingOpen = true;
        this.isDragging = false;
        this.open = true;
        int tY = this.height;

        for (HudComponent hudComponent : Past.hudComponentManager.getHudComponents()) {
            HudButton hudButton = new HudButton(hudComponent, this, tY);
            this.elements.add(hudButton);
            tY += 15;
        }
        this.refresh();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (Past.settingsManager.getSettingID("HudEditorRainbow").getValBoolean()) {
            Gui.drawRect(x - 1, y - 1, x + width + 1, y + height + 1, ColourUtil.getMultiColour().getRGB());
        } else {
            Gui.drawRect(x - 1, y - 1, x + width + 1, y + height + 1, GUIColourUtil.getHudEditorColour());
        }
        Gui.drawRect(x, y, x + width, y + height, 0x75101010);

        if (Past.settingsManager.getSettingID("HudEditorCustomFont").getValBoolean()) {
            Past.customFontRenderer.drawStringWithShadow(title, x + 2 + width / 2 - StringUtil.getStringWidthCustomFont(title) / 2, y + height / 2 - Past.customFontRenderer.getHeight() / 2, -1);
        } else {
            mc.fontRenderer.drawStringWithShadow(title, x + 2 + width / 2 - StringUtil.getStringWidth(title) / 2, y + height / 2 - mc.fontRenderer.FONT_HEIGHT / 2, -1);
        }

        if (this.open && !this.elements.isEmpty()) {
            for (Element element : elements) {
                element.renderElement();
            }
        }
    }

    public void refresh() {
        int off = this.height;
        for (Element element : elements) {
            element.setOff(off);
            off += element.getHeight();
        }
    }

    public boolean isWithinHeader(int x, int y) {
        if (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height) {
            return true;
        } else {
            return false;
        }
    }

    public void updatePosition(int mouseX, int mouseY) {
        if (this.isDragging) {
            this.setX(mouseX - dragX);
            this.setY(mouseY - dragY);
        }
    }

    public void closeAllSetting() {
        for (Element elem : elements) {
            elem.closeAllSub();
        }
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public int getWidth() {
        return width;
    }

    public void setDragging(boolean drag) {
        this.isDragging = drag;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY) {
        this.y = newY;
    }
}
