package dev.strace.twings.utils;

import java.util.ArrayList;

import org.bukkit.scheduler.BukkitRunnable;

import dev.strace.twings.Main;
import dev.strace.twings.api.API;

public class WingPreview {
	private LocationBuilder builder = new LocationBuilder();
	public void enablePreview() {

		new BukkitRunnable() {
			API api = new API(null);

			public void run() {
				for (String locations : getLocs()) {
					//api.sendWingsToLocation(builder.getLocation(locations), locations + ".yml");
				}
			}
		}.runTaskTimerAsynchronously(Main.getInstance(), 0, 4);
	}

	private ArrayList<String> getLocs() {
		return builder.getStringList("");
	}

}
