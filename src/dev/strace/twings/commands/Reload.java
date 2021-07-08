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

	public Reload() {
		super("reload", "Reloads the Plugin.", "/wings reload");
	}

	@Override
	public void perform(Player p, String[] args) {
		if (args.length == 1) {
			if (p.hasPermission("twings.admin"))
				Main.load();
			else
				p.sendMessage(Main.getInstance().getMsg().getNopermission());
		}
	}

}
