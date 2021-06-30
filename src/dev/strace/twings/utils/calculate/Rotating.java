package dev.strace.twings.utils.calculate;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class Rotating {
    /**
     * Rotates a Vector around X axis.
     *
     * @param v     org.bukkit.util.Vector
     * @param angle rotation
     * @return org.bukkit.util.Vector
     */
    public static Vector rotateAroundAxisX(Vector v, double angle) {
        angle = Math.toRadians(angle);
        double y, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        y = v.getY() * cos - v.getZ() * sin;
        z = v.getY() * sin + v.getZ() * cos;
        return v.setY(y).setZ(z);
    }

    /**
     * Rotates a Vector around Y axis.
     *
     * @param v     org.bukkit.util.Vector
     * @param angle rotation
     * @return org.bukkit.util.Vector
     */
    public static Vector rotateAroundAxisY(Vector v, double angle) {
        double x, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = v.getX() * cos + v.getZ() * sin;
        z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }

    /**
     * Gets the Player Direction Vector but in reverse.
     *
     * @param loc  location
     * @param left rotation
     * @return org.bukkit.util.Vector
     */
    public static Vector getBackVector(Location loc, boolean left) {
        final float newZ;
        final float newX;
        if (!left) {
            newZ = (float) (loc.getZ() + (1 * Math.sin(Math.toRadians(loc.getYaw() + 90))));
            newX = (float) (loc.getX() + (1 * Math.cos(Math.toRadians(loc.getYaw() + 90))));
        } else {
            newZ = (float) (loc.getZ() - (1 * Math.sin(Math.toRadians(loc.getYaw() + 90))));
            newX = (float) (loc.getX() - (1 * Math.cos(Math.toRadians(loc.getYaw() + 90))));
        }
        return new Vector(newX - loc.getX(), 0, newZ - loc.getZ());
    }
}
