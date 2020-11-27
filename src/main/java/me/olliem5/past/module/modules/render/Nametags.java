package me.olliem5.past.module.modules.render;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.colour.ColourUtil;
import me.olliem5.past.util.module.NametagUtil;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class Nametags extends Module {
    public Nametags() {
        super("Nametags", "Displays a nametag above players heads", Category.RENDER);
    }

    /**
     * Credit RemainingToast/ToastClientForge for some base code (I suck at opengl)
     * Although, this is Kami/Rusherhack nametags iirc
     * https://github.com/RemainingToast/ToastClientForge/blob/master/src/main/java/me/remainingtoast/toastclient/module/modules/render/NameTags.java
     */

    /**
     * TODO: Armour colours for durability, also bar of durability (mode option)
     * TODO: Self nametag option
     */

    Setting scale;
    Setting armour;
    Setting mainhand;
    Setting offhand;
    Setting health;
    Setting ping;
    Setting customfont;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(scale = new Setting("Scale", "NametagsScale", 2.0, 2.5, 10.0, this));
        Past.settingsManager.registerSetting(armour = new Setting("Armour", "NametagsArmour", true, this));
        Past.settingsManager.registerSetting(mainhand = new Setting("Main Hand", "NametagsMainhand", true, this));
        Past.settingsManager.registerSetting(offhand = new Setting("Off Hand", "NametagsOffhand", true, this));
        Past.settingsManager.registerSetting(health = new Setting("Health", "NametagsHealth", true, this));
        Past.settingsManager.registerSetting(ping = new Setting("Ping", "NametagsPing", true, this));
        Past.settingsManager.registerSetting(customfont = new Setting("Custom Font", "NametagsCustomFont", true, this));
    }

    @EventHandler
    public Listener<RenderWorldLastEvent> listener = new Listener<>(event -> {
        if (nullCheck()) return;

        for (EntityPlayer entityPlayer : mc.world.playerEntities) {
            if (entityPlayer != null && !entityPlayer.equals(mc.player)) {
                renderNametag(entityPlayer);
            }
        }
    });

    private void renderNametag(EntityPlayer entity) {
        if (entity != null) {
            GlStateManager.pushMatrix();
            Vec3d vec3d = NametagUtil.getInterpolatedRenderPos(entity, mc.getRenderPartialTicks());

            float yAdd = entity.height + 0.5f - (entity.isSneaking() ? 0.25f : 0);
            double x = vec3d.x;
            double y = vec3d.y + yAdd;
            double z = vec3d.z;

            float viewerYaw = mc.getRenderManager().playerViewY;
            float viewerPitch = mc.getRenderManager().playerViewX;
            boolean isFacingThirdPerson = mc.getRenderManager().options.thirdPersonView == 2;

            GlStateManager.translate(x, y, z);
            GlStateManager.rotate(-viewerYaw, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate((float) (isFacingThirdPerson ? -1 : 1) * viewerPitch, 1.0F, 0.0F, 0.0F);

            float dist = mc.player.getDistance(entity);
            float math = (dist / 8f) * (float) (Math.pow(1.258254f, scale.getValueDouble()));

            GlStateManager.scale(math, math, math);
            GlStateManager.scale(-0.025F, -0.025F, 0.025F);

            String name;

            String finalPing = "";
            if (ping.getValBoolean()) {
                finalPing = getPing(entity) + "ms";
            }

            if (health.getValBoolean()) {
                name = entity.getName() + " " + "|" + " " + colourHealth(entity, Math.round(entity.getHealth() + entity.getAbsorptionAmount())) + (ping.getValBoolean() ? ColourUtil.white + " " + "|" + " " + finalPing : "");
            } else if (ping.getValBoolean()) {
                name = entity.getName() + " " + "|" + " " + finalPing + (health.getValBoolean() ? " " + "|" + " " + colourHealth(entity, Math.round(entity.getHealth() + entity.getAbsorptionAmount())) : "");
            } else {
                name = entity.getName();
            }

            int length;

            if (customfont.getValBoolean()) {
                length = Past.customFontRenderer.getStringWidth(name) / 2;
            } else {
                length = mc.fontRenderer.getStringWidth(name) / 2;
            }

            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.disableTexture2D();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            GlStateManager.disableDepth();
            glTranslatef(0, -20, 0);

            bufferBuilder.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
            bufferBuilder.pos(-length - 1, 8, 0.0D).color(0.0F, 0.0F, 0.0F, 0.5F).endVertex();
            bufferBuilder.pos(-length - 1, 19, 0.0D).color(0.0F, 0.0F, 0.0F, 0.5F).endVertex();
            bufferBuilder.pos(length + 1, 19, 0.0D).color(0.0F, 0.0F, 0.0F, 0.5F).endVertex();
            bufferBuilder.pos(length + 1, 8, 0.0D).color(0.0F, 0.0F, 0.0F, 0.5F).endVertex();
            tessellator.draw();

            bufferBuilder.begin(GL_LINE_LOOP, DefaultVertexFormats.POSITION_COLOR);
            bufferBuilder.pos(-length - 1, 8, 0.0D).color(.1f, .1f, .1f, .1f).endVertex();
            bufferBuilder.pos(-length - 1, 19, 0.0D).color(.1f, .1f, .1f, .1f).endVertex();
            bufferBuilder.pos(length + 1, 19, 0.0D).color(.1f, .1f, .1f, .1f).endVertex();
            bufferBuilder.pos(length + 1, 8, 0.0D).color(.1f, .1f, .1f, .1f).endVertex();
            tessellator.draw();

            GlStateManager.enableTexture2D();
            GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);

            if (!entity.isSneaking()) {
                if (customfont.getValBoolean()) {
                    Past.customFontRenderer.drawString(name, -length, 10, 0xffffff);
                } else {
                    mc.fontRenderer.drawString(name, -length, 10, 0xffffff);
                }
            } else {
                if (customfont.getValBoolean()) {
                    Past.customFontRenderer.drawString(name, -length, 10, 0x00FF00);
                } else {
                    mc.fontRenderer.drawString(name, -length, 10, 0x00FF00);
                }
            }

            int xOffset = 0;
            for (ItemStack armourStack : entity.getArmorInventoryList()) {
                if (armourStack != null) {
                    xOffset -= 8;
                }
            }

            Object renderStack;

            if (mainhand.getValBoolean() && entity.getHeldItemMainhand() != null) {
                xOffset -= 8;
                renderStack = entity.getHeldItemMainhand().copy();
                renderItem((ItemStack) renderStack, xOffset, -10);
                xOffset += 16;
            }

            for (int index = 3; index >= 0; --index) {
                ItemStack armourStack = entity.inventory.armorInventory.get(index);
                if (armour.getValBoolean() && armourStack != null) {
                    ItemStack renderStack1 = armourStack.copy();
                    renderItem(renderStack1, xOffset, -10);
                    xOffset += 16;
                }
            }

            ItemStack renderOffhand;

            if (offhand.getValBoolean() && entity.getHeldItemOffhand() != null) {
                xOffset -= 0;
                renderOffhand = entity.getHeldItemOffhand().copy();
                renderItem(renderOffhand, xOffset, -10);
            }

            GlStateManager.glNormal3f(0.0F, 0.0F, 0.0F);
            glTranslatef(0, 20, 0);

            GlStateManager.scale(-40, -40, 40);
            GlStateManager.enableDepth();
            GlStateManager.popMatrix();
        }
    }

    private void renderItem(ItemStack stack, int x, int y) {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(256);

        GlStateManager.disableDepth();
        GlStateManager.enableDepth();

        RenderHelper.enableStandardItemLighting();
        mc.getRenderItem().zLevel = -100.0F;
        GlStateManager.scale(1, 1, 0.01f);
        mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, (y / 2) - 12);
        mc.getRenderItem().renderItemOverlays(mc.fontRenderer, stack, x, (y / 2) - 12);
        mc.getRenderItem().zLevel = 0.0F;

        GlStateManager.scale(1, 1, 1);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5D, 0.5D, 0.5D);
        GlStateManager.disableDepth();
        renderEnchantText(stack, x, y - 18);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        GL11.glPopMatrix();
    }

    private void renderEnchantText(ItemStack stack, int x, int y) {
        int encY = y - 24;
        int yCount = encY - -5;

        if (stack.getItem() instanceof ItemArmor || stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemTool) {
            if (customfont.getValBoolean()) {
                Past.customFontRenderer.drawStringWithShadow(stack.getMaxDamage() - stack.getItemDamage() + "\u00A74", x * 2 + 8, y + 26, -1);
            } else {
                mc.fontRenderer.drawStringWithShadow(stack.getMaxDamage() - stack.getItemDamage() + "\u00A74", x * 2 + 8, y + 26, -1);
            }
        }

        NBTTagList enchants = stack.getEnchantmentTagList();

        for (int index = 0; index < enchants.tagCount(); ++index) {
            short id = enchants.getCompoundTagAt(index).getShort("id");
            short level = enchants.getCompoundTagAt(index).getShort("lvl");
            Enchantment enc = Enchantment.getEnchantmentByID(id);
            if (enc != null) {
                String encName = enc.isCurse()
                        ? ColourUtil.red
                        + enc.getTranslatedName(level).substring(11).substring(0, 1).toLowerCase()
                        : enc.getTranslatedName(level).substring(0, 1).toLowerCase();
                encName = encName + level;
                GL11.glPushMatrix();
                GL11.glScalef(0.9f, 0.9f, 0);

                if (customfont.getValBoolean()) {
                    Past.customFontRenderer.drawStringWithShadow(encName, x * 2 + 13, yCount, -1);
                } else {
                    mc.fontRenderer.drawStringWithShadow(encName, x * 2 + 13, yCount, -1);
                }

                GL11.glScalef(1f, 1f, 1);
                GL11.glPopMatrix();
                encY += 8;
                yCount -= 10;
            }
        }
    }

    private int getPing(final EntityPlayer entity) {
        int playerPing = 0;

        try {
            playerPing = mc.getConnection().getPlayerInfo(entity.getUniqueID()).getResponseTime();
            String playerPing0dp = String.format ("%", playerPing);
            playerPing = Integer.parseInt(playerPing0dp);
        } catch (Exception ignored) {}

        return playerPing;
    }

    private String colourHealth(EntityPlayer entity, int health) {
        float max = entity.getMaxHealth();
        int result = Math.round((health / max) * 100);

        if (result > 75) {
            return ColourUtil.green + "" + health;
        } else if (result > 50) {
            return ColourUtil.yellow + "" + health;
        } else if (result > 25) {
            return ColourUtil.red + "" + health;
        } else {
            return ColourUtil.darkRed + "" + health;
        }
    }
}
