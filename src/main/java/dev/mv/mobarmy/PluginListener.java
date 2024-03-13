package dev.mv.mobarmy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Arrays;

public class PluginListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        LivingEntity dead = e.getEntity();
        if (dead instanceof Player) return;
        if (dead.getKiller() != null) {
            if (Timer.running()) {
                MobArmy.addMob(dead.getKiller(), new EntityData(dead));
            }
        }

        String name = e.getEntity().getWorld().getName();
        if (name.startsWith("fight_")) {
            String[] parts = name.split("_")[1].split("vs");
            int[] opponents = Arrays.stream(parts).mapToInt(Integer::parseInt).toArray();
            Fight fight = Fight.getFight(opponents);
            if (fight != null) {
                fight.mobKilled();
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        int team = Teams.getTeam(player);
        if (team != -1) {
            Fight fight = Fight.getFight(team);
            if (fight != null) {
                if (!fight.completed()) {
                    Bukkit.getScheduler().runTaskLater(MobArmyMod.instance(), () -> player.teleport(Arena.evalPSpawn), 40);
                }
            }
        }
    }
}
