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
           String paneltitle = Character.toUpperCase(c.name().toLowerCase().charAt(0)) + c.name().toLowerCase().substring(1);
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

           for (Component comp : p.getComponents()) { comp.updateComponent(mouseX, mouseY); }
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
           if (p.isWithinHeader(mouseX, mouseY) && mouseButton == 1) { p.setOpen(!p.isOpen()); }

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
                for (Component component : panel.getComponents()) { component.keyTyped(typedChar, keyCode); }
            }
        }
        //So you are able to close the GUI.
        if (keyCode == 1) { this.mc.displayGuiScreen(null); }
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
