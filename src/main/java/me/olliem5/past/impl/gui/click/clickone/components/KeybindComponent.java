package me.olliem5.past.impl.gui.click.clickone.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.olliem5.past.impl.gui.click.Component;
import me.olliem5.past.api.util.render.text.FontUtil;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

public class KeybindComponent extends Component {
    private boolean isBinding;
    private ModuleButton parent;
    private int offset;
    private int x;
    private int y;

    public KeybindComponent(ModuleButton parent, int offset) {
        this.parent = parent;
        this.x = parent.parent.getX() + parent.parent.getWidth();
        this.y = parent.parent.getY() + parent.offset;
        this.offset = offset;
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(parent.parent.getX() + 80, parent.parent.getY() - 12 + offset, parent.parent.getX() + parent.parent.getWidth() + parent.parent.getWidth(), parent.parent.getY() + offset, 0xFF111111);

        if (isBinding) {
            FontUtil.drawText("Listening" + ChatFormatting.GRAY + " " + "...", parent.parent.getX() + 82, (parent.parent.getY() + offset - 10), -1);
        } else {
            FontUtil.drawText("Bind" + ChatFormatting.GRAY + " " + Keyboard.getKeyName(this.parent.mod.getKey()), parent.parent.getX() + 82, (parent.parent.getY() + offset - 10), -1);
        }
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.y = parent.parent.getY() - 12 + this.offset;
        this.x = parent.parent.getX() + 80;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.isOpen()) {
            this.isBinding = !this.isBinding;
        }
    }

    @Override
    public void keyTyped(char typedChar, int key) {
        if (this.isBinding) {
            if (Keyboard.isKeyDown(Keyboard.KEY_DELETE)) {
                this.parent.mod.setKey(Keyboard.KEY_NONE);
                this.isBinding = false;
            } else if (Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
                this.parent.mod.setKey(Keyboard.KEY_NONE);
                this.isBinding = false;
            } else {
                this.parent.mod.setKey(key);
                this.isBinding = false;
            }
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
