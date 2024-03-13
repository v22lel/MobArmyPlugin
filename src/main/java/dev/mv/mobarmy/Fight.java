package dev.mv.mobarmy;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fight {
    private static final Map<Integer, List<Fight>> fights = new HashMap<>();

    private boolean completed;

    private int teamPlayers, teamMobs;
    private World world;

    public Fight(int teamPlayers, int teamMobs, World world) {
        this.teamPlayers = teamPlayers;
        this.teamMobs = teamMobs;
        this.world = world;

        try {
            fights.get(teamPlayers).add(this);
        } catch (NullPointerException e) {
            fights.put(teamPlayers, new ArrayList<>());
            fights.get(teamPlayers).add(this);
        }
    }

    public static Fight getFight(int[] opponents) {
        List<Fight> res = fights.get(opponents[0]);
        if (res != null) {
            for (Fight fight : res) {
                if (fight.teamMobs == opponents[1]) {
                    return fight;
                }
            }
        }
        return null;
    }

    public static Fight getFight(int team) {
        try {
            return fights.get(team).get(0);
        } catch (NullPointerException e) {
            return null;
        }
    }

    private boolean insideArena(Location location) {
        Location l1 = new Location(world, 0, 0, 0);
        Location l2 = Arena.pos2.clone().subtract(Arena.pos1);

        if ((location.getBlockX() >= l1.getBlockX() && location.getBlockX() <= l2.getBlockX()) || (location.getBlockX() <= l1.getBlockX() && location.getBlockX() >= l2.getBlockX())) { // Check X value
            if ((location.getBlockZ() >= l1.getBlockZ() && location.getBlockZ() <= l2.getBlockZ()) || (location.getBlockZ() <= l1.getBlockZ() && location.getBlockZ() >= l2.getBlockZ())) { // Check Z value
                if ((location.getBlockY() >= l1.getBlockY() && location.getBlockY() <= l2.getBlockY()) || (location.getBlockY() <= l1.getBlockY() && location.getBlockY() >= l2.getBlockY())) { // Check Y value
                    return true;
                }
            }
        }
        return false;
    }

    public void mobKilled() {
        if (completed) return;

        for (LivingEntity entity : world.getLivingEntities()) {
            if (!(entity instanceof Player)) {
                if (insideArena(entity.getLocation())) {
                    return;
                }
            }
        }

        completed = true;

        //No mob in arena
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (Teams.getTeam(player) == teamPlayers) {
                player.sendMessage(Utils.chat("&aYour team has defeated all of the mobs! GG"));
                player.teleport(Arena.world.getSpawnLocation());
            }
        });

        fights.values().forEach(fs -> fs.forEach(fight -> fight.otherWon(teamPlayers)));
        Worlds.deleteWorld(world);
    }

    public void otherWon(int winnerTeam) {
        if (completed) return;
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (Teams.getTeam(player) == teamPlayers) {
                player.sendMessage(Utils.chat("&6Team " + winnerTeam + " was faster than you! Still GG!"));
                player.teleport(Arena.world.getSpawnLocation());
            }
        });
        completed = true;
        Worlds.deleteWorld(world);
    }

    public boolean completed() {
        return completed;
    }
}
