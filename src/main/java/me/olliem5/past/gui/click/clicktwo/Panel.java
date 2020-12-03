package me.olliem5.past.gui.click.clicktwo;

import me.olliem5.past.gui.click.Component;
import me.olliem5.past.module.Category;
import me.olliem5.past.util.colour.ColourUtil;
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
    public int tY;

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
        this.tY = this.height;

//        for (Module mod : Past.moduleManager.getModules()) {
//            if (mod.getCategory() == cat) {
//                ModuleButton modButton = new ModuleButton(mod, this, tY);
//                this.components.add(modButton);
//                tY += 15;
//            }
//        }
//        this.refresh();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (open) {
            Gui.drawRect(x - 1, y - 1, x + width + 1, y + height, ColourUtil.getMultiColour().getRGB());
            Gui.drawRect(x -1, y + tY, x + width + 1,y + tY + 1, ColourUtil.getMultiColour().getRGB());
        } else {
            Gui.drawRect(x -1, y -1, x + width + 1, y + height + 1, ColourUtil.getMultiColour().getRGB());
        }
        Gui.drawRect(x, y, x + width, y + height, 0x75101010);
        mc.fontRenderer.drawStringWithShadow(title, x + 2 + width / 2 - mc.fontRenderer.getStringWidth(title) / 2, y + height / 2 - mc.fontRenderer.FONT_HEIGHT / 2, -1);

        if (this.open && !this.components.isEmpty()) {
            for (Component component : components) {
                component.renderComponent();
            }
        }
    }

    public void refresh() {
        int off = this.height;
        for (Component comp : components) {
            comp.setOff(off);
            off += comp.getHeight();
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
        for (Component component : components) {
            component.closeAllSub();
        }
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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

    public Category getCategory() {
        return cat;
    }
}
