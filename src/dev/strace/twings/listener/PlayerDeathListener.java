package dev.strace.twings.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import dev.strace.twings.Main;
import dev.strace.twings.players.CurrentWings;
import dev.strace.twings.utils.ConfigManager;

/**
 * 
 * @author Jason Holweg [STRACE] <b>TWINGS</b><br>
 *         Website: <a>https://strace.dev/</a><br>
 *         GitHub: <a>https://github.com/MrStrace</a><br>
 *         Created: Jun 24, 2021<br>
 *
 */
public class PlayerDeathListener implements Listener {

	// Player Death Event
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {

		// Checks if the entity is an player.
		if (e.getEntity() instanceof Player) {
			Player p = e.getEntity();

			// creates the untildeath.yml file.
			ConfigManager manager = new ConfigManager("untildeath");

			/*
			 * if the untildeath file contains the player it will remove the wings, since he
			 * got wings equiped "untildeath". also he recieves a custom message.
			 */
			if (manager.getString(p.getUniqueId().toString()) != null) {
				new CurrentWings().removeCurrentWing(p);
				// if show messages is enabled the player revieces the message.
				if (Main.getInstance().getMsg().isShowMessages())
					p.sendMessage(Main.getInstance().getMsg().getWingsgone());
				manager.set(p.getUniqueId().toString(), null);
				manager.save();
			}

		}

	}

}
