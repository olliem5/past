package me.olliem5.past.command.commands;

import me.olliem5.past.Past;
import me.olliem5.past.command.Command;
import me.olliem5.past.module.Module;
import me.olliem5.past.util.client.MessageUtil;
import me.olliem5.past.util.colour.ColourUtil;
import org.lwjgl.input.Keyboard;

public class BindCommand extends Command {
    public BindCommand() {
        super("bind", "Binds modules to a key.","bind" + " " + "[module]" + " " + "[key]");
    }

    @Override
    public void runCommand(String[] args) {
        if (args.length > 2) {
            for (Module module : Past.moduleManager.getModules()) {
                if (module.getName().equalsIgnoreCase(args[1])) {
                    try {
                        module.setKey(Keyboard.getKeyIndex(args[2].toUpperCase()));
                        MessageUtil.sendMessagePrefix(ColourUtil.aqua + module.getName() + ColourUtil.white + " is now bound to " + ColourUtil.red + args[2].toUpperCase() + ColourUtil.gray + " (" + ColourUtil.white + Keyboard.getKeyIndex(args[2].toUpperCase()) + ColourUtil.gray + ")");
                    } catch (Exception e) {
                        MessageUtil.sendMessagePrefix(ColourUtil.red + "Something went wrong, yikes!");
                    }
                }
            }
        }
    }
}
