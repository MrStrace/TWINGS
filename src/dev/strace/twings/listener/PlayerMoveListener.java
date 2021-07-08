package dev.strace.twings.listener;

import dev.strace.twings.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

/**
 * @author Jason Holweg [STRACE] <b>TWINGS</b><br>
 * Website: <a>https://strace.dev/</a><br>
 * GitHub: <a>https://github.com/MrStrace</a><br>
 * Created: Jun 24, 2021<br>
 */
public class PlayerMoveListener implements Listener {

    // A List with all Players that moved a block.
    public static ArrayList<Player> moving = new ArrayList<>();

    // Player Move Event

    @EventHandler
    public void onMove(final PlayerMoveEvent e) {

        final Player p = e.getPlayer();
        // checks if the player moves 1 block.
        if (e.getTo() != null)
            if (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockY() != e.getTo().getBlockY()
                    || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
                // if the player already moved it will return.
                if (moving.contains(p))
                    return;
                // otherwise he will be added in the list.
                moving.add(p);

                // After 1.5 seconds he will be removed again in the runnable.
                Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> moving.remove(p), 30);

            }
    }
}
