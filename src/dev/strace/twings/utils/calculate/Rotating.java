package dev.strace.twings.utils.calculate;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class Rotating {
	/**
	 * Rotates a Vector around X axis.
	 * 
	 * @param v
	 * @param angle
	 * @return
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
	 * @param v
	 * @param fire
	 * @return
	 */
	public static Vector rotateAroundAxisY(Vector v, double fire) {
		double x, z, cos, sin;
		cos = Math.cos(fire);
		sin = Math.sin(fire);
		x = v.getX() * cos + v.getZ() * sin;
		z = v.getX() * -sin + v.getZ() * cos;
		return v.setX(x).setZ(z);
	}

	/**
	 * Gets the Player Direction Vector but in reverse.
	 * 
	 * @param loc
	 * @param left
	 * @return
	 */
	public static Vector getBackVector(Location loc, boolean left) {
		if (left == false) {
			final float newZ = (float) (loc.getZ() + (1 * Math.sin(Math.toRadians(loc.getYaw() + 90 * 1)))); // + 90
			final float newX = (float) (loc.getX() + (1 * Math.cos(Math.toRadians(loc.getYaw() + 90 * 1))));
			return new Vector(newX - loc.getX(), 0, newZ - loc.getZ());
		} else {
			final float newZ = (float) (loc.getZ() - (1 * Math.sin(Math.toRadians(loc.getYaw() + 90 * 1)))); // + 90
			final float newX = (float) (loc.getX() - (1 * Math.cos(Math.toRadians(loc.getYaw() + 90 * 1))));
			return new Vector(newX - loc.getX(), 0, newZ - loc.getZ());
		}
	}
}
