package me.olliem5.past.module.modules.chat;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.ColourUtil;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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

    @SubscribeEvent
    public void onChat(ClientChatEvent event) {

        String suffix = "";

        if (suffixmode.getValueString() == "Classic") {
            suffix = " \uff30\uff41\uff53\uff54";
        }

        if (suffixmode.getValueString() == "Version") {
            suffix = " \uff30\uff41\uff53\uff54" + " " + Past.version;
        }

        if (blue.getValBoolean()) {
            event.setMessage(event.getMessage() + " `" + suffix);
        } else if (green.getValBoolean()) {
            event.setMessage(event.getMessage() + " >" + suffix);
        } else {
            event.setMessage(event.getMessage() + suffix);
        }

        if (event.getMessage().startsWith("/")) {
            return;
        }

        if (event.getMessage().startsWith(Past.prefix)) {
            return;
        }
    }

    public String getArraylistInfo() {
        return ColourUtil.gray + " " + suffixmode.getValueString().toUpperCase();
    }
}
