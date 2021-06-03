package dev.strace.twings.commands;

import org.bukkit.entity.Player;

import dev.strace.twings.utils.WingUtils;
import dev.strace.twings.utils.gui.WingPreviewGUI;


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

		if (args.length == 1) {
			new WingPreviewGUI(WingUtils.winglist.size()).openGUI(p);
		}
	}
}
