package dev.mv.mobarmy;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            Location location = player.getLocation();

            if (args[0].equals("mobs")) {
                Arena.setMobSpawn(location);
                player.sendMessage(Utils.chat(String.format("&7Mob's Spawnpoint set to &1(%f, %f, %f)", location.getX(), location.getY(), location.getZ())));
            }

            if (args[0].equals("players")) {
                Arena.setPlayerSpawn(location);
                player.sendMessage(Utils.chat(String.format("&7Player's Spawnpoint set to &1(%f, %f, %f)", location.getX(), location.getY(), location.getZ())));

            }
        }
        return false;
    }
}
