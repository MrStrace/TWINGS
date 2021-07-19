package dev.strace.twings.utils;

import dev.strace.twings.Main;
import dev.strace.twings.utils.objects.TWING;
import org.bukkit.Bukkit;

import java.util.ArrayList;

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

        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), new Runnable() {
            int times = 0;
            @Override
            public void run() {
                ArrayList<String> locs = getLocs();
                if (locs != null)
                    for (String locations : locs) {
                        //Create TWING via API and filename
                        TWING wing = new WingUtils().getByName(locations);

                        if (wing != null) {
                            times++;
                            //EditPreview
                            times = handleEditPreview(times, wing);
                            //Send preview
                            wing.drawWings(builder.getLocation(locations));
                        }

                    }
            }
        }, 100, Main.getInstance().getConfig().getInt("Wings.updaterate"));

    }

    private ArrayList<String> getLocs() {
        builder = new LocationBuilder();
        return builder.getStringList("");
    }

    private int handleEditPreview(int times, TWING wing) {
        /*
         * EditPreview (Updates a specific Wings each Run) if it was run over 3000 times
         * it will automatically be changed and wont go through any more.
         */
        if (edit == null) return 0;

        // if TWING edit isn't null it will register the TWING again;

        if (edit.getItemName().equalsIgnoreCase(wing.getItemName()))
            wing.register();


        if (times >= 3000) {
            edit = null;
            return 0;
        }
        return times;
    }
}
