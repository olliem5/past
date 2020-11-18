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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HoleESP extends Module {
    public HoleESP() {
        super("HoleESP", "Highlights safe holes for crystal pvp", Category.RENDER);
    }

    Setting rendermode;

    private ArrayList<String> rendermodes;

    @Override
    public void setup() {
        rendermodes = new ArrayList<>();
        rendermodes.add("Full");
        rendermodes.add("FullFrame");
        rendermodes.add("Frame");

        Past.settingsManager.registerSetting(rendermode = new Setting("Mode", "HoleESPMode", this, rendermodes, "Full"));
    }

    private static HoleUtil holeUtil = new HoleUtil();

    private BlockPos renderBlock;

    public void onUpdate() {
        if (nullCheck()) {
            return;
        }

        List<BlockPos> obsidianHoles = holeUtil.findObsidianHoles();
        List<BlockPos> bedrockHoles = holeUtil.findBedrockHoles();

        BlockPos blockPosRender = null;

        Iterator<BlockPos> obsidianIterator = obsidianHoles.iterator();
        while (obsidianIterator.hasNext()) {
            blockPosRender = obsidianIterator.next();
        }

        Iterator<BlockPos> bedrockIterator = bedrockHoles.iterator();
        while (bedrockIterator.hasNext()) {
            blockPosRender = bedrockIterator.next();
        }

        this.renderBlock = blockPosRender;
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        if (nullCheck()) {
            return;
        }

        for (BlockPos obsidianHoles : holeUtil.findObsidianHoles()) {
            if (rendermode.getValueString() == "Full") {
                RenderUtil.drawBoxOutline(RenderUtil.generateBB(obsidianHoles.getX(), obsidianHoles.getY(), obsidianHoles.getZ()), 0.3f, 0.3f, 0.3f, 0.5f);
            }

//            if (rendermode.getValueString() == "FullFrame") {
//                RenderUtil.drawBoxOutlineBlockPos(renderBlock, 0.3f, 0.3f, 0.3f, 0.5f);
//            }
//
//            if (rendermode.getValueString() == "Frame") {
//                RenderUtil.drawOutlineBlockPos(renderBlock, 0.3f, 0.3f, 0.3f, 0.5f);
//            }
        }

        for (BlockPos bedrockHoles : holeUtil.findBedrockHoles()) {
            if (rendermode.getValueString() == "Full") {
                RenderUtil.drawBoxOutline(RenderUtil.generateBB(bedrockHoles.getX(), bedrockHoles.getY(), bedrockHoles.getZ()), 0.3f, 0.3f, 0.3f, 0.5f);
            }

//            if (rendermode.getValueString() == "FullFrame") {
//                RenderUtil.drawBoxOutlineBlockPos(renderBlock, 0.3f, 0.3f, 0.3f, 0.5f);
//            }
//
//            if (rendermode.getValueString() == "Frame") {
//                RenderUtil.drawOutlineBlockPos(renderBlock, 0.3f, 0.3f, 0.3f, 0.5f);
//            }
        }
    }
}
