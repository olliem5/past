package me.olliem5.past.module.modules.misc;

import com.mojang.authlib.GameProfile;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;

import java.util.UUID;

public class FakePlayer extends Module {
    public FakePlayer() {
        super("FakePlayer", "Creates a fake player, usually for testing other modules", Category.MISC);
    }

    public void onEnable() {
        if (mc.world == null) {
            return;
        }
        EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("873e2766-9254-49bc-89d7-5d4d585ad29d"), "Obama"));
        fakePlayer.copyLocationAndAnglesFrom(mc.player);
        fakePlayer.rotationYawHead = mc.player.rotationYawHead;
        mc.world.addEntityToWorld(69420, fakePlayer);
    }

    @Override
    public void onDisable() {
        mc.world.removeEntityFromWorld(69420);
    }
}
