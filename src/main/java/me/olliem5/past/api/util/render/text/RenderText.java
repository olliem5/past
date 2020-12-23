package me.olliem5.past.api.util.render.text;

import me.olliem5.past.Past;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

/**
 * Modified from Wurst+2
 * https://github.com/TrvsF/wurstplus-two/blob/77b6ac96dbbf840c46e549c8df1b0f600ddc7221/src/main/java/me/travis/wurstplus/wurstplustwo/util/WurstplusRenderUtil.java#L20
 */

public class RenderText {
    protected static Minecraft mc = Minecraft.getMinecraft();

    public static void glBillboard(final float x, final float y, final float z) {
        final float scale = 0.02666667f;
        GlStateManager.translate(x - mc.getRenderManager().renderPosX, y - mc.getRenderManager().renderPosY, z - mc.getRenderManager().renderPosZ);
        GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-mc.player.rotationYaw, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(mc.player.rotationPitch, (mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
    }

    public static void glBillboardDistanceScaled(final float x, final float y, final float z, final EntityPlayer player, final float scale) {
        glBillboard(x, y, z);
        final int distance = (int) player.getDistance(x, y, z);
        float scaleDistance = distance / 2.0f / (2.0f + (2.0f - scale));
        if (scaleDistance < 1.0f) {
            scaleDistance = 1.0f;
        }
        GlStateManager.scale(scaleDistance, scaleDistance, scaleDistance);
    }

    public static void drawText(final BlockPos pos, final String text) {
        GlStateManager.pushMatrix();
        glBillboardDistanceScaled(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, mc.player, 1.0f);
        GlStateManager.disableDepth();

        if (Past.settingsManager.getSettingID("FontFont").getValueString() == "Lato") {
            GlStateManager.translate(-(Past.latoFont.getStringWidth(text) / 2.0), 0.0, 0.0);
        } else if (Past.settingsManager.getSettingID("FontFont").getValueString() == "Verdana") {
            GlStateManager.translate(-(Past.verdanaFont.getStringWidth(text) / 2.0), 0.0, 0.0);
        } else if (Past.settingsManager.getSettingID("FontFont").getValueString() == "Arial") {
            GlStateManager.translate(-(Past.arialFont.getStringWidth(text) / 2.0), 0.0, 0.0);
        } else {
            GlStateManager.translate(-(mc.fontRenderer.getStringWidth(text) / 2.0), 0.0, 0.0);
        }

        FontUtil.drawText(text, 0.0f, 0.0f, -1);
        GlStateManager.popMatrix();
    }
}
