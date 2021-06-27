package dev.strace.twings.utils.gui;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import dev.strace.twings.Main;
import dev.strace.twings.utils.ItemBuilder;
import dev.strace.twings.utils.MyColors;
import dev.strace.twings.utils.WingUtils;
import dev.strace.twings.utils.objects.TWING;
import dev.strace.twings.utils.objects.TWING.GUI;

public class WingPreviewGUI {

	private int size;
	private boolean pagination = false;
	private String name = Main.getInstance().getConfigString("Menu.title").replace("%prefix%",
			Main.getInstance().getPrefix()) + MyColors.format("&c&lPREVIEW");

	public WingPreviewGUI(int wingcount) {
		int size = (wingcount + 9) / 9;
		if (size >= 6) {
			size = 6;
			this.pagination = true;
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

		Inventory inv = Bukkit.createInventory(null, size, name);

		insertWings(inv, p);

		return inv;
	}

	private void insertWings(Inventory inv, Player p) {

		for (TWING wing : WingUtils.winglist.values()) {
			inv.addItem(wing.createItemStack(p, GUI.PREVIEW));
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
