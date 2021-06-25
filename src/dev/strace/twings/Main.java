package dev.strace.twings;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import dev.strace.twings.api.SpigotMcAPI;
import dev.strace.twings.commands.WingsCommand;
import dev.strace.twings.listener.InventoryClickListener;
import dev.strace.twings.listener.PlayerConnectionListener;
import dev.strace.twings.listener.PlayerDeathListener;
import dev.strace.twings.listener.PlayerMoveListener;
import dev.strace.twings.players.CurrentWings;
import dev.strace.twings.players.PlayWings;
import dev.strace.twings.utils.ConfigManager;
import dev.strace.twings.utils.Messages;
import dev.strace.twings.utils.Metrics;
import dev.strace.twings.utils.MyColors;
import dev.strace.twings.utils.SendWings;
import dev.strace.twings.utils.WingPreview;
import dev.strace.twings.utils.WingReader;
import dev.strace.twings.utils.WingTemplate;

/**
 * 
 * @author Jason Holweg [STRACE] <b>TWINGS</b><br>
 *         Website: <a>https://strace.dev/</a><br>
 *         GitHub: <a>https://github.com/MrStrace</a><br>
 *         Created: May 31, 2021<br>
 *
 */
public class Main extends JavaPlugin {

	public static Main instance;
	public static Plugin plugin;
	public ConfigManager config;
	public Messages msg;

	@Override
	public void onEnable() {
		// Init plugin
		plugin = this;
		// Init instance
		instance = this;

		// Creates or loads the Config.yml
		config = new ConfigManager("config");
		// Config is getting written (Defaults)
		registerConfig();

		// Init lang.yml
		msg = new Messages().init().load();

		load(false);

		// All Listeners getting enabled.
		registerListener();

		// Register command
		this.getCommand("wings").setExecutor(new WingsCommand());

		// Register bStats
		handleMetrics();

		// creates pictures folder
		new File(Main.getInstance().getDataFolder(), "pictures").mkdir();
	}

	public static void load(Boolean reload) {
		if (reload)
			new CurrentWings().onDisable();

		// Check Plugin version (is it uptodate?)
		checkVersion();

		// Init Template.yml (a Example of an Wing)
		new WingTemplate("template").createTemplate();

		// Init WingReader all Wings getting saved in cached.
		new WingReader().registerWings();

		// Init Wing animation.
		new SendWings().enableAnimated();

		// Wings getting displayed every ticks.
		new PlayWings().playOnPlayers();

		// Enables animated Wings.
		new PlayWings().enableAnimated();

		// Players getting there old Wings equipped (Important after reload)
		new CurrentWings().onEnable();

		// Enables the Wing previews
		new WingPreview().enablePreview();

		// Init lang.yml
		Main.getInstance().msg = new Messages().init().load();

		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.hasPermission("twings.admin"))
				p.sendMessage(Main.getInstance().getPrefix() + MyColors.format(" &cparticles reloaded."));
		}
	}

	// Things happen when the plugin is disabled.
	@Override
	public void onDisable() {
		new CurrentWings().onDisable();
	}

	/**
	 * Handles the bStats
	 */
	private void handleMetrics() {
		// Plugin Metrics/bStats
		int pluginId = 11688;
		new Metrics(this, pluginId);

	}

	/**
	 * Checks if the Version == plugin.yml version
	 */
	public static void checkVersion() {
		try {
			if (!plugin.getDescription().getVersion().equalsIgnoreCase(new SpigotMcAPI("82088").getVersion())) {
				System.out
						.println("[TWINGS] isn't uptodate! Visit https://www.spigotmc.org/resources/82088/ to update!");
			} else {
				System.out.println("[TWINGS] Plugin is uptodate!");
				System.out.println("[TWINGS] Thanks for downloading and using my plugin! ~ Strace");
			}
		} catch (IOException e) {
		}

	}

	/**
	 * Registers all Listener/Events
	 */
	private void registerListener() {
		PluginManager pm = Bukkit.getPluginManager();
		;
		pm.registerEvents(new PlayerMoveListener(), this);
		pm.registerEvents(new PlayerConnectionListener(), this);
		pm.registerEvents(new InventoryClickListener(), this);
		pm.registerEvents(new PlayerDeathListener(), this);
	}

	/**
	 * Creates the config.yml
	 */
	private void registerConfig() {
		config.addDefault("Prefix", "&e&lTWings");
		config.addDefault("Wings.showwithperms", false);
		config.addDefault("Wings.updaterate", 3);
		config.addDefault("Menu.title", "%prefix% &9Choose your Wings!");
		config.addDefault("Menu.symbol", "⏹");
		config.addDefault("Menu.creator", "&c&lCreator:&f %creator%");
		config.addDefault("Menu.permissions", "&7unlocked: [%perms%&7]");
		config.addDefault("Menu.preview", true);
		config.addDefault("haspermission", "&aYES");
		config.addDefault("nopermission", "&cNO");
		config.save();
	}

	/**
	 * 
	 * @return plugin prefix
	 */
	public String getPrefix() {
		if (this.getConfig().getString("Prefix") == null)
			return "ERROR";
		String prefix = MyColors.format(this.getConfig().getString("Prefix"));
		return prefix;
	}

	/**
	 * 
	 * @param path
	 * @return colored string
	 */
	public String getConfigString(String path) {
		if (this.getConfig().getString(path) == null)
			return "ERROR";
		String string = MyColors.format(this.getConfig().getString(path));
		return string;
	}

	public Messages getMsg() {
		return msg;
	}

	public static Main getInstance() {
		return instance;
	}

	public Plugin getPlugin() {
		return plugin;
	}
}