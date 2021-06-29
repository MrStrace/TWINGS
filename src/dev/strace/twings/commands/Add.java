package dev.strace.twings.commands;

import dev.strace.twings.Main;
import dev.strace.twings.players.CurrentWings;
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
public class Add extends SubCommands {

	@Override
	public String getName() {
		return "add";
	}

	@Override
	public String getDesc() {
		return "Add a currentwing.";
	}

	@Override
	public String getSyntax() {
		return "/wings " + getName() + "[twing]";
	}

	@Override
	public void perform(Player p, String[] args) {
		switch (args.length) {
		/*
		 * If no specific particle is stated a list of all wings is going to be
		 * displayed.
		 */
		case 1:
			// First Line of the Wing list.
			p.sendMessage(Main.getInstance().getPrefix() + " §7List of all Particles:");
			for (File file : WingUtils.winglist.keySet()) {
				TWING wing = WingUtils.winglist.get(file);
				/*
				 * Only if the player has the permission of the wing the wings will get
				 * displayed.
				 */
				if (p.hasPermission(wing.getPermission()))
					p.sendMessage(Main.getInstance().getMsg().getListpoint(wing));
				else
					p.sendMessage(Main.getInstance().getMsg().getNopermission());
			}
			break;

		/*
		 * If a specific particle is stated the particle is going to be equiped if it
		 * exists.
		 */
		case 2:
			for (TWING wing : WingUtils.winglist.values()) {
				String name = "";
				if (wing.getItemName().contains("&")) {
					String[] to = wing.getItemName().split("&");
					for (String x : to) {
						if (x.length() > 0)
							name += x.substring(1);
					}
				} else
					name = wing.getItemName();
				if (name.replace(" ", "_").equalsIgnoreCase(args[1])) {
					if (p.hasPermission(wing.getPermission())) {
						new CurrentWings().addCurrentWing(p, wing.getFile());
						p.sendMessage(Main.getInstance().getMsg().getEquip(wing));
						return;
					}
					p.sendMessage(Main.getInstance().getMsg().getNopermission());
					return;
				}
			}
			// If its not finding the particle it will return nothing and displays this
			// message.
			p.sendMessage(Main.getInstance().getMsg().getWingnotfound(args[1]));
			break;
		default:
			break;
		}

	}

}
