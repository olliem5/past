package me.olliem5.past.command.commands;

import me.olliem5.past.command.Command;
import me.olliem5.past.util.colour.ColourUtil;
import me.olliem5.past.util.client.MessageUtil;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help", "Shows you all the client commands you can do, as well as other useful tips.");
    }

    @Override
    public void onCommand(String[] args) {
        MessageUtil.sendMessagePrefix(ColourUtil.red + "Past Client" + ColourUtil.gray + " - " + ColourUtil.red + "Help");
        MessageUtil.sendMessagePrefix(ColourUtil.white + "Available Commands:");
        MessageUtil.sendMessagePrefix(ColourUtil.aqua + ".bind" + " " + ColourUtil.gray + "[" + ColourUtil.red + "Bind a module to a set key" + ColourUtil.gray + "]" + " " + ColourUtil.gray + " - " + ColourUtil.white + "Usage (.bind <module> <key>)");
        MessageUtil.sendRawMessage(ColourUtil.gray + "-");
        MessageUtil.sendMessagePrefix(ColourUtil.aqua + ".toggle" + " " + ColourUtil.gray + "[" + ColourUtil.red + "Toggle a specified module" + ColourUtil.gray + "]" + " " + ColourUtil.gray + " - " + ColourUtil.white + "Usage (.toggle <module>)");
        MessageUtil.sendRawMessage(ColourUtil.gray + "-");
        MessageUtil.sendMessagePrefix(ColourUtil.aqua + ".modules" + " " + ColourUtil.gray + "[" + ColourUtil.red + "Gives you a list of all client modules" + ColourUtil.gray + "]" + " " + ColourUtil.gray + " - " + ColourUtil.white + "Usage (.modules)");
    }
}
