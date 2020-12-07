package me.olliem5.past.command.commands;

import me.olliem5.past.Past;
import me.olliem5.past.command.Command;
import me.olliem5.past.module.Module;
import me.olliem5.past.util.client.MessageUtil;
import me.olliem5.past.util.colour.ColourUtil;
import org.lwjgl.input.Keyboard;

/* Credit: LittleDraily */
public class BindCommand extends Command {
    public BindCommand() {
        super("bind", "Binds modules to a set key.");
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length > 2) {
            try {
                for (Module m : Past.moduleManager.getModules()) {
                    if (m.getName().equalsIgnoreCase(args[1])) {
                        try {
                            m.setKey(Keyboard.getKeyIndex(args[2].toUpperCase()));
                            MessageUtil.sendMessagePrefix(ColourUtil.aqua + m.getName() + ColourUtil.white + " is now bound to " + ColourUtil.red + args[2].toUpperCase() + ColourUtil.gray + " (" + ColourUtil.white + Keyboard.getKeyIndex(args[2].toUpperCase() + "") + ColourUtil.gray + ")");
                        } catch (Exception e) {
                            MessageUtil.sendMessagePrefix(ColourUtil.red + m.getName() + ColourUtil.white + " Something went wrong :(");
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {}
        }
    }
}
