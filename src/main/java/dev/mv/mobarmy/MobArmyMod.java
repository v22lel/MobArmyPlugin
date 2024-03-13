package dev.mv.mobarmy;

import org.bukkit.plugin.java.JavaPlugin;

public final class MobArmyMod extends JavaPlugin {
    private static MobArmyMod instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new PluginListener(), this);
        getCommand("timer").setExecutor(new Timer());
        getCommand("team").setExecutor(new TeamCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("fight").setExecutor(new FightCommand());
        getCommand("arena").setExecutor(new ArenaCommand());
        instance = this;
    }

    @Override
    public void onDisable() {
        // ePlugin shutdown logic
    }

    public static MobArmyMod instance() {
        return instance;
    }
}
