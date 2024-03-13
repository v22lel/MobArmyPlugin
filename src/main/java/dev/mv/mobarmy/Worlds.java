package dev.mv.mobarmy;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class Worlds {
    public static void deleteWorld(World world) {
        Bukkit.unloadWorld(world, false);
        deleteWorldDirectory(world.getName());
    }

    private static boolean deleteWorldDirectory(String worldName) {
        java.io.File worldFolder = new java.io.File(worldName);
        if (worldFolder.exists()) {
            try {
                deleteRecursive(worldFolder);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    private static void deleteRecursive(java.io.File file) {
        if (file.isDirectory()) {
            for (java.io.File subFile : file.listFiles()) {
                deleteRecursive(subFile);
            }
        }
        file.delete();
    }
}
