package me.ollieobama.past.module.modules.chat;

import me.ollieobama.past.Past;
import me.ollieobama.past.module.Category;
import me.ollieobama.past.module.Module;
import me.ollieobama.past.settings.Setting;
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
        Past.settingsManager.rSetting(blue = new Setting("Blue", this, false));
        Past.settingsManager.rSetting(green = new Setting("Green", this, false));
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
