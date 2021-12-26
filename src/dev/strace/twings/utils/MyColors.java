package dev.strace.twings.utils;

import dev.strace.twings.utils.objects.ParticleCode;
import dev.strace.twings.utils.objects.ParticleColor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jason Holweg [STRACE] <b>TWINGS</b><br>
 * Website: <a>https://strace.dev/</a><br>
 * GitHub: <a>https://github.com/MrStrace</a><br>
 * Created: Jun 25, 2021<br>
 */
public class MyColors {

    private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    /**
     * formats the String into colored minecraft string, also formats hexcolor
     * codes.
     *
     * @param msg the string which is being formatted.
     * @return cool string.
     */
    public static String format(String msg) {
        String ver = Bukkit.getVersion();
        if (ver.contains("1.16") || ver.contains("1.17") || ver.contains("1.18") || ver.contains("1.19")) {
            if (msg.contains("#")) {
                Matcher match = pattern.matcher(msg);
                while (match.find()) {
                    String color = msg.substring(match.start(), match.end());
                    msg = msg.replace(color, ChatColor.of(color) + "");
                    match = pattern.matcher(msg);
                }
            }
        }
        return ChatColor.translateAlternateColorCodes('&', msg);

    }

    /**
     * This function spawns all Particles from twings.
     *
     * @param code ParticleCode
     * @param L    The Location
     */
    public static void sendParticles(ParticleCode code, Location L) {
        // If the ParticleCode has an ParticleColor it will get the RGB and sends the
        // Redstone Particle with DustOptions.
        if (L.getWorld() == null) {
            System.out.println("[TWINGS] world not found ERROR!");
            return;
        }
        if (code instanceof ParticleColor) {
            ParticleColor color = (ParticleColor) code;

            int r = color.getR();
            int g = color.getG();
            int b = color.getB();

            L.getWorld().spawnParticle(color.getParticle(), L, 0,
                    new org.bukkit.Particle.DustOptions(Color.fromRGB(r, g, b), (float) code.getSpeed()));
            return;

        }

        // If the ParticleCode doesn't have any color it will send the normal Particles.
        L.getWorld().spawnParticle(code.getParticle(), L, 1, 0.0F, 0F, 0F, code.getSpeed());

    }

}
