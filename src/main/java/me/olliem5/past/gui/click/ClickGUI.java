package me.olliem5.past.gui.click;

import me.olliem5.past.module.Category;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;

public class ClickGUI extends GuiScreen {
   public static ArrayList<Panel> panels;

   public ClickGUI() {
       panels = new ArrayList<>();
       int panelX = 10;
       int panelY = 5;
       int panelWidth = 80;
       int panelHeight = 15;

       for (Category c : Category.values()) {
           String paneltitle = /*ColourManager.underline + "" + */Character.toUpperCase(c.name().toLowerCase().charAt(0)) + c.name().toLowerCase().substring(1);
           ClickGUI.panels.add(new Panel(paneltitle, panelX, panelY, panelWidth, panelHeight, c));
           panelX += 81;
       }
   }

   @Override
   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
       drawDefaultBackground();
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
           if (p.isWithinHeader(mouseX, mouseY) && mouseButton == 0) { //Left mouse button.
               p.setDragging(true); //Puts the panel into dragging mode.
               p.dragX = mouseX - p.getX();
               p.dragY = mouseY - p.getY();
           }
           if (p.isWithinHeader(mouseX, mouseY) && mouseButton == 1) { //Right mouse button.
               p.setOpen(!p.isOpen()); //Set the panel to be opened, displaying modules.
           }
           if (p.isOpen()) {
               if (!p.getComponents().isEmpty()) {
                   for (Component component : p.getComponents()) {
                       component.mouseClicked(mouseX, mouseY, mouseButton);
                   }
               }
           }
       }
   }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        for (Panel panel : panels) {
            if (panel.isOpen() && keyCode != 1) {
                if (!panel.getComponents().isEmpty()) {
                    for (Component component : panel.getComponents()) {
                        component.keyTyped(typedChar, keyCode);
                    }
                }
            }
        }
        if (keyCode == 1) { this.mc.displayGuiScreen(null); } //So you are able to close the GUI.
    }

   @Override
   public void mouseReleased(int mouseX, int mouseY, int state) {
       for (Panel p : panels) {
           p.setDragging(false);
       }
   }

   @Override
   public boolean doesGuiPauseGame() { return false; }
}
