package dev.mv.mobarmy;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;

public class Arena {
    static World world;
    static Location pos1;
    static Location pos2;
    private static Location mspawn;
    private static Location pspawn;
    static Location evalMSpawn, evalPSpawn;

    public static void save(Location l1, Location l2) {
        if (l1.getBlockX() > l2.getBlockX()) {
            double x = l1.getX();
            l1.setX(l2.getX());
            l2.setX(x);
        }
        if (l1.getBlockY() > l2.getBlockY()) {
            double y = l1.getY();
            l1.setX(l2.getY());
            l2.setY(y);
        }
        if (l1.getBlockZ() > l2.getBlockZ()) {
            double z = l1.getZ();
            l1.setZ(l2.getZ());
            l2.setZ(z);
        }

        world = l1.getWorld();
        pos1 = l1;
        pos2 = l2;
    }

    public static void setMobSpawn(Location location) {
        mspawn = location.subtract(pos1);
    }

    public static void setPlayerSpawn(Location location) {
        pspawn = location.subtract(pos1);
    }

    public static void place(World destination, Location location) {
        for (int x = 0; x < pos2.getBlockX() - pos1.getBlockX(); x++) {
            for (int y = 0; y < pos2.getBlockY() - pos1.getBlockY(); y++) {
                for (int z = 0; z < pos2.getBlockZ() - pos1.getBlockZ(); z++) {
                    Location origin = new Location(world, pos1.getX() + x, pos1.getY() + y, pos1.getZ() + z);
                    Location loc = new Location(destination, location.getX() + x, location.getY() + y, location.getZ() + z);
                    BlockData blockData = world.getBlockData(origin);
                    destination.setBlockData(loc, blockData);
                }
            }
        }

        evalMSpawn = new Location(destination, mspawn.getX() + location.getX(), mspawn.getY() + location.getY(), mspawn.getZ() + location.getZ());
        evalPSpawn = new Location(destination, pspawn.getX() + location.getX(), pspawn.getY() + location.getY(), pspawn.getZ() + location.getZ());
    }
}
