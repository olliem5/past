package me.olliem5.past.impl.gui.editor.component.components;

import me.olliem5.past.impl.gui.editor.component.HudComponent;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

public class Inventory extends HudComponent {
    public Inventory() {
        super("Inventory");

        setWidth(144);
        setHeight(48);
    }

    public void render(float ticks) {
        Gui.drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 0x75101010);

        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();

        for (int i = 0; i < 27; i++) {
            ItemStack itemStack = mc.player.inventory.mainInventory.get(i + 9);

            int offsetX = getX() + (i % 9) * 16;
            int offsetY = getY() + (i / 9) * 16;

            mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, offsetX, offsetY);
            mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, itemStack, offsetX, offsetY, null);
        }

        RenderHelper.disableStandardItemLighting();
        mc.getRenderItem().zLevel = 0.0F;
        GlStateManager.popMatrix();
    }
}
