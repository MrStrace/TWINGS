package dev.strace.twings.utils;

import java.io.File;

import dev.strace.twings.Main;
import dev.strace.twings.utils.objects.Wing;

/**
 * 
 * @author Jason Holweg [STRACE] TWINGS Website: https://strace.dev/ GitHub:
 *         https://github.com/MrStrace Erstellt May 23, 2021
 *
 */
public class WingReader {
	
	public void registerWings() {
		
		//Clears the Wing Cache.
		WingUtils.winglist.clear();
		
		/**
		 * Feur jede File wird ein neues Wing Object kreiert und im cache gespeichert.
		 * 
		 * For each file a new Wing object is getting created and cached.
		 */
		for (File files : new File(Main.instance.getDataFolder(), "wings").listFiles()) {
			Wing wing = new Wing(new ConfigManager(files)).register();
			WingUtils.winglist.put(files, wing);
		}
	}

}
