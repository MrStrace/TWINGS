package dev.strace.twings.utils;

import java.util.ArrayList;

import org.bukkit.scheduler.BukkitRunnable;

import dev.strace.twings.Main;
import dev.strace.twings.api.API;
import dev.strace.twings.utils.objects.Wing;

public class WingPreview {
	private LocationBuilder builder = new LocationBuilder();

	public static Wing edit = null;

	public void enablePreview() {

		new BukkitRunnable() {
			int times = 0;

			public void run() {
				for (String locations : getLocs()) {
					API api = new API(locations + ".yml");
					if (edit != null) {
						if (edit.getFile().getName().replace(".yml", "").equalsIgnoreCase(locations)) {

						} else
							api.sendWingTo(builder.getLocation(locations));

					} else
						api.sendWingTo(builder.getLocation(locations));
					/*
					 * EditPreview (Updates a specific Wings each Run) if it was run over 3000 times
					 * it will automatically be changed and wont go through any more.
					 */
					if (edit != null) {
						times++;
						new SendWings().drawWings(builder.getLocation(edit.getFile().getName().replace(".yml", "")),
								new Wing(new ConfigManager(edit.getFile())).register());
						if (times >= 3000) {
							edit = null;
							times = 0;
						}
					}
				}
			}
		}.runTaskTimerAsynchronously(Main.getInstance(), 0, Main.getInstance().getConfig().getInt("Wings.updaterate"));
	}

	private ArrayList<String> getLocs() {
		return builder.getStringList("");
	}

}
