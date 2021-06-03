package dev.strace.twings.commands;

import org.bukkit.entity.Player;

import dev.strace.twings.players.CurrentWings;

/**
 * 
 * @author Jason Holweg
 *
 */
public class UnEquip extends SubCommands {

	@Override
	public String getName() {
		return "unequip";
	}

	@Override
	public String getDesc() {
		return "UnEquips any current Particles from you.";
	}

	@Override
	public String getSyntax() {
		return "/wings " + getName();
	}

	@Override
	public void perform(Player p, String[] args) {

		if (args.length == 1) {
			CurrentWings.current.remove(p.getUniqueId());
			new CurrentWings().removeCurrentWing(p);
		}
	}
}
