package me.olliem5.past.gui.click.components;

import me.olliem5.past.Past;
import me.olliem5.past.gui.click.Component;
import me.olliem5.past.util.ColourUtil;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

public class KeybindListener extends Component {
    private boolean isBinding;
    private ModuleButton parent;
    private int offset;
    private int x;
    private int y;

    //TODO: Make it set listening to false when escape is pressed & when a default Minecraft gui like the advancements tab (bound to L) is pressed it will not display it if a module is bound to it....?

    public KeybindListener(ModuleButton parent, int offset) {
        this.parent = parent;
        this.x = parent.parent.getX() + parent.parent.getWidth();
        this.y = parent.parent.getY() + parent.offset;
        this.offset = offset;
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(parent.parent.getX() + 80, this.parent.parent.getY() -12 + this.offset, parent.parent.getX() + parent.parent.getWidth() + parent.parent.getWidth(), this.parent.parent.getY() + this.offset, 0xFF111111);

        if (isBinding) {
            if (Past.settingsManager.getSettingID("ClickGUICustomFont").getValBoolean()) {
                Past.customFontRenderer.drawStringWithShadow("Listening" + ColourUtil.gray + " " + "...", parent.parent.getX() + 82, (parent.parent.getY() + this.offset - 10), -1);
            } else {
                mc.fontRenderer.drawStringWithShadow("Listening" + ColourUtil.gray + " " + "...", parent.parent.getX() + 82, (parent.parent.getY() + this.offset - 10), -1);
            }
        }
        else {
            if (Past.settingsManager.getSettingID("ClickGUICustomFont").getValBoolean()) {
                Past.customFontRenderer.drawStringWithShadow("Bind" + ColourUtil.gray + " " + Keyboard.getKeyName(this.parent.mod.getKey()), parent.parent.getX() + 82, (parent.parent.getY() + this.offset - 10), -1);
            } else {
                mc.fontRenderer.drawStringWithShadow("Bind" + ColourUtil.gray + " " + Keyboard.getKeyName(this.parent.mod.getKey()), parent.parent.getX() + 82, (parent.parent.getY() + this.offset - 10), -1);
            }
        }
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.y = parent.parent.getY() -12 + this.offset;
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
            if (Keyboard.isKeyDown(Keyboard.KEY_DELETE)) { //If the key pressed is the delete key, it will delete the current keybind (if there is one set)
                this.parent.mod.setKey(Keyboard.KEY_NONE);
                this.isBinding = false;
            } else if (Keyboard.isKeyDown(Keyboard.KEY_BACK)) { //If the key pressed is the backspace key, it will delete the current keybind (if there is one set)
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
