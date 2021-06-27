package dev.strace.twings.utils;

import java.util.ArrayList;

import org.bukkit.scheduler.BukkitRunnable;

import dev.strace.twings.Main;
import dev.strace.twings.api.API;
import dev.strace.twings.utils.objects.TWING;

/**
 * 
 * @author Jason Holweg [STRACE] <b>TWINGS</b><br>
 *         Website: <a>https://strace.dev/</a><br>
 *         GitHub: <a>https://github.com/MrStrace</a><br>
 *         Created: Jun 25, 2021<br>
 *
 */
public class WingPreview {

	private LocationBuilder builder;

	public static TWING edit = null;

	/**
	 * this will run on reload/server start.
	 * 
	 * it will create a runnable which runs every few ticks (setup in config)
	 * 
	 * it will display a preview of set wings.
	 * 
	 */
	public void enablePreview() {

		new BukkitRunnable() {
			int times = 0;

			public void run() {
				for (String locations : getLocs()) {
					TWING wing = new API(builder.getString(locations + ".name") + ".yml").getTwing();

					if (edit != null) {
						if (edit.getFile().getName().replace(".yml", "").equalsIgnoreCase(locations)) {

						} else
							wing.drawWings(builder.getLocation(locations));

					} else
						wing.drawWings(builder.getLocation(locations));
					/*
					 * EditPreview (Updates a specific Wings each Run) if it was run over 3000 times
					 * it will automatically be changed and wont go through any more.
					 */
					if (edit != null) {
						times++;

						new TWING(new ConfigManager(edit.getFile())).register()
								.drawWings(builder.getLocation(edit.getFile().getName().replace(".yml", "")));
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
		builder = new LocationBuilder();
		return new LocationBuilder().getStringList("");
	}

}
