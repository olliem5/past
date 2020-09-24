package me.olliem5.past.module.modules.misc;

import me.olliem5.past.managers.DiscordManager;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;

public class DiscordRPC extends Module {
    public DiscordRPC() {
        super ("DiscordRPC", "Shows the client on discord", Category.MISC);
    }

    @Override
    public void onEnable() {  DiscordManager.startup(); }

    @Override
    public void onDisable() { DiscordManager.shutdown(); }
}
