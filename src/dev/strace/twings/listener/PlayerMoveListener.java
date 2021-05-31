package dev.strace.twings.listener;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import dev.strace.twings.Main;

public class PlayerMoveListener implements Listener {

	public static ArrayList<Player> moving = new ArrayList<Player>();

	@EventHandler
	public void onMove(final PlayerMoveEvent e) {

		final Player p = e.getPlayer();
		if (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockY() != e.getTo().getBlockY()
				|| e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
			if (moving.contains(p))
				return;

			moving.add(p);
			new BukkitRunnable() {
				public void run() {
					moving.remove(p);
				}
			}.runTaskLaterAsynchronously(Main.instance, 30);
		}

	}
}
