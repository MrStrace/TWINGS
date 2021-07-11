package dev.strace.twings.utils.gui;

import dev.strace.twings.Main;
import dev.strace.twings.utils.MyColors;
import dev.strace.twings.utils.WingUtils;
import org.bukkit.entity.Player;

public class WingEditGUI extends GUI {

	public WingEditGUI(Player p, int page, String category) {
		super(p, Main.getInstance().getConfigString("Menu.title").replace("%prefix%", Main.getInstance().getPrefix()) + MyColors.format(" &c&lEDIT"), page, category);
		this.insertItems(WingUtils.categorymap.get(category), category);
		p.openInventory(this.inventory);
	}
}
