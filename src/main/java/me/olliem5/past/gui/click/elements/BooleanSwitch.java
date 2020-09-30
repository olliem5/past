package me.olliem5.past.gui.click.elements;

import me.olliem5.past.Past;
import me.olliem5.past.gui.click.Component;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.ColourUtil;
import net.minecraft.client.gui.Gui;

public class BooleanSwitch extends Component {
    private Setting op;
    private ModuleButton parent;
    private int offset;
    private int x;
    private int y;

    public BooleanSwitch(Setting op, ModuleButton parent, int offset) {
        this.op = op;
        this.parent = parent;
        this.x = parent.parent.getX() + parent.parent.getWidth();
        this.y = parent.parent.getY() + parent.offset;
        this.offset = offset;
    }

    @Override
    public void renderComponent() {
        if (Past.settingsManager.getSettingName("RainbowGUI").getValBoolean() && this.op.getValBoolean()) {
            Gui.drawRect(parent.parent.getX() + 80, this.parent.parent.getY() -12 + this.offset, parent.parent.getX() + parent.parent.getWidth() + parent.parent.getWidth(), this.parent.parent.getY() + this.offset, ColourUtil.getMultiColour().getRGB());
        } else if (this.op.getValBoolean()) {
            Gui.drawRect(parent.parent.getX() + 80, this.parent.parent.getY() -12 + this.offset, parent.parent.getX() + parent.parent.getWidth() + parent.parent.getWidth(), this.parent.parent.getY() + this.offset, 0xFF222222);
        } else {
            Gui.drawRect(parent.parent.getX() + 80, this.parent.parent.getY() -12 + this.offset, parent.parent.getX() + parent.parent.getWidth() + parent.parent.getWidth(), this.parent.parent.getY() + this.offset, 0xFF111111);
        }
        mc.fontRenderer.drawStringWithShadow(this.op.getName(), parent.parent.getX() + 82, (parent.parent.getY() + this.offset -10), -1);
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.y = parent.parent.getY() -12 + this.offset;
        this.x = parent.parent.getX() + 80;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.isOpen()) {
            this.op.setValBoolean(!op.getValBoolean());
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        if (x > this.x && x < this.x + 80 && y > this.y && y < this.y + 12) {
            return true;
        } else {
            return false;
        }
    }
}