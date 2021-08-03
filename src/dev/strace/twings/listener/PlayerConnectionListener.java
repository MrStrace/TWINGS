package dev.strace.twings.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * 
 * @author Jason Holweg [STRACE] <b>TWINGS</b><br>
 *         Website: <a>https://strace.dev/</a><br>
 *         GitHub: <a>https://github.com/MrStrace</a><br>
 *         Created: Jun 24, 2021<br>
 *
 */
public class PlayerConnectionListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();

	}
}
