package dev.strace.twings.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;

import dev.strace.twings.Main;

public class ConfigManager {

	// File die erstellt und zugegriffen wird.
	File file;
	// Config datei von Spigot die einfach gelesen werden kann.
	YamlConfiguration cfg;
	// Der Name der File.
	String fileName;

	/**
	 * Erstellt eine .yml File welche so einfach bearbeitet werden kann. Beispiel:
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
			System.out.println("[TWINGS] "+this.fileName + "Could'nt be saved.");
			return false;
		}
		// System.out.println("[TWINGS] "+this.fileName + " loaded and saved.");
		return true;
	}

	/**
	 * Enthaelt die Liste von einem Titel in der Config.
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

	public void addDefault(String string, Object object) {
		if(cfg.get(string) != null) return;
		set(string, object);
	}

	

}
