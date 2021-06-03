package dev.strace.twings.api;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import dev.strace.twings.Main;
import dev.strace.twings.utils.ItemBuilder;
import dev.strace.twings.utils.MyColors;
import dev.strace.twings.utils.SendWings;
import dev.strace.twings.utils.WingUtils;
import dev.strace.twings.utils.objects.Wing;

/**
 * 
 * @author Jason Holweg [STRACE]
 * <b>TWINGS</b><br>
 * Website: <a>https://strace.dev/</a><br>
 * GitHub: <a>https://github.com/MrStrace</a><br>
 * Created: May 25, 2021<br>
 *
 */
public class API {

	private WingUtils utils;
	private File file;
	private YamlConfiguration cfg;
	private Wing wing;

	/**
	 * 
	 */
	public API() {
		utils = new WingUtils();
	}

	/**
	 * This is needed to use <b>WingSpecific</b> functions. Use the Wings FileName
	 * <b>WITH .yml</b>
	 * 
	 * @param fileName
	 */
	public API(String fileName) {
		utils = new WingUtils();
		file = new File(Main.instance.getDataFolder() + "/wings/"+fileName);
		wing = WingUtils.winglist.get(file);
	}
	
	public API loadCfg() {
		cfg = YamlConfiguration.loadConfiguration(file);
		return this;
	}

	/**
	 * <b>WingSpecific</b>
	 * 
	 * @param lore
	 * @return ItemStack
	 */
	public ItemStack getWingItem(boolean lore) {
		try {
			String name = cfg.getString("Item.Name");
			ItemBuilder stack = new ItemBuilder(Material.valueOf(cfg.getString("Item.Material")))
					.setName(MyColors.format(name));
			if (lore) {
				//stack.setLore(cfg.getString("creator"), new WingGUI().modify(file));
			}

			return stack.build();

		} catch (Exception e) {
			utils.logError("Check your " + file.getName() + " maybe you missspelled the item material wrong!");
		}
		return null;
	}

	/**
	 * 
	 * @return WingName with minecraft color.
	 */
	public String getWingName() {
		try {
			return MyColors.format(cfg.getString("Item.Name"));

		} catch (Exception e) {
			utils.logError("Check your " + file.getName() + " maybe you missspelled the item material wrong!");
		}
		return null;
	}

	public void sendWingTo(Location loc) {
		try {
		new SendWings().drawWings(loc, wing);
		} catch (Exception e) {
			utils.logError("This file is not a Wing or might not exist? : " + file.getName());
		}
	}
	
	public void sendWingTo(World world,double x, double y, double z) {
		try {
		new SendWings().drawWings(new Location(world, x, y, z), wing);
		} catch (Exception e) {
			utils.logError("This file is not a Wing or might not exist? : " + file.getName());
		}
		
	}
	
}
