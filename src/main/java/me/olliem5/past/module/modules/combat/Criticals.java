package me.olliem5.past.module.modules.combat;

import me.olliem5.past.Past;
import me.olliem5.past.event.events.PacketEvent;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.ColourUtil;
import me.olliem5.past.util.CooldownUtil;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;

import java.util.ArrayList;

public class Criticals extends Module {
    public Criticals() {
        super("Criticals", "Makes every attack a critical", Category.COMBAT);
    }

    Setting mode;

    CooldownUtil timer = new CooldownUtil();

    private ArrayList<String> criticalmodes;

    @Override
    public void setup() {
        criticalmodes = new ArrayList<>();
        criticalmodes.add("Packet");
        criticalmodes.add("2b2t");
        criticalmodes.add("Bypass");
        criticalmodes.add("Jump");

        Past.settingsManager.registerSetting(mode = new Setting("Mode", "CriticalsMode", this, criticalmodes, "Bypass"));
    }

    @EventHandler
    private Listener<PacketEvent.Send> sendListener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketUseEntity) {
            if (((CPacketUseEntity) event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && mc.player.onGround) {
                if (mode.getValueString() == "Packet") {
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1f, mc.player.posZ, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                }
                if (mode.getValueString() == "2b2t") {
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.11, mc.player.posZ, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.11, mc.player.posZ, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1100013579, mc.player.posZ, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1100013579, mc.player.posZ, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1100013579, mc.player.posZ, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1100013579, mc.player.posZ, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                }
                if (mode.getValueString() == "Bypass") {
                    if (this.mc.player.fallDistance > 0.0f) {
                        return;
                    } if (this.mc.player.isInLava() || this.mc.player.isInWater()) {
                        return;
                    } if (this.timer.passed(1000)) {
                        this.timer.reset();
                        this.mc.player.connection.sendPacket(new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 0.11, this.mc.player.posZ, false));
                        this.mc.player.connection.sendPacket(new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 0.1100013579, this.mc.player.posZ, false));
                        this.mc.player.connection.sendPacket(new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 1.3579E-6, this.mc.player.posZ, false));
                    }
                }
                if (mode.getValueString() == "Jump") {
                    mc.player.jump();
                }
            }
        }
    });

    public String getArraylistInfo() {
        return ColourUtil.gray + " " + mode.getValueString().toUpperCase();
    }
}
