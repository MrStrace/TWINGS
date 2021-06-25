package dev.strace.twings.utils.objects;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import dev.strace.twings.Main;
import dev.strace.twings.api.API;
import dev.strace.twings.players.CurrentWings;
import dev.strace.twings.utils.ConfigManager;

/**
 * 
 * @author Jason Holweg [STRACE] <b>TWINGS</b><br>
 *         Website: <a>https://strace.dev/</a><br>
 *         GitHub: <a>https://github.com/MrStrace</a><br>
 *         Created: Jun 25, 2021<br>
 *
 */
public class EquipTimer {

	private Player p;
	private long time;
	private Wing wing;
	private String equiptime;
	private BukkitTask timer;

	/**
	 * creates a new timer for a specific player with a specific wing and equipt
	 * time.<br>
	 * 
	 * Equiptime could be something like:<br>
	 * 300M (300 Minutes which is 5 hours also could be written as just 5H or
	 * 18000S) <br>
	 * also there is an option "untildeath" (in equiptime) so the wings will stay
	 * till death.
	 * 
	 * @param p
	 * @param wing
	 * @param equiptime
	 */
	public EquipTimer(Player p, Wing wing, String equiptime) {
		this.p = p;
		this.wing = wing;
		this.time = System.currentTimeMillis();
		this.equiptime = equiptime;
		Symbol symbol = getSymbol();
		String timeleft = equiptime.toUpperCase().replace(symbol.getSign(), "");
		if (symbol != Symbol.UNTILDEATH) {
			p.sendMessage(
					Main.getInstance().getMsg().getWingsrecieved(timeleft + " " + symbol.toString().toLowerCase()));
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
		if (getSymbol() == Symbol.UNTILDEATH) {
			new ConfigManager("untildeath").set(p.getUniqueId().toString(), null);
		} else
			timer.cancel();
	}

	/**
	 * 
	 * @author Jason Holweg [STRACE] <b>TWINGS</b><br>
	 *         Website: <a>https://strace.dev/</a><br>
	 *         GitHub: <a>https://github.com/MrStrace</a><br>
	 *         Created: Jun 25, 2021<br>
	 *
	 */
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

	/**
	 * returns the time by symbol in seconds. e.g: (1 hour) = 1 * 60 (seconds) * 60
	 * (minutes) = 3600 seconds
	 * 
	 * @param s
	 * @return
	 */
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

	/**
	 * returns the symbol of the time it will be equiped.
	 * 
	 * @return
	 */
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

	public Player getP() {
		return p;
	}

	public void setP(Player p) {
		this.p = p;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public Wing getWing() {
		return wing;
	}

	public void setWing(Wing wing) {
		this.wing = wing;
	}

	public BukkitTask getTimer() {
		return timer;
	}

	public void setTimer(BukkitTask timer) {
		this.timer = timer;
	}

	public void setEquiptime(String equiptime) {
		this.equiptime = equiptime;
	}

}
