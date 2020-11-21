package me.olliem5.past.gui.click.components;

import me.olliem5.past.Past;
import me.olliem5.past.gui.click.Component;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.colour.ColourUtil;
import net.minecraft.client.gui.Gui;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DoubleSlider extends Component {
    private Setting set;
    private ModuleButton parent;
    private int offset;
    private int x;
    private int y;
    private boolean dragging;
    private double sliderWidth;

    public DoubleSlider(Setting value, ModuleButton button, int offset) {
        this.dragging = false;
        this.set = value;
        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(parent.parent.getX() + 80, this.parent.parent.getY() - 12 + this.offset, parent.parent.getX() + parent.parent.getWidth() + parent.parent.getWidth(), this.parent.parent.getY() + this.offset, 0xFF111111);
        if (Past.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean()) {
            Gui.drawRect(parent.parent.getX() + 80, this.parent.parent.getY() - 12 + this.offset, parent.parent.getX() + parent.parent.getWidth() + (int) this.sliderWidth, this.parent.parent.getY() + this.offset, ColourUtil.getMultiColour().getRGB());
        } else {
            Gui.drawRect(parent.parent.getX() + 80, this.parent.parent.getY() - 12 + this.offset, parent.parent.getX() + parent.parent.getWidth() + (int) this.sliderWidth, this.parent.parent.getY() + this.offset, 0xFF222222);
        }

        if (Past.settingsManager.getSettingID("ClickGUICustomFont").getValBoolean()) {
            Past.customFontRenderer.drawStringWithShadow(this.set.getName() + " " + ColourUtil.gray + this.set.getValueDouble(), parent.parent.getX() + 82, (parent.parent.getY() + this.offset - 10), -1);
        } else {
            mc.fontRenderer.drawStringWithShadow(this.set.getName() + " " + ColourUtil.gray + this.set.getValueDouble(), parent.parent.getX() + 82, (parent.parent.getY() + this.offset - 10), -1);
        }
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.y = parent.parent.getY() - 12 + this.offset;
        this.x = parent.parent.getX() + 80;
        double diff = Math.min(80, Math.max(0, mouseX - this.x));
        double min = this.set.getDmin();
        double max = this.set.getDmax();
        this.sliderWidth = 80 * (this.set.getValueDouble() - min) / (max - min);
        if (this.dragging) {
            if (diff == 0) {
                this.set.setValueDouble(this.set.getDmin());
            } else {
                double newValue = roundToPlace(diff / 80 * (max - min) + min, 2);
                this.set.setValueDouble(newValue);
            }
        }
    }

    private static double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.isOpen()) {
            this.dragging = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        this.dragging = false;
    }

    public boolean isMouseOnButton(int x, int y) {
        if (x > this.x && x < this.x + 80 && y > this.y && y < this.y + 12) {
            return true;
        } else {
            return false;
        }
    }
}