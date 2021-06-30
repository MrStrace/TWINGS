package dev.strace.twings.utils;

import dev.strace.twings.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
		StringBuilder fullPath = new StringBuilder();
		for (String inPath : path) {
			fullPath.append(inPath).append("/");
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
		return new ArrayList<String>(maps);
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
		return cfg.getString(key);
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
		StringBuilder fullPath = new StringBuilder();
		for (String inPath : path) {
			fullPath.append(inPath).append(".");
		}
		return cfg.getString(fullPath + key);
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
		return cfg.getString(path + "." + key);
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
		return cfg.getDouble(key);
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
		StringBuilder fullPath = new StringBuilder();
		for (String inPath : path) {
			fullPath.append(inPath).append(".");
		}
		return cfg.getDouble(fullPath + key);
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
		return cfg.getDouble(path + "." + key);
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
		return cfg.getInt(key);
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
		StringBuilder fullPath = new StringBuilder();
		for (String inPath : path) {
			fullPath.append(inPath).append(".");
		}
		return cfg.getInt(fullPath + key);
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
		return cfg.getInt(path + "." + key);
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
	 * returns an list of Strings.
	 * 
	 * @param key
	 * @return {@link ArrayList} of Strings
	 */
	@SuppressWarnings("unchecked")
	public List<String> getList(String key) {
		if (cfg.getList(key) != null)
			return (List<String>) cfg.getList(key);
		return new ArrayList<String>();
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
		return this.cfg.get(path + "." + value) != null;
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

	public void delete() {
		file.delete();
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
