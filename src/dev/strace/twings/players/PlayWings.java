package dev.strace.twings.players;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import dev.strace.twings.Main;
import dev.strace.twings.utils.WingUtils;

public class PlayWings {

	
	public void playOnPlayers() {

		new BukkitRunnable() {
			public void run() {
		
				if (Main.getInstance().config.getBoolean("ShowWithPerms")) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						File fil = null;
						for (File f : new File(Main.instance.getDataFolder(), "wing").listFiles()) {
							YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
							String perms = cfg.getString("permission");
							if (p.hasPermission(perms) && !p.isOp()) {
								fil = f;
							}
						}
					WingUtils.winglist.get(fil).drawWings(p);
					}
					return;
				}
				HashMap<UUID, ArrayList<File>> map = CurrentWings.getCurrent();
				if (map.isEmpty())
					return;
				for (UUID uuid : map.keySet()) {
					if (Bukkit.getPlayer(uuid) != null) {
						Player p = Bukkit.getPlayer(uuid);
						if (p.isOnline()) {
							for(File file : map.get(uuid))
							WingUtils.winglist.get(file).drawWings(p);
						}
					}
				}
			}
		}.runTaskTimerAsynchronously(Main.getInstance(), 0, Main.getInstance().config.getInt("UpdateRateTicks"));

	}
	
}
