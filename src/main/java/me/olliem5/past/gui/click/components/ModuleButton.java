package me.olliem5.past.gui.click.components;

import me.olliem5.past.Past;
import me.olliem5.past.gui.click.Component;
import me.olliem5.past.gui.click.Panel;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.ColourUtil;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;

public class ModuleButton extends Component {
    private ArrayList<Component> subcomponents;
    public Module mod;
    public Panel parent;
    public int offset;
    private boolean open;

    public ModuleButton(Module mod, Panel parent, int offset) {
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
        this.subcomponents = new ArrayList<>();
        this.open = false;
        int opY = offset + 12;

        if (Past.settingsManager.getSettingsModule(mod) != null) {
            for (Setting setting : Past.settingsManager.getSettingsModule(mod)) {
                if (setting.getType() == "boolean") {
                    this.subcomponents.add(new BooleanSwitch(setting, this, opY));
                    opY += 12;
                }
                if (setting.getType() == "intslider") {
                    this.subcomponents.add(new IntegerSlider(setting, this, opY));
                    opY += 12;
                }
                if (setting.getType() == "mode") {
                    this.subcomponents.add(new ModeChanger(setting, this, opY));
                    opY += 12;
                }
            }
        }
        //Add keybind component to all modules.
        this.subcomponents.add(new KeybindListener(this, opY));
    }

    @Override
    public void renderComponent() {
        if (Past.settingsManager.getSettingName("RainbowGUI").getValBoolean() && this.mod.isToggled()) {
            Gui.drawRect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 12 + this.offset, ColourUtil.getMultiColour().getRGB());
        } else if (this.mod.isToggled()) {
            Gui.drawRect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 12 + this.offset, 0xFF222222);
        } else {
            Gui.drawRect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 12 + this.offset, 0xFF111111);
        }
        mc.fontRenderer.drawStringWithShadow(this.mod.getName(), parent.getX() + 2, (parent.getY() + offset + 2),-1);
        if (this.subcomponents.size() > 1) {
            if (!this.isOpen()) {
                mc.fontRenderer.drawStringWithShadow("+", parent.getX() + parent.getWidth() -10, (parent.getY() + offset + 2),-1);
            } else if (this.isOpen()) {
                mc.fontRenderer.drawStringWithShadow("-", parent.getX() + parent.getWidth() -10, (parent.getY() + offset + 2),-1);
            }
        }

        if (this.open && !this.subcomponents.isEmpty()) {
            for (Component comp : this.subcomponents) { comp.renderComponent(); }
        }
    }

    @Override
    public void closeAllSub() { this.open = false; }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        if (!this.subcomponents.isEmpty()) {
            for (Component comp : this.subcomponents) { comp.updateComponent(mouseX, mouseY); }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        //Left mouse button, when clicked the module the button belongs to is toggled.
        if (isMouseOnButton(mouseX, mouseY) && button == 0) { this.mod.toggle(); }
        //Right mouse button, when clicked the module button will display it's subcomponents specific to the module it belongs to.
        if (isMouseOnButton(mouseX, mouseY) && button == 1) {
            if (!this.isOpen()) {
                parent.closeAllSetting();
                this.setOpen(true);
            } else {
                this.setOpen(false);
            }
        }
        for (Component comp : this.subcomponents) { comp.mouseClicked(mouseX, mouseY, button); }
    }

    @Override
    public void keyTyped(char typedChar, int key) {
        for (Component comp : this.subcomponents) {
            comp.keyTyped(typedChar, key);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (Component comp : this.subcomponents) {
            comp.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        if (x > parent.getX() && x < parent.getX() + 80 && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isOpen() { return open; }
    public void setOpen(boolean open) { this.open = open; }
}
