package dev.strace.twings.utils;

import dev.strace.twings.Main;
import dev.strace.twings.utils.objects.TWING;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Random;

public class Animation {

    private HashMap<Player, Integer> animated;
    private HashMap<Player, Boolean> plus;
    private HashMap<TWING, Integer> rotation;

    public Animation() {
        animated = new HashMap<>();
        plus = new HashMap<>();
        rotation = new HashMap<>();

        for (TWING torotate : WingUtils.winglist.values()) {
            if (torotate.isAnimated() && !torotate.isMirrow()) {
                int rand = new Random().nextInt(300);
                rotation.put(torotate, rand);
            }
        }

        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), () -> {

            for (Player p : animated.keySet()) {
                handleFlapping(p);
            }
            for (TWING torotate : rotation.keySet()) {
                if (rotation.get(torotate) >= 360)
                    rotation.replace(torotate, 0);
                rotation.replace(torotate, rotation.get(torotate) + Main.getInstance().getConfig().getInt("rotation speed"));
            }

        }, 20, 3);
    }


    private void handleFlapping(Player p) {
        if (animated.get(p) <= 30 && plus.get(p))
            animated.put(p, animated.get(p) + 2);
        else
            plus.put(p, false);
        if (animated.get(p) > 0 && !plus.get(p))
            animated.put(p, animated.get(p) - 2);
        else
            plus.put(p, true);
    }

    private void handleRotation() {

    }

    public HashMap<Player, Integer> getAnimated() {
        return animated;
    }

    public HashMap<Player, Boolean> getPlus() {
        return plus;
    }

    public HashMap<TWING, Integer> getRotation() {
        return rotation;
    }
}
