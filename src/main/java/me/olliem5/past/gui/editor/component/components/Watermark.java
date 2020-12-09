package me.olliem5.past.gui.editor.component.components;

import me.olliem5.past.Past;
import me.olliem5.past.gui.editor.component.HudComponent;
import me.olliem5.past.util.text.StringUtil;

public class Watermark extends HudComponent {
    public Watermark() {
        super("Watermark");

        setWidth(StringUtil.getStringWidth(Past.nameversion));
        setHeight(mc.fontRenderer.FONT_HEIGHT);
    }

    public void render(float ticks) {
        mc.fontRenderer.drawStringWithShadow(Past.nameversion, getX(), getY(), -1);
    }
}
