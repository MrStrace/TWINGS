package dev.strace.twings.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;

import dev.strace.twings.Main;

public class ConfigManager {

	// The file which is getting created.
	File file;
	// spigot config file
	YamlConfiguration cfg;
	// the name of the file
	String fileName;

	/**
	 * Erstellt eine .yml File welche so einfach bearbeitet werden kann. Beispiel:
	 * ../plugins/PLUGINNAME/fileName.yml
	 * 
	 * 
	 * Creates an spigot config file (.yml) which is easy to edit. e.g:
	 * ../plugins/PLUGINNAME/fileName.yml
	 * 
	 * @param fileName
	 */
	public ConfigManager(String fileName) {
		this.file = new File(Main.getInstance().getDataFolder(), fileName + ".yml");
		this.cfg = YamlConfiguration.loadConfiguration(this.file);
		this.fileName = fileName;
		save();
	}

	/**
	 * Erstellt eine .yml File welche so einfach bearbeitet werden kann. Beispiel:
	 * ../plugins/PLUGINNAME/fileName.yml
	 * 
	 * Creates an spigot config file (.yml) which is easy to edit. e.g:
	 * ../plugins/PLUGINNAME/fileName.yml
	 * 
	 * 
	 * @param file
	 */
	public ConfigManager(File file) {
		this.file = file;
		this.cfg = YamlConfiguration.loadConfiguration(this.file);
		this.fileName = file.getName();
		save();
	}

	/**
	 * Erstellt eine .yml File welche so einfach bearbeitet werden kann. Beispiel:
	 * ../plugins/PLUGINNAME/PATH../fileName.yml
	 * 
	 * 
	 * Creates an spigot config file (.yml) which is easy to edit. e.g:
	 * ../plugins/PLUGINNAME/fileName.yml
	 * 
	 * @param path
	 * @param fileName
	 */
	public ConfigManager(String path, String fileName) {
		this.file = new File(Main.getInstance().getDataFolder(), path + fileName + ".yml");
		this.cfg = YamlConfiguration.loadConfiguration(this.file);
		this.fileName = fileName;
		save();
	}

	/**
	 * Erstellt eine .yml File welche so einfach bearbeitet werden kann. Beispiel:
	 * ../plugins/PLUGINNAME/PATH../fileName.yml
	 * 
	 * 
	 * Creates an spigot config file (.yml) which is easy to edit. e.g:
	 * ../plugins/PLUGINNAME/fileName.yml
	 * 
	 * @param path
	 * @param fileName
	 */
	public ConfigManager(String[] path, String fileName) {
		String fullPath = "";
		for (String inPath : path) {
			fullPath += inPath + "/";
		}
		this.file = new File(Main.getInstance().getDataFolder(), fullPath + fileName + ".yml");
		this.cfg = YamlConfiguration.loadConfiguration(this.file);
		this.fileName = fileName;
		save();
	}

	/**
	 * Safes the Configuration.
	 * 
	 * @return
	 */
	public boolean save() {
		try {
			this.cfg.save(this.file);
		} catch (IOException e) {
			System.out.println("[TWINGS] " + this.fileName + "Could'nt be saved.");
			return false;
		}
		// Safed.
		return true;
	}

	/**
	 * Enthaelt die Liste von einem Titel in der Config.
	 * 
	 * Contains the list of all String of a point in an .yml file. e.g:
	 * 
	 * Particles: A: dadad B: adfasf C: a: Ddwewe: x
	 * 
	 * List<String> list = getStringList("Particles"): returns: - "A" - "B" - "C"
	 * 
	 * @param title
	 * @return
	 */
	public ArrayList<String> getStringList(String title) {
		Set<String> maps = this.cfg.getConfigurationSection(title).getKeys(false);
		ArrayList<String> list = new ArrayList<String>(maps);
		return list;
	}

	/**
	 * Gibt den Wert des Keys als String wieder.
	 * 
	 * returns a String from a key.
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		String value = cfg.getString(key);
		return value;
	}

	/**
	 * Gibt den Wert des Pfades+Keys als String wieder.
	 * 
	 * returns a String from a key and path
	 * 
	 * @param path
	 * @param key
	 * @return
	 */
	public String getString(String[] path, String key) {
		String fullPath = "";
		for (String inPath : path) {
			fullPath += inPath + ".";
		}
		String value = cfg.getString(fullPath + key);
		return value;
	}

	/**
	 * Gibt den Wert des Pfades+Keys als String wieder.
	 * 
	 * returns a String from a key and path
	 * 
	 * @param path
	 * @param key
	 * @return
	 */
	public String getString(String path, String key) {
		String value = cfg.getString(path + "." + key);
		return value;
	}

	/**
	 * Gibt den Wert des Keys als double wieder.
	 * 
	 * returns a double from a given key.
	 * 
	 * @param key
	 * @return
	 */
	public double getDouble(String key) {
		double value = cfg.getDouble(key);
		return value;
	}

	/**
	 * Gibt den Wert des Pfades+Keys als double wieder.
	 * 
	 * * returns a double from a given key+path.
	 * 
	 * @param path
	 * @param key
	 * @return
	 */
	public double getDouble(String[] path, String key) {
		String fullPath = "";
		for (String inPath : path) {
			fullPath += inPath + ".";
		}
		double value = cfg.getDouble(fullPath + key);
		return value;
	}

	/**
	 * Gibt den Wert des Pfades+Keys als double wieder.
	 * 
	 * returns a double from a given key+path.
	 * 
	 * @param path
	 * @param key
	 * @return
	 */
	public double getDouble(String path, String key) {
		double value = cfg.getDouble(path + "." + key);
		return value;
	}

	/**
	 * Gibt den Wert des Keys als ganze Zahl wieder.
	 * 
	 * returns a int from a given key.
	 * 
	 * @param key
	 * @return
	 */
	public int getInt(String key) {
		int value = cfg.getInt(key);
		return value;
	}

	/**
	 * Gibt den Wert des Pfades+Keys als int wieder.
	 * 
	 * returns a int from a given key+path.
	 * 
	 * @param path
	 * @param key
	 * @return
	 */
	public int getInt(String[] path, String key) {
		String fullPath = "";
		for (String inPath : path) {
			fullPath += inPath + ".";
		}
		int value = cfg.getInt(fullPath + key);
		return value;
	}

	/**
	 * Gibt den Wert des Pfades+Keys als int wieder.
	 *
	 * returns a int from a given key+path.
	 * 
	 * @param path
	 * @param key
	 * @return
	 */
	public int getInt(String path, String key) {
		int value = cfg.getInt(path + "." + key);
		return value;
	}

	/**
	 * Gibt den Wert des Keys als Boolean wieder.
	 * 
	 * returns a boolean from a given key.
	 * 
	 * @param string
	 * @return
	 */
	public boolean getBoolean(String string) {
		// TODO Auto-generated method stub
		return cfg.getBoolean(string);
	}

	/**
	 * Erstellt einen Pfad+Key mit einem Wert, falls es diesen bereits gibt wird
	 * dieser ueberschrieben und es wird TRUE returned.
	 * 
	 * Sets a path+key with a value if this path+key already exists it will be
	 * overwritten and it returns true.
	 * 
	 * @param path
	 * @param value
	 * @return
	 */
	public boolean set(String path, Object value) {
		this.cfg.set(path, value);
		if (this.cfg.get(path + "." + value) != null)
			return true;
		return false;
	}

	/**
	 * adds an default option which is not getting overwritten after a safe.
	 * 
	 * @param string
	 * @param object
	 */
	public void addDefault(String string, Object object) {
		if (cfg.get(string) != null)
			return;
		set(string, object);
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public YamlConfiguration getCfg() {
		return cfg;
	}

	public void setCfg(YamlConfiguration cfg) {
		this.cfg = cfg;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
