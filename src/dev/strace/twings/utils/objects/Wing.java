package dev.strace.twings.utils.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;

import dev.strace.twings.Main;
import dev.strace.twings.utils.ConfigManager;
import dev.strace.twings.utils.ItemBuilder;
import dev.strace.twings.utils.MyColors;
import dev.strace.twings.utils.WingUtils;

public class Wing {

	// Die Neigung der Wings in Grad
	public int tilt = 0;
	
	// If show when running is enabled
	// the tilt can also be adjusted >D
	public int runTilt = 0;

	// Das Pattern der Wings mit Codes
	public ParticleCode[][] pattern;

	// Animiert ja/nein
	public boolean animated = true;
	
	// If the Pattern should be mirrored
	public boolean mirrow = true;

	// Grad Zahl die addiert wird beim Sneaken (damit es so aussieht als ob die ein
	// wenig zusammen gehen)
	public int sneakAddition = 0;

	// Standart grad zahl wo weit die Wings zusammen sind.
	// (0) --
	// (45) /\
	// (90) |
	public int degreeAddition = 0;

	// Falls ja werden die Wings weiterhin auch beim Rennen angezeigt.
	public boolean showWhenRunning = false;

	// Moves the Wings behind the Body (MULTIPLIES the Direction Vector)
	public double moveback = 0;

	//Moves the Wing overall up on the Y coordinate
	public double moveup = 0;
	
	// The Item which is going in the GUI;
	public ItemStack item;
	public Material material;
	public String itemName;
	public String creator;

	public String permission;

	private ConfigManager config;
	public File file;

	public Wing(ConfigManager config) {
		this.config = config;

		tilt = config.getInt("tilt");
		animated = config.getBoolean("Animated");
		showWhenRunning = config.getBoolean("ShowWhenRunning");
		sneakAddition = config.getInt("SneakingDegreeAddition");
		degreeAddition = config.getInt("DegreeAddition");
		moveback = config.getDouble("moveback");
		moveup = config.getDouble("moveup");
		material = Material.valueOf(config.getString("Item.Material"));
		itemName = config.getString("Item.Name");
		creator = config.getString("creator");
		permission = config.getString("permission");
		file = config.getFile();
		mirrow = config.getBoolean("mirrow");
		runTilt = config.getInt("runtilt");
	}

	public Wing register() {

		/*
		 * Fuer jede File geht er nun die ParticleListe durch und fuegt jeden
		 * ParticleCode zur codelist hinzu.
		 */
		ArrayList<ParticleCode> codelist = handleParticleCodeCache(config);

		@SuppressWarnings("unchecked")
		List<String> patterntiles = (List<String>) config.getCfg().getList("pattern");
		int row = patterntiles.size();
		int col = patterntiles.get(0).split(",").length + 1;
		ParticleCode[][] pattern = new ParticleCode[row][col];
		int i = 0;
		int j = 0;
		for (String patternCode : patterntiles) {
			for (String code : patternCode.split(",")) {

				for (ParticleCode pc : codelist) {
					if (code.equalsIgnoreCase(pc.getCode())) {
						pattern[i][j] = pc;
						break;
					} else {
						pattern[i][j] = null;
					}

				}
				j++;
			}
			j = 0;
			i++;
		}
		this.setPattern(pattern);

		item = createItemStack();

		return this;
	}

	public ArrayList<ParticleCode> handleParticleCodeCache(ConfigManager config) {
		ArrayList<ParticleCode> list = new ArrayList<ParticleCode>();

		for (String particleCode : config.getStringList("Particles")) {

			String value = config.getString("Particles", particleCode);

			// Wenn Redstone als Partikel erkannt wird geht es davon aus das RGB vorhanden
			// ist, und filtert so die RGB Zahlen und
			if (value.contains("REDSTONE")) {

				// int[] erstellt mit 3 Werten (R1 G2 B3)
				int[] rgb = new int[3];

				// String teilen in 2 (PARTICLETYPE : RGB)
				String[] split = value.split(":");

				// Unnoetige Zeichen entfernen.
				String code = split[1].replace(")", "").replace("(", "").replace(" ", "");

				// RGB Zahlen nach "," filtern und zuweisen.
				String[] SRGB = code.split(",");
				rgb = new int[] { Integer.parseInt(SRGB[0]), Integer.parseInt(SRGB[1]), Integer.parseInt(SRGB[2]) };

				// Erstellt neuen ParticleCode mit Farbe.
				list.add(new ParticleColor(particleCode, rgb));

			} else {
				// Erstellt neuen ParticleCode.
				list.add(new ParticleCode(particleCode, Particle.valueOf(value)));

			}
		}
		return list;
	}

	public ItemStack createItemStack() {
		ItemBuilder item = new ItemBuilder(material);
		item.setName(MyColors.format(itemName));
		if (!Main.instance.getConfig().getBoolean("Menu.preview"))
			return item.build();
		ArrayList<String> lore = new ArrayList<String>();

		for (int i = 0; i < pattern.length; i++) {
			String add = "";
			ParticleCode[] alone = pattern[i];
			for (int j = 0; j < alone.length; j++) {
				if (alone[j] != null) {
					if (alone[j] instanceof ParticleColor)
						add += MyColors.format(((ParticleColor) alone[j]).getHexaCode()
								+ Main.instance.getConfig().getString("Menu.symbol") + "&r");
					else
						add += Main.instance.getConfig().getString("Menu.symbol");
				} else
					add += "§r§f" + Main.instance.getConfig().getString("Menu.symbol");

			}
			lore.add(add);
		}
		item.setLore(lore);

		return item.build();

	}

	
	
	public int getRunTilt() {
		return runTilt;
	}

	public void setRunTilt(int runTilt) {
		this.runTilt = runTilt;
	}

	public boolean isMirrow() {
		return mirrow;
	}

	public void setMirrow(boolean mirrow) {
		this.mirrow = mirrow;
	}

	public double getMoveup() {
		return moveup;
	}

	public void setMoveup(double moveup) {
		this.moveup = moveup;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public double getMoveback() {
		return moveback;
	}

	public void setMoveback(double moveback) {
		this.moveback = moveback;
	}

	public int getTilt() {
		return tilt;
	}

	public void setTilt(int tilt) {
		this.tilt = tilt;
	}

	public ParticleCode[][] getPattern() {
		return pattern;
	}

	public void setPattern(ParticleCode[][] pattern) {
		this.pattern = pattern;
	}

	public boolean isAnimated() {
		return animated;
	}

	public void setAnimated(boolean animated) {
		this.animated = animated;
	}

	public int getSneakAddition() {
		return sneakAddition;
	}

	public void setSneakAddition(int sneakAddition) {
		this.sneakAddition = sneakAddition;
	}

	public int getDegreeAddition() {
		return degreeAddition;
	}

	public void setDegreeAddition(int degreeAddition) {
		this.degreeAddition = degreeAddition;
	}

	public boolean isShowWhenRunning() {
		return showWhenRunning;
	}

	public void setShowWhenRunning(boolean showWhenRunning) {
		this.showWhenRunning = showWhenRunning;
	}

}
