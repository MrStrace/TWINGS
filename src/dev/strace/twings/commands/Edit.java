package dev.strace.twings.commands;

import dev.strace.twings.Main;
import dev.strace.twings.utils.gui.WingEditGUI;
import org.bukkit.entity.Player;

/**
 * 
 * @author Jason Holweg [STRACE]
 * <b>TWINGS</b><br>
 * Website: <a>https://strace.dev/</a><br>
 * GitHub: <a>https://github.com/MrStrace</a><br>
 * Created: Jun 10, 2021<br>
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

		if (!p.hasPermission("twings.admin")) {
			p.sendMessage(Main.getInstance().getMsg().getNopermission());
			return;
		} 
		
		if (args.length == 1) {
			new WingEditGUI(p, 0, "XXX");
		}
	}
}
