package me.olliem5.past.impl.gui.editor.component.components;

import me.olliem5.past.Past;
import me.olliem5.past.impl.gui.editor.component.HudComponent;

public class Watermark extends HudComponent {
    public Watermark() {
        super("Watermark");

        setWidth(mc.fontRenderer.getStringWidth(Past.nameversion));
        setHeight(mc.fontRenderer.FONT_HEIGHT);
    }

    public void render(float ticks) {
        mc.fontRenderer.drawStringWithShadow(Past.nameversion, getX(), getY(), -1);
    }
}
