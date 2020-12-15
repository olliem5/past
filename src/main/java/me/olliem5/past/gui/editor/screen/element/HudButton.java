package me.olliem5.past.gui.editor.screen.element;

import me.olliem5.past.Past;
import me.olliem5.past.gui.editor.component.HudComponent;
import me.olliem5.past.gui.editor.screen.Element;
import me.olliem5.past.gui.editor.screen.HudPanel;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.colour.ColourUtil;
import me.olliem5.past.util.module.GUIColourUtil;
import me.olliem5.past.util.text.FontUtil;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;

public class HudButton extends Element {
    private ArrayList<Element> subelements;
    public HudComponent comp;
    public HudPanel parent;
    public int offset;
    private boolean open;
    private boolean hovered;

    public HudButton(HudComponent comp, HudPanel parent, int offset) {
        this.comp = comp;
        this.parent = parent;
        this.offset = offset;
        this.subelements = new ArrayList<>();
        this.open = false;
        int opY = offset + 15;

        if (Past.settingsManager.getSettingsHudComponent(comp) != null) {
            for (Setting setting : Past.settingsManager.getSettingsHudComponent(comp)) {
                if (setting.getType() == "hudboolean") {
                    this.subelements.add(new HudBooleanComponent(setting, this, opY));
                    opY += 15;
                }
            }
        }
    }

    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
        int opY = this.offset + 15;
        for (final Element elem : this.subelements) {
            elem.setOff(opY);
            opY += 15;
        }
    }

    @Override
    public void renderElement() {
        if (this.comp.isEnabled()) {
            Gui.drawRect(parent.getX() - 1, this.parent.getY() + this.offset, parent.getX() + parent.getWidth() + 1, this.parent.getY() + 15 + this.offset + 1, Past.settingsManager.getSettingID("HudEditorRainbow").getValBoolean() ? ColourUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());
            Gui.drawRect(parent.getX() - 1, this.parent.getY() + this.offset, parent.getX(), this.parent.getY() + 15 + this.offset, Past.settingsManager.getSettingID("HudEditorRainbow").getValBoolean() ? ColourUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());
            Gui.drawRect(parent.getX() + parent.getWidth(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth() + 1, this.parent.getY() + 15 + this.offset, Past.settingsManager.getSettingID("HudEditorRainbow").getValBoolean() ? ColourUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());
            Gui.drawRect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 15 + this.offset, Past.settingsManager.getSettingID("HudEditorRainbow").getValBoolean() ? ColourUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());

            Gui.drawRect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 15 + this.offset, 0x75101010);
        } else {
            Gui.drawRect(parent.getX() - 1, this.parent.getY() + this.offset, parent.getX() + parent.getWidth() + 1, this.parent.getY() + 15 + this.offset + 1, Past.settingsManager.getSettingID("HudEditorRainbow").getValBoolean() ? ColourUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());
            Gui.drawRect(parent.getX() - 1, this.parent.getY() + this.offset, parent.getX(), this.parent.getY() + 15 + this.offset, Past.settingsManager.getSettingID("HudEditorRainbow").getValBoolean() ? ColourUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());
            Gui.drawRect(parent.getX() + parent.getWidth(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth() + 1, this.parent.getY() + 15 + this.offset, Past.settingsManager.getSettingID("HudEditorRainbow").getValBoolean() ? ColourUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());

            Gui.drawRect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 15 + this.offset, 0xFF111111);
        }

        if (Past.settingsManager.getSettingID("HudEditorHoverChange").getValBoolean() && hovered == true) {
            FontUtil.drawText(this.comp.getName(), parent.getX() + 6, parent.getY() + this.offset + 4, -1);
        } else {
            FontUtil.drawText(this.comp.getName(), parent.getX() + 4, parent.getY() + this.offset + 4, -1);
        }

        if (this.subelements.size() > 0) {
            FontUtil.drawText("...", parent.getX() + parent.getWidth() - 10, (parent.getY() + offset + 4), -1);
        }

        if (this.open && !this.subelements.isEmpty()) {
            for (Element elem : this.subelements) {
                elem.renderElement();
            }
        }
    }

    @Override
    public void closeAllSub() {
        this.open = false;
    }

    @Override
    public int getHeight() {
        if (this.open) {
            return 15 * (this.subelements.size() + 1);
        }
        return 15;
    }

    @Override
    public void updateElement(int mouseX, int mouseY) {
        this.hovered = this.isMouseOnButton(mouseX, mouseY);

        if (!this.subelements.isEmpty()) {
            for (Element elem : this.subelements) {
                elem.updateElement(mouseX, mouseY);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && button == 0) {
            this.comp.setEnabled(!comp.isEnabled());
        }

        if (isMouseOnButton(mouseX, mouseY) && button == 1) {
            if (!this.isOpen()) {
                parent.closeAllSetting();
                this.setOpen(true);
            } else {
                this.setOpen(false);
            }
        }

        for (Element elem : this.subelements) {
            elem.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (Element elem : this.subelements) {
            elem.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        if (x > parent.getX() && x < parent.getX() + 80 && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
