package dev.mv.mobarmy;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class FightCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            for (String match : args) {
                int[] opponents = Arrays.stream(match.split("vs")).mapToInt(Integer::parseInt).toArray();
                startFight(opponents[0], opponents[1]);
            }
        }

        return false;
    }

    private void startFight(int team, int mobTeam) {
        String name = "fight_" + team + "vs" + mobTeam;
        WorldCreator creator = new WorldCreator(name);
        creator.type(WorldType.FLAT);
        World world = creator.createWorld();
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.KEEP_INVENTORY, true);
        Arena.place(world, new Location(world, 0, 0, 0));

        Bukkit.getOnlinePlayers().forEach(player -> {
            int playerTeam = Teams.getTeam(player);
            if (playerTeam != -1 && playerTeam == team) {
                player.sendMessage(Utils.chat("&4Prepare! &7The final fight has begun!"));
                player.sendMessage(Utils.chat("&7You fight against the mobs of &2team " + mobTeam + "&7!"));
                Location spawn = Arena.evalPSpawn;
                player.teleport(spawn);
            }
        });

        List<EntityData> mobs = MobArmy.getAllMobs(mobTeam);
        Location spawn = Arena.evalMSpawn;
        for (EntityData data : mobs) {
            data.spawn(spawn);
        }

        new Fight(team, mobTeam, world);
    }
}
