package me.olliem5.past.impl.modules.misc;

import com.mojang.authlib.GameProfile;
import me.olliem5.past.Past;
import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.module.ModuleInfo;
import me.olliem5.past.api.setting.Setting;
import net.minecraft.client.entity.EntityOtherPlayerMP;

import java.util.ArrayList;
import java.util.UUID;

@ModuleInfo(name = "FakePlayer", description = "Summons a fake player, usually for testing other modules", category = Category.MISC)
public class FakePlayer extends Module {

    Setting name;

    private ArrayList<String> names;

    private EntityOtherPlayerMP fakePlayer;

    @Override
    public void setup() {
        names = new ArrayList<>();
        names.add("_o_b_a_m_a_");
        names.add("LittleDraily");
        names.add("You");

        Past.settingsManager.registerSetting(name = new Setting("Name", "FakePlayerName", this, names, "_o_b_a_m_a_"));
    }

    public void onEnable() {
        if (mc.world == null) return;

        generateFakePlayer();

        fakePlayer.copyLocationAndAnglesFrom(mc.player);
        fakePlayer.rotationYawHead = mc.player.rotationYawHead;

        mc.world.addEntityToWorld(69420, fakePlayer);
    }

    private void generateFakePlayer() {
        if (name.getValueString() == "_o_b_a_m_a_") {
            fakePlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("8deac414-6c37-44fb-82bd-6873efc1b0cf"), "_o_b_a_m_a_"));
        } else if (name.getValueString() == "LittleDraily") {
            fakePlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("d4b0faa7-368d-4656-a60b-f859ba0f7853"), "LittleDraily"));
        } else {
            fakePlayer = new EntityOtherPlayerMP(mc.world, mc.getSession().getProfile());
        }
    }

    @Override
    public void onDisable() {
        mc.world.removeEntityFromWorld(69420);
    }
}
