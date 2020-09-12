package me.ollieobama.past.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ollieobama.past.Past;
import me.ollieobama.past.managers.MessageManager;
import me.ollieobama.past.module.Module;
import me.ollieobama.past.command.Command;
import org.lwjgl.input.Keyboard;

/* Credit: LittleDraily */
public class Bind extends Command {
    public Bind() {
        super("bind", new String[]{"b", "bind"});
    }
    @Override
    public void onCommand(String[] args) {
        if (args.length > 2) {
            try {
                for (Module m: Past.moduleManager.getModules()) {
                    if (m.getName().equalsIgnoreCase(args[1])) {
                        try {
                            m.setKey(Keyboard.getKeyIndex(args[2].toUpperCase()));
                            MessageManager.sendMessagePrefix(ChatFormatting.AQUA + m.getName() + ChatFormatting.WHITE + " is now binded to " + ChatFormatting.RED + args[2].toUpperCase() + ChatFormatting.GRAY + " (" + ChatFormatting.WHITE + Keyboard.getKeyIndex(args[2].toUpperCase() + "") + ChatFormatting.GRAY + ")");
                        } catch (Exception e) {
                            MessageManager.sendMessagePrefix(ChatFormatting.RED + m.getName() + ChatFormatting.WHITE + " Something went wrong :(");
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
