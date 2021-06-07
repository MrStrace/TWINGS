package dev.strace.twings.commands;

import java.io.File;

import org.bukkit.entity.Player;

import dev.strace.twings.Main;
import dev.strace.twings.utils.WingUtils;
import dev.strace.twings.utils.objects.Wing;

/**
 * 
 * @author Jason Holweg [STRACE] <b>TWINGS</b><br>
 *         Website: <a>https://strace.dev/</a><br>
 *         GitHub: <a>https://github.com/MrStrace</a><br>
 *         Created: Jun 7, 2021<br>
 *
 */
public class list extends SubCommands {

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
			p.sendMessage(Main.getInstance().getPrefix() + " §7List of all Particles:");
			for (File file : WingUtils.winglist.keySet()) {
				Wing wing = WingUtils.winglist.get(file);
				/*
				 * Only if the player has the permission of the wing the wings will get
				 * displayed.
				 */
				if (p.hasPermission(wing.getPermission()))
					p.sendMessage(" §7- §f" + file.getName().replace(".yml", "").replace(" ", "_"));
			}
			break;

		default:
			break;
		}
	}

}
