package dev.strace.twings.utils;

import java.io.File;

import dev.strace.twings.Main;
import dev.strace.twings.utils.objects.Wing;

/**
 * 
 * @author Jason Holweg [STRACE]
 * <b>TWINGS</b><br>
 * Website: <a>https://strace.dev/</a><br>
 * GitHub: <a>https://github.com/MrStrace</a><br>
 * Created: Jun 25, 2021<br>
 *
 */
public class WingReader {
	
	public void registerWings() {
		
		//Clears the Wing Cache.
		WingUtils.winglist.clear();
		
		/**
		 * For each file a new Wing object is getting created and cached.
		 */
		for (File files : new File(Main.instance.getDataFolder(), "wings").listFiles()) {
			Wing wing = new Wing(new ConfigManager(files)).register();
			WingUtils.winglist.put(files, wing);
		}
	}

}
