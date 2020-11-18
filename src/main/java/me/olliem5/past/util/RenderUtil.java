package me.olliem5.past.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

public class RenderUtil {
    protected static Minecraft mc = Minecraft.getMinecraft();

    public static void prepareGL() {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glLineWidth(2.5f);
    }

    public static void releaseGL() {
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static AxisAlignedBB generateBB(long x,long y ,long z) {
        BlockPos blockPos = new BlockPos(x,y,z);
        final AxisAlignedBB bb = new AxisAlignedBB(blockPos.getX() - mc.getRenderManager().viewerPosX,
                blockPos.getY() - mc.getRenderManager().viewerPosY, blockPos.getZ() - mc.getRenderManager().viewerPosZ,
                blockPos.getX() + 1 - mc.getRenderManager().viewerPosX,
                blockPos.getY() + (1) - mc.getRenderManager().viewerPosY,
                blockPos.getZ() + 1 - mc.getRenderManager().viewerPosZ);
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

//    public static void drawBoxBlockPos(BlockPos blockPos, float r, float g, float b, float a) {
//        AxisAlignedBB boxBlock = new AxisAlignedBB(blockPos.getX() - Minecraft.getMinecraft().getRenderManager().viewerPosX, blockPos.getY() - Minecraft.getMinecraft().getRenderManager().viewerPosY, blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().viewerPosZ, blockPos.getX() + 1 - Minecraft.getMinecraft().getRenderManager().viewerPosX, blockPos.getY() + 1 - Minecraft.getMinecraft().getRenderManager().viewerPosY, blockPos.getZ() + 1 - Minecraft.getMinecraft().getRenderManager().viewerPosZ);
//        drawBox(boxBlock, r, g, b, a);
//    }
//
//    public static void drawBoxOutlineBlockPos(BlockPos blockPos, float r, float g, float b, float a) {
//        AxisAlignedBB boxOutlineBlock = new AxisAlignedBB(blockPos.getX() - Minecraft.getMinecraft().getRenderManager().viewerPosX, blockPos.getY() - Minecraft.getMinecraft().getRenderManager().viewerPosY, blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().viewerPosZ, blockPos.getX() + 1 - Minecraft.getMinecraft().getRenderManager().viewerPosX, blockPos.getY() + 1 - Minecraft.getMinecraft().getRenderManager().viewerPosY, blockPos.getZ() + 1 - Minecraft.getMinecraft().getRenderManager().viewerPosZ);
//        drawBoxOutline(boxOutlineBlock, r, g, b, a);
//    }
//
//    public static void drawOutlineBlockPos(BlockPos blockPos, float r, float g, float b, float a) {
//        AxisAlignedBB boxOutline = new AxisAlignedBB(blockPos.getX() - Minecraft.getMinecraft().getRenderManager().viewerPosX, blockPos.getY() - Minecraft.getMinecraft().getRenderManager().viewerPosY, blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().viewerPosZ, blockPos.getX() + 1 - Minecraft.getMinecraft().getRenderManager().viewerPosX, blockPos.getY() + 1 - Minecraft.getMinecraft().getRenderManager().viewerPosY, blockPos.getZ() + 1 - Minecraft.getMinecraft().getRenderManager().viewerPosZ);
//        drawOutline(boxOutline, r, g, b, a);
//    }
}
