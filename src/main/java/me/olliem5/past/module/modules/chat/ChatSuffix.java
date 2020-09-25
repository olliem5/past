package me.olliem5.past.module.modules.chat;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatSuffix extends Module {
    public ChatSuffix() {
        super ("ChatSuffix", "Adds a custom ending to your messages", Category.CHAT);
    }

    Setting blue;
    Setting green;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(blue = new Setting("Blue", false, this));
        Past.settingsManager.registerSetting(green = new Setting("Green", true, this));
    }

    @SubscribeEvent
    public void onChat(ClientChatEvent event) {

        if (blue.getValBoolean()) { event.setMessage(event.getMessage() + " ` \uff30\uff41\uff53\uff54"); }
        else if (green.getValBoolean()) { event.setMessage(event.getMessage() + " > \uff30\uff41\uff53\uff54"); }
        else { event.setMessage(event.getMessage() + " \uff30\uff41\uff53\uff54"); }

        if (event.getMessage().startsWith("/")) { return; }
        if (event.getMessage().startsWith(Past.prefix)) { return; }
    }
}
