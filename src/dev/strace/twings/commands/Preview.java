package dev.strace.twings.commands;

import dev.strace.twings.Main;
import dev.strace.twings.listener.InventoryClickListener;
import dev.strace.twings.utils.ConfigManager;
import dev.strace.twings.utils.gui.CategoryGUI;
import dev.strace.twings.utils.gui.GUI;
import dev.strace.twings.utils.gui.WingPreviewGUI;
import org.bukkit.entity.Player;

/**
 * 
 * @author Jason Holweg
 *
 */
public class Preview extends SubCommands {

	@Override
	public String getName() {
		return "preview";
	}

	@Override
	public String getDesc() {
		return "Opens a GUI with all Wings to set a Preview Location.";
	}

	@Override
	public String getSyntax() {
		return "/wings " + getName();
	}

	@Override
	public void perform(Player p, String[] args) {
		

		if (!p.hasPermission("twings.admin")) {
			p.sendMessage(Main.getInstance().getMsg().getNopermission());
			return;
		}
		
		if (args.length == 1) {
			ConfigManager manager = new ConfigManager("CategoryGUI");
			if (manager.getBoolean("enabled")) {
				new CategoryGUI(p);
				InventoryClickListener.menuMap.put(p, GUI.CAT.PREVIEW);
			}
			else
			new WingPreviewGUI(p, 0, "XXX");
		}
	}
}
