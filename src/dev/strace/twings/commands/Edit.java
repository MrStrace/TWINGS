package dev.strace.twings.commands;

import org.bukkit.entity.Player;


import dev.strace.twings.utils.WingUtils;
import dev.strace.twings.utils.gui.WingEditGUI;


/**
 * 
 * @author Jason Holweg
 *
 */
public class Edit extends SubCommands {

	@Override
	public String getName() {
		return "edit";
	}

	@Override
	public String getDesc() {
		return "Opens a GUI with all Wings to set a File in Edit mode.";
	}

	@Override
	public String getSyntax() {
		return "/wings " + getName();
	}

	@Override
	public void perform(Player p, String[] args) {

		if (args.length == 1) {
			new WingEditGUI(WingUtils.winglist.size()).openGUI(p);
		}
	}
}
