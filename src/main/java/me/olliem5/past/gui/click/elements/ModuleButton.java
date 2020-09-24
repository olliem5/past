package me.olliem5.past.gui.click.elements;

import me.olliem5.past.gui.click.Component;
import me.olliem5.past.gui.click.Panel;
import me.olliem5.past.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class ModuleButton extends Component {
    public Module mod;
    public Panel parent;
    public int offset;
    private boolean isHovered;

    public ModuleButton(Module mod, Panel parent, int offset) {
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 12 + this.offset, this.isHovered ? (this.mod.isToggled() ? new Color(0xFF222222).darker().getRGB() : 0xFF222222) : (this.mod.isToggled() ? new Color(14,14,14).getRGB() : 0xFF111111));
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.mod.getName(), parent.getX() + 2, (parent.getY() + offset + 2), this.mod.isToggled() ? 0x999999 : -1);
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.isHovered = isMouseOnButton(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && button == 0) {
            this.mod.toggle();
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        if (x > parent.getX() && x < parent.getX() + 80 && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset) {
            return true;
        } else {
            return false;
        }
    }
}
