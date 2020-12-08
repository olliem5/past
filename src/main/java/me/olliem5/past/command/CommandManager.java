package me.olliem5.past.command;

import me.olliem5.past.Past;
import me.olliem5.past.command.commands.BindCommand;
import me.olliem5.past.command.commands.ToggleCommand;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraftforge.client.event.ClientChatEvent;

import java.util.ArrayList;

public class CommandManager {

    public String prefix = "@";

    public CommandManager() {
        Past.EVENT_BUS.subscribe(this);
        init();
    }

    public static ArrayList<Command> commands = new ArrayList<>();

    public static void init() {
        commands.add(new ToggleCommand());
        commands.add(new BindCommand());
    }

    @EventHandler
    public Listener<ClientChatEvent> listener = new Listener<>(event -> {
        String[] args = event.getMessage().split(" ");
        if (event.getMessage().startsWith(prefix)) {
            event.setCanceled(true);
            for (Command command : commands) {
                if (args[0].equalsIgnoreCase(prefix + command.getName())) {
                    command.runCommand(args);
                }
            }
        }
    });
}
