package dev.strace.twings.utils;

import dev.strace.twings.utils.objects.ParticleCode;
import dev.strace.twings.utils.objects.TWING;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Jason Holweg [STRACE] <b>TWINGS</b><br>
 * Website: <a>https://strace.dev/</a><br>
 * GitHub: <a>https://github.com/MrStrace</a><br>
 * Created: Jun 25, 2021<br>
 */
public class WingUtils {

    /**
     * Cache um auf das Wing Objekt durch die dazugehoerige File zuzugreifen.
     * <p>
     * The Wing Object cache with the right file.
     */
    public static HashMap<File, TWING> winglist = new HashMap<>();
    public static HashMap<String, ArrayList<TWING>> categorymap = new HashMap<>();


    public TWING getByName(String filename) {
        for (File file : winglist.keySet()) {
            String name = file.getName();
            if (filename.equalsIgnoreCase(name.replace(".yml", ""))) {
                return winglist.get(file);
            }
        }
        return null;
    }

    /**
     * Gets the ParticleCode by String.
     *
     * @param list List of ParticleCodes
     * @param code Code of a specific ParticleCode
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
     *
     * @param messages String...
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
