package me.olliem5.past.util.module;

import me.olliem5.past.Past;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HoleUtil {
    protected static Minecraft mc = Minecraft.getMinecraft();

    public boolean isInObsidianHole(BlockPos blockPos) {
        BlockPos boost = blockPos.add(0, 1, 0);
        BlockPos boost2 = blockPos.add(0, 0, 0);
        BlockPos boost3 = blockPos.add(0, 0, -1);
        BlockPos boost4 = blockPos.add(1, 0, 0);
        BlockPos boost5 = blockPos.add(-1, 0, 0);
        BlockPos boost6 = blockPos.add(0, 0, 1);
        BlockPos boost7 = blockPos.add(0, 2, 0);
        BlockPos boost8 = blockPos.add(0.5, 0.5, 0.5);
        BlockPos boost9 = blockPos.add(0, -1, 0);

        return !(mc.world.getBlockState(boost).getBlock() != Blocks.AIR || this.isInBedrockHole(blockPos) || mc.world.getBlockState(boost2).getBlock() != Blocks.AIR || mc.world.getBlockState(boost7).getBlock() != Blocks.AIR || mc.world.getBlockState(boost3).getBlock() != Blocks.OBSIDIAN && mc.world.getBlockState(boost3).getBlock() != Blocks.BEDROCK || mc.world.getBlockState(boost4).getBlock() != Blocks.OBSIDIAN && mc.world.getBlockState(boost4).getBlock() != Blocks.BEDROCK || mc.world.getBlockState(boost5).getBlock() != Blocks.OBSIDIAN && mc.world.getBlockState(boost5).getBlock() != Blocks.BEDROCK || mc.world.getBlockState(boost6).getBlock() != Blocks.OBSIDIAN && mc.world.getBlockState(boost6).getBlock() != Blocks.BEDROCK || mc.world.getBlockState(boost8).getBlock() != Blocks.AIR || mc.world.getBlockState(boost9).getBlock() != Blocks.OBSIDIAN && mc.world.getBlockState(boost9).getBlock() != Blocks.BEDROCK);
    }

    public boolean isInBedrockHole(BlockPos blockPos) {
        BlockPos boost = blockPos.add(0, 1, 0);
        BlockPos boost2 = blockPos.add(0, 0, 0);
        BlockPos boost3 = blockPos.add(0, 0, -1);
        BlockPos boost4 = blockPos.add(1, 0, 0);
        BlockPos boost5 = blockPos.add(-1, 0, 0);
        BlockPos boost6 = blockPos.add(0, 0, 1);
        BlockPos boost7 = blockPos.add(0, 2, 0);
        BlockPos boost8 = blockPos.add(0.5, 0.5, 0.5);
        BlockPos boost9 = blockPos.add(0, -1, 0);

        return mc.world.getBlockState(boost).getBlock() == Blocks.AIR && mc.world.getBlockState(boost2).getBlock() == Blocks.AIR && mc.world.getBlockState(boost7).getBlock() == Blocks.AIR && mc.world.getBlockState(boost3).getBlock() == Blocks.BEDROCK && mc.world.getBlockState(boost4).getBlock() == Blocks.BEDROCK && mc.world.getBlockState(boost5).getBlock() == Blocks.BEDROCK && mc.world.getBlockState(boost6).getBlock() == Blocks.BEDROCK && mc.world.getBlockState(boost8).getBlock() == Blocks.AIR && mc.world.getBlockState(boost9).getBlock() == Blocks.BEDROCK;
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }

    //TODO: Not make range specific to HoleESP

    public List<BlockPos> findObsidianHoles() {
        NonNullList positions = NonNullList.create();
        positions.addAll(this.getSphere(getPlayerPos(), Past.settingsManager.getSettingID("HoleESPHoleRange").getValueInt(), Past.settingsManager.getSettingID("HoleESPHoleRange").getValueInt(), false, true, 0).stream().filter(this::isInObsidianHole).collect(Collectors.toList()));
        return positions;
    }

    public List<BlockPos> findBedrockHoles() {
        NonNullList positions = NonNullList.create();
        positions.addAll(this.getSphere(getPlayerPos(), Past.settingsManager.getSettingID("HoleESPHoleRange").getValueInt(), Past.settingsManager.getSettingID("HoleESPHoleRange").getValueInt(), false, true, 0).stream().filter(this::isInBedrockHole).collect(Collectors.toList()));
        return positions;
    }

    public List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        ArrayList<BlockPos> circleblocks = new ArrayList<>();
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        int x = cx - (int)r;
        while ((float)x <= (float)cx + r) {
            int z = cz - (int)r;
            while ((float)z <= (float)cz + r) {
                int y = sphere ? cy - (int)r : cy;
                do {
                    float f = sphere ? (float)cy + r : (float)(cy + h);
                    if (!((float)y < f)) break;
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (!(!(dist < (double)(r * r)) || hollow && dist < (double)((r - 1.0f) * (r - 1.0f)))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                    ++y;
                } while (true);
                ++z;
            }
            ++x;
        }
        return circleblocks;
    }
}
