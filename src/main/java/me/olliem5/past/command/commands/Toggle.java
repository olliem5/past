package me.olliem5.past.command.commands;

import me.olliem5.past.Past;
import me.olliem5.past.managers.MessageManager;
import me.olliem5.past.module.Module;
import net.minecraft.util.text.TextFormatting;
import me.olliem5.past.command.Command;

/* Credit: LittleDraily */
public class Toggle extends Command {
    public Toggle() {
        super ("Toggle", new String[]{"toggle"});
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
