package me.ollieobama.past.module.modules.chat;

import me.ollieobama.past.Past;
import me.ollieobama.past.module.Category;
import me.ollieobama.past.module.Module;
import net.minecraft.client.gui.GuiChat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class PrefixChat extends Module {
    public PrefixChat() {
        super ("PrefixChat", "Opens chat when the prefix is pressed", Category.CHAT);
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if (mc.currentScreen == null && Keyboard.isKeyDown(Past.prefixchatkey)) {
            mc.displayGuiScreen(new GuiChat(Past.prefix));
        }
    }
}
