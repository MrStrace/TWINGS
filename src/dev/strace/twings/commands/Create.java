package dev.strace.twings.commands;

import org.bukkit.entity.Player;

import dev.strace.twings.Main;
import dev.strace.twings.utils.gui.PictureGUI;

/**
 * 
 * @author Jason Holweg [STRACE]
 * <b>TWINGS</b><br>
 * Website: <a>https://strace.dev/</a><br>
 * GitHub: <a>https://github.com/MrStrace</a><br>
 * Created: Jun 10, 2021<br>
 *
 */
public class Create extends SubCommands {

	public Create() {
		super("create", "Creates a wing from a picture.", "/wings create [picture]");
	}

	@Override
	public void perform(Player p, String[] args) {

		if (!p.hasPermission("twings.admin")) {
			p.sendMessage(Main.getInstance().getMsg().getNopermission());
			return;
		} 
		
		if (args.length == 1) {
			new PictureGUI().openGUI(p);
		}
	}
}
