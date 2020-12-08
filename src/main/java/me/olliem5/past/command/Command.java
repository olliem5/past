package me.olliem5.past.command;

public class Command {
    private String name;
    private String description;
    private String syntax;

    public Command(String name, String description, String syntax) {
        this.name = name;
        this.description = description;
        this.syntax = syntax;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSyntax() {
        return syntax;
    }

    public void runCommand(String[] args) {}
}
