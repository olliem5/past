package me.olliem5.past.gui.click.clicktwo.components;

import me.olliem5.past.Past;
import me.olliem5.past.gui.click.Component;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.colour.ColourUtil;
import me.olliem5.past.util.module.GUIColourUtil;
import me.olliem5.past.util.text.FontUtil;
import net.minecraft.client.gui.Gui;

public class ModeComponent extends Component {
    private Setting op;
    private ModuleButton parent;
    private int offset;
    private int x;
    private int y;
    private int modeIndex;

    public ModeComponent(Setting op, ModuleButton parent, int offset) {
        this.op = op;
        this.parent = parent;
        this.x = parent.parent.getX() + parent.parent.getWidth();
        this.y = parent.parent.getY() + parent.offset;
        this.offset = offset;
        this.modeIndex = 0;
    }

    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(parent.parent.getX() - 1, parent.parent.getY() + offset, parent.parent.getX(), parent.parent.getY() + 15 + offset, Past.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean() ? ColourUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());
        Gui.drawRect(parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth() + 1, parent.parent.getY() + 15 + offset, Past.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean() ? ColourUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());
        Gui.drawRect(parent.parent.getX() - 1, parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth() + 1, parent.parent.getY() + offset + 16, Past.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean() ? ColourUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());

        Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset + 15, 0xFF111111);

        Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + 1, parent.parent.getY() + offset + 15, Past.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean() ? ColourUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());
        Gui.drawRect(parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth() - 1, parent.parent.getY() + offset + 15, Past.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean() ? ColourUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());

        FontUtil.drawText(this.op.getName() + " " + ColourUtil.gray + this.op.getValueString().toUpperCase(), parent.parent.getX() + 4, parent.parent.getY() + offset + 4, -1);
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.y = parent.parent.getY() + this.offset;
        this.x = parent.parent.getX();
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.isOpen()) {
            final int maxIndex = this.op.getModes().size() - 1;
            this.modeIndex++;
            if (this.modeIndex > maxIndex) {
                this.modeIndex = 0;
            }
            this.op.setValueString(this.op.getModes().get(this.modeIndex));
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        if (x > this.x && x < this.x + 100 && y > this.y && y < this.y + 15) {
            return true;
        } else {
            return false;
        }
    }
}
