package me.olliem5.past.module.modules.misc;

import com.mojang.authlib.GameProfile;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.UUID;

public class FakePlayer extends Module {
    public FakePlayer() {
        super ("FakePlayer", "Creates a fake player, usually for testing other modules", Category.MISC);
    }

    public void onEnable() {
        if (mc.world == null) { return; }
        EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP((World)mc.world, new GameProfile(UUID.fromString("0f75a81d-70e5-43c5-b892-f33c524284f2"), "popbob"));
        fakePlayer.copyLocationAndAnglesFrom((Entity)mc.player);
        fakePlayer.rotationYawHead = mc.player.rotationYawHead;
        mc.world.addEntityToWorld(-100, (Entity)fakePlayer);
    }

    @Override
    public void onDisable() {
        mc.world.removeEntityFromWorld(-100);
    }
}
