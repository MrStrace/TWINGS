package dev.strace.twings.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;

import net.md_5.bungee.api.ChatColor;

public enum MyColors {
	NULL(0, 0, 0), RED(255, 0, 0), BLUE(0, 0, 255), GREEN(0, 255, 0), GB(218, 165, 032), GB2(255, 165, 000),
	BLACK(1, 1, 1), WHITE(255, 255, 255), PINK(255, 105, 180), LACHS(255, 192, 203), PURPLE(148, 0, 211),
	NAVY(39, 64, 139), DEEPBLUE(0, 0, 139), LIGHTBLUE(135, 206, 255), SKY(141, 238, 238), AQUA(0, 255, 255),
	BLUECYAN(0, 178, 238), GREENCYAN(127, 255, 212), LIME(173, 255, 47), EMERALD(0, 255, 127), DESERT(255, 246, 143),
	GRAY(139, 139, 122), GOLD(205, 205, 0), YELLOW(255, 255, 0), SKINCOLOR(255, 193, 193), CHILLRED(255, 106, 106),
	CORAL(255, 130, 71), ORANGE(255, 127, 36), REDAPPLE(255, 48, 48), DEEPRED(139, 0, 0), BLUTORANGE(255, 69, 0),
	MAGENTA(255, 20, 147), DEEPPINK(255, 52, 179), INDINGO(125, 38, 205), MAYONNAISE(255, 255, 238), MAROON(128, 0, 0),
	TEAL(0, 128, 128), FUCHSIA(255, 0, 255), SILVER(192, 192, 192), Gray(128, 128, 128), Maroon(128, 0, 0),
	Olive(128, 128, 0), Green(0, 128, 0), Purple(128, 0, 128), Teal(0, 128, 128), Navy(0, 0, 128), maroon(128, 0, 0),
	dark_red(139, 0, 0), BROWN(165, 42, 42), firebrick(178, 34, 34), crimson(220, 20, 60), tomato(255, 99, 71),
	coral(255, 127, 80), indian_red(205, 92, 92), light_coral(240, 128, 128), dark_salmon(233, 150, 122),
	salmon(250, 128, 114), light_salmon(255, 160, 122), orange_red(255, 69, 0), dark_orange(255, 140, 0),
	orange(255, 165, 0), gold(255, 215, 0), dark_golden_rod(184, 134, 11), golden_rod(218, 165, 32),
	pale_golden_rod(238, 232, 170), dark_khaki(189, 183, 107), khaki(240, 230, 140), olive(128, 128, 0),
	dark_olive_green(85, 107, 47), olive_drab(107, 142, 35), lawn_green(124, 252, 0), chart_reuse(127, 255, 0),
	green_yellow(173, 255, 47), dark_green(0, 100, 0), forest_green(34, 139, 34), lime(0, 255, 0),
	lime_green(50, 205, 50), light_green(144, 238, 144), pale_green(152, 251, 152), dark_sea_green(143, 188, 143),
	medium_spring_green(0, 250, 154), spring_green(0, 255, 127), sea_green(46, 139, 87),
	medium_aqua_marine(102, 205, 170), medium_sea_green(60, 179, 113), light_sea_green(32, 178, 170),
	dark_slate_gray(47, 79, 79), dark_cyan(0, 139, 139), light_cyan(224, 255, 255), dark_turquoise(0, 206, 209),
	turquoise(64, 224, 208), medium_turquoise(72, 209, 204), pale_turquoise(175, 238, 238), aqua_marine(127, 255, 212),
	powder_blue(176, 224, 230), cadet_blue(95, 158, 160), steel_blue(70, 130, 180), corn_flower_blue(100, 149, 237),
	deep_sky_blue(0, 191, 255), dodger_blue(30, 144, 255), light_blue(173, 216, 230), light_sky_blue(135, 206, 250),
	midnight_blue(25, 25, 112), navy(0, 0, 128), dark_blue(0, 0, 139), medium_blue(0, 0, 205), royal_blue(65, 105, 225),
	blue_violet(138, 43, 226), indigo(75, 0, 130), dark_slate_blue(72, 61, 139), slate_blue(106, 90, 205),
	medium_slate_blue(123, 104, 238), medium_purple(147, 112, 219), dark_magenta(139, 0, 139), dark_violet(148, 0, 211),
	dark_orchid(153, 50, 204), medium_orchid(186, 85, 211), purple(128, 0, 128), thistle(216, 191, 216),
	plum(221, 160, 221), orchid(218, 112, 214), medium_violet_red(199, 21, 133), pale_violet_red(219, 112, 147),
	deep_pink(255, 20, 147), hot_pink(255, 105, 180), light_pink(255, 182, 193), pink(255, 192, 203),
	antique_white(250, 235, 215), beige(245, 245, 220), bisque(255, 228, 196), blanched_almond(255, 235, 205),
	wheat(245, 222, 179), corn_silk(255, 248, 220), lemon_chiffon(255, 250, 205),
	light_golden_rod_yellow(250, 250, 210), light_yellow(255, 255, 224), saddle_brown(139, 69, 19), sienna(160, 82, 45),
	chocolate(210, 105, 30), peru(205, 133, 63), sandy_brown(244, 164, 96), burly_wood(222, 184, 135),
	tan(210, 180, 140), rosy_brown(188, 143, 143), moccasin(255, 228, 181), navajo_white(255, 222, 173),
	peach_puff(255, 218, 185), misty_rose(255, 228, 225), lavender_blush(255, 240, 245), linen(250, 240, 230),
	old_lace(253, 245, 230), papaya_whip(255, 239, 213), sea_shell(255, 245, 238), mint_cream(245, 255, 250),
	slate_gray(112, 128, 144), light_slate_gray(119, 136, 153), light_steel_blue(176, 196, 222),
	LAVENDAR(230, 230, 250), FLORAL_WHITE(255, 250, 240), ALICE_BLUE(240, 248, 255), GHOST_WHITE(248, 248, 255),
	HONEYDEW(240, 255, 240), IVORY(255, 255, 240), AZURE(240, 255, 255), SNOW(255, 250, 250),;

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	int r, g, b;

	MyColors(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

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
