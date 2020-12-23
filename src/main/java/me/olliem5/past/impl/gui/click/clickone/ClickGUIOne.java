package me.olliem5.past.impl.gui.click.clickone;

import me.olliem5.past.Past;
import me.olliem5.past.impl.gui.click.Component;
import me.olliem5.past.api.module.Category;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;

public class ClickGUIOne extends GuiScreen {
    public static ArrayList<Panel> panels;

    public ClickGUIOne() {
        panels = new ArrayList<>();
        int panelX = 10;
        int panelY = 5;
        int panelWidth = 80;
        int panelHeight = 15;

        for (Category c : Category.values()) {
            ClickGUIOne.panels.add(new Panel(c.getCategoryName(), panelX, panelY, panelWidth, panelHeight, c));
            panelX += 81;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (Past.settingsManager.getSettingID("OldClickGUIBackground").getValBoolean()) {
            drawDefaultBackground();
        }
        for (Panel p : panels) {
            p.updatePosition(mouseX, mouseY);
            p.drawScreen(mouseX, mouseY, partialTicks);

            for (Component comp : p.getComponents()) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (Panel p : panels) {
            if (p.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
                p.setDragging(true);
                p.dragX = mouseX - p.getX();
                p.dragY = mouseY - p.getY();
            }

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
    protected void keyTyped(char typedChar, int keyCode) {
        for (Panel panel : panels) {
            if (panel.isOpen() && !panel.getComponents().isEmpty() && keyCode != 1) {
                for (Component component : panel.getComponents()) {
                    component.keyTyped(typedChar, keyCode);
                }
            }
        }
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        for (Panel p : panels) {
            p.setDragging(false);

            if (p.isOpen() && !p.getComponents().isEmpty()) {
                for (Component component : p.getComponents()) {
                    component.mouseReleased(mouseX, mouseY, state);
                }
            }
        }
    }

    public static ArrayList<Panel> getPanels() {
        return panels;
    }

    public static Panel getPanelByName(String name) {
        Panel panel = null;
        for (Panel p : getPanels()) {
            if (p.title.equalsIgnoreCase(name)) {
                panel = p;
            }
        }
        return panel;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
