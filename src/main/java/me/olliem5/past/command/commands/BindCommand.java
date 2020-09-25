package me.olliem5.past.command.commands;

import me.olliem5.past.Past;
import me.olliem5.past.util.ColourManager;
import me.olliem5.past.util.MessageManager;
import me.olliem5.past.module.Module;
import me.olliem5.past.command.Command;
import org.lwjgl.input.Keyboard;

/* Credit: LittleDraily */
public class BindCommand extends Command {
    public BindCommand() {
        super ("bind", "Binds modules to a set key.");
    }
    @Override
    public void onCommand(String[] args) {
        if (args.length > 2) {
            try {
                for (Module m: Past.moduleManager.getModules()) {
                    if (m.getName().equalsIgnoreCase(args[1])) {
                        try {
                            m.setKey(Keyboard.getKeyIndex(args[2].toUpperCase()));
                            MessageManager.sendMessagePrefix(ColourManager.aqua + m.getName() + ColourManager.white + " is now binded to " + ColourManager.red + args[2].toUpperCase() + ColourManager.gray + " (" + ColourManager.white + Keyboard.getKeyIndex(args[2].toUpperCase() + "") + ColourManager.gray + ")");
                        } catch (Exception e) {
                            MessageManager.sendMessagePrefix(ColourManager.red + m.getName() + ColourManager.white + " Something went wrong :(");
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {}
        }
    }
}
