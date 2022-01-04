package dev.strace.twings.utils;

import dev.strace.twings.Main;
import dev.strace.twings.utils.objects.TWING;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Jason Holweg [STRACE] <b>TWINGS</b><br>
 * Website: <a>https://strace.dev/</a><br>
 * GitHub: <a>https://github.com/MrStrace</a><br>
 * Created: Jun 25, 2021<br>
 */
public class WingPreview {

    private LocationBuilder builder;

    public static TWING edit = null;

    /**
     * this will run on reload/server start.
     * <p>
     * it will create a runnable which runs every few ticks (setup in config)
     * <p>
     * it will display a preview of set wings.
     */
    public void enablePreview() {
        builder = new LocationBuilder();
        HashMap<TWING, Location> wings = new HashMap<>();
        if (getLocs() != null) {
            for (String locations : getLocs()) {
                String name = builder.getString(locations.replace(".", "") + ".name");
                TWING wing = new WingUtils().getByName(name);
                wings.put(wing, builder.getLocation(locations));
            }
        }
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (TWING wing : wings.keySet()) {
                    wing.general.drawWings(wings.get(wing));
                }
            }
        }, 20, Main.getInstance().getConfig().getInt("Wings.updaterate"));

    }

    private ArrayList<String> getLocs() {
        return builder.getStringList("");
    }
}
