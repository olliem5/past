package me.olliem5.past.command.commands;

import me.olliem5.past.command.Command;
import me.olliem5.past.util.ColourManager;
import me.olliem5.past.util.MessageManager;

public class HelpCommand extends Command {
    public HelpCommand() {
        super ("help", "Shows you all the client commands you can do, as well as other useful tips.");
    }

    @Override
    public void onCommand(String[] args) {
        MessageManager.sendMessagePrefix(ColourManager.red + "Past Client" + ColourManager.gray + " - " + ColourManager.red + "Help");
        MessageManager.sendMessagePrefix(ColourManager.white + "Available Commands:");
        MessageManager.sendMessagePrefix(ColourManager.aqua + ".bind" + " " + ColourManager.gray + "[" + ColourManager.red + "Bind a module to a set key" + ColourManager.gray + "]" + " " + ColourManager.gray + " - " + ColourManager.white + "Usage (.bind <module> <key>)");
        MessageManager.sendRawMessage(ColourManager.gray + "-");
        MessageManager.sendMessagePrefix(ColourManager.aqua + ".toggle" + " " + ColourManager.gray + "[" + ColourManager.red + "Toggle a specified module" + ColourManager.gray + "]" + " " + ColourManager.gray + " - " + ColourManager.white + "Usage (.toggle <module>)");
        MessageManager.sendRawMessage(ColourManager.gray + "-");
        MessageManager.sendMessagePrefix(ColourManager.aqua + ".modules" + " " + ColourManager.gray + "[" + ColourManager.red + "Gives you a list of all client modules" + ColourManager.gray + "]" + " " + ColourManager.gray + " - " + ColourManager.white + "Usage (.modules)");
    }
}
