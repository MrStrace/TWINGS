package dev.strace.twings.players;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import dev.strace.twings.utils.ConfigManager;
import dev.strace.twings.utils.WingUtils;

/**
 * 
 * @author Jason Holweg [STRACE] <b>TWINGS</b><br>
 *         Website: <a>https://strace.dev/</a><br>
 *         GitHub: <a>https://github.com/MrStrace</a><br>
 *         Created: Jun 27, 2021<br>
 *
 */
public class CurrentWings extends ConfigManager {

	// UUID & FileName der Wings
	public static HashMap<UUID, ArrayList<File>> current = new HashMap<>();

	public CurrentWings() {
		super("wings_equiped");
	}

	/**
	 * enables all wings that player already had equiped.
	 */
	public void onEnable() {

		if (this.getStringList("") == null || this.getStringList("").isEmpty())
			return;

		// Feur jeden UUID-Eintrag wird nun in die HashMap der zugehoerige Wert
		// hinzugefuegt.

		for (String uid : this.getStringList("")) {
			ArrayList<File> list = new ArrayList<File>();
			if (getString(uid).contains("(X-X)")) {
				String[] split = getString(uid).split("(X-X)");
				for (String filenames : split) {
					for (File file : WingUtils.winglist.keySet()) {
						if (file.getName().equalsIgnoreCase(filenames)) {
							list.add(file);
							break;
						}
					}
				}
			} else {
				for (File file : WingUtils.winglist.keySet()) {
					if (file.getName().equalsIgnoreCase(getString(uid))) {
						list.add(file);
					}

				}
			}
			current.put(UUID.fromString(uid), list);
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
		ArrayList<File> list = new ArrayList<File>();
		list.add(file);
		current.put(p.getUniqueId(), list);
		set(p.getUniqueId().toString(), file.getName());
		save();
	}

	public void addCurrentWing(Player p, File file) {

		if (current.get(p.getUniqueId()).contains(file))
			return;

		ArrayList<File> list = current.get(p.getUniqueId());
		list.add(file);
		current.put(p.getUniqueId(), list);
		String equiped = "";
		int i = 0;
		for (File files : current.get(p.getUniqueId())) {
			i++;
			equiped += files.getName();
			if (list.size() != i)
				equiped += "(X-X)";
			
		}
		set(p.getUniqueId().toString(), equiped);
		save();
	}

	public void removeCurrentWing(Player p, File file) {

		if (!current.get(p.getUniqueId()).contains(file))
			return;

		ArrayList<File> list = current.get(p.getUniqueId());
		list.remove(file);
		current.put(p.getUniqueId(), list);
		String equiped = "";
		int i = 0;
		for (File files : current.get(p.getUniqueId())) {
			i++;
			equiped += files.getName();
			if (list.size() != i)
				equiped += "(X-X)";
		
		}
		set(p.getUniqueId().toString(), equiped);
		save();
	}

	public void removeAllCurrentWing(Player p) {
		current.remove(p.getUniqueId());
		set(p.getUniqueId().toString(), null);
		save();
	}

	public static HashMap<UUID, ArrayList<File>> getCurrent() {
		return current;
	}
}
