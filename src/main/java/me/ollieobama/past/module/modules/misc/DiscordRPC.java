package me.ollieobama.past.module.modules.misc;

import me.ollieobama.past.managers.DiscordManager;
import me.ollieobama.past.module.Category;
import me.ollieobama.past.module.Module;

public class DiscordRPC extends Module {
    public DiscordRPC() {
        super ("DiscordRPC", "Shows the client on discord", Category.MISC);
    }

    @Override
    public void onEnable() {  DiscordManager.startup(); }

    @Override
    public void onDisable() { DiscordManager.shutdown(); }
}
