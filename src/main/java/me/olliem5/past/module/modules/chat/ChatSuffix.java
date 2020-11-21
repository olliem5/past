package me.olliem5.past.module.modules.chat;

import me.olliem5.past.Past;
import me.olliem5.past.event.events.PacketEvent;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.ColourUtil;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketChatMessage;

import java.util.ArrayList;

public class ChatSuffix extends Module {
    public ChatSuffix() {
        super("ChatSuffix", "Adds a custom ending to your messages", Category.CHAT);
    }

    Setting suffixmode;
    Setting blue;
    Setting green;

    @Override
    public void setup() {
        ArrayList<String> modes = new ArrayList<>();
        modes.add("Classic");
        modes.add("Version");

        Past.settingsManager.registerSetting(suffixmode = new Setting("Suffix", "ChatSuffixMode", this, modes, "Classic"));
        Past.settingsManager.registerSetting(blue = new Setting("Blue Suffix", "ChatSuffixBlue", false, this));
        Past.settingsManager.registerSetting(green = new Setting("Green Suffix", "ChatSuffixGreen", false, this));
    }

    private String classicsuffix = " \uff30\uff41\uff53\uff54";
    private String versionsuffix = " \uff30\uff41\uff53\uff54" + " " + Past.version;

    @EventHandler
    public Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketChatMessage) {

            String s = ((CPacketChatMessage) event.getPacket()).getMessage();

            if (s.startsWith("/") || s.startsWith(Past.prefix)) {
                return;
            }

            if (suffixmode.getValueString() == "Classic") {
                if (blue.getValBoolean()) {
                    s += " `" + classicsuffix;
                } else if (green.getValBoolean()) {
                    s += " >" + classicsuffix;
                } else {
                    s += classicsuffix;
                }
            }

            if (suffixmode.getValueString() == "Version") {
                if (blue.getValBoolean()) {
                    s += " `" + versionsuffix;
                } else if (green.getValBoolean()) {
                    s += " >" + versionsuffix;
                } else {
                    s += versionsuffix;
                }
            }

            if (s.length() >= 256) {
                s = s.substring(0, 256);
            }

            ((CPacketChatMessage) event.getPacket()).message = s;
        }
    });

    public String getArraylistInfo() {
        return ColourUtil.gray + " " + suffixmode.getValueString().toUpperCase();
    }
}
