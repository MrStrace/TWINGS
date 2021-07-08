package dev.strace.twings.commands;

import java.io.File;

import org.bukkit.entity.Player;

import dev.strace.twings.Main;
import dev.strace.twings.players.CurrentWings;
import dev.strace.twings.utils.WingUtils;

/**
 * 
 * @author Jason Holweg
 *
 */
public class UnEquip extends SubCommands {

	public UnEquip() {
		super("unequip", "UnEquips any current Particles from you.", "/wings unequip");
	}

	@Override
	public void perform(Player p, String[] args) {

		if (args.length == 1) {
			if (!CurrentWings.current.containsKey(p.getUniqueId()))
				return;
			
			if (Main.getInstance().getMsg().isShowMessages())
				for (File file : CurrentWings.getCurrent().get(p.getUniqueId())) {
					p.sendMessage(Main.getInstance().getMsg().getUnequip(WingUtils.winglist.get(file)));
				}

			CurrentWings.current.remove(p.getUniqueId());
			new CurrentWings().removeAllCurrentWing(p);
		}
	}
}
