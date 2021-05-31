package dev.strace.twings.utils;

import java.util.ArrayList;

import org.bukkit.Material;
/**
 * 
 * @author Jason Holweg [STRACE]
 * TWINGS
 * Website: https://strace.dev/
 * GitHub: https://github.com/MrStrace
 * Erstellt May 23, 2021
 *
 */
public class WingTemplate extends ConfigManager {

	public WingTemplate() {
		super(new String[] {"wings"}, "template");
		createTemplate();
		save();
	}

	public void createTemplate() {
		
		cfg.addDefault("Particles.R", "REDSTONE:(255,0,0)");
		cfg.addDefault("Particles.G", "REDSTONE:(0,255,0)");
		cfg.addDefault("Particles.B", "REDSTONE:0,0,255");
		cfg.addDefault("Particles.SWEEP", "SWEEP_ATTACK");
		cfg.addDefault("Particles.E", "VILLAGER_HAPPY");
		cfg.addDefault("Item.Material", Material.MAP.toString());
		cfg.addDefault("Item.Name", "&dUgly Template Wings");
		cfg.addDefault("permission", "twings.template");
		cfg.addDefault("creator", "PixelStrace");
		cfg.addDefault("DegreeAddition", 10);
		cfg.addDefault("SneakingDegreeAddition", 20);
		cfg.addDefault("ShowWhenRunning", false);
		cfg.addDefault("tilt", 10);
		cfg.addDefault("Animated", true);
		
		ArrayList<String> pt = new ArrayList<String>();
		pt.add("x,E,x,x,x,x,x,x,x,x,x,x,x");
		pt.add("x,x,R,x,x,x,x,x,x,x,x,x,x");
		pt.add("x,G,R,R,x,x,x,x,x,x,x,x,x");
		pt.add("x,x,G,B,B,B,x,x,x,x,x,x,x");
		pt.add("x,x,x,x,B,B,B,R,x,x,x,x,x");
		pt.add("x,SWEEP,x,x,B,B,B,B,R,x,x,x,x");
		pt.add("x,x,x,R,B,B,B,B,R,R,x,x,x");
		pt.add("x,x,x,R,x,G,G,B,B,R,G,G,x");
		pt.add("x,x,x,x,x,x,x,x,B,B,B,x,B");
		pt.add("x,x,x,x,x,x,x,x,x,x,B,B,B");
		pt.add("x,x,x,x,x,x,x,x,B,B,B,B,B");
		pt.add("x,x,x,x,x,x,B,B,B,B,x,x,x");
		pt.add("x,x,x,x,B,B,x,B,x,x,x,x,x");
		pt.add("x,x,x,x,R,x,x,x,x,x,x,x,x");
		pt.add("x,x,x,x,x,x,x,x,x,x,x,x,x");
		cfg.addDefault("pattern", pt);
		cfg.options().copyDefaults(true);
		
	}
	
}
