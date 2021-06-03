package dev.strace.twings.listener;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import dev.strace.twings.Main;
import dev.strace.twings.players.CurrentWings;
import dev.strace.twings.utils.LocationBuilder;
import dev.strace.twings.utils.MyColors;
import dev.strace.twings.utils.WingPreview;
import dev.strace.twings.utils.WingUtils;
import dev.strace.twings.utils.gui.WingEditGUI;
import dev.strace.twings.utils.gui.WingPreviewGUI;
import dev.strace.twings.utils.objects.Wing;

public class InventoryClickListener implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getView() == null)
			return;
		if (e.getView().getTitle() == null)
			return;
		if (e.getWhoClicked() == null)
			return;
		if (!(e.getWhoClicked() instanceof Player))
			return;

		if (e.getView().getTitle().equalsIgnoreCase(
				Main.getInstance().getConfigString("Menu.title").replace("%prefix%", Main.getInstance().getPrefix()))) {
			e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
			if (e.getCurrentItem() == null)
				return;

			for (Wing wing : WingUtils.winglist.values()) {
				handleNormalMenu(e, p, wing);
			}

		}

		if (e.getView().getTitle().equalsIgnoreCase(new WingPreviewGUI(0).getName())) {
			e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
			if (e.getCurrentItem() == null)
				return;

			for (Wing wing : WingUtils.winglist.values()) {
				handlePreviewMenu(e, p, wing);
			}

		}

		if (e.getView().getTitle().equalsIgnoreCase(new WingEditGUI(0).getName())) {
			e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
			if (e.getCurrentItem() == null)
				return;

			for (Wing wing : WingUtils.winglist.values()) {
				handleEditMenu(e, p, wing);
			}

		}

	}

	public void handlePreviewMenu(InventoryClickEvent e, Player p, Wing wing) {
		if (e.getCurrentItem().equals(wing.getItem())) {
			/*
			 * Sets the Preview Location
			 */
			if (e.getClick().equals(ClickType.LEFT)) {

				if (p.hasPermission("twings.setpreview") || p.hasPermission("twings.admin")) {
					LocationBuilder lb = new LocationBuilder();
					lb.setLocation(wing.getFile().getName().replace(".yml", ""), p.getLocation());
					p.sendMessage(Main.getInstance().getPrefix() + MyColors.format(" &aPreview Location set."));
					return;
				}
			}

			/*
			 * Removes the Preview Location
			 */
			if (e.getClick().equals(ClickType.RIGHT)) {
				if (p.hasPermission("twings.setpreview") || p.hasPermission("twings.admin")) {
					LocationBuilder lb = new LocationBuilder();
					lb.set(wing.getFile().getName().replace(".yml", ""), null);
					p.sendMessage(Main.getInstance().getPrefix() + MyColors.format(" &cPreview removed."));
					return;
				}
			}
		}
	}

	public void handleEditMenu(InventoryClickEvent e, Player p, Wing wing) {
		if (e.getCurrentItem().equals(wing.getItem())) {
			/*
			 * Sets the Preview Location
			 */
			if (e.getClick().equals(ClickType.LEFT)) {

				if (p.hasPermission("twings.setedit") || p.hasPermission("twings.admin")) {
					WingPreview.edit = wing;
					return;
				}
			}

			/*
			 * Removes the Preview Location
			 */
			if (e.getClick().equals(ClickType.RIGHT)) {
				if (p.hasPermission("twings.setpreview") || p.hasPermission("twings.admin")) {
					WingPreview.edit = null;
					return;
				}
			}
		}
	}

	public void handleNormalMenu(InventoryClickEvent e, Player p, Wing wing) {
		if (e.getCurrentItem().equals(wing.getItem())) {

			if (e.getClick().equals(ClickType.LEFT)) {
				if (!CurrentWings.getCurrent().isEmpty()) {
					if (!CurrentWings.getCurrent().get(p.getUniqueId()).exists())
						return;
					p.sendMessage(Main.getInstance().getConfigString("Messages.remove")
							.replace("%prefix%", Main.getInstance().getPrefix())
							.replace("%WingName%", MyColors.format(WingUtils.winglist
									.get(CurrentWings.getCurrent().get(p.getUniqueId())).getItemName())));
				}
				if (Main.instance.getConfig().getBoolean("Messages.onEquip")) {
					p.sendMessage(Main.getInstance().getConfigString("Messages.choose")
							.replace("%prefix%", Main.getInstance().getPrefix())
							.replace("%WingName%", MyColors.format(wing.getItemName())));
				}
				new CurrentWings().setCurrentWing(p, wing.getFile());
				p.playSound(p.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 0.4f, 10f);
				p.closeInventory();
				return;
			}

			/*
			 * Removes the clicked wing on rightclick.
			 */
			if (e.getClick().equals(ClickType.RIGHT)) {

				if (CurrentWings.getCurrent().isEmpty())
					return;
				if (!CurrentWings.getCurrent().containsKey(p.getUniqueId()))
					return;
				new CurrentWings().removeCurrentWing(p);
				return;
			}
		}
	}
}
