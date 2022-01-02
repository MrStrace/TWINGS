package dev.strace.twings.utils;

import dev.strace.twings.Main;
import dev.strace.twings.utils.objects.ParticleColor;
import org.bukkit.Material;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jason Holweg [STRACE] <b>TWINGS</b><br>
 * Website: <a>https://strace.dev/</a><br>
 * GitHub: <a>https://github.com/MrStrace</a><br>
 * Created: Jun 25, 2021<br>
 */
public class WingTemplate extends ConfigManager {

    public WingTemplate(String name) {
        super(new String[]{"wings"}, name);
    }

    /**
     * creates a template particle from picture.
     *
     * @param bimg {@link BufferedImage}
     * @return if the particle was created.
     */
    public boolean createFromPicture(BufferedImage bimg, Material mat, String name, String category, String permission, String exclude) {

        // using ReadImage class to get easy access
        ReadImage img = new ReadImage();

        // get the particlecolor pattern from ReadImage from the picturefile.
        ParticleColor[][] pattern = img.getColor(bimg);

        // if the pattern is null (too big) it will not create the wings (safety
        // reasons)
        if (pattern == null) {
            return false;
        }
        // Pattern list with all particlecodes as a list
        /*
         * e.g: pattern: - x,x,x,x,x,x - x,x,x,x,x,x - x,x,x,x,x,x
         */
        List<String> pt = new ArrayList<String>();

        /*
         * foreach row in the pattern we now create the lines with strings e.g:
         * x,x,x,x,x,x,x,x,x,x,x,x
         */
        for (int i = 0; i < pattern.length; i++) {
            StringBuilder row = new StringBuilder();
            for (int y = 0; y < pattern[0].length; y++) {
                // while the pattern isn't null it will get the code and add it to the string.
                if (pattern[i][y] != null)
                    row.append("").append(pattern[i][y].getCode());
                else
                    // if it is null it will place an x so its no particle at all.
                    row.append("x");
                // while we didn't reach the end of the line we add an comma after each code.
                if (y != pattern[0].length)
                    row.append(",");
            }
            // add the line to the list.
            pt.add(row.toString());
        }

        /*
         * now we create the particlecodes: since we got already the color and "code" we
         * can use it and simply add it to a string and put it in the file under
         * "Particles.".
         */
        for (Color color : img.getColorlist().keySet()) {
            String code = img.getColorlist().get(color);
            int R = color.getRed();
            int G = color.getGreen();
            int B = color.getBlue();
            cfg.addDefault("Particles." + code, "REDSTONE:" + R + "," + G + "," + B);
        }

        // Add all options to let the user change what ever he likes.
        cfg.addDefault("Item.Material", mat.toString());
        cfg.addDefault("Item.Name", name);
        cfg.addDefault("permission", permission);
        cfg.addDefault("creator", "STRACE");
        cfg.addDefault("DegreeAddition", 0);
        cfg.addDefault("SneakingDegreeAddition", 0);
        cfg.addDefault("ShowWhenRunning", false);
        cfg.addDefault("tilt", 0);
        cfg.addDefault("tiltbefore", false);
        cfg.addDefault("runtilt", 0);
        cfg.addDefault("mirrow", false);
        cfg.addDefault("rotation", 0);
        cfg.addDefault("moveup", 1);
        cfg.addDefault("moveback", 0);
        cfg.addDefault("Animated", false);
        cfg.addDefault("spacing", 0.08);
        cfg.addDefault("category", category);
        if (exclude != null) {
            List<String> exc = new ArrayList<String>();
            exc.add(exclude);
            cfg.addDefault("exclude", exc);
        }
        cfg.addDefault("pattern", pt);
        cfg.options().copyDefaults(true);
        save();
        return true;
    }

    /**
     * creates the template wing with all options available.
     */
    public void createTemplate() {

        //If there is any twing file it wont create the template file.
        File wingfolder = new File(Main.instance.getDataFolder(), "wings");
        if (wingfolder.listFiles() != null)
            if (wingfolder.listFiles().length > 1)
                for (File f : wingfolder.listFiles()) {
                    if (f.getName() != "Template.yml") {
                        this.getFile().delete();
                        return;
                    }
                }

        cfg.addDefault("Particles.R", "REDSTONE:(255,0,0):2");
        cfg.addDefault("Particles.G", "REDSTONE:(0,255,0)");
        cfg.addDefault("Particles.B", "REDSTONE:0,0,255");
        cfg.addDefault("Particles.SWEEP", "SWEEP_ATTACK");
        cfg.addDefault("Particles.E", "VILLAGER_HAPPY:10");
        cfg.addDefault("Item.Material", Material.ELYTRA.toString());
        cfg.addDefault("Item.Name", "&dUgly Template Wings");
        cfg.addDefault("permission", "twings.template");
        cfg.addDefault("creator", "PixelStrace");
        cfg.addDefault("DegreeAddition", 10);
        cfg.addDefault("SneakingDegreeAddition", 20);
        cfg.addDefault("ShowWhenRunning", false);
        cfg.addDefault("tilt", 10);
        cfg.addDefault("tiltbefore", 0);
        cfg.addDefault("runtilt", 0);
        cfg.addDefault("mirrow", true);
        cfg.addDefault("moveup", 0);
        cfg.addDefault("moveback", 0);
        cfg.addDefault("rotation", 0);
        cfg.addDefault("spacing", 0.07);
        cfg.addDefault("category", "wings");
        List<String> exc = new ArrayList<String>();
        exc.add("x");
        exc.add("SWEEP");
        cfg.addDefault("exclude", exc);
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
        save();
    }

}
