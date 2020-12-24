package me.olliem5.past.impl.mixins;

import me.olliem5.past.Past;
import me.olliem5.past.api.util.render.RubikUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import org.lwjgl.util.vector.Quaternion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Thanks to rebane2001 for the rubik's cube crystal that has been modified for use here
 * https://github.com/rebane2001/cr3stal/blob/master/1.12/ModelEnderCrystalMixin.java
 */

@Mixin(ModelEnderCrystal.class)
public abstract class MixinModelEnderCrystal extends ModelBase {

    //The cube model for the Ender Crystal
    @Shadow
    @Final
    private ModelRenderer cube;

    //The glass model for the Ender Crystal
    @Shadow
    private ModelRenderer glass = new ModelRenderer(this, "glass");

    //The base model for the Ender Crystal
    @Shadow
    private ModelRenderer base;

    //Currently rotating side for rubiks cube crystal
    private int rotatingSide = 0;
    //front - 0
    //back - 1
    //top - 2
    //bottom - 3
    //left - 4
    //right - 5

    private long lastTime = 0;

    private static final int ANIMATION_LENGTH = 400;
    private static final double CUBELET_SCALE = 0.4;

    @Overwrite
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (Past.moduleManager.getModuleByName("CrystalCustomize").isToggled() && Past.settingsManager.getSettingID("CrystalCustomizeCrystalMode").getValueString() == "RubiksCube") {

            /**
             * Rubiks cube crystal rendering
             */

            GlStateManager.pushMatrix();
            GlStateManager.scale(2.0F, 2.0F, 2.0F);
            GlStateManager.translate(0.0F, -0.5F, 0.0F);

            if (this.base != null) {
                if (Past.moduleManager.getModuleByName("CrystalCustomize").isToggled() && Past.settingsManager.getSettingID("CrystalCustomizeBase").getValBoolean()) {
                    this.base.render(scale);
                }
            }

            GlStateManager.rotate(limbSwingAmount, 0.0F, 1.0F, 0.0F);

            if (this.base != null) {
                if (Past.moduleManager.getModuleByName("CrystalCustomize").isToggled() && Past.settingsManager.getSettingID("CrystalCustomizeBase").getValBoolean()) {
                    GlStateManager.translate(0.0F, 1.2F, 0.0F);
                }
            } else {
                GlStateManager.translate(0.0F, 1.0F, 0.0F);
            }

            GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);

            GlStateManager.scale(0.875F, 0.875F, 0.875F);
            GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
            GlStateManager.rotate(limbSwingAmount, 0.0F, 1.0F, 0.0F);

            if (Past.moduleManager.getModuleByName("CrystalCustomize").isToggled() && Past.settingsManager.getSettingID("CrystalCustomizeGlass").getValBoolean()) {
                this.glass.render(scale);
            }

            GlStateManager.scale(0.875F, 0.875F, 0.875F);
            GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
            GlStateManager.rotate(limbSwingAmount, 0.0F, 1.0F, 0.0F);

            GlStateManager.scale(CUBELET_SCALE, CUBELET_SCALE, CUBELET_SCALE);
            scale *= CUBELET_SCALE * 2;

            long currentTime = Minecraft.getSystemTime();
            if (currentTime - ANIMATION_LENGTH > lastTime) {
                //Rotate sides and corners
                int[] currentSide = RubikUtil.cubeSides[rotatingSide];
                Quaternion[] cubletsTemp = {
                        RubikUtil.cubeletStatus[currentSide[0]],
                        RubikUtil.cubeletStatus[currentSide[1]],
                        RubikUtil.cubeletStatus[currentSide[2]],
                        RubikUtil.cubeletStatus[currentSide[3]],
                        RubikUtil.cubeletStatus[currentSide[4]],
                        RubikUtil.cubeletStatus[currentSide[5]],
                        RubikUtil.cubeletStatus[currentSide[6]],
                        RubikUtil.cubeletStatus[currentSide[7]],
                        RubikUtil.cubeletStatus[currentSide[8]]
                };

                //Rotation direction
                if (true) {
                    RubikUtil.cubeletStatus[currentSide[0]] = cubletsTemp[6];
                    RubikUtil.cubeletStatus[currentSide[1]] = cubletsTemp[3];
                    RubikUtil.cubeletStatus[currentSide[2]] = cubletsTemp[0];
                    RubikUtil.cubeletStatus[currentSide[3]] = cubletsTemp[7];
                    RubikUtil.cubeletStatus[currentSide[4]] = cubletsTemp[4];
                    RubikUtil.cubeletStatus[currentSide[5]] = cubletsTemp[1];
                    RubikUtil.cubeletStatus[currentSide[6]] = cubletsTemp[8];
                    RubikUtil.cubeletStatus[currentSide[7]] = cubletsTemp[5];
                    RubikUtil.cubeletStatus[currentSide[8]] = cubletsTemp[2];
                } else {
                    RubikUtil.cubeletStatus[currentSide[0]] = cubletsTemp[2];
                    RubikUtil.cubeletStatus[currentSide[1]] = cubletsTemp[5];
                    RubikUtil.cubeletStatus[currentSide[2]] = cubletsTemp[8];
                    RubikUtil.cubeletStatus[currentSide[3]] = cubletsTemp[1];
                    RubikUtil.cubeletStatus[currentSide[4]] = cubletsTemp[4];
                    RubikUtil.cubeletStatus[currentSide[5]] = cubletsTemp[7];
                    RubikUtil.cubeletStatus[currentSide[6]] = cubletsTemp[0];
                    RubikUtil.cubeletStatus[currentSide[7]] = cubletsTemp[3];
                    RubikUtil.cubeletStatus[currentSide[8]] = cubletsTemp[6];
                }

                int[] trans = RubikUtil.cubeSideTransforms[rotatingSide];
                for (int x = -1; x < 2; x++) {
                    for (int y = -1; y < 2; y++) {
                        for (int z = -1; z < 2; z++) {
                            if (x != 0 || y != 0 || z != 0) {
                                applyCubeletRotation(x, y, z, trans[0], trans[1], trans[2]);
                            }
                        }
                    }
                }
                rotatingSide = ThreadLocalRandom.current().nextInt(0, 5 + 1);
                lastTime = currentTime;
            }

            //Draw non-rotating cubes
            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    for (int z = -1; z < 2; z++) {
                        if (x != 0 || y != 0 || z != 0) {
                            if (Past.moduleManager.getModuleByName("CrystalCustomize").isToggled() && Past.settingsManager.getSettingID("CrystalCustomizeCube").getValBoolean()) {
                                drawCubeletStatic(scale, x, y, z);
                            }
                        }
                    }
                }
            }

            //Draw rotating cubes
            int[] trans = RubikUtil.cubeSideTransforms[rotatingSide];

            GlStateManager.pushMatrix();
            GlStateManager.translate(trans[0] * CUBELET_SCALE, trans[1] * CUBELET_SCALE, trans[2] * CUBELET_SCALE);

            float RotationAngle = (float) Math.toRadians(RubikUtil.easeInOutCubic(((float) (currentTime - lastTime)) / ANIMATION_LENGTH) * 90);
            float xx = (float) (trans[0] * Math.sin(RotationAngle / 2));
            float yy = (float) (trans[1] * Math.sin(RotationAngle / 2));
            float zz = (float) (trans[2] * Math.sin(RotationAngle / 2));
            float ww = (float) Math.cos(RotationAngle / 2);

            Quaternion q = new Quaternion(xx, yy, zz, ww);

            GlStateManager.rotate(q);

            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    for (int z = -1; z < 2; z++) {
                        if (x != 0 || y != 0 || z != 0) {
                            if (Past.moduleManager.getModuleByName("CrystalCustomize").isToggled() && Past.settingsManager.getSettingID("CrystalCustomizeCube").getValBoolean()) {
                                drawCubeletRotating(scale, x, y, z);
                            }
                        }
                    }
                }
            }
            GlStateManager.popMatrix();
            GlStateManager.popMatrix();
        } else if (Past.moduleManager.getModuleByName("CrystalCustomize").isToggled() && Past.settingsManager.getSettingID("CrystalCustomizeCrystalMode").getValueString() == "Vanilla") {

            /**
             * Modified vanilla crystal rendering
             */

            GlStateManager.pushMatrix();
            GlStateManager.scale(2.0F, 2.0F, 2.0F);
            GlStateManager.translate(0.0F, -0.5F, 0.0F);

            if (this.base != null) {
                if (Past.moduleManager.getModuleByName("CrystalCustomize").isToggled() && Past.settingsManager.getSettingID("CrystalCustomizeBase").getValBoolean()) {
                    this.base.render(scale);
                }
            }

            GlStateManager.rotate(limbSwingAmount, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, 0.8F + ageInTicks, 0.0F);
            GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);

            if (Past.moduleManager.getModuleByName("CrystalCustomize").isToggled() && Past.settingsManager.getSettingID("CrystalCustomizeGlass").getValBoolean()) {
                this.glass.render(scale);
            }

            GlStateManager.scale(0.875F, 0.875F, 0.875F);
            GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
            GlStateManager.rotate(limbSwingAmount, 0.0F, 1.0F, 0.0F);

            if (Past.moduleManager.getModuleByName("CrystalCustomize").isToggled() && Past.settingsManager.getSettingID("CrystalCustomizeGlass").getValBoolean()) {
                this.glass.render(scale);
            }

            GlStateManager.scale(0.875F, 0.875F, 0.875F);
            GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
            GlStateManager.rotate(limbSwingAmount, 0.0F, 1.0F, 0.0F);

            if (Past.moduleManager.getModuleByName("CrystalCustomize").isToggled() && Past.settingsManager.getSettingID("CrystalCustomizeCube").getValBoolean()) {
                this.cube.render(scale);
            }

            GlStateManager.popMatrix();
        } else {

            /**
             * Pure vanilla crystal rendering
             */

            GlStateManager.pushMatrix();
            GlStateManager.scale(2.0F, 2.0F, 2.0F);
            GlStateManager.translate(0.0F, -0.5F, 0.0F);

            if (this.base != null) {
                this.base.render(scale);
            }

            GlStateManager.rotate(limbSwingAmount, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, 0.8F + ageInTicks, 0.0F);
            GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);

            this.glass.render(scale);

            GlStateManager.scale(0.875F, 0.875F, 0.875F);
            GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
            GlStateManager.rotate(limbSwingAmount, 0.0F, 1.0F, 0.0F);

            this.glass.render(scale);

            GlStateManager.scale(0.875F, 0.875F, 0.875F);
            GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
            GlStateManager.rotate(limbSwingAmount, 0.0F, 1.0F, 0.0F);

            this.cube.render(scale);

            GlStateManager.popMatrix();
        }
    }

    private void drawCubeletStatic(float scale, int x, int y, int z) {
        int cubletId = RubikUtil.cubletLookup[x + 1][y + 1][z + 1];
        if (Arrays.stream(RubikUtil.cubeSides[rotatingSide]).anyMatch(i -> i == cubletId)) return;
        drawCubelet(scale, x, y, z, cubletId);
    }

    private void drawCubeletRotating(float scale, int x, int y, int z) {
        int cubletId = RubikUtil.cubletLookup[x + 1][y + 1][z + 1];
        if (!Arrays.stream(RubikUtil.cubeSides[rotatingSide]).anyMatch(i -> i == cubletId)) return;
        int[] trans = RubikUtil.cubeSideTransforms[rotatingSide];
        drawCubelet(scale, x - trans[0], y - trans[1], z - trans[2], cubletId);
    }

    private void applyCubeletRotation(int x, int y, int z, int rX, int rY, int rZ) {
        int cubletId = RubikUtil.cubletLookup[x + 1][y + 1][z + 1];
        if (!Arrays.stream(RubikUtil.cubeSides[rotatingSide]).anyMatch(i -> i == cubletId)) return;

        float RotationAngle = (float) Math.toRadians(90);
        float xx = (float) (rX * Math.sin(RotationAngle / 2));
        float yy = (float) (rY * Math.sin(RotationAngle / 2));
        float zz = (float) (rZ * Math.sin(RotationAngle / 2));
        float ww = (float) Math.cos(RotationAngle / 2);

        RubikUtil.cubeletStatus[cubletId] = Quaternion.mul(new Quaternion(xx, yy, zz, ww), RubikUtil.cubeletStatus[cubletId], null);
    }

    private void drawCubelet(float scale, int x, int y, int z, int cubletId) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x * CUBELET_SCALE, y * CUBELET_SCALE, z * CUBELET_SCALE);
        GlStateManager.pushMatrix();
        GlStateManager.rotate(RubikUtil.cubeletStatus[cubletId]);

        this.cube.render(scale);

        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }
}
