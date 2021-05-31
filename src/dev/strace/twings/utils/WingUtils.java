package dev.strace.twings.utils;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;

import dev.strace.twings.utils.objects.ParticleCode;
import dev.strace.twings.utils.objects.Wing;

public class WingUtils {

	/**
	 * Cache um auf das Wing Objekt durch die dazugehoerige File zuzugreifen.
	 * 
	 * The Wing Object cache with the right file.
	 */
	public static HashMap<File, Wing> winglist = new HashMap<File, Wing>();

	/**
	 * Gets the ParticleCode by String.
	 * 
	 * @param list
	 * @param code
	 * @return ParticleCode
	 */
	public ParticleCode getByString(ArrayList<ParticleCode> list, String code) {

		/*
		 * For each ParticleCode from the given list it will check if the code equals
		 * the String if so it returns the ParticleCode
		 */
		for (ParticleCode codes : list) {
			if (code.equalsIgnoreCase(codes.getCode()))
				return codes;
		}

		return null;

	}

	/**
	 * Logs a Error message in the console with fancy message
	 * @param messages
	 */
	public void logError(String... messages) {
		String prefix = "[TWINGS]";
		System.out.println(prefix + " ERROR VVVV");
		System.out.println(prefix);
		for (String msgs : messages)
			System.out.println(prefix + msgs);
		System.out.println(prefix);
		System.out.println(prefix + " ERROR ^^^^");
	}

}
