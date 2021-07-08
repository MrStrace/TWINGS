package dev.strace.twings.api;

import dev.strace.twings.Main;
import dev.strace.twings.players.CurrentWings;
import dev.strace.twings.utils.ItemBuilder;
import dev.strace.twings.utils.MyColors;
import dev.strace.twings.utils.WingUtils;
import dev.strace.twings.utils.objects.TWING;
import dev.strace.twings.utils.objects.TWING.GUI;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;

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
	private TWING wing;

    /**
     * The API Constructor.<br>
     * <p>
     * Use the Wings FileName <b>WITH .yml</b>
     *
     * @param fileName
     */
    public API(String fileName) {
        utils = new WingUtils();
        file = new File(Main.instance.getDataFolder() + "/wings/" + fileName);
        wing = WingUtils.winglist.get(file);
        if (wing == null) throw new NullPointerException("Cannot find wing!");

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
	public ItemStack getWingItem(Player p, boolean lore) {
		try {
			String name = getWingName();
			ItemBuilder stack = new ItemBuilder(wing.getMaterial()).setName(name);
			if (!lore)
				return stack.build();

			return wing.getItem(p, GUI.WINGS);

		} catch (Exception e) {
			utils.logError("Check your " + file.getName() + " maybe you missspelled the item material wrong!");
		}
		return null;
	}

	/**
	 *
	 * @return Wing object
	 */
	public TWING getTwing() {
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

	/*
	 * adds a TWING to a player
	 */
	public void addPlayerCurrentWings(Player p) {
		new CurrentWings().addCurrentWing(p, file);
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

	public void setWing(TWING wing) {
		this.wing = wing;
	}

}
