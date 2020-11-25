package me.olliem5.past.gui.editor.screen;

import me.olliem5.past.Past;
import me.olliem5.past.gui.editor.component.HudComponent;
import me.olliem5.past.util.colour.ColourUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;

public class HudEditor extends GuiScreen {
    public static ArrayList<HudPanel> hudpanels;

    public HudEditor() {
        hudpanels = new ArrayList<>();
        int hudPanelX = 10;
        int hudPanelY = 5;
        int hudPanelWidth = 80;
        int hudPanelHeight = 15;

        HudEditor.hudpanels.add(new HudPanel("Past Client HUD", hudPanelX, hudPanelY, hudPanelWidth, hudPanelHeight));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (Past.settingsManager.getSettingID("HudEditorBackground").getValBoolean()) {
            drawDefaultBackground();
        }

        for (HudPanel hp : hudpanels) {
            hp.updatePosition(mouseX, mouseY);
            hp.drawScreen(mouseX, mouseY, partialTicks);

            for (Element elem : hp.getElements()) {
                elem.updateElement(mouseX, mouseY);
            }
        }

        for (HudComponent hudComponent : Past.hudComponentManager.getHudComponents()) {
            if (hudComponent.isEnabled()) {
                hudComponent.updatePosition(mouseX,mouseY);
                if (hudComponent.isDragging()) {
                    Gui.drawRect(hudComponent.getX() + -2, hudComponent.getY() + -2, hudComponent.getX() + hudComponent.getWidth() + 2, hudComponent.getY() + hudComponent.getHeight() + 2, ColourUtil.getMultiColour().getRGB());
                }
                Gui.drawRect(hudComponent.getX() + -1, hudComponent.getY() + -1, hudComponent.getX() + hudComponent.getWidth() + 1, hudComponent.getY() + hudComponent.getHeight() + 1, 0xFF111111);
                hudComponent.render(partialTicks);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (HudPanel hp : hudpanels) {
            if (hp.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
                hp.setDragging(true);
                hp.dragX = mouseX - hp.getX();
                hp.dragY = mouseY - hp.getY();
            }

            if (hp.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
                hp.setOpen(!hp.isOpen());
            }

            if (hp.isOpen() && !hp.getElements().isEmpty()) {
                for (Element elem : hp.getElements()) {
                    elem.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }

        for (HudComponent hudComponent : Past.hudComponentManager.getHudComponents()) {
            if (hudComponent.isMouseOnComponent(mouseX, mouseY) && mouseButton == 0 && hudComponent.isEnabled()) {
                hudComponent.setDragging(true);
                hudComponent.setDragX(mouseX - hudComponent.getX());
                hudComponent.setDragY(mouseY - hudComponent.getY());
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        for (HudPanel hp : hudpanels) {
            hp.setDragging(false);

            if (hp.isOpen() && !hp.getElements().isEmpty()) {
                for (Element elem : hp.getElements()) {
                    elem.mouseReleased(mouseX, mouseY, state);
                }
            }
        }

        for (HudComponent hudComponent : Past.hudComponentManager.getHudComponents()) {
            if (hudComponent.isMouseOnComponent(mouseX, mouseY) && hudComponent.isEnabled()) {
                hudComponent.setDragging(false);
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}

