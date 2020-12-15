package me.olliem5.past.util.render;

import me.olliem5.past.Past;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

public class RenderUtil {
    protected static Minecraft mc = Minecraft.getMinecraft();

    public static void prepareGL() {
        GL11.glPushMatrix();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(Past.settingsManager.getSettingID("RenderLineWidth").getValueInt());
    }

    public static void releaseGL() {
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public static AxisAlignedBB generateBB(long x, long y, long z) {
        BlockPos blockPos = new BlockPos(x, y, z);
        final AxisAlignedBB bb = new AxisAlignedBB
                (
                blockPos.getX() - mc.getRenderManager().viewerPosX,
                blockPos.getY() - mc.getRenderManager().viewerPosY,
                blockPos.getZ() - mc.getRenderManager().viewerPosZ,
                blockPos.getX() + 1 - mc.getRenderManager().viewerPosX,
                blockPos.getY() + (1) - mc.getRenderManager().viewerPosY,
                blockPos.getZ() + 1 - mc.getRenderManager().viewerPosZ
                );
        return bb;
    }

    public static void drawBox(AxisAlignedBB bb, float r, float g, float b, float a) {
        prepareGL();
        RenderGlobal.renderFilledBox(bb, r, g, b, a);
        releaseGL();
    }

    public static void drawBoxOutline(AxisAlignedBB bb, float r, float g, float b, float a) {
        prepareGL();
        RenderGlobal.renderFilledBox(bb, r, g, b, a);
        RenderGlobal.drawSelectionBoundingBox(bb, r, g, b, a * 1.5F);
        releaseGL();
    }

    public static void drawOutline(AxisAlignedBB bb, float r, float g, float b, float a) {
        prepareGL();
        RenderGlobal.drawSelectionBoundingBox(bb, r, g, b, a * 1.5F);
        releaseGL();
    }
}
