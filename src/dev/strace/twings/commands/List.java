package dev.strace.twings.commands;

import dev.strace.twings.Main;
import dev.strace.twings.utils.WingUtils;
import dev.strace.twings.utils.objects.TWING;
import org.bukkit.entity.Player;

import java.io.File;

/**
 * 
 * @author Jason Holweg [STRACE] <b>TWINGS</b><br>
 *         Website: <a>https://strace.dev/</a><br>
 *         GitHub: <a>https://github.com/MrStrace</a><br>
 *         Created: Jun 7, 2021<br>
 *
 */
public class List extends SubCommands {

	@Override
	public String getName() {
		return "list";
	}

	@Override
	public String getDesc() {
		return "Lists all available particles.";
	}

	@Override
	public String getSyntax() {
		return "/wings " + getName();
	}

	@Override
	public void perform(Player p, String[] args) {
		switch (args.length) {
		case 1:
			// First Line of the Wing list.
			p.sendMessage(Main.getInstance().getMsg().getList());
			for (File file : WingUtils.winglist.keySet()) {
				TWING wing = WingUtils.winglist.get(file);
				/*
				 * Only if the player has the permission of the wing the wings will get
				 * displayed.
				 */
				if (p.hasPermission(wing.getPermission()))
					p.sendMessage(Main.getInstance().getMsg().getListpoint(wing));
			}
			break;

		default:
			break;
		}
	}

}
