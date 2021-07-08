package dev.strace.twings.players;

import dev.strace.twings.Main;
import dev.strace.twings.utils.WingUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

import static dev.strace.twings.Main.getInstance;

public class PlayWings {

    private final File dataFolder = new File(getInstance().getDataFolder(), "wing");

    public void playOnPlayers() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(getInstance(), () -> {
            if (Main.getInstance().config.getBoolean("ShowWithPerms")) {
                for (Player player : Bukkit.getOnlinePlayers()) {

                    if (dataFolder.list() == null) continue;

                    File file = null;
                    for (File f : dataFolder.listFiles()) {
                        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);

                        if (!cfg.contains("permissions")) continue;
                        String perms = cfg.getString("permissions");

                        if (perms != null && player.hasPermission(perms) && !player.isOp()) {
                            file = f;
                        }
                    }

                    if (file == null) return;
                    WingUtils.winglist.get(file).drawWings(player);
                }
                return;
            }
            if (CurrentWings.getCurrent().isEmpty()) return;

            for (UUID uuid : CurrentWings.getCurrent().keySet()) {
                if (Bukkit.getPlayer(uuid) == null) continue;

                Player p = Bukkit.getPlayer(uuid);
                if (p == null || !p.isOnline()) continue;

                CurrentWings.getCurrent().get(uuid).forEach(file -> WingUtils.winglist.get(file).drawWings(p));
            }

        }, 0, getInstance().config.getInt("UpdateRateTicks"));
    }

}
