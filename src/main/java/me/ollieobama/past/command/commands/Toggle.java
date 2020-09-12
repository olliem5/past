package me.ollieobama.past.command.commands;

import me.ollieobama.past.Past;
import me.ollieobama.past.managers.MessageManager;
import me.ollieobama.past.module.Module;
import net.minecraft.util.text.TextFormatting;
import me.ollieobama.past.command.Command;

/* Credit: LittleDraily */
public class Toggle extends Command {
    public Toggle() {
        super("Toggle", new String[]{"t", "toggle"});
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length > 1) {
            try {
            for (Module m: Past.moduleManager.getModules()) {
                if (m.getName().equalsIgnoreCase(args[1])) {
                    m.toggle();
                    if (m.isToggled()) {
                        MessageManager.sendMessagePrefix(TextFormatting.AQUA + m.getName() + TextFormatting.WHITE + " is now " + TextFormatting.GREEN + "ON");
                    } else {
                        MessageManager.sendMessagePrefix(TextFormatting.AQUA + m.getName() + TextFormatting.WHITE + " is now " + TextFormatting.RED + "OFF");
                    }
                }
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
