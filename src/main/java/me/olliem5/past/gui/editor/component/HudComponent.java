package me.olliem5.past.gui.editor.component;

import net.minecraft.client.Minecraft;

public class HudComponent {
    protected Minecraft mc = Minecraft.getMinecraft();

    private String name;
    private boolean enabled;
    private boolean dragging;
    private int x;
    private int y;
    private int width;
    private int height;
    private int dragX;
    private int dragY;

    /**
     * TODO: Make all the hud components start the render in the top left corner
     *
     * Also, why do we need a width and height?
     */

    public HudComponent(String name, boolean enabled, int x, int y, int width, int height) {
        this.name = name;
        this.enabled = enabled;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isDragging() {
        return dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDragX() {
        return dragX;
    }

    public void setDragX(int dragX) {
        this.dragX = dragX;
    }

    public int getDragY() {
        return dragY;
    }

    public void setDragY(int dragY) {
        this.dragY = dragY;
    }

    public boolean isMouseOnComponent(int x, int y) {
        if (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height) {
            return true;
        } else {
            return false;
        }
    }

    public void updatePosition(int mouseX, int mouseY) {
        if (this.dragging) {
            this.setX(mouseX - getDragX());
            this.setY(mouseY - getDragY());
        }
    }

    public void render() {}
}
