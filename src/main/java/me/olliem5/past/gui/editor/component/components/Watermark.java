package me.olliem5.past.gui.editor.component.components;

import me.olliem5.past.Past;
import me.olliem5.past.gui.editor.component.HudComponent;
import me.olliem5.past.util.text.StringUtil;

public class Watermark extends HudComponent {
    public Watermark() {
        super("Watermark", boxWidth);
    }

    private static String renderText = Past.nameversion;

    private static int boxWidth = StringUtil.getStringWidth(renderText);

    public void render(float ticks) {
        mc.fontRenderer.drawStringWithShadow(renderText, getX(), getY(), -1);
    }
}
