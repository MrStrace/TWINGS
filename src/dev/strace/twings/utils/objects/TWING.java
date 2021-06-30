package dev.strace.twings.utils.objects;

import dev.strace.twings.Main;
import dev.strace.twings.listener.PlayerMoveListener;
import dev.strace.twings.players.CurrentWings;
import dev.strace.twings.utils.ConfigManager;
import dev.strace.twings.utils.ItemBuilder;
import dev.strace.twings.utils.MyColors;
import dev.strace.twings.utils.calculate.Rotating;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TWING {

	// Die Neigung der Wings in Grad
	public int tilt = 0;

	// If show when running is enabled
	// the tilt can also be adjusted >D
	public int runTilt = 0;

	// Rotation
	public int rotation = 0;

	// Das Pattern der Wings mit Codes
	public ParticleCode[][] pattern;

	public List<String> exclude = new ArrayList<String>();

	// Animiert ja/nein
	public boolean animated = true;

	// If the Pattern should be mirrored
	public boolean mirrow = true;

	// Tilts the wings before rotating if true
	public boolean tiltBefore = false;

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

	// Moves the Wing overall up on the Y coordinate
	public double moveup = 0;

	// How far each particle is away from each other
	public double spacing = 0.07;

	// The Item which is going in the GUI;
	public ItemStack item;
	public Material material;
	public String itemName;
	public String creator;

	public String permission;

	private ConfigManager config;
	public File file;

	public TWING(ConfigManager config) {
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
		tiltBefore = config.getBoolean("tiltbefore");
		rotation = config.getInt("rotation");
		if (config.getDouble("spacing") == 0)
			spacing = 0.07;
		else
			spacing = config.getDouble("spacing");
		exclude = config.getList("exclude");

	}

	/**
	 * This Function sets up the Wing Particle Location.
	 * 
	 * 
	 * @param p    Player
	 * @param wing
	 */
	public void drawWings(Player p) {

		
		Location ploc = p.getLocation().clone();
		ploc.setPitch(-45);
		Location location = p.getLocation().clone().subtract(ploc.getDirection().setY(0).multiply(this.getMoveback()));
		int patternlenght = this.getPattern()[0].length;

		int addition = this.getDegreeAddition();
		int sneakAddition = this.getSneakAddition();
		double space = this.getSpacing();
		double y = location.clone().getY() + 1.4;
		if (!this.isTiltBefore())
			y += this.getMoveup();
		if (p.isSneaking()) {
			y -= 0.3;
			space -= 0.01;
		}

		HashMap<Player, Integer> animated = Main.getInstance().getAnimation().getAnimated();
		if (!animated.isEmpty() && this.isAnimated() == true) {
			addition += animated.get(p);
			sneakAddition += animated.get(p);
		}

		if (isRunning(p, this)) {
			return;
		}

		double defX;
		if (this.isMirrow())
			defX = location.getX() - (space * patternlenght) + space;
		else
			defX = location.getX() - (space * patternlenght / 2) + space;
		double x = defX;

		double fire = -((location.getYaw() + (180 + addition)) / 60);

		fire = -((location.getYaw() + (180 + sneakAddition)) / 60);
		fire += (location.getYaw() < -180 ? Math.PI : 2.985);
		this.calculateEachLocation(space, x, y, fire, defX, location, false);
		if (this.isMirrow()) {
			defX = location.getX() + (space * patternlenght);
			x = defX;
			fire = -((location.getYaw() + (180 - sneakAddition)) / 60);
			fire += (location.getYaw() < -180 ? Math.PI : 2.985);
			this.calculateEachLocation(space, x, y, fire, defX, location, true);
		}
		return;
	}

	/**
	 * 
	 * @param location
	 */
	public void drawWings(Location location) {
		int addition = this.getDegreeAddition();
		int patternlenght = this.getPattern()[0].length;

		double space = this.getSpacing();
		double defX;

		if (this.isMirrow())
			defX = location.getX() - (space * patternlenght) + space;
		else
			defX = location.getX() - (space * patternlenght / 2) + space;
		double x = defX;
		double y = location.clone().getY() + 1.4;
		if (!this.isTiltBefore())
			y += this.getMoveup();
		double fire = -((location.getYaw() + (180 + addition)) / 60);

		fire += (location.getYaw() < -180 ? Math.PI : 2.985);

		this.calculateEachLocation(space, x, y, fire, defX, location, false);

		if (this.isMirrow()) {
			defX = location.getX() + (space * patternlenght);
			x = defX;
			y = location.clone().getY() + 1.4;
			if (!this.isTiltBefore())
				y += this.getMoveup();
			fire = -((location.getYaw() + (180 - addition)) / 60);
			fire += (location.getYaw() < -180 ? Math.PI : 2.985);
			this.calculateEachLocation(space, x, y, fire, defX, location, true);
		}
		return;

	}

	private void calculateEachLocation(double space, double x, double y, double fire, double defX, Location location,
			boolean left) {

		for (int i = 0; i < this.getPattern().length; i++) {
			ParticleCode[] alone = this.getPattern()[i];
			for (int j = 0; j < alone.length; j++) {
				/*
				 * if the particlecode is given it will display at the perfect location.
				 */
				if (alone[j] != null) {
					Location target = location.clone();
					target.setX(x);
					target.setY(y);
					handleVectorChangeAndSend(alone[j], target, location, left, fire);
				}

				if (left == false)
					x += space;
				else
					x -= space;
			}
			y -= space;
			x = defX;
		}

	}

	/**
	 * This function will handle all vector calculations/changes and will send it to
	 * the location, after that it will turn back to the normal vector.
	 * 
	 * @param code
	 * @param target
	 * @param tochange
	 * @param left
	 * @param fire
	 */
	private void handleVectorChangeAndSend(ParticleCode code, Location target, Location tochange, boolean left,
			double fire) {

		Vector v = target.toVector().subtract(tochange.toVector());
		Vector vpitch = Rotating.rotateAroundAxisX(v, tilt);
		Vector v2 = Rotating.getBackVector(tochange, left);
		v = Rotating.rotateAroundAxisY(v, fire + this.getRotation());

		if (this.isMirrow()) {
			if (left == false)
				v2.setY(0).multiply(-0.25);
			else
				v2.setY(0).multiply(0.25);
		} else
			v2.setY(0).multiply(-0.5);

		tochange.add(vpitch);
		tochange.add(v);
		tochange.add(v2);
		if (this.isTiltBefore())
			tochange.setY(tochange.getY() + this.getMoveup());
		MyColors.sendParticles(code, tochange);
		if (this.isTiltBefore())
			tochange.setY(tochange.getY() - this.getMoveup());
		tochange.subtract(vpitch);
		tochange.subtract(v2);
		tochange.subtract(v);
	}

	public TWING register() {

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

		System.out.println(this.getFile().getName() + "LOADED");
		return this;
	}

	/**
	 * registers and handles all ParticleCodes and saves them in the List.
	 * 
	 * @param config
	 * @return {@link ArrayList} with {@link ParticleCode}'s
	 */
	public ArrayList<ParticleCode> handleParticleCodeCache(ConfigManager config) {
		ArrayList<ParticleCode> list = new ArrayList<ParticleCode>();

		for (String particleCode : config.getStringList("Particles")) {
			if (!this.getExclude().contains(particleCode)) {
				String value = config.getString("Particles", particleCode);

				// Wenn Redstone als Partikel erkannt wird geht es davon aus das RGB vorhanden
				// ist, und filtert so die RGB Zahlen und

				// String teilen in 2 (PARTICLETYPE : RGB)
				String[] split = value.split(":");
				ParticleCode newcode;
				double speed = 0;
				if (value.contains("REDSTONE")) {

					// in case no speed is given the redstone PArticle needs to have a bigger size
					// then 0 so default i set 0.8
					// For Redstone Particles speed = size.
					speed = 0.8;

					// int[] erstellt mit 3 Werten (R1 G2 B3)
					int[] rgb = new int[3];

					// Unnoetige Zeichen entfernen.
					String code = split[1].replace(")", "").replace("(", "").replace(" ", "");

					// RGB Zahlen nach "," filtern und zuweisen.
					String[] SRGB = code.split(",");
					rgb = new int[] { Integer.parseInt(SRGB[0]), Integer.parseInt(SRGB[1]), Integer.parseInt(SRGB[2]) };

					if (split.length > 2) {
						speed = Double.parseDouble(split[2]);
					}
					// Erstellt neuen ParticleCode mit Farbe.
					newcode = new ParticleColor(particleCode, rgb).setSpeed(speed);
				} else {
					// Erstellt neuen ParticleCode.
					Particle particle;
					if (split.length > 1) {
						particle = Particle.valueOf(split[0]);
						speed = Double.parseDouble(split[1]);
					} else
						particle = Particle.valueOf(value);
					newcode = new ParticleCode(particleCode, particle).setSpeed(speed);
				}
				list.add(newcode);
			}
		}
		return list;
	}

	/**
	 * Creates the Item for the GUI's
	 * 
	 * @return {@link ItemStack}
	 */
	public ItemStack createItemStack(Player p, GUI gui) {
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

		if (this.getCreator() != null) {
			lore.add(Main.getInstance().getConfigString("Menu.creator").replace("%creator%", this.getCreator()));
		}
		switch (gui) {
		case WINGS:
			String perms = "";
			/**
			 * If the person has Permission the Symbol has to change so we look if he has
			 * the perms, if so the Symbol is the right one.
			 */
			if (p.hasPermission(this.getPermission())) {
				perms = Main.getInstance().getConfigString("haspermission");
			} else {
				perms = Main.getInstance().getConfigString("nopermission");
			}

			// Add the Permission Lore
			lore.add(Main.getInstance().getConfigString("Menu.permissions").replace("%perms%", perms));

			// Enchant if equipped:
			if (CurrentWings.current != null)
				if (!CurrentWings.current.isEmpty())
					if (CurrentWings.current.containsKey(p.getUniqueId()))
						if (CurrentWings.current.get(p.getUniqueId()).contains(this.file)) {
							item.addGlow();
							lore.add(Main.getInstance().getMsg().getRightclick());
							lore.add(Main.getInstance().getMsg().getShiftrightclick());
							break;
						}
			lore.add(Main.getInstance().getMsg().getLeftclick());
			lore.add(Main.getInstance().getMsg().getShiftleftclick());
			break;
		case PREVIEW:
			// Add the Permission Lore
			lore.add(MyColors.format("&cLeft click to set the Preview Location"));
			lore.add(MyColors.format("&cRight click to remove the Preview"));
			break;
		case EDIT:
			lore.add(Main.getInstance().getMsg().getEditleft(this));
			lore.add(Main.getInstance().getMsg().getEditright(this));
			break;
		default:
			break;
		}

		item.setLore(lore);

		return item.build();

	}

	public enum GUI {
		EDIT, WINGS, PREVIEW, PICTURE

	};

	private boolean isRunning(Player p, TWING wing) {
		if (wing.showWhenRunning == true && PlayerMoveListener.moving.contains(p))
			return false;

		if (PlayerMoveListener.moving.contains(p))
			return true;

		return false;

	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public boolean isTiltBefore() {
		return tiltBefore;
	}

	public void setTiltBefore(boolean tiltBefore) {
		this.tiltBefore = tiltBefore;
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

	public ItemStack getItem(Player p, GUI gui) {
		return createItemStack(p, gui);
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

	public double getSpacing() {
		return spacing;
	}

	public void setSpacing(double spacing) {
		this.spacing = spacing;
	}

	public List<String> getExclude() {
		return exclude;
	}

	public void setExclude(List<String> exclude) {
		this.exclude = exclude;
	}

}
