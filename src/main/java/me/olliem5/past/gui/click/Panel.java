package me.olliem5.past.gui.click;

import me.olliem5.past.Past;
import me.olliem5.past.gui.click.components.ModuleButton;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.util.ColourUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;

public class Panel {
    protected Minecraft mc = Minecraft.getMinecraft();

    public ArrayList<Component> components;
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
    Category cat;

    //For ClickGUI.
    public Panel(String title, int x, int y, int width, int height, Category cat) {
        this.components = new ArrayList<>();
        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dragX = 0;
        this.isSettingOpen = true;
        this.isDragging = false;
        this.open = true;
        this.cat = cat;
        int tY = this.height;

        for (Module mod : Past.moduleManager.getModules()) {
            if (mod.getCategory() == cat) {
                ModuleButton modButton = new ModuleButton(mod, this, tY);
                this.components.add(modButton);
                tY += 12;
            }
        }
    }

    //For HudEditor.
    public Panel(String title, int x, int y, int width, int height) {
        this.components = new ArrayList<>();
        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dragX = 0;
        this.isSettingOpen = true;
        this.isDragging = false;
        this.open = true;

        //Add the HUD toggle buttons here.
    }

    //Initial header of module category.
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (Past.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean()) {
            Gui.drawRect(x, y, x + width, y + height, ColourUtil.getMultiColour().getRGB());
        } else {
            Gui.drawRect(x, y, x + width, y + height, 0xFF222222);
        }

        if (Past.settingsManager.getSettingID("ClickGUICustomFont").getValBoolean()) {
            Past.customFontRenderer.drawStringWithShadow(title, x + 2, y + height / 2 - mc.fontRenderer.FONT_HEIGHT / 2, -1);
        } else {
            mc.fontRenderer.drawStringWithShadow(title, x + 2, y + height / 2 - mc.fontRenderer.FONT_HEIGHT / 2, -1);
        }

        //Rendering components when the GUI is opened.
        if (this.open && !this.components.isEmpty()) {
            for (Component component : components) { component.renderComponent(); }
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
        for (Component component : components) { component.closeAllSub(); }
    }

    public ArrayList<Component> getComponents() { return components; }

    public int getWidth() { return width; }

    public void setDragging(boolean drag) { this.isDragging = drag; }

    public boolean isOpen() { return open; }
    public void setOpen(boolean open) { this.open = open; }

    public int getX() { return x; }
    public int getY() { return y; }

    public void setX(int newX) {
        this.x = newX;

        if (Past.configUtil != null) { try { Past.configUtil.saveGuiPanels(); } catch (Exception e) {} }
    }

    public void setY(int newY) {
        this.y = newY;

        if (Past.configUtil != null) { try { Past.configUtil.saveGuiPanels(); } catch (Exception e) {} }
    }

    public Category getCategory() { return cat; }
}
