package dev.strace.twings.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import dev.strace.twings.Main;
import dev.strace.twings.utils.WingUtils;
import dev.strace.twings.utils.objects.EquipTimer;
import dev.strace.twings.utils.objects.Wing;

/**
 * 
 * @author Jason Holweg [STRACE] <b>TWINGS</b><br>
 *         Website: <a>https://strace.dev/</a><br>
 *         GitHub: <a>https://github.com/MrStrace</a><br>
 *         Created: Jun 7, 2021<br>
 *
 */
public class Give extends SubCommands {

	private static HashMap<Player, EquipTimer> timers = new HashMap<Player, EquipTimer>();

	@Override
	public String getName() {
		return "give";
	}

	@Override
	public String getDesc() {
		return "Gives a player Wings for time.";
	}

	@Override
	public String getSyntax() {
		return "/wings " + getName() + " [player] [twing] [time]";
	}

	@Override
	public void perform(Player p, String[] args) {
		switch (args.length) {

		case 1:
		case 2:
		case 3:
			if (!p.hasPermission("twings.admin")) {
				p.sendMessage(Main.getInstance().getMsg().getNopermission());
				return;
			} 
			p.sendMessage(Main.getInstance().getPrefix() + " §7try: §c" + getSyntax());
			break;
		case 4:
			if (!p.hasPermission("twings.admin")) {
				p.sendMessage(Main.getInstance().getMsg().getNopermission());
				return;
			} 
			if (Bukkit.getPlayerExact(args[1]) == null) {
				p.sendMessage(Main.getInstance().getMsg().getPlayernotfound());
				break;
			}
			Player target = Bukkit.getPlayerExact(args[1]);

			for (Wing wing : WingUtils.winglist.values()) {
				String name = "";
				if (wing.getItemName().contains("&")) {
					String[] to = wing.getItemName().split("&");
					for (String x : to) {
						if (x.length() > 0)
							name += x.substring(1);
					}
				} else
					name = wing.getItemName();
				if (name.replace(" ", "_").equalsIgnoreCase(args[2])) {
					if(timers.containsKey(p))
							timers.get(p).cancel();
					EquipTimer timer = new EquipTimer(target, wing, args[3]);
					timers.put(target,timer);
					return;
				}
			}
			// If its not finding the particle it will return nothing and displays this
			// message.
			p.sendMessage(Main.getInstance().getMsg().getWingnotfound(args[2]));
			break;
		default:
			break;
		}

	}

}
