package dev.mv.mobarmy;

import com.sun.tools.javac.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Teams {
    private static Map<UUID, Integer> mapping = new HashMap<>();

    public static void joinTeam(Player player, int team) {
        mapping.remove(player.getUniqueId());
        mapping.put(player.getUniqueId(), team);
    }

    public static int getTeam(Player player) {
        try {
            return mapping.get(player.getUniqueId());
        } catch (NullPointerException ignore) {
            return -1;
        }
    }

    public static void timerFinished() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            int team = getTeam(player);
            StringBuilder builder = new StringBuilder("Mobs for team " + team + ":\n");
            Map<EntityType, Integer> counted = new HashMap<>();
            List<EntityData> mobs = MobArmy.getAllMobs(team);
            for (EntityData mob : mobs) {
                if (!counted.containsKey(mob.type)) {
                    counted.put(mob.type, 1);
                } else {
                    int amt = counted.remove(mob.type);
                    counted.put(mob.type, amt + 1);
                }
            }
            for (Map.Entry<EntityType, Integer> entry : counted.entrySet()) {
                builder.append(entry.getKey().toString());
                builder.append(" x");
                builder.append(entry.getValue());
                builder.append('\n');
            }
            player.sendMessage(builder.toString());
        });
    }
}
