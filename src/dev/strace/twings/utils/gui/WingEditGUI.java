package dev.strace.twings.utils.gui;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import dev.strace.twings.Main;
import dev.strace.twings.utils.ItemBuilder;
import dev.strace.twings.utils.MyColors;
import dev.strace.twings.utils.WingUtils;
import dev.strace.twings.utils.objects.Wing;

public class WingEditGUI {

	private int size;
	private boolean pagination = false;
	private String name = Main.getInstance().getConfigString("Menu.title").replace("%prefix%",
			Main.getInstance().getPrefix()) + MyColors.format("&c&lEDIT");

	public WingEditGUI(int wingcount) {
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

		for (Wing wing : WingUtils.winglist.values()) {
			ItemBuilder builder = new ItemBuilder(wing.getItem());

			// If the Creator Tag isnt already applied it will be created.
			if (!wing.getItem().getItemMeta().getLore().contains(
					Main.getInstance().getConfigString("Menu.creator").replace("%creator%", wing.getCreator()))) {
				/**
				 * Adding the Creator Tag to the WingPreview Lore
				 */
				if (wing.getCreator() != null) {
					builder.addLore(
							Main.getInstance().getConfigString("Menu.creator").replace("%creator%", wing.getCreator()));
				}

				String perms = "";
				/**
				 * If the person has Permission the Symbol has to change so we look if he has
				 * the perms, if so the Symbol is the right one.
				 */
				if (p.hasPermission(wing.getPermission())) {
					perms = Main.getInstance().getConfigString("haspermission");
				} else {
					perms = Main.getInstance().getConfigString("nopermission");
				}

				// Add the Permission Lore
				builder.addLore(Main.getInstance().getConfigString("Menu.permissions").replace("%perms%", perms));
				builder.addLore(MyColors.format("&cLeft click to set the edit status"));
				builder.addLore(MyColors.format("&cRight click to remove the edit status"));
			}
			// Add the Item in the GUI (Inventory)
			inv.addItem(builder.build());
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
