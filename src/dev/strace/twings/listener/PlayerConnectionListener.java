package dev.strace.twings.listener;

import java.io.File;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import dev.strace.twings.Main;
import dev.strace.twings.players.CurrentWings;
import dev.strace.twings.utils.SendWings;

public class PlayerConnectionListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		if (CurrentWings.current.containsKey(p.getUniqueId())) {
			SendWings.animated.put(p, 0);
			SendWings.plus.put(p, true);
		}

	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (CurrentWings.current.containsKey(p.getUniqueId())) {
			SendWings.animated.remove(p);
			SendWings.plus.remove(p);
		}

	}

	public File getRandomWing() {
		File[] file = new File(Main.getInstance().getDataFolder(), "wings").listFiles();
		return file[0];
	}
	
}
