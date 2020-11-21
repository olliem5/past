package me.olliem5.past.gui.editor.component.components;

import me.olliem5.past.Past;
import me.olliem5.past.gui.editor.component.HudComponent;

public class Watermark extends HudComponent {
    public Watermark() {
        super("Watermark", false, 2, 2, 10, 10);
    }

    public void render() {
        mc.fontRenderer.drawStringWithShadow(Past.nameversion, getX(), getY(), -1);
    }
}
