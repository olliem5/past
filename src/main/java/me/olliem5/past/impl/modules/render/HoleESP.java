package me.olliem5.past.impl.modules.render;

import me.olliem5.past.Past;
import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.module.ModuleInfo;
import me.olliem5.past.api.setting.Setting;
import me.olliem5.past.api.util.world.HoleUtil;
import me.olliem5.past.api.util.render.RenderUtil;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@ModuleInfo(name = "HoleESP", description = "Highlights safe holes for crystal pvp", category = Category.RENDER)
public class HoleESP extends Module {

    Setting rendermode;
    Setting holerange;
    Setting obsidian;
    Setting obsidianred;
    Setting obsidiangreen;
    Setting obsidianblue;
    Setting obsidianalpha;
    Setting obsidianrainbow;
    Setting bedrock;
    Setting bedrockred;
    Setting bedrockgreen;
    Setting bedrockblue;
    Setting bedrockalpha;
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
        Past.settingsManager.registerSetting(obsidianalpha = new Setting("Obi Alpha", "HoleESPObsidianAlpha", 0, 100, 255, this));
        Past.settingsManager.registerSetting(obsidianrainbow = new Setting("Obi Rainbow", "HoleESPObsidianRainbow", false, this));
        Past.settingsManager.registerSetting(bedrock = new Setting("BRock Holes", "HoleESPBedrockHoles", true, this));
        Past.settingsManager.registerSetting(bedrockred = new Setting("BRock Red", "HoleESPBedrockRed", 0, 100, 255, this));
        Past.settingsManager.registerSetting(bedrockgreen = new Setting("BRock Green", "HoleESPBedrockGreen", 0, 100, 255, this));
        Past.settingsManager.registerSetting(bedrockblue = new Setting("BRock Blue", "HoleESPBedrockBlue", 0, 100, 255, this));
        Past.settingsManager.registerSetting(bedrockalpha = new Setting("BRock Alpha", "HoleESPBedrockAlpha", 0, 100, 255, this));
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
                            RenderUtil.drawBox(RenderUtil.generateBB(obiHoles.getX(), obiHoles.getY(), obiHoles.getZ()), obsidianred.getValueInt() / 255f, obsidiangreen.getValueInt() / 255f, obsidianblue.getValueInt() / 255f, obsidianalpha.getValueInt() / 255f);
                        } else if (rendermode.getValueString() == "FullFrame") {
                            RenderUtil.drawBoxOutline(RenderUtil.generateBB(obiHoles.getX(), obiHoles.getY(), obiHoles.getZ()), obsidianred.getValueInt() / 255f, obsidiangreen.getValueInt() / 255f, obsidianblue.getValueInt() / 255f, obsidianalpha.getValueInt() / 255f);
                        } else {
                            RenderUtil.drawOutline(RenderUtil.generateBB(obiHoles.getX(), obiHoles.getY(), obiHoles.getZ()), obsidianred.getValueInt() / 255f, obsidiangreen.getValueInt() / 255f, obsidianblue.getValueInt() / 255f, obsidianalpha.getValueInt() / 255f);
                        }
                    } else {
                        if (rendermode.getValueString() == "Full") {
                            RenderUtil.drawBox(RenderUtil.generateBB(obiHoles.getX(), obiHoles.getY(), obiHoles.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, obsidianalpha.getValueInt() / 255f);
                        } else if (rendermode.getValueString() == "FullFrame") {
                            RenderUtil.drawBoxOutline(RenderUtil.generateBB(obiHoles.getX(), obiHoles.getY(), obiHoles.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, obsidianalpha.getValueInt() / 255f);
                        } else {
                            RenderUtil.drawOutline(RenderUtil.generateBB(obiHoles.getX(), obiHoles.getY(), obiHoles.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, obsidianalpha.getValueInt() / 255f);
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
                            RenderUtil.drawBox(RenderUtil.generateBB(bRockHoles.getX(), bRockHoles.getY(), bRockHoles.getZ()), bedrockred.getValueInt() / 255f, bedrockgreen.getValueInt() / 255f, bedrockblue.getValueInt() / 255f, bedrockalpha.getValueInt() / 255f);
                        } else if (rendermode.getValueString() == "FullFrame") {
                            RenderUtil.drawBoxOutline(RenderUtil.generateBB(bRockHoles.getX(), bRockHoles.getY(), bRockHoles.getZ()), bedrockred.getValueInt() / 255f, bedrockgreen.getValueInt() / 255f, bedrockblue.getValueInt() / 255f, bedrockalpha.getValueInt() / 255f);
                        } else {
                            RenderUtil.drawOutline(RenderUtil.generateBB(bRockHoles.getX(), bRockHoles.getY(), bRockHoles.getZ()), bedrockred.getValueInt() / 255f, bedrockgreen.getValueInt() / 255f, bedrockblue.getValueInt() / 255f, bedrockalpha.getValueInt() / 255f);
                        }
                    } else {
                        if (rendermode.getValueString() == "Full") {
                            RenderUtil.drawBox(RenderUtil.generateBB(bRockHoles.getX(), bRockHoles.getY(), bRockHoles.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, bedrockalpha.getValueInt() / 255f);
                        } else if (rendermode.getValueString() == "FullFrame") {
                            RenderUtil.drawBoxOutline(RenderUtil.generateBB(bRockHoles.getX(), bRockHoles.getY(), bRockHoles.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, bedrockalpha.getValueInt() / 255f);
                        } else {
                            RenderUtil.drawOutline(RenderUtil.generateBB(bRockHoles.getX(), bRockHoles.getY(), bRockHoles.getZ()), rgbred / 255f, rgbgreen / 255f, rgbblue / 255f, bedrockalpha.getValueInt() / 255f);
                        }
                    }
                }
            }
        }
    });
}
