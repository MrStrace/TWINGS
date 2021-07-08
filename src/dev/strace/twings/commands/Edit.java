package dev.strace.twings.commands;

import org.bukkit.entity.Player;

import dev.strace.twings.Main;
import dev.strace.twings.utils.WingUtils;
import dev.strace.twings.utils.gui.WingEditGUI;

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

	public Edit() {
		super("edit", "Opens a GUI with all Wings to set a File in Edit mode.", "/wings edit");
	}

	@Override
	public void perform(Player p, String[] args) {

		if (!p.hasPermission("twings.admin")) {
			p.sendMessage(Main.getInstance().getMsg().getNopermission());
			return;
		} 
		
		if (args.length == 1) {
			new WingEditGUI(WingUtils.winglist.size()).openGUI(p);
		}
	}
}
