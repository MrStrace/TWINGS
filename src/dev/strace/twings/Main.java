package dev.strace.twings;

import dev.strace.twings.api.SpigotMcAPI;
import dev.strace.twings.commands.WingsCommand;
import dev.strace.twings.listener.InventoryClickListener;
import dev.strace.twings.listener.PlayerConnectionListener;
import dev.strace.twings.listener.PlayerDeathListener;
import dev.strace.twings.listener.PlayerMoveListener;
import dev.strace.twings.players.CurrentWings;
import dev.strace.twings.players.PlayWings;
import dev.strace.twings.utils.*;
import dev.strace.twings.utils.gui.CategoryGUI;
import dev.strace.twings.utils.objects.Category;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * @author Jason Holweg [STRACE] <b>TWINGS</b><br>
 * Website: <a>https://strace.dev/</a><br>
 * GitHub: <a>https://github.com/MrStrace</a><br>
 * Created: May 31, 2021<br>
 */
public class Main extends JavaPlugin {

    public static Main instance;
    public static Plugin plugin;
    public ConfigManager config;
    public Messages msg;
    public Animation animation;

    @Override
    public void onEnable() {
        // Init plugin
        plugin = this;

        // Init instance
        instance = this;

        // Init animation
        animation = new Animation();

        // Creates or loads the Config.yml
        config = new ConfigManager("config");

        // Config is getting written (Defaults)
        registerConfig();

        // Init lang.yml
        msg = new Messages().init().load();

        load();

        // All Listeners getting enabled.
        registerListener();

        // Register command
        this.getCommand("wings").setExecutor(new WingsCommand());

        // Register bStats
        handleMetrics();

        // creates pictures folder
        new File(Main.getInstance().getDataFolder(), "pictures").mkdir();
    }

    public static void load() {


        // Check Plugin version (is it uptodate?)
        checkVersion();

        // Creat first Category (Wings)
        CategoryGUI.createDefaultConfig();

        // Init all Categories
        Category.initAll();

        // Init Template.yml (a Example of an Wing)
        new WingTemplate("template").createTemplate();

        // Init WingReader all Wings getting saved in cached.
        new WingReader().registerWings();

        // Wings getting displayed every ticks.
        new PlayWings().playOnPlayers();

        // Players getting there old Wings equipped (Important after reload)
        new CurrentWings().onEnable();

        // Init lang.yml
        Main.getInstance().msg = new Messages().init().load();

        // Enables the Wing previews
        new WingPreview().enablePreview();

        for (Player p : Bukkit.getOnlinePlayers()) {

            if (CurrentWings.current.containsKey(p.getUniqueId())) {
                Main.getInstance().getAnimation().getAnimated().put(p, 0);
                Main.getInstance().getAnimation().getPlus().put(p, true);
            }

            if (p.hasPermission("twings.admin"))
                p.sendMessage(Main.getInstance().getPrefix() + MyColors.format(" &cparticles reloaded."));
        }
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
        } catch (IOException ignored) {
        }

    }

    /**
     * Registers all Listener/Events
     */
    private void registerListener() {
        PluginManager pm = Bukkit.getPluginManager();
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
        config.addDefault("max equipped twings", 2);
        config.addDefault("Wings.showwithperms", false);
        config.addDefault("Wings.updaterate", 3);
        config.addDefault("Menu.title", "%prefix% &9Choose your Wings!");
        config.addDefault("Menu.symbol", "⏹");
        config.addDefault("Menu.creator", "&c&lCreator:&f %creator%");
        config.addDefault("Menu.permissions", "&7unlocked: [%perms%&7]");
        config.addDefault("Menu.lore.pattern", true);
        config.addDefault("Menu.lore.creator", true);
        config.addDefault("Menu.lore.permissions", true);
        config.addDefault("Menu.lore.action1", true);
        config.addDefault("Menu.lore.action2", true);
        config.addDefault("haspermission", "&aYES");
        config.addDefault("nopermission", "&cNO");
        config.addDefault("gui.arrowback", "&c<- back");
        config.addDefault("gui.arrownext", "&anext ->");
        config.addDefault("gui.unequip", "&4unequip particles");
        config.addDefault("hide on spectator", true);
        config.addDefault("hide on invis", true);
        config.save();
    }

    public int getMaxEquippedTwings() {
        return config.getInt("max equipped twings");
    }

    /**
     * @return plugin prefix
     */
    public String getPrefix() {
        if (this.getConfig().getString("Prefix") == null)
            return "ERROR";
        return MyColors.format(this.getConfig().getString("Prefix"));
    }

    /**
     * @param path file path
     * @return colored string
     */
    public String getConfigString(String path) {
        if (this.getConfig().getString(path) == null)
            return "ERROR";
        return MyColors.format(this.getConfig().getString(path)).replace("%prefix%", getPrefix());
    }

    public Messages getMsg() {
        return msg;
    }

    public static Main getInstance() {
        return instance;
    }

    public Animation getAnimation() {
        return animation;
    }


}