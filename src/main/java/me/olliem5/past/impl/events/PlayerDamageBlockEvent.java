package me.olliem5.past.impl.events;

import me.olliem5.past.api.event.Event;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class PlayerDamageBlockEvent extends Event {
    private BlockPos blockPos;
    private EnumFacing enumFacing;

    public PlayerDamageBlockEvent(BlockPos posBlock, EnumFacing directionFacing) {
        blockPos = posBlock;
        setDirection(directionFacing);
    }

    public BlockPos getPos() {
        return blockPos;
    }

    public EnumFacing getDirection() {
        return enumFacing;
    }

    public void setDirection(EnumFacing direction) {
        enumFacing = direction;
    }
}
