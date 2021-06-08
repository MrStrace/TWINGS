package dev.strace.twings.commands;

import org.bukkit.entity.Player;

import dev.strace.twings.Main;

/**
 * 
 * @author Jason Holweg [STRACE] <b>TWINGS</b><br>
 *         Website: <a>https://strace.dev/</a><br>
 *         GitHub: <a>https://github.com/MrStrace</a><br>
 *         Created: Jun 8, 2021<br>
 *
 */
public class Reload extends SubCommands {

	@Override
	public String getName() {
		return "reload";
	}

	@Override
	public String getDesc() {
		return "Reloads the Plugin.";
	}

	@Override
	public String getSyntax() {
		return "/wings " + getName();
	}

	@Override
	public void perform(Player p, String[] args) {
		switch (args.length) {
		case 1:
			if (p.hasPermission("twings.admin"))
				Main.load(true);
			
			break;

		default:
			break;
		}
	}

}
