package me.olliem5.past.module.modules.misc;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.util.ColourUtil;
import me.olliem5.past.util.MessageUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Mouse;

public class MCF extends Module {
    public MCF() {
        super ("MCF", "Allows you to middle click to add/del friends", Category.MISC);
    }

    @SubscribeEvent
    public void onMouse(InputEvent.MouseInputEvent event) {
        if (mc.objectMouseOver.typeOfHit.equals(RayTraceResult.Type.ENTITY) && mc.objectMouseOver.entityHit instanceof EntityPlayer && Mouse.getEventButton() == 2) {
            if (Past.friendsManager.isFriend(mc.objectMouseOver.entityHit.getName())) {
                Past.friendsManager.delFriend(mc.objectMouseOver.entityHit.getName());
                MessageUtil.sendFreindsMessage(ColourUtil.white + "Player" + " " + ColourUtil.aqua + mc.objectMouseOver.entityHit.getName() + " " + ColourUtil.white + "has been" + " " + ColourUtil.red + "removed" + " " + ColourUtil.white + "from the friends list");
            } else {
                Past.friendsManager.addFriend(mc.objectMouseOver.entityHit.getName());
                MessageUtil.sendFreindsMessage(ColourUtil.white + "Player" + " " + ColourUtil.aqua + mc.objectMouseOver.entityHit.getName() + " " + ColourUtil.white + "has been" + " " + ColourUtil.green + "added" + " " + ColourUtil.white + "to the friends list");
            }
        }
    }
}
