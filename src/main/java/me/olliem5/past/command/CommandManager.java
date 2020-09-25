package me.olliem5.past.command;

import me.olliem5.past.Past;
import me.olliem5.past.command.commands.BindCommand;
import me.olliem5.past.command.commands.HelpCommand;
import me.olliem5.past.command.commands.ModulesCommand;
import me.olliem5.past.command.commands.ToggleCommand;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashSet;

/* Credit: LittleDraily */
public class CommandManager {
    public static HashSet<Command> commands = new HashSet<>();

    public static void init() {
        commands.clear();
        commands.add(new ToggleCommand());
        commands.add(new BindCommand());
        commands.add(new ModulesCommand());
        commands.add(new HelpCommand());
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void chatEvent(ClientChatEvent event) {
        String[] args = event.getMessage().split(" ");
        if (event.getMessage().startsWith(Past.prefix)) {
            event.setCanceled(true);
            for (Command c : commands){
                if (args[0].equalsIgnoreCase(Past.prefix + c.getCommand())) {
                    c.onCommand(args);
                }
            }
        }
    }
}
