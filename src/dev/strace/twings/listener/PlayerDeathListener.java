package dev.strace.twings.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import dev.strace.twings.Main;
import dev.strace.twings.players.CurrentWings;
import dev.strace.twings.utils.ConfigManager;

public class PlayerDeathListener implements Listener {

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {

		if (e.getEntity() instanceof Player) {
			Player p = e.getEntity();
			ConfigManager manager = new ConfigManager("untildeath");
			if (manager.getString(p.getUniqueId().toString()) != null) {
				new CurrentWings().removeCurrentWing(p);
				p.sendMessage(Main.getInstance().getMsg().getWingsgone());
				manager.set(p.getUniqueId().toString(), null);
				manager.save();
			}

		}

	}

}
