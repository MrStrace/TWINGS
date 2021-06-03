package dev.strace.twings.players;

import java.io.File;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import dev.strace.twings.utils.ConfigManager;
import dev.strace.twings.utils.WingUtils;

/**
 * 
 * @author Jason Holweg
 *
 */
public class CurrentWings extends ConfigManager {

	// UUID & FileName der Wings
	public static HashMap<UUID, File> current = new HashMap<UUID, File>();

	/**
	 * Erstellt wings_equiped.yml
	 * 
	 */
	public CurrentWings() {
		super("wings_equiped");
	}

	/**
	 * Beim Start des Plugins soll dies ausgefuehrt
	 */
	public void onEnable() {

		if (this.getStringList("") == null || this.getStringList("").isEmpty())
			return;

		// Feur jeden UUID-Eintrag wird nun in die HashMap der zugehoerige Wert
		// hinzugefuegt.
		for (String uid : this.getStringList("")) {
			for(File file : WingUtils.winglist.keySet()) {
				if(file.getName().equalsIgnoreCase(getString(uid))) {
					current.put(UUID.fromString(uid), file);
					break;
				}
			}
		}
	}

	/**
	 * Speichert alle Wings welche Spieler ausgerwaehlt haben.
	 */
	public void onDisable() {
		save();
	}

	/**
	 * Setzt die ausgewaehlten Wings als "CurrentWings".
	 * 
	 * @param p
	 * @param fileName
	 */
	public void setCurrentWing(Player p, File file) {
		
		if (file == null) {
			if (current.containsKey(p.getUniqueId()))
				current.remove(p.getUniqueId());
			return;
		}
		current.put(p.getUniqueId(), file);
		set(p.getUniqueId().toString(), file.getName());
		save();
	}

	public void removeCurrentWing(Player p) {
		
		current.remove(p.getUniqueId());
		set(p.getUniqueId().toString(), null);
		save();
	}
	
	public static HashMap<UUID, File> getCurrent() {
		return current;
	}



}
