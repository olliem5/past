package me.olliem5.past.command.commands;

import me.olliem5.past.Past;
import me.olliem5.past.command.Command;
import me.olliem5.past.module.Module;
import me.olliem5.past.util.client.MessageUtil;
import me.olliem5.past.util.colour.ColourUtil;

public class ToggleCommand extends Command {
    public ToggleCommand() {
        super("toggle", "Allows you to toggle a module.","toggle" + " " + "[module]");
    }

    @Override
    public void runCommand(String[] args) {
        if (args.length > 1) {
            try {
                for (Module m : Past.moduleManager.getModules()) {
                    if (m.getName().equalsIgnoreCase(args[1])) {
                        m.toggle();
                        if (m.isToggled()) {
                            MessageUtil.sendMessagePrefix(ColourUtil.aqua + m.getName() + ColourUtil.white + " has been " + ColourUtil.green + "enabled");
                        } else {
                            MessageUtil.sendMessagePrefix(ColourUtil.aqua + m.getName() + ColourUtil.white + " has been " + ColourUtil.red + "disabled");
                        }
                    }
                }
            } catch (Exception e) {}
        }
    }
}
