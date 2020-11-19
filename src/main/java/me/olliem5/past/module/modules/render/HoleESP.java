package me.olliem5.past.module.modules.render;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.HoleUtil;
import me.olliem5.past.util.RenderUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HoleESP extends Module {
    public HoleESP() {
        super("HoleESP", "Highlights safe holes for crystal pvp", Category.RENDER);
    }

    Setting rendermode;
    Setting holerange;
    Setting obsidian;
    Setting obsidianred;
    Setting obsidiangreen;
    Setting obsidianblue;
    Setting obsidianopacity;
    Setting obsidianrainbow;
    Setting bedrock;
    Setting bedrockred;
    Setting bedrockgreen;
    Setting bedrockblue;
    Setting bedrockopacity;
    Setting bedrockrainbow;

    private ArrayList<String> rendermodes;

    @Override
    public void setup() {
        rendermodes = new ArrayList<>();
        rendermodes.add("Full");
        rendermodes.add("FullFrame");
        rendermodes.add("Frame");

        Past.settingsManager.registerSetting(rendermode = new Setting("Mode", "HoleESPMode", this, rendermodes, "Full"));
        Past.settingsManager.registerSetting(holerange = new Setting("Hole Range", "HoleESPHoleRange", 1, 5, 10, this));
        Past.settingsManager.registerSetting(obsidian = new Setting("Obi Holes", "HoleESPObsidianHoles", true, this));
        Past.settingsManager.registerSetting(obsidianred = new Setting("Obi Red", "HoleESPObsidianRed", 0, 100, 255, this));
        Past.settingsManager.registerSetting(obsidiangreen = new Setting("Obi Green", "HoleESPObsidianGreen", 0, 100, 255, this));
        Past.settingsManager.registerSetting(obsidianblue = new Setting("Obi Blue", "HoleESPObsidianBlue", 0, 100, 255, this));
        Past.settingsManager.registerSetting(obsidianopacity = new Setting("Obi Opacity", "HoleESPObsidianOpacity", 0, 100, 255, this));
        Past.settingsManager.registerSetting(obsidianrainbow = new Setting("Obi Rainbow", "HoleESPObsidianRainbow", false, this));
        Past.settingsManager.registerSetting(bedrock = new Setting("BRock Holes", "HoleESPBedrockHoles", true, this));
        Past.settingsManager.registerSetting(bedrockred = new Setting("BRock Red", "HoleESPBedrockRed", 0, 100, 255, this));
        Past.settingsManager.registerSetting(bedrockgreen = new Setting("BRock Green", "HoleESPBedrockGreen", 0, 100, 255, this));
        Past.settingsManager.registerSetting(bedrockblue = new Setting("BRock Blue", "HoleESPBedrockBlue", 0, 100, 255, this));
        Past.settingsManager.registerSetting(bedrockopacity = new Setting("BRock Opacity", "HoleESPBedrockOpacity", 0, 100, 255, this));
        Past.settingsManager.registerSetting(bedrockrainbow = new Setting("BRock Rainbow", "HoleESPBedrockRainbow", false, this));
    }

    private static HoleUtil holeUtil = new HoleUtil();

    private BlockPos renderBlock;

    @Override
    public void onUpdate() {
        if (nullCheck()) {
            return;
        }

        List<BlockPos> obsidianHoles = holeUtil.findObsidianHoles();
        List<BlockPos> bedrockHoles = holeUtil.findBedrockHoles();

        BlockPos blockPosRender = null;

        if (obsidian.getValBoolean()) {
            Iterator<BlockPos> obsidianIterator = obsidianHoles.iterator();
            while (obsidianIterator.hasNext()) {
                blockPosRender = obsidianIterator.next();
            }
        }

        if (bedrock.getValBoolean()) {
            Iterator<BlockPos> bedrockIterator = bedrockHoles.iterator();
            while (bedrockIterator.hasNext()) {
                blockPosRender = bedrockIterator.next();
            }
        }

        this.renderBlock = blockPosRender;
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        if (nullCheck()) {
            return;
        }

        float[] hue = new float[] {(float) (System.currentTimeMillis() % 7500L) / 7500f};
        int rgb = Color.HSBtoRGB(hue[0], 0.8f, 0.8f);
        int rgbred = rgb >> 16 & 255;
        int rgbgreen = rgb >> 8 & 255;
        int rgbblue = rgb & 255;

        if (obsidian.getValBoolean()) {
            for (BlockPos obsidianHoles : holeUtil.findObsidianHoles()) {
                if (!obsidianrainbow.getValBoolean()) {
                    if (rendermode.getValueString() == "Full") {
                        RenderUtil.drawBox(RenderUtil.generateBB(obsidianHoles.getX(), obsidianHoles.getY(), obsidianHoles.getZ()), obsidianred.getValueInt(), obsidiangreen.getValueInt(), obsidianblue.getValueInt(), obsidianopacity.getValueInt());
                    }

                    if (rendermode.getValueString() == "FullFrame") {
                        RenderUtil.drawBoxOutline(RenderUtil.generateBB(obsidianHoles.getX(), obsidianHoles.getY(), obsidianHoles.getZ()), obsidianred.getValueInt(), obsidiangreen.getValueInt(), obsidianblue.getValueInt(), obsidianopacity.getValueInt());
                    }

                    if (rendermode.getValueString() == "Frame") {
                        RenderUtil.drawOutline(RenderUtil.generateBB(obsidianHoles.getX(), obsidianHoles.getY(), obsidianHoles.getZ()), obsidianred.getValueInt(), obsidiangreen.getValueInt(), obsidianblue.getValueInt(), obsidianopacity.getValueInt());
                    }
                } else {
                    if (rendermode.getValueString() == "Full") {
                        RenderUtil.drawBox(RenderUtil.generateBB(obsidianHoles.getX(), obsidianHoles.getY(), obsidianHoles.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, obsidianopacity.getValueInt());
                    }

                    if (rendermode.getValueString() == "FullFrame") {
                        RenderUtil.drawBoxOutline(RenderUtil.generateBB(obsidianHoles.getX(), obsidianHoles.getY(), obsidianHoles.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, obsidianopacity.getValueInt());
                    }

                    if (rendermode.getValueString() == "Frame") {
                        RenderUtil.drawOutline(RenderUtil.generateBB(obsidianHoles.getX(), obsidianHoles.getY(), obsidianHoles.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, obsidianopacity.getValueInt());
                    }
                }
            }
        }

        if (bedrock.getValBoolean()) {
            for (BlockPos bedrockHoles : holeUtil.findBedrockHoles()) {
                if (!bedrockrainbow.getValBoolean()) {
                    if (rendermode.getValueString() == "Full") {
                        RenderUtil.drawBox(RenderUtil.generateBB(bedrockHoles.getX(), bedrockHoles.getY(), bedrockHoles.getZ()), bedrockred.getValueInt(), bedrockgreen.getValueInt(), bedrockblue.getValueInt(), bedrockopacity.getValueInt());
                    }

                    if (rendermode.getValueString() == "FullFrame") {
                        RenderUtil.drawBoxOutline(RenderUtil.generateBB(bedrockHoles.getX(), bedrockHoles.getY(), bedrockHoles.getZ()), bedrockred.getValueInt(), bedrockgreen.getValueInt(), bedrockblue.getValueInt(), bedrockopacity.getValueInt());
                    }

                    if (rendermode.getValueString() == "Frame") {
                        RenderUtil.drawOutline(RenderUtil.generateBB(bedrockHoles.getX(), bedrockHoles.getY(), bedrockHoles.getZ()), bedrockred.getValueInt(), bedrockgreen.getValueInt(), bedrockblue.getValueInt(), bedrockopacity.getValueInt());
                    }
                } else {
                    if (rendermode.getValueString() == "Full") {
                        RenderUtil.drawBox(RenderUtil.generateBB(bedrockHoles.getX(), bedrockHoles.getY(), bedrockHoles.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, bedrockopacity.getValueInt());
                    }

                    if (rendermode.getValueString() == "FullFrame") {
                        RenderUtil.drawBoxOutline(RenderUtil.generateBB(bedrockHoles.getX(), bedrockHoles.getY(), bedrockHoles.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, bedrockopacity.getValueInt());
                    }

                    if (rendermode.getValueString() == "Frame") {
                        RenderUtil.drawOutline(RenderUtil.generateBB(bedrockHoles.getX(), bedrockHoles.getY(), bedrockHoles.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, bedrockopacity.getValueInt());
                    }
                }
            }
        }
    }
}
