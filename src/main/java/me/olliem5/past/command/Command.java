package me.olliem5.past.command;

/* Credit: LittleDraily */
public class Command {
    private String command;
    private String[] usage;

    public Command(String name, String[] usage) {
        this.command = name;
        this.usage = usage;
    }

    public String getCommand() { return command; }
    public void onCommand(String[] args) {}
}
