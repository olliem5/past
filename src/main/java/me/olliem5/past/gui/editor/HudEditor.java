package me.olliem5.past.gui.editor;

import me.olliem5.past.gui.click.Component;
import me.olliem5.past.gui.click.Panel;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;

public class HudEditor extends GuiScreen {
    public static ArrayList<Panel> panels;

    public HudEditor() {
        panels = new ArrayList<>();
        int panelX = 10;
        int panelY = 5;
        int panelWidth = 80;
        int panelHeight = 15;

        String paneltitle = "Past HUD (WIP)";
        HudEditor.panels.add(new Panel(paneltitle, panelX, panelY, panelWidth, panelHeight));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        for (Panel p : panels) {
            p.updatePosition(mouseX, mouseY);
            p.drawScreen(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (Panel p : panels) {
            //Left mouse button, puts the panel into dragging mode.
            if (p.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
                p.setDragging(true);
                p.dragX = mouseX - p.getX();
                p.dragY = mouseY - p.getY();
            }
            //Right mouse button, sets the panel to be opened, displaying the module buttons.
            if (p.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
                p.setOpen(!p.isOpen());
            }

            if (p.isOpen() && !p.getComponents().isEmpty()) {
                for (Component component : p.getComponents()) {
                    component.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        for (Panel p : panels) {
            p.setDragging(false);

            if (p.isOpen() && !p.getComponents().isEmpty()) {
                for (Component component : p.getComponents()) { component.mouseReleased(mouseX, mouseY, state); }
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }
}

