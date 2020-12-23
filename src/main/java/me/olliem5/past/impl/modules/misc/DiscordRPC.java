package me.olliem5.past.impl.modules.misc;

import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.module.ModuleInfo;
import me.olliem5.past.api.util.client.DiscordUtil;

@ModuleInfo(name = "DiscordRPC", description = "Shows off Past on discord", category = Category.MISC)
public class DiscordRPC extends Module {

    /**
     * TODO: Customisation
     */

    @Override
    public void onEnable() {
        DiscordUtil.startup();
    }

    @Override
    public void onDisable() {
        DiscordUtil.shutdown();
    }
}
