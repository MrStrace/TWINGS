package dev.strace.twings.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;

import dev.strace.twings.Main;
import dev.strace.twings.utils.objects.ParticleColor;

/**
 * 
 * @author Jason Holweg [STRACE] <b>TWINGS</b><br>
 *         Website: <a>https://strace.dev/</a><br>
 *         GitHub: <a>https://github.com/MrStrace</a><br>
 *         Created: Jun 24, 2021<br>
 *
 */
public class ReadImage {
	private HashMap<Color, String> colorlist;

	public ReadImage() {
		colorlist = new HashMap<Color, String>();
	}

	public ParticleColor[][] getColor(BufferedImage img) {
		ParticleColor[][] particlecolor = new ParticleColor[img.getHeight()][img.getWidth()];
		int count = 1;
		if (img.getWidth() * img.getHeight() > 5000) {
			return null;
		}
		for (int i = 0; i < img.getWidth(); i++) {
			for (int y = 0; y < img.getHeight(); y++) {
				if (img.getRGB(i, y) != 16777215 && img.getRGB(i, y) != 0) {
					Color col = new Color(img.getRGB(i, y));
					if (!colorlist.containsKey(col)) {
						String c = "C" + count;
						colorlist.put(col, c);
						count++;
					}
					int[] rgb = new int[] { col.getRed(), col.getGreen(), col.getBlue() };
					particlecolor[y][i] = new ParticleColor(colorlist.get(col), rgb);
				} else
					particlecolor[y][i] = null;
			}
		}

		return particlecolor;
	}

	public BufferedImage getImage(String name) {
		BufferedImage img = null;
		for (File files : new File(Main.getInstance().getDataFolder(), "pictures").listFiles()) {
			String filename = files.getName();

			if (filename.equalsIgnoreCase(name)) {
				try {
					img = ImageIO.read(files);
					return img;
				} catch (Exception e) {
				}
			}
		}
		return img;

	}

	public HashMap<Color, String> getColorlist() {
		return colorlist;
	}

	public void setColorlist(HashMap<Color, String> colorlist) {
		this.colorlist = colorlist;
	}

}
