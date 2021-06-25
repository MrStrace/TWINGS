package dev.strace.twings.utils.objects;

import org.bukkit.Particle;

/**
 * 
 * @author Jason Holweg [STRACE]
 * <b>TWINGS</b><br>
 * Website: <a>https://strace.dev/</a><br>
 * GitHub: <a>https://github.com/MrStrace</a><br>
 * Created: Jun 25, 2021<br>
 *
 */
public class ParticleColor extends ParticleCode {

	int[] rgb;
	public ParticleColor(String code, int[] rgb) {
		super(code, Particle.REDSTONE);
		this.rgb = rgb;
	}

	public int[] getRgb() {
		return rgb;
	}

	public void setRgb(int[] rgb) {
		this.rgb = rgb;
	}

	public int getR() {
		return rgb[0];
	}

	public int getG() {
		return rgb[1];
	}

	public int getB() {
		return rgb[2];
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + "[" + getR() + "|" + getG() + "|" + getB() + "]";
	}
	
	public String getHexaCode() {
		String ret = "#,00,00,00";
		String[] toedit = ret.split(",");
		if (getR() < 16) {
			toedit[1] = toedit[1].replace("00", "0" + Integer.toHexString(getR()));
		} else {
			toedit[1] = toedit[1].replace("00", Integer.toHexString(getR()));
		}
		if (getG() < 16) {
			toedit[2] = toedit[2].replace("00", "0" + Integer.toHexString(getG()));
		} else {
			toedit[2] = toedit[2].replace("00", Integer.toHexString(getG()));
		}
		if (getB() < 16) {
			toedit[3] = toedit[3].replace("00", "0" + Integer.toHexString(getB()));
		} else {
			toedit[3] = toedit[3].replace("00", Integer.toHexString(getB()));
		}
		ret = toedit[0] + toedit[1] + toedit[2] + toedit[3];
		return ret.replace(",", "");
	}
	
}
