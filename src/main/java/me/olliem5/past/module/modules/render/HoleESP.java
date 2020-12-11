package me.olliem5.past.module.modules.render;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.module.HoleUtil;
import me.olliem5.past.util.render.RenderUtil;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.awt.*;
import java.util.ArrayList;
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

    @EventHandler
    public Listener<RenderWorldLastEvent> listener = new Listener<>(event -> {
        if (nullCheck()) return;

        List<BlockPos> obsidianHoles = holeUtil.findObsidianHoles();
        List<BlockPos> bedrockHoles = holeUtil.findBedrockHoles();

        float[] hue = new float[] {(float) (System.currentTimeMillis() % 7500L) / 7500f};
        int rgb = Color.HSBtoRGB(hue[0], 0.8f, 0.8f);
        int rgbred = rgb >> 16 & 255;
        int rgbgreen = rgb >> 8 & 255;
        int rgbblue = rgb & 255;

        if (obsidian.getValBoolean()) {
            if (obsidianHoles != null) {
                for (BlockPos obiHoles : holeUtil.findObsidianHoles()) {
                    if (!obsidianrainbow.getValBoolean()) {
                        if (rendermode.getValueString() == "Full") {
                            RenderUtil.drawBox(RenderUtil.generateBB(obiHoles.getX(), obiHoles.getY(), obiHoles.getZ()), obsidianred.getValueInt(), obsidiangreen.getValueInt(), obsidianblue.getValueInt(), obsidianopacity.getValueInt());
                        } else if (rendermode.getValueString() == "FullFrame") {
                            RenderUtil.drawBoxOutline(RenderUtil.generateBB(obiHoles.getX(), obiHoles.getY(), obiHoles.getZ()), obsidianred.getValueInt(), obsidiangreen.getValueInt(), obsidianblue.getValueInt(), obsidianopacity.getValueInt());
                        } else {
                            RenderUtil.drawOutline(RenderUtil.generateBB(obiHoles.getX(), obiHoles.getY(), obiHoles.getZ()), obsidianred.getValueInt(), obsidiangreen.getValueInt(), obsidianblue.getValueInt(), obsidianopacity.getValueInt());
                        }
                    } else {
                        if (rendermode.getValueString() == "Full") {
                            RenderUtil.drawBox(RenderUtil.generateBB(obiHoles.getX(), obiHoles.getY(), obiHoles.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, obsidianopacity.getValueInt());
                        } else if (rendermode.getValueString() == "FullFrame") {
                            RenderUtil.drawBoxOutline(RenderUtil.generateBB(obiHoles.getX(), obiHoles.getY(), obiHoles.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, obsidianopacity.getValueInt());
                        } else {
                            RenderUtil.drawOutline(RenderUtil.generateBB(obiHoles.getX(), obiHoles.getY(), obiHoles.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, obsidianopacity.getValueInt());
                        }
                    }
                }
            }
        }

        if (bedrock.getValBoolean()) {
            if (bedrockHoles != null) {
                for (BlockPos bRockHoles : holeUtil.findBedrockHoles()) {
                    if (!bedrockrainbow.getValBoolean()) {
                        if (rendermode.getValueString() == "Full") {
                            RenderUtil.drawBox(RenderUtil.generateBB(bRockHoles.getX(), bRockHoles.getY(), bRockHoles.getZ()), bedrockred.getValueInt(), bedrockgreen.getValueInt(), bedrockblue.getValueInt(), bedrockopacity.getValueInt());
                        } else if (rendermode.getValueString() == "FullFrame") {
                            RenderUtil.drawBoxOutline(RenderUtil.generateBB(bRockHoles.getX(), bRockHoles.getY(), bRockHoles.getZ()), bedrockred.getValueInt(), bedrockgreen.getValueInt(), bedrockblue.getValueInt(), bedrockopacity.getValueInt());
                        } else {
                            RenderUtil.drawOutline(RenderUtil.generateBB(bRockHoles.getX(), bRockHoles.getY(), bRockHoles.getZ()), bedrockred.getValueInt(), bedrockgreen.getValueInt(), bedrockblue.getValueInt(), bedrockopacity.getValueInt());
                        }
                    } else {
                        if (rendermode.getValueString() == "Full") {
                            RenderUtil.drawBox(RenderUtil.generateBB(bRockHoles.getX(), bRockHoles.getY(), bRockHoles.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, bedrockopacity.getValueInt());
                        } else if (rendermode.getValueString() == "FullFrame") {
                            RenderUtil.drawBoxOutline(RenderUtil.generateBB(bRockHoles.getX(), bRockHoles.getY(), bRockHoles.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, bedrockopacity.getValueInt());
                        } else {
                            RenderUtil.drawOutline(RenderUtil.generateBB(bRockHoles.getX(), bRockHoles.getY(), bRockHoles.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, bedrockopacity.getValueInt());
                        }
                    }
                }
            }
        }
    });
}
