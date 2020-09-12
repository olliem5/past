package me.ollieobama.past.command;

import me.ollieobama.past.Past;
import me.ollieobama.past.command.commands.Bind;
import me.ollieobama.past.command.commands.Toggle;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashSet;

/* Credit: LittleDraily */
public class CommandManager {
    public static HashSet<Command> commands = new HashSet<>();

    public static void init() {
        commands.clear();
        commands.add(new Toggle());
        commands.add(new Bind());
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
