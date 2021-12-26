package dev.strace.twings.players;

import dev.strace.twings.Main;
import dev.strace.twings.utils.WingUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

public class PlayWings {


    public void playOnPlayers() {

        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), () -> {

            if (handleShowWithPerms()) return;

            if (CurrentWings.getCurrent().isEmpty())
                return;

            handleShowTwings();

        }, 0, Main.getInstance().config.getInt("UpdateRateTicks"));

    }

    private void handleShowTwings() {
        for (UUID uuid : CurrentWings.getCurrent().keySet()) {
            if (Bukkit.getPlayer(uuid) != null) {
                Player p = Bukkit.getPlayer(uuid);
                if (p != null && p.isOnline()) {
                    for (File file : CurrentWings.getCurrent().get(uuid))
                        WingUtils.winglist.get(file).general.drawWings(p);
                }
            }
        }
    }

    private boolean handleShowWithPerms() {
        if (Main.getInstance().config.getBoolean("ShowWithPerms")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                File fil = null;
                for (File f : new File(Main.instance.getDataFolder(), "wing").listFiles()) {
                    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
                    String perms = cfg.getString("permission");
                    if (perms != null && p.hasPermission(perms) && !p.isOp()) {
                        fil = f;
                    }
                }
                WingUtils.winglist.get(fil).general.drawWings(p);
            }
            return true;
        }
        return false;
    }
}
