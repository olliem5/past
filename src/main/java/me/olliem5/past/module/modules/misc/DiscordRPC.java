package me.olliem5.past.module.modules.misc;

import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.util.DiscordUtil;

public class DiscordRPC extends Module {
    public DiscordRPC() {
        super ("DiscordRPC", "Shows the client on discord", Category.MISC);
    }

    @Override
    public void onEnable() {  DiscordUtil.startup(); }

    @Override
    public void onDisable() { DiscordUtil.shutdown(); }
}
