package dev.strace.twings.players;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import dev.strace.twings.Main;
import dev.strace.twings.utils.SendWings;
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
						new SendWings().drawWings(p, WingUtils.winglist.get(fil));
					}
					return;
				}
				HashMap<UUID, File> map = CurrentWings.getCurrent();
				if (map.isEmpty())
					return;
				for (UUID uuid : map.keySet()) {
					if (Bukkit.getPlayer(uuid) != null) {
						Player p = Bukkit.getPlayer(uuid);
						if (p.isOnline()) {
							new SendWings().drawWings(p, WingUtils.winglist.get(map.get(uuid)));
						}
					}

				}
			}
		}.runTaskTimerAsynchronously(Main.getInstance(), 0, Main.getInstance().config.getInt("UpdateRateTicks"));

	}

	public void enableAnimated() {
        new BukkitRunnable() {
            public void run() {
                for (Player p : SendWings.animated.keySet()) {
                    if (SendWings.animated.get(p) <= 30 && SendWings.plus.get(p))
                    	SendWings.animated.put(p, SendWings.animated.get(p) + 2);
                    else
                    	SendWings.plus.put(p, false);
                    if (SendWings.animated.get(p) > 0 && !SendWings.plus.get(p))
                    	SendWings.animated.put(p, SendWings.animated.get(p) - 2);
                    else
                    	SendWings.plus.put(p, true);
                }
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 20, 4);
    }
	
}
