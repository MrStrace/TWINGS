package dev.strace.twings.players;

import dev.strace.twings.Main;
import dev.strace.twings.utils.WingUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.UUID;

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
							if (perms != null && p.hasPermission(perms) && !p.isOp()) {
								fil = f;
							}
						}
					WingUtils.winglist.get(fil).drawWings(p);
					}
					return;
				}
								if (CurrentWings.getCurrent().isEmpty())
					return;
				for (UUID uuid : CurrentWings.getCurrent().keySet()) {
					if (Bukkit.getPlayer(uuid) != null) {
						Player p = Bukkit.getPlayer(uuid);
						if (p != null && p.isOnline()) {
							for (File file : CurrentWings.getCurrent().get(uuid))
								WingUtils.winglist.get(file).drawWings(p);
						}
					}
				}
			}
		}.runTaskTimer(Main.getInstance(), 0, Main.getInstance().config.getInt("UpdateRateTicks"));

	}
	
}
