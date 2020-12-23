package me.olliem5.past.impl.modules.render;

import me.olliem5.past.Past;
import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.setting.Setting;
import me.olliem5.past.api.util.render.RenderUtil;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.awt.*;
import java.util.ArrayList;

public class BlockHighlight extends Module {
    public BlockHighlight() {
        super("BlockHighlight", "Highlights the block that you are looking at", Category.RENDER);
    }

    Setting rendermode;
    Setting red;
    Setting green;
    Setting blue;
    Setting alpha;
    Setting rainbow;

    private ArrayList<String> rendermodes;

    private RayTraceResult rayTraceResult;

    @Override
    public void setup() {
        rendermodes = new ArrayList<>();
        rendermodes.add("Full");
        rendermodes.add("FullFrame");
        rendermodes.add("Frame");

        Past.settingsManager.registerSetting(rendermode = new Setting("Mode", "BlockHighlightMode", this, rendermodes, "FullFrame"));
        Past.settingsManager.registerSetting(red = new Setting("Red", "BlockHighlightRed", 0, 100, 255, this));
        Past.settingsManager.registerSetting(green = new Setting("Green", "BlockHighlightGreen", 0, 100, 255, this));
        Past.settingsManager.registerSetting(blue = new Setting("Blue", "BlockHighlightBlue", 0, 100, 255, this));
        Past.settingsManager.registerSetting(alpha = new Setting("Alpha", "BlockHighlightAlpha", 0, 100, 255, this));
        Past.settingsManager.registerSetting(rainbow = new Setting("Rainbow", "BlockHighlightRainbow", false, this));
    }

    @Override
    public void onUpdate() {
        if (nullCheck()) return;

        rayTraceResult = mc.objectMouseOver;
    }

    @EventHandler
    public Listener<RenderWorldLastEvent> listener = new Listener<>(event -> {
        if (nullCheck()) return;

        float[] hue = new float[] {(float) (System.currentTimeMillis() % 7500L) / 7500f};
        int rgb = Color.HSBtoRGB(hue[0], 0.8f, 0.8f);
        int rgbred = rgb >> 16 & 255;
        int rgbgreen = rgb >> 8 & 255;
        int rgbblue = rgb & 255;

        if (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {

            BlockPos blockPos = rayTraceResult.getBlockPos();

            if (rayTraceResult != null) {
                if (!rainbow.getValBoolean()) {
                    if (rendermode.getValueString() == "Full") {
                        RenderUtil.drawBox(RenderUtil.generateBB(blockPos.getX(), blockPos.getY(), blockPos.getZ()), red.getValueInt() / 255f, green.getValueInt() / 255f, blue.getValueInt() / 255f, alpha.getValueInt() / 255f);
                    } else if (rendermode.getValueString() == "FullFrame") {
                        RenderUtil.drawBoxOutline(RenderUtil.generateBB(blockPos.getX(), blockPos.getY(), blockPos.getZ()), red.getValueInt() / 255f, green.getValueInt() / 255f, blue.getValueInt() / 255f, alpha.getValueInt() / 255f);
                    } else {
                        RenderUtil.drawOutline(RenderUtil.generateBB(blockPos.getX(), blockPos.getY(), blockPos.getZ()), red.getValueInt() / 255f, green.getValueInt() / 255f, blue.getValueInt() / 255f, alpha.getValueInt() / 255f);
                    }
                } else {
                    if (rendermode.getValueString() == "Full") {
                        RenderUtil.drawBox(RenderUtil.generateBB(blockPos.getX(), blockPos.getY(), blockPos.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, alpha.getValueInt() / 255f);
                    } else if (rendermode.getValueString() == "FullFrame") {
                        RenderUtil.drawBoxOutline(RenderUtil.generateBB(blockPos.getX(), blockPos.getY(), blockPos.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, alpha.getValueInt() / 255f);
                    } else {
                        RenderUtil.drawOutline(RenderUtil.generateBB(blockPos.getX(), blockPos.getY(), blockPos.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, alpha.getValueInt() / 255f);
                    }
                }
            }
        }
    });
}
