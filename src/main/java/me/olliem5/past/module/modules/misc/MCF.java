package me.olliem5.past.module.modules.misc;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.util.client.MessageUtil;
import me.olliem5.past.util.colour.ColourUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Mouse;

public class MCF extends Module {
    public MCF() {
        super("MCF", "Allows you to middle click to add/del friends", Category.MISC);
    }

    private boolean hasClicked = false;

    public void onUpdate() {

        if (!Mouse.isButtonDown(2)) {
            hasClicked = false;
            return;
        }

        if (!hasClicked) {

            hasClicked = true;

            final RayTraceResult result = mc.objectMouseOver;
            Entity player = result.entityHit;

            if (result == null || result.typeOfHit != RayTraceResult.Type.ENTITY || !(result.entityHit instanceof EntityPlayer)) return;

            if (Past.friendsManager.isFriend(player.getName())) {
                Past.friendsManager.delFriend(mc.objectMouseOver.entityHit.getName());
                MessageUtil.sendFreindsMessage(ColourUtil.white + "Player" + " " + ColourUtil.aqua + mc.objectMouseOver.entityHit.getName() + " " + ColourUtil.white + "has been" + " " + ColourUtil.red + "removed" + " " + ColourUtil.white + "from the friends list");
            } else {
                Past.friendsManager.addFriend(mc.objectMouseOver.entityHit.getName());
                MessageUtil.sendFreindsMessage(ColourUtil.white + "Player" + " " + ColourUtil.aqua + mc.objectMouseOver.entityHit.getName() + " " + ColourUtil.white + "has been" + " " + ColourUtil.green + "added" + " " + ColourUtil.white + "to the friends list");
            }
        }
    }
}
