package me.olliem5.past.impl.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.olliem5.past.Past;
import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.module.ModuleInfo;
import me.olliem5.past.api.util.client.MessageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Mouse;

@ModuleInfo(name = "MCF", description = "Allows you to middle click to add/del friends", category = Category.MISC)
public class MCF extends Module {

    /**
     * TODO: Make this a MiddleClick module with modes: friend, pearl
     */

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
                MessageUtil.sendFreindsMessage(ChatFormatting.WHITE + "Player" + " " + ChatFormatting.AQUA + mc.objectMouseOver.entityHit.getName() + " " + ChatFormatting.WHITE + "has been" + " " + ChatFormatting.RED + "removed" + " " + ChatFormatting.WHITE + "from the friends list");
            } else {
                Past.friendsManager.addFriend(mc.objectMouseOver.entityHit.getName());
                MessageUtil.sendFreindsMessage(ChatFormatting.WHITE + "Player" + " " + ChatFormatting.AQUA + mc.objectMouseOver.entityHit.getName() + " " + ChatFormatting.WHITE + "has been" + " " + ChatFormatting.GREEN + "added" + " " + ChatFormatting.WHITE + "to the friends list");
            }
        }
    }
}
