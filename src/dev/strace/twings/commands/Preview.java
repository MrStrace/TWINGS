package dev.strace.twings.commands;

import org.bukkit.entity.Player;

import dev.strace.twings.Main;
import dev.strace.twings.utils.WingUtils;
import dev.strace.twings.utils.gui.WingPreviewGUI;

/**
 * 
 * @author Jason Holweg
 *
 */
public class Preview extends SubCommands {

	public Preview() {
		super("preview", "Opens a GUI with all Wings to set a Preview Location.", "/wings preview");
	}

	@Override
	public void perform(Player p, String[] args) {
		

		if (!p.hasPermission("twings.admin")) {
			p.sendMessage(Main.getInstance().getMsg().getNopermission());
			return;
		}
		
		if (args.length == 1) {
			new WingPreviewGUI(WingUtils.winglist.size()).openGUI(p);
		}
	}
}
