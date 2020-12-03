package me.olliem5.past.gui.click.clickone.components;

import me.olliem5.past.Past;
import me.olliem5.past.gui.click.Component;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.colour.ColourUtil;
import net.minecraft.client.gui.Gui;

public class ModeChanger extends Component {
    private Setting op;
    private ModuleButton parent;
    private int offset;
    private int x;
    private int y;
    private int modeIndex;

    public ModeChanger(Setting op, ModuleButton parent, int offset) {
        this.op = op;
        this.parent = parent;
        this.x = parent.parent.getX() + parent.parent.getWidth();
        this.y = parent.parent.getY() + parent.offset;
        this.offset = offset;
        this.modeIndex = 0;
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(parent.parent.getX() + 80, this.parent.parent.getY() - 12 + this.offset, parent.parent.getX() + parent.parent.getWidth() + parent.parent.getWidth(), this.parent.parent.getY() + this.offset, 0xFF111111);

        if (Past.settingsManager.getSettingID("OldClickGUICustomFont").getValBoolean()) {
            Past.customFontRenderer.drawStringWithShadow(this.op.getName() + " " + ColourUtil.gray + this.op.getValueString().toUpperCase(), parent.parent.getX() + 82, (parent.parent.getY() + this.offset - 10), -1);
        } else {
            mc.fontRenderer.drawStringWithShadow(this.op.getName() + " " + ColourUtil.gray + this.op.getValueString().toUpperCase(), parent.parent.getX() + 82, (parent.parent.getY() + this.offset - 10), -1);
        }
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.y = parent.parent.getY() - 12 + this.offset;
        this.x = parent.parent.getX() + 80;
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
        if (x > this.x && x < this.x + 80 && y > this.y && y < this.y + 12) {
            return true;
        } else {
            return false;
        }
    }
}
