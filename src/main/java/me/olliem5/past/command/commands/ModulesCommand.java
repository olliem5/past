package me.olliem5.past.command.commands;

import me.olliem5.past.Past;
import me.olliem5.past.command.Command;
import me.olliem5.past.module.Module;
import me.olliem5.past.util.ColourManager;
import me.olliem5.past.util.MessageManager;

public class ModulesCommand extends Command {
    public ModulesCommand() {
        super ("modules", "Lists available modules in the client.");
    }

    @Override
    public void onCommand(String[] args) {
        MessageManager.sendMessagePrefix(ColourManager.red + "Past Client" + ColourManager.gray + " - " + ColourManager.red + "Available Modules");
        for (Module module : Past.moduleManager.getModules()) {
            MessageManager.sendMessagePrefix(ColourManager.aqua + module.getName() + ColourManager.gray + " - " + ColourManager.white + module.getDescription());
        }
    }
}
