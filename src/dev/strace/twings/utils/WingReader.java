package dev.strace.twings.utils;

import dev.strace.twings.Main;
import dev.strace.twings.utils.objects.Category;
import dev.strace.twings.utils.objects.TWING;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Jason Holweg [STRACE]
 * <b>TWINGS</b><br>
 * Website: <a>https://strace.dev/</a><br>
 * GitHub: <a>https://github.com/MrStrace</a><br>
 * Created: Jun 25, 2021<br>
 */
public class WingReader {

    public void registerWings() {

        //Clears the Wing Cache.
        WingUtils.winglist.clear();

        /*
         * For each file a new Wing object is getting created and cached.
         */
        for (File files : new File(Main.instance.getDataFolder(), "wings").listFiles()) {
            TWING wing = new TWING(new ConfigManager(files)).register();
            WingUtils.winglist.put(files, wing);

        }
        for (Category cat : Category.categories) {
            ArrayList<TWING> list = new ArrayList<>();
            for (TWING twing : WingUtils.winglist.values()) {
                if (twing.category.equalsIgnoreCase(cat.getName()))
                    list.add(twing);
            }
            WingUtils.categorymap.put(cat.getName(), list);
        }
    }

}
