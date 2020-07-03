package me.therealmck.skywars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SkyWarsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        String sub = "";
        try { sub = args[0]; }
        catch (Exception e) { commandSender.sendMessage("Unknown subcommand. Run [/skywars help] for help."); }

        switch (sub) {
            case "help":
                commandSender.sendMessage("§6/skywars help§b: Display this help menu");
                commandSender.sendMessage("§6/skywars create§b: Create a custom game of SkyWars");
                commandSender.sendMessage("§6/skywars join [IGN of host]§b: Join the specified host's game of SkyWars");

                break;
            case "create":


                break;
            case "join":


                break;
        }



        return true;
    }
}
