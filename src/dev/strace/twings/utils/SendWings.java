package dev.strace.twings.utils;

import java.util.HashMap;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import dev.strace.twings.Main;
import dev.strace.twings.listener.PlayerMoveListener;
import dev.strace.twings.utils.objects.ParticleCode;
import dev.strace.twings.utils.objects.ParticleColor;
import dev.strace.twings.utils.objects.Wing;

/**
 * 
 * @author Jason Holweg [STRACE] <b>TWINGS</b><br>
 *         Website: <a>https://strace.dev/</a><br>
 *         GitHub: <a>https://github.com/MrStrace</a><br>
 *         Created: May 30, 2021<br>
 *
 */
public class SendWings {

	private void sendColor(Wing wing, double space, double x, double y, double fire, double defX, Location location,
			boolean left, int tilt) {

		for (int i = 0; i < wing.pattern.length; i++) {
			ParticleCode[] alone = wing.pattern[i];
			for (int j = 0; j < alone.length; j++) {
				if (alone[j] != null) {
					Location target = location.clone();
					target.setX(x);
					target.setY(y);

					Vector v = target.toVector().subtract(location.toVector());
					Vector vpitch = rotateAroundAxisX(v, tilt);
					Vector v2 = getBackVector(location, left);
					v = rotateAroundAxisY(v, fire + wing.getRotation());

					if (wing.isMirrow()) {
						if (left == false)
							v2.setY(0).multiply(-0.25);
						else
							v2.setY(0).multiply(0.25);
					} else
						v2.setY(0).multiply(-0.5);

					location.add(vpitch);
					location.add(v);
					location.add(v2);
					if (wing.isTiltBefore())
						location.setY(location.getY() + wing.getMoveup());
					sendParticles(alone[j], location);
					if (wing.isTiltBefore())
						location.setY(location.getY() - wing.getMoveup());
					location.subtract(vpitch);
					location.subtract(v2);
					location.subtract(v);

				}
				if (left == false)
					x += space;
				else
					x -= space;
			}
			y -= space;
			x = defX;
		}

	}

	private boolean isRunning(Player p, Wing wing) {
		if (wing.showWhenRunning == true && PlayerMoveListener.moving.contains(p))
			return false;

		if (PlayerMoveListener.moving.contains(p))
			return true;

		return false;

	}

	public static HashMap<Player, Integer> animated = new HashMap<Player, Integer>();
	public static HashMap<Player, Boolean> plus = new HashMap<Player, Boolean>();

	/**
	 * This Function sets up the Wing Particle Location.
	 * 
	 * 
	 * @param p Player
	 * @param wing
	 */
	public void drawWings(Player p, Wing wing) {
		
		if (wing == null)
			return;
		boolean ani = true;
		
		int addition = 6;
		int sneakAddition = 12;
		
		try {
			addition = wing.getDegreeAddition();
			sneakAddition = wing.getSneakAddition();
		} catch (Exception e) {
			System.out.println(e);
		}
		if (wing.isAnimated() == false) {
			ani = false;
		}

		if (!animated.isEmpty() && ani == true) {
			addition += animated.get(p);
			sneakAddition += animated.get(p);
		}
		int tilt = 0;
		if (isRunning(p, wing)) {
			return;
		} else {
			tilt = wing.getTilt() + wing.getRunTilt();
		}
		Location ploc = p.getLocation().clone();
		ploc.setPitch(-45);
		Location location = p.getLocation().clone().subtract(ploc.getDirection().setY(0).multiply(wing.getMoveback()));
		int patternlenght = wing.getPattern()[0].length;

		double space = 0.07;
		double defX;
		if (wing.isMirrow())
			defX = location.getX() - (space * patternlenght) + space;
		else
			defX = location.getX() - (space * patternlenght / 2) + space;
		double x = defX;
		double y = location.clone().getY() + 1.4;
		if (!wing.isTiltBefore())
			y += wing.getMoveup();
		double fire = -((location.getYaw() + (180 + addition)) / 60);
		if (!p.isSneaking()) {

			fire += (location.getYaw() < -180 ? Math.PI : 2.985);

			sendColor(wing, space, x, y, fire, defX, location, false, tilt);

			if (wing.isMirrow()) {
				defX = location.getX() + (space * patternlenght);
				x = defX;
				y = location.clone().getY() + 1.4;
				if (!wing.isTiltBefore())
					y += wing.getMoveup();
				fire = -((location.getYaw() + (180 - addition)) / 60);
				fire += (location.getYaw() < -180 ? Math.PI : 2.985);
				sendColor(wing, space, x, y, fire, defX, location, true, tilt);
			}
			return;
		}

		space = 0.06;
		if (wing.isMirrow())
			defX = location.getX() - (space * patternlenght) + space;
		else
			defX = location.getX() - (space * patternlenght / 2) + space;
		x = defX;
		y = location.clone().getY() + 1.1;
		if (!wing.isTiltBefore())
			y += wing.getMoveup();
		fire = -((location.getYaw() + (180 + sneakAddition)) / 60);
		fire += (location.getYaw() < -180 ? Math.PI : 2.985);
		sendColor(wing, space, x, y, fire, defX, location, false, tilt);
		if (wing.isMirrow()) {
			defX = location.getX() + (space * patternlenght);
			x = defX;
			y = location.clone().getY() + 1.1;
			if (!wing.isTiltBefore())
				y += wing.getMoveup();
			fire = -((location.getYaw() + (180 - sneakAddition)) / 60);
			fire += (location.getYaw() < -180 ? Math.PI : 2.985);
			sendColor(wing, space, x, y, fire, defX, location, true, tilt);
		}
		return;
	}

	public void drawWings(Location l, Wing wing) {
		int addition = wing.getDegreeAddition();
		int tilt = 0;
		tilt = wing.getTilt();
		Location location = l;
		int patternlenght = wing.getPattern()[0].length;

		double space = 0.07;
		double defX;
		if (wing.isMirrow())
			defX = location.getX() - (space * patternlenght) + space;
		else
			defX = location.getX() - (space * patternlenght / 2) + space;
		double x = defX;
		double y = location.clone().getY() + 1.4;
		if (!wing.isTiltBefore())
			y += wing.getMoveup();
		double fire = -((location.getYaw() + (180 + addition)) / 60);

		fire += (location.getYaw() < -180 ? Math.PI : 2.985);

		sendColor(wing, space, x, y, fire, defX, location, false, tilt);

		if (wing.isMirrow()) {
			defX = location.getX() + (space * patternlenght);
			x = defX;
			y = location.clone().getY() + 1.4;
			if (!wing.isTiltBefore())
				y += wing.getMoveup();
			fire = -((location.getYaw() + (180 - addition)) / 60);
			fire += (location.getYaw() < -180 ? Math.PI : 2.985);
			sendColor(wing, space, x, y, fire, defX, location, true, tilt);
		}
		return;

	}

	public void drawWings(Location l, Wing wing, int decrease) {
		int addition = decrease;
		try {
			Location location = l;
			int patternlenght = wing.getPattern()[0].length;
			double space = 0.1;
			double defX = location.getX() - (space * patternlenght) + space;
			double x = defX;
			double y = location.clone().getY() + 1.4 + wing.getMoveup();
			double fire = -((location.getYaw() + (180 + addition)) / 60);
			fire += (location.getYaw() < -180 ? Math.PI : 2.985);

			sendColor(wing, space, x, y, fire, defX, location, false, wing.getTilt());

			defX = location.getX() + (space * patternlenght);
			x = defX;
			y = location.clone().getY() + 1.4 + wing.getMoveup();
			fire = -((location.getYaw() + (180 - addition)) / 60);
			fire += (location.getYaw() < -180 ? Math.PI : 2.985);
			sendColor(wing, space, x, y, fire, defX, location, true, wing.getTilt());

		} catch (Exception e) {

		}
	}

	/*
	 * Enabled the Animated Wings.
	 */
	public void enableAnimated() {
		// new runnable which runs async every 3 ticks.
		new BukkitRunnable() {
			public void run() {
				for (Player p : animated.keySet()) {
					if (animated.get(p) <= 30 && plus.get(p))
						animated.put(p, animated.get(p) + 2);
					else
						plus.put(p, false);
					if (animated.get(p) > 0 && !plus.get(p))
						animated.put(p, animated.get(p) - 2);
					else
						plus.put(p, true);
				}
			}
		}.runTaskTimerAsynchronously(Main.instance, 20, 3);
	}

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

	/**
	 * This function spawns all Particles from twings.
	 * 
	 * @param code ParticleCode
	 * @param L    The Location
	 */
	public static void sendParticles(ParticleCode code, Location L) {

		// If the ParticleCode has an ParticleColor it will get the RGB and sends the
		// Redstone Particle with DustOptions.
		if (code instanceof ParticleColor) {
			ParticleColor color = (ParticleColor) code;

			int r = color.getR();
			int g = color.getG();
			int b = color.getB();

			L.getWorld().spawnParticle(color.getParticle(), L, 0,
					new org.bukkit.Particle.DustOptions(Color.fromRGB((int) r, (int) g, (int) b), (float) code.getSpeed()));
			return;

		}

		// If the ParticleCode doesn't have any color it will send the normal Particles.
		L.getWorld().spawnParticle(code.getParticle(), L, 1, 0.0F, 0F, 0F, code.getSpeed());

	}
}
