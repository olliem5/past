package me.olliem5.past.gui.editor.screen.element;

import me.olliem5.past.Past;
import me.olliem5.past.gui.click.components.BooleanSwitch;
import me.olliem5.past.gui.click.components.DoubleSlider;
import me.olliem5.past.gui.click.components.IntegerSlider;
import me.olliem5.past.gui.click.components.ModeChanger;
import me.olliem5.past.gui.editor.component.HudComponent;
import me.olliem5.past.gui.editor.screen.Element;
import me.olliem5.past.gui.editor.screen.HudPanel;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.colour.ColourUtil;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;

public class HudButton extends Element {
    private ArrayList<Element> subelements;
    public HudComponent comp;
    public HudPanel parent;
    public int offset;
    private boolean open;

    public HudButton(HudComponent comp, HudPanel parent, int offset) {
        this.comp = comp;
        this.parent = parent;
        this.offset = offset;
        this.subelements = new ArrayList<>();
        this.open = false;
        int opY = offset + 12;

        if (Past.settingsManager.getSettingsHudComponent(comp) != null) {
            for (Setting setting : Past.settingsManager.getSettingsHudComponent(comp)) {
                if (setting.getType() == "hudboolean") {
                    this.subelements.add(new HudBooleanSwitch(setting, this, opY));
                    opY += 12;
                }
            }
        }
    }

    /**
     * TODO: Make these settings in the HudEditor module
     */

    @Override
    public void renderElement() {
        if (Past.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean() && this.comp.isEnabled()) {
            Gui.drawRect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 12 + this.offset, ColourUtil.getMultiColour().getRGB());
        } else if (this.comp.isEnabled()) {
            Gui.drawRect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 12 + this.offset, 0xFF222222);
        } else {
            Gui.drawRect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 12 + this.offset, 0xFF111111);
        }

        if (Past.settingsManager.getSettingID("ClickGUICustomFont").getValBoolean()) {
            Past.customFontRenderer.drawStringWithShadow(this.comp.getName(), parent.getX() + 2, (parent.getY() + offset + 2), -1);
        } else {
            mc.fontRenderer.drawStringWithShadow(this.comp.getName(), parent.getX() + 2, (parent.getY() + offset + 2), -1);
        }

        if (this.subelements.size() > 0) {
            if (!this.isOpen()) {
                if (Past.settingsManager.getSettingID("ClickGUICustomFont").getValBoolean()) {
                    Past.customFontRenderer.drawStringWithShadow("+", parent.getX() + parent.getWidth() - 10, (parent.getY() + offset + 2), -1);
                } else {
                    mc.fontRenderer.drawStringWithShadow("+", parent.getX() + parent.getWidth() - 10, (parent.getY() + offset + 2), -1);
                }
            } else if (this.isOpen()) {
                if (Past.settingsManager.getSettingID("ClickGUICustomFont").getValBoolean()) {
                    Past.customFontRenderer.drawStringWithShadow("-", parent.getX() + parent.getWidth() - 10, (parent.getY() + offset + 2), -1);
                } else {
                    mc.fontRenderer.drawStringWithShadow("-", parent.getX() + parent.getWidth() - 10, (parent.getY() + offset + 2), -1);
                }
            }
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
    public void updateElement(int mouseX, int mouseY) {
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
