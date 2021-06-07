package dev.strace.twings.api;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import dev.strace.twings.Main;
import dev.strace.twings.players.CurrentWings;
import dev.strace.twings.utils.ItemBuilder;
import dev.strace.twings.utils.MyColors;
import dev.strace.twings.utils.SendWings;
import dev.strace.twings.utils.WingUtils;
import dev.strace.twings.utils.objects.Wing;

/**
 * 
 * @author Jason Holweg [STRACE] <b>TWINGS</b><br>
 *         Website: <a>https://strace.dev/</a><br>
 *         GitHub: <a>https://github.com/MrStrace</a><br>
 *         Created: May 25, 2021<br>
 *
 */
public class API {

	private WingUtils utils;
	private File file;
	private YamlConfiguration cfg;
	private Wing wing;

	/**
	 * The API Constructor.<br>
	 * 
	 * Use the Wings FileName <b>WITH .yml</b>
	 * 
	 * @param fileName
	 */
	public API(String fileName) {
		utils = new WingUtils();
		file = new File(Main.instance.getDataFolder() + "/wings/" + fileName);
		wing = WingUtils.winglist.get(file);
	}

	public API loadCfg() {
		cfg = YamlConfiguration.loadConfiguration(file);
		return this;
	}

	/**
	 * 
	 * @param lore
	 * @return ItemStack
	 */
	public ItemStack getWingItem(boolean lore) {
		try {
			String name = getWingName();
			ItemBuilder stack = new ItemBuilder(wing.getMaterial()).setName(name);
			if (!lore)
				return stack.build();

			return wing.getItem();

		} catch (Exception e) {
			utils.logError("Check your " + file.getName() + " maybe you missspelled the item material wrong!");
		}
		return null;
	}

	/**
	 * 
	 * @return Wing object
	 */
	public Wing getWing() {
		return wing;
	}

	/**
	 * 
	 * @return WingName with minecraft color.
	 */
	public String getWingName() {
		try {
			return MyColors.format(wing.getItemName());

		} catch (Exception e) {
			utils.logError("Check your " + file.getName() + " maybe you missspelled the item material wrong!");
		}
		return null;
	}

	/**
	 * Sets the player new Wings.
	 * 
	 * @param p
	 */
	public void setPlayerCurrentWing(Player p) {
		new CurrentWings().setCurrentWing(p, file);
	}

	/**
	 * sends the Particle to a Location.
	 * 
	 * @param loc
	 */
	public void sendWingTo(Location loc) {
		try {
			new SendWings().drawWings(loc, wing);
		} catch (Exception e) {
			utils.logError("This file is not a Wing or might not exist? : " + file.getName());
		}
	}

	/**
	 * sends the Particle to a new Location.
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 */
	public void sendWingTo(World world, double x, double y, double z) {
		try {
			new SendWings().drawWings(new Location(world, x, y, z), wing);
		} catch (Exception e) {
			utils.logError("This file is not a Wing or might not exist? : " + file.getName());
		}

	}

	/*
	 * getters and setters
	 */

	public WingUtils getUtils() {
		return utils;
	}

	public void setUtils(WingUtils utils) {
		this.utils = utils;
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

	public void setWing(Wing wing) {
		this.wing = wing;
	}

}
