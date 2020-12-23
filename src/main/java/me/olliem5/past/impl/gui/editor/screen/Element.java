package me.olliem5.past.impl.gui.editor.screen;

import net.minecraft.client.Minecraft;

public class Element {
    protected Minecraft mc = Minecraft.getMinecraft();

    public void renderElement() {}

    public void updateElement(int mouseX, int mouseY) {}

    public void mouseClicked(int mouseX, int mouseY, int button) {}

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {}

    public void keyTyped(char typedChar, int key) {}

    public void closeAllSub() {}

    public void setOff(final int newOff) {}

    public int getHeight() {
        return 0;
    }
}
