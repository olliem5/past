package me.olliem5.past.command.commands;

import me.olliem5.past.Past;
import me.olliem5.past.command.Command;
import me.olliem5.past.module.Module;
import me.olliem5.past.util.colour.ColourUtil;
import me.olliem5.past.util.client.MessageUtil;

public class ModulesCommand extends Command {
    public ModulesCommand() {
        super("modules", "Lists available modules in the client.");
    }

    @Override
    public void onCommand(String[] args) {
        MessageUtil.sendMessagePrefix(ColourUtil.red + "Past Client" + ColourUtil.gray + " - " + ColourUtil.red + "Available Modules");
        for (Module module : Past.moduleManager.getModules()) {
            MessageUtil.sendMessagePrefix(ColourUtil.aqua + module.getName() + ColourUtil.gray + " - " + ColourUtil.white + module.getDescription());
        }
    }
}
