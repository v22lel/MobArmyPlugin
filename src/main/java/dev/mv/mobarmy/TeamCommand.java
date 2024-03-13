package dev.mv.mobarmy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args[0].equals("join")) {
            try {
                int team = Integer.parseInt(args[1]);
                if (team < 0) throw new NumberFormatException();
                if (commandSender instanceof Player) {
                    Teams.joinTeam((Player) commandSender, team);
                    commandSender.sendMessage(Utils.chat("&7Joined &2team " + team + "&7!"));
                }
            } catch (NumberFormatException ignore) {
                commandSender.sendMessage("&4Not a valid team " + args[1]);
            }
        }

        return false;
    }
}
