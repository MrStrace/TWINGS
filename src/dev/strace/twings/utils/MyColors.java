package dev.strace.twings.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;

import net.md_5.bungee.api.ChatColor;

/**
 * 
 * @author Jason Holweg [STRACE] <b>TWINGS</b><br>
 *         Website: <a>https://strace.dev/</a><br>
 *         GitHub: <a>https://github.com/MrStrace</a><br>
 *         Created: Jun 25, 2021<br>
 *
 */
public class MyColors {

	private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

	/**
	 * formats the String into colored minecraft string, also formats hexcolor
	 * codes.
	 * 
	 * @param msg
	 * @return cool string.
	 */
	public static String format(String msg) {
		if (Bukkit.getVersion().contains("1.16") || Bukkit.getVersion().contains("1.17")) {
			if (msg.contains("#")) {
				Matcher match = pattern.matcher(msg);
				while (match.find()) {
					String color = msg.substring(match.start(), match.end());
					msg = msg.replace(color, ChatColor.of(color) + "");
					match = pattern.matcher(msg);
				}
			}
		}
		return ChatColor.translateAlternateColorCodes('&', msg);

	}

}
