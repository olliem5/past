package me.olliem5.past.gui.editor.screen;

import me.olliem5.past.Past;
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
        if (Past.settingsManager.getSettingID("ClickGUIBackground").getValBoolean()) {
            drawDefaultBackground();
        }
        for (HudPanel hp : hudpanels) {
            hp.updatePosition(mouseX, mouseY);
            hp.drawScreen(mouseX, mouseY, partialTicks);

            for (Element elem : hp.getElements()) {
                elem.updateElement(mouseX, mouseY);
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
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}

