package dev.mv.mobarmy;

import org.bukkit.entity.Player;

import java.util.*;

public class MobArmy {
    private static final Map<Integer, List<EntityData>> list = new HashMap<>();

    public static void addMob(Player player, EntityData type) {
        try {
            list.get(Teams.getTeam(player)).add(type);
        } catch (NullPointerException ignore) {
            list.put(Teams.getTeam(player), new ArrayList<>());
            addMob(player, type);
        }
    }

    public static List<EntityData> getAllMobs(int team) {
        return list.get(team);
    }
}
