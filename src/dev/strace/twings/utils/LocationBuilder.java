package dev.strace.twings.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * @author Jason Holweg | MrStrace
 */

public class LocationBuilder extends ConfigManager {

    /**
     * Erstellt locations.yml falls sie noch nicht exestiert.
     */
    public LocationBuilder() {
        super("locations");
    }

    /**
     * Speichert die Location in der locations.yml.
     *
     * @param name
     * @param loc
     */
    public void setLocation(String name, Location loc) {
        cfg.set(name.replace(".", "") + ".name", name);
        if (loc.getWorld() != null) cfg.set(name.replace(".", "") + ".World", loc.getWorld().getName());
        cfg.set(name.replace(".", "") + ".X", loc.getX());
        cfg.set(name.replace(".", "") + ".Y", loc.getY());
        cfg.set(name.replace(".", "") + ".Z", loc.getZ());
        cfg.set(name.replace(".", "") + ".Yaw", loc.getYaw());
        cfg.set(name.replace(".", "") + ".Pitch", loc.getPitch());
        this.save();
    }

    /**
     * Enthaelt eine gespeicherte Location aus der locations.yml.
     *
     * @param name
     * @return bukkit.Location
     */
    public Location getLocation(String name) {
        String world = cfg.getString(name + ".World");
        double x = cfg.getDouble(name + ".X");
        double y = cfg.getDouble(name + ".Y");
        double z = cfg.getDouble(name + ".Z");
        float yaw = (float) cfg.getDouble(name + ".Yaw");
        float pitch = (float) cfg.getDouble(name + ".Pitch");

        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    /**
     * Enthaelt eine Welt aus einer gespeicherten Location aus der locations.yml.
     *
     * @param name
     */
    public World getWorld(String name) {
        String world = cfg.getString(name + ".World");
        if (world != null)
            return Bukkit.getWorld(world);
        return null;
    }
}
