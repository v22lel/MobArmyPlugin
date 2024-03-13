package dev.mv.mobarmy;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommand implements CommandExecutor {
    private static Location pos1, pos2;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            Location location = player.getLocation();

            if (args[0].equals("pos1")) {
                pos1 = location;
                player.sendMessage(Utils.chat(String.format("&3Position 1 &7of arena set to &1(%f, %f, %f)", location.getX(), location.getY(), location.getZ())));
            }

            if (args[0].equals("pos2")) {
                pos2 = location;
                player.sendMessage(Utils.chat(String.format("&3Position 2 &7of arena set to &1(%f, %f, %f)", location.getX(), location.getY(), location.getZ())));

            }

            if (args[0].equals("save")) {
                player.sendMessage(Utils.chat("&7Saved Arena!"));

                Arena.save(pos1, pos2);
            }
        }

        return false;
    }
}
