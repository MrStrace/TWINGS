package dev.strace.twings.utils.gui;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import dev.strace.twings.Main;
import dev.strace.twings.utils.ItemBuilder;
import dev.strace.twings.utils.MyColors;

public class PictureGUI {

	private int size;
	private String name = Main.getInstance().getConfigString("Menu.title").replace("%prefix%",
			Main.getInstance().getPrefix()) + MyColors.format(" &c&lPICTURES");
	public PictureGUI() {
		int size = (new File(Main.getInstance().getDataFolder(), "pictures").listFiles().length + 9) / 9;
		if (size >= 6) {
			size = 6;
		}
		this.size = size * 9;
	}

	public void openGUI(Player p) {
		p.openInventory(createGUI(p));
		p.playSound(p.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.3f, 1f);
	}

	/**
	 * Creates the Inventory GUI for a specific Player.
	 * 
	 * @param p
	 * @return
	 */
	private Inventory createGUI(Player p) {

		Inventory inv = Bukkit.createInventory(null, size,
				name);

		insertWings(inv, p);

		return inv;
	}

	private void insertWings(Inventory inv, Player p) {

		for (File files : new File(Main.getInstance().getDataFolder(), "pictures").listFiles()) {
			ItemBuilder builder = new ItemBuilder(Material.MAP);
			builder.setName(files.getName());
			inv.addItem(builder.build());
		}

	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
