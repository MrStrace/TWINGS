package dev.strace.twings.utils.objects;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import dev.strace.twings.Main;
import dev.strace.twings.api.API;
import dev.strace.twings.players.CurrentWings;
import dev.strace.twings.utils.ConfigManager;

public class EquipTimer {

	Player p;
	long time;
	Wing wing;
	String equiptime;
	BukkitTask timer;
	
	public EquipTimer(Player p, Wing wing, String equiptime) {
		this.p = p;
		this.wing = wing;
		this.time = System.currentTimeMillis();
		this.equiptime = equiptime;
		Symbol symbol = getSymbol();
		String timeleft = equiptime.toUpperCase().replace(symbol.getSign(), "");
		if (symbol != Symbol.UNTILDEATH) {
			p.sendMessage(Main.getInstance().getMsg().getWingsrecieved(timeleft + " " + symbol.toString().toLowerCase()));
			new API(wing.getFile().getName()).setPlayerCurrentWing(p);
			 this.timer = new BukkitRunnable() {
				@Override
				public void run() {
					new CurrentWings().removeCurrentWing(p);
					p.sendMessage(Main.getInstance().getMsg().getWingsgone());
				}
			}.runTaskLaterAsynchronously(Main.getInstance(), getTime(symbol) * 20);
		} else {
			new API(wing.getFile().getName()).setPlayerCurrentWing(p);
			ConfigManager manager = new ConfigManager("untildeath");
			manager.set(p.getUniqueId().toString(), wing.getFile().getName());
			manager.save();
			p.sendMessage(Main.getInstance().getMsg().getWingsrecieved(symbol.toString().toLowerCase()));
		}
	}
	
	public void cancel() {
		new CurrentWings().removeCurrentWing(p);
		p.sendMessage(Main.getInstance().getMsg().getWingsgone());
		if(getSymbol()==Symbol.UNTILDEATH) {
			new ConfigManager("untildeath").set(p.getUniqueId().toString(), null);
		} else
		timer.cancel();
	}

	public enum Symbol {

		UNTILDEATH("UntilDeath"), SECONDS("S"), HOURS("H"), DAYS("D"), MINUTES("M");

		private String sign;

		private Symbol(String sign) {
			this.sign = sign;
		}

		public String getSign() {
			return sign;
		}

	}

	private int getTime(Symbol s) {
		String t = equiptime.toUpperCase().replace(s.getSign(), "");
		int time = Integer.parseInt(t);
		switch (s) {
		case SECONDS:
			return time;
		case MINUTES:
			return time * 60;
		case HOURS:
			return time * 60 * 60;
		case DAYS:
			return time * 60 * 60 * 24;
		default:
			return time;
		}

	}

	private Symbol getSymbol() {
		for (Symbol sym : Symbol.values()) {
			if (sym.getSign().equalsIgnoreCase(equiptime))
				return sym;
			if (sym.getSign().equalsIgnoreCase(equiptime.charAt(equiptime.length() - 1) + ""))
				return sym;
		}
		return Symbol.SECONDS;
	}

	public String getEquiptime() {
		return equiptime;
	}
}
