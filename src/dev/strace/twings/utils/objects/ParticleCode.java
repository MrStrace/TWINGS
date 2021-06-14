package dev.strace.twings.utils.objects;

import org.bukkit.Particle;

/**
 * 
 * @author Jason Holweg [STRACE]
 * TWINGS
 * Website: https://strace.dev/
 * GitHub: https://github.com/MrStrace
 * Erstellt May 23, 2021
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
