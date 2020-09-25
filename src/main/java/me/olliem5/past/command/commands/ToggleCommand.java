package me.olliem5.past.command.commands;

import me.olliem5.past.Past;
import me.olliem5.past.util.ColourManager;
import me.olliem5.past.util.MessageManager;
import me.olliem5.past.module.Module;
import me.olliem5.past.command.Command;

/* Credit: LittleDraily */
public class ToggleCommand extends Command {
    public ToggleCommand() {
        super ("toggle", "Allows you to toggle a module.");
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length > 1) {
            try {
            for (Module m: Past.moduleManager.getModules()) {
                if (m.getName().equalsIgnoreCase(args[1])) {
                    m.toggle();
                    if (m.isToggled()) {
                        MessageManager.sendMessagePrefix(ColourManager.aqua + m.getName() + ColourManager.white + " is now " + ColourManager.green + "ON");
                    } else {
                        MessageManager.sendMessagePrefix(ColourManager.aqua + m.getName() + ColourManager.white + " is now " + ColourManager.red + "OFF");
                    }
                }
            }
            } catch (Exception e) {}
        }
    }
}
