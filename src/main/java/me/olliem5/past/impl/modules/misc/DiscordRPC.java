package me.olliem5.past.impl.modules.misc;

import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.util.client.DiscordUtil;

public class DiscordRPC extends Module {
    public DiscordRPC() {
        super("DiscordRPC", "Shows the client on discord", Category.MISC);
    }

    @Override
    public void onEnable() {
        DiscordUtil.startup();
    }

    @Override
    public void onDisable() {
        DiscordUtil.shutdown();
    }
}
