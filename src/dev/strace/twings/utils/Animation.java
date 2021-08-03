package dev.strace.twings.utils;

import dev.strace.twings.Main;
import dev.strace.twings.utils.objects.TWING;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Random;

public class Animation {

    private HashMap<TWING, Integer> animated;
    private HashMap<TWING, Boolean> plus;
    private HashMap<TWING, Integer> rotation;
    private HashMap<TWING, Integer> m;

    public Animation() {
        animated = new HashMap<>();
        plus = new HashMap<>();
        rotation = new HashMap<>();
        m = new HashMap<>();

        for (TWING torotate : WingUtils.winglist.values()) {
            if (torotate.isAnimated() && !torotate.isMirrow()) {
                int rand = new Random().nextInt(20);
                m.put(torotate, 0);
                rotation.put(torotate, rand);
            } else if (torotate.isMirrow() && torotate.animated) {
                animated.put(torotate, 0);
                plus.put(torotate, true);
            }
        }

        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), () -> {

            for (TWING twing : animated.keySet()) {
                handleFlapping(twing);
            }
            for (TWING torotate : rotation.keySet()) {
                handleRotation(torotate);
            }

        }, 20, Main.getInstance().config.getInt("UpdateRateTicks"));
    }


    private void handleFlapping(TWING p) {
        if (animated.get(p) <= 30 && plus.get(p))
            animated.put(p, animated.get(p) + 2);
        else
            plus.put(p, false);
        if (animated.get(p) > 0 && !plus.get(p))
            animated.put(p, animated.get(p) - 2);
        else
            plus.put(p, true);
    }


    private void handleRotation(TWING torotate) {
        m.put(torotate, m.get(torotate) + 1);
        if (rotation.get(torotate) >= 300) {
            rotation.replace(torotate, 0);
            m.put(torotate,0);
        }
        if (m.get(torotate) % 5 == 0)
            rotation.put(torotate, rotation.get(torotate) + 1);

        System.out.println(rotation.get(torotate));
    }

    public HashMap<TWING, Integer> getAnimated() {
        return animated;
    }

    public HashMap<TWING, Boolean> getPlus() {
        return plus;
    }

    public HashMap<TWING, Integer> getRotation() {
        return rotation;
    }
}
