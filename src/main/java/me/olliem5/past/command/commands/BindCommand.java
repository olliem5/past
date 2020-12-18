package me.olliem5.past.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.olliem5.past.Past;
import me.olliem5.past.command.Command;
import me.olliem5.past.module.Module;
import me.olliem5.past.util.client.MessageUtil;
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
                        MessageUtil.sendMessagePrefix(ChatFormatting.AQUA + module.getName() + ChatFormatting.WHITE + " is now bound to " + ChatFormatting.RED + args[2].toUpperCase() + ChatFormatting.GRAY + " (" + ChatFormatting.WHITE + Keyboard.getKeyIndex(args[2].toUpperCase()) + ChatFormatting.GRAY + ")");
                    } catch (Exception e) {
                        MessageUtil.sendMessagePrefix(ChatFormatting.RED + "Something went wrong, yikes!");
                    }
                }
            }
        }
    }
}
