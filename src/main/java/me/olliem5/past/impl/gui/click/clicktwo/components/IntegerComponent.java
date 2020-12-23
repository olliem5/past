package me.olliem5.past.impl.gui.click.clicktwo.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.olliem5.past.Past;
import me.olliem5.past.impl.gui.click.Component;
import me.olliem5.past.api.setting.Setting;
import me.olliem5.past.api.util.colour.RainbowUtil;
import me.olliem5.past.api.util.colour.GUIColourUtil;
import me.olliem5.past.api.util.render.text.FontUtil;
import net.minecraft.client.gui.Gui;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class IntegerComponent extends Component {
    private Setting set;
    private ModuleButton parent;
    private int offset;
    private int x;
    private int y;
    private boolean dragging;
    private double sliderWidth;

    public IntegerComponent(Setting value, ModuleButton button, int offset) {
        this.dragging = false;
        this.set = value;
        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }

    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(parent.parent.getX() - 1, parent.parent.getY() + offset, parent.parent.getX(), parent.parent.getY() + 15 + offset, Past.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean() ? RainbowUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());
        Gui.drawRect(parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth() + 1, parent.parent.getY() + 15 + offset, Past.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean() ? RainbowUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());
        Gui.drawRect(parent.parent.getX() - 1, parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth() + 1, parent.parent.getY() + offset + 16, Past.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean() ? RainbowUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());

        Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset + 15, 0xFF111111);

        Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + 1, parent.parent.getY() + offset + 15, Past.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean() ? RainbowUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());
        Gui.drawRect(parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth() - 1, parent.parent.getY() + offset + 15, Past.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean() ? RainbowUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());

        Gui.drawRect(parent.parent.getX() + 1, parent.parent.getY() + offset, parent.parent.getX() + (int) sliderWidth - 1, parent.parent.getY() + offset + 15, Past.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean() ? RainbowUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());

        Gui.drawRect(parent.parent.getX() + 1, parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth() -1, parent.parent.getY() + offset + 15, 0x75101010);

        FontUtil.drawText(this.set.getName() + ChatFormatting.GRAY + " " + this.set.getValueInt(), parent.parent.getX() + 4, parent.parent.getY() + offset + 3, -1);
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.y = parent.parent.getY() + this.offset;
        this.x = parent.parent.getX();

        double diff = Math.min(100, Math.max(0, mouseX - this.x));
        int min = this.set.getMin();
        int max = this.set.getMax();
        this.sliderWidth = 100 * (this.set.getValueInt() - min) / (max - min);

        if (this.dragging) {
            if (diff == 0) {
                this.set.setValueInt(this.set.getMin());
            } else {
                int newValue = (int) roundToPlace(diff / 100 * (max - min) + min, 2);
                this.set.setValueInt(newValue);
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
        if (x > this.x && x < this.x + 100 && y > this.y && y < this.y + 15) {
            return true;
        } else {
            return false;
        }
    }
}
