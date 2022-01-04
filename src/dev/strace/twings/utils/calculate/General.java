package dev.strace.twings.utils.calculate;

import dev.strace.twings.Main;
import dev.strace.twings.listener.PlayerMoveListener;
import dev.strace.twings.utils.ConfigManager;
import dev.strace.twings.utils.MyColors;
import dev.strace.twings.utils.objects.ParticleCode;
import dev.strace.twings.utils.objects.TWING;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class General {

    TWING twing;

    public General(TWING twing) {
        this.twing = twing;
    }

    /**
     * This Function sets up the Wing Particle Location.
     *
     * @param p {@link Player}
     */
    public void drawWings(Player p) {

        if (Main.getInstance().getConfig().getBoolean("hide on invis")) {
            if (p.hasPotionEffect(PotionEffectType.INVISIBILITY)) return;
            if (isVanished(p)) return;
        }
        if (Main.getInstance().getConfig().getBoolean("hide on spectator"))
            if (p.getGameMode() == GameMode.SPECTATOR) return;

        Location ploc = p.getLocation().clone();
        ploc.setPitch(-45);
        Location location = p.getLocation().clone().subtract(ploc.getDirection().setY(0).multiply(twing.getMoveback()));
        int patternlenght = twing.getPattern()[0].length;

        int addition = twing.getDegreeAddition();
        int sneakAddition = twing.getSneakAddition();
        double space = twing.getSpacing();
        double y = location.clone().getY() + 1.4;
        if (!twing.isTiltBefore())
            y += twing.getMoveup();
        if (p.isSneaking()) {
            y -= 0.3;
            space -= 0.01;
        }

        if (Main.getInstance().getAnimation().getAnimated() != null) {
            HashMap<TWING, Double> animated = Main.getInstance().getAnimation().getAnimated();
            if (!animated.isEmpty() && twing.isAnimated() && twing.isMirrow()) {
                addition += animated.get(twing);
                sneakAddition += animated.get(twing);
            }
        }
        if (isRunning(p, twing)) {
            return;
        }

        double defX;
        if (twing.isMirrow())
            defX = location.getX() - (space * patternlenght) + space;
        else
            defX = location.getX() - (space * patternlenght / 2) + space;
        double x = defX;

        double fire = -((location.getYaw() + (180 + sneakAddition + addition)) / 60);
        fire += (location.getYaw() < -180 ? Math.PI : 2.985);
        calculateEachLocation(space, x, y, fire, defX, location, false);
        if (twing.isMirrow()) {
            defX = location.getX() + (space * patternlenght);
            x = defX;
            fire = -((location.getYaw() + (180 - sneakAddition - addition)) / 60);
            fire += (location.getYaw() < -180 ? Math.PI : 2.985);
            calculateEachLocation(space, x, y, fire, defX, location, true);
        }
    }

    /**
     * @param location
     */
    public void drawWings(Location location) {
        int addition = twing.getDegreeAddition();
        int patternlenght = twing.getPattern()[0].length;

        double space = twing.getSpacing();
        double defX;

        if (twing.isMirrow())
            defX = location.getX() - (space * patternlenght) + space;
        else
            defX = location.getX() - (space * patternlenght / 2) + space;
        double x = defX;
        double y = location.clone().getY() + 1.4;
        if (!twing.isTiltBefore())
            y += twing.getMoveup();
        double fire = -((location.getYaw() + (180 + addition)) / 60);

        fire += (location.getYaw() < -180 ? Math.PI : 2.985);

        calculateEachLocation(space, x, y, fire, defX, location, false);

        if (twing.isMirrow()) {
            defX = location.getX() + (space * patternlenght);
            x = defX;
            y = location.clone().getY() + 1.4;
            if (!twing.isTiltBefore())
                y += twing.getMoveup();
            fire = -((location.getYaw() + (180 - addition)) / 60);
            fire += (location.getYaw() < -180 ? Math.PI : 2.985);
            calculateEachLocation(space, x, y, fire, defX, location, true);
        }
    }

    private void calculateEachLocation(double space, double x, double y, double fire, double defX, Location location,
                                       boolean left) {

        for (int i = 0; i < twing.getPattern().length; i++) {
            ParticleCode[] alone = twing.getPattern()[i];
            for (ParticleCode particleCode : alone) {
                /*
                 * if the particlecode is given it will display at the perfect location.
                 */
                if (particleCode != null) {
                    Location target = location.clone();
                    target.setX(x);
                    target.setY(y);

                    handleVectorChangeAndSend(particleCode, target, location, left, fire);
                }

                if (!left)
                    x += space;
                else
                    x -= space;
            }
            y -= space;
            x = defX;
        }

    }

    /**
     * This function will handle all vector calculations/changes and will send it to
     * the location, after that it will turn back to the normal vector.
     *
     * @param code
     * @param target
     * @param tochange
     * @param left
     * @param fire
     */
    private void handleVectorChangeAndSend(ParticleCode code, Location target, Location tochange, boolean left,
                                           double fire) {
        double rotani = 0;
        Vector v = target.toVector().subtract(tochange.toVector());
        Vector vpitch = Rotating.rotateAroundAxisX(v, twing.tilt);
        Vector v2 = Rotating.getBackVector(tochange, left);

        if (Main.getInstance().getAnimation().getRotation().get(twing) != null) {
            rotani = Main.getInstance().getAnimation().getRotation().get(twing);
            v = Rotating.rotateAroundAxisY(v, (double) (rotani / ((double) Main.getInstance().getConfig().getDouble("rotation speed") * 90)));
        } else
            v = Rotating.rotateAroundAxisY(v, fire + twing.getRotation());

        if (twing.isMirrow()) {
            if (!left)
                v2.setY(0).multiply(-0.25);
            else
                v2.setY(0).multiply(0.25);
        } else
            v2.setY(0).multiply(-0.5);

        tochange.add(vpitch);
        tochange.add(v);
        tochange.add(v2);
        if (twing.isTiltBefore()) {
            tochange.setY(tochange.getY() + twing.getMoveup());
        }
        MyColors.sendParticles(code, tochange);
        if (twing.isTiltBefore()) {
            tochange.setY(tochange.getY() - twing.getMoveup());
        }
        tochange.subtract(vpitch);
        tochange.subtract(v2);
        tochange.subtract(v);
    }

    private boolean isRunning(Player p, TWING wing) {
        if (wing.showWhenRunning && PlayerMoveListener.moving.contains(p))
            return false;

        return PlayerMoveListener.moving.contains(p);

    }

    private boolean isVanished(Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }
}
