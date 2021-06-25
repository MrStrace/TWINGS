package dev.strace.twings.utils.objects;

import org.bukkit.Particle;

/**
 * 
 * @author Jason Holweg [STRACE] <b>TWINGS</b><br>
 *         Website: <a>https://strace.dev/</a><br>
 *         GitHub: <a>https://github.com/MrStrace</a><br>
 *         Created: Jun 25, 2021<br>
 *
 */
public class ParticleCode {

	String code;
	Particle particle;
	double speed = 0;

	public ParticleCode(String code, Particle particle) {
		this.code = code;
		this.particle = particle;
	}

	public double getSpeed() {
		return speed;
	}

	public ParticleCode setSpeed(double speed) {
		this.speed = speed;
		return this;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Particle getParticle() {
		return particle;
	}

	public void setParticle(Particle particle) {
		this.particle = particle;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return code + " /  " + particle.toString();
	}

}
