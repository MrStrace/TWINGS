package dev.strace.twings.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import dev.strace.twings.Main;
import dev.strace.twings.players.CurrentWings;

/**
 * 
 * @author Jason Holweg [STRACE] <b>TWINGS</b><br>
 *         Website: <a>https://strace.dev/</a><br>
 *         GitHub: <a>https://github.com/MrStrace</a><br>
 *         Created: Jun 24, 2021<br>
 *
 */
public class PlayerConnectionListener implements Listener {

	// Join Event

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		// if the player has wings equiped on join he will get the animated timer.
		if (CurrentWings.current.containsKey(p.getUniqueId())) {
			Main.getInstance().getAnimation().getAnimated().put(p, 0);
			Main.getInstance().getAnimation().getPlus().put(p, true);
		}

	}

	// Disconnect Event

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();

		// if the player disconnects he will get kicked out of any active maps.
		if (CurrentWings.current.containsKey(p.getUniqueId())) {
			Main.getInstance().getAnimation().getAnimated().remove(p);
			Main.getInstance().getAnimation().getPlus().remove(p);
		}

	}
}
