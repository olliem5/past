package me.olliem5.past.impl.modules.render;

import me.olliem5.past.Past;
import me.olliem5.past.impl.events.PacketEvent;
import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.setting.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketSpawnExperienceOrb;
import net.minecraft.network.play.server.SPacketSpawnPainting;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;

public class NoRender extends Module {
    public NoRender() {
        super("NoRender", "Disables the rendering of certain things", Category.RENDER);
    }

    Setting fire;
    Setting armour;
    Setting bossbar;
    Setting hurtcam;
    Setting xp;
    Setting paintings;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(fire = new Setting("Fire", "NoRenderFire", true, this));
        Past.settingsManager.registerSetting(armour = new Setting("Armour", "NoRenderArmour", false, this));
        Past.settingsManager.registerSetting(bossbar = new Setting("Boss Bar", "NoRenderBossBar", false, this));
        Past.settingsManager.registerSetting(hurtcam = new Setting("Hurt Cam", "NoRenderHurtCam", true, this));
        Past.settingsManager.registerSetting(xp = new Setting("XP", "NoRenderXP", false, this));
        Past.settingsManager.registerSetting(paintings = new Setting("Paintings", "NoRenderPaintings", false, this));
    }

    @EventHandler
    public Listener<PacketEvent.Send> packetSendListener = new Listener<>(event -> {
        Packet packet = event.getPacket();

        if (packet instanceof SPacketSpawnExperienceOrb && xp.getValBoolean() || packet instanceof SPacketSpawnPainting && paintings.getValBoolean()) {
            event.cancel();
        }
    });

    @EventHandler
    public Listener<RenderBlockOverlayEvent> renderBlockOverlayEventListener = new Listener<>(event -> {
        if (fire.getValBoolean() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.FIRE) {
            event.setCanceled(true);
        }
    });
}
