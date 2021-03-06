package dev.strace.twings.players;

import dev.strace.twings.Main;
import dev.strace.twings.utils.ConfigManager;
import dev.strace.twings.utils.WingUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author Jason Holweg [STRACE] <b>TWINGS</b><br>
 * Website: <a>https://strace.dev/</a><br>
 * GitHub: <a>https://github.com/MrStrace</a><br>
 * Created: Jun 27, 2021<br>
 */
public class CurrentWings extends ConfigManager {

    // UUID & FileName der Wings
    public static HashMap<UUID, ArrayList<File>> current = new HashMap<>();

    public CurrentWings() {
        super("wings_equiped");
    }

    /**
     * enables all wings that player already had equipped.
     */
    public void onEnable() {

        if (this.getStringList("") == null || this.getStringList("").isEmpty())
            return;

        // Feur jeden UUID-Eintrag wird nun in die HashMap der zugehoerige Wert
        // hinzugefuegt.

        for (String uid : this.getStringList("")) {
            ArrayList<File> list = new ArrayList<>();
            if (getString(uid).contains("(X-X)")) {
                String[] split = getString(uid).split("(X-X)");
                for (String filenames : split) {
                    for (File file : WingUtils.winglist.keySet()) {
                        if (file.getName().equalsIgnoreCase(filenames)) {
                            list.add(file);
                            break;
                        }
                    }
                }
            } else {
                for (File file : WingUtils.winglist.keySet()) {
                    if (file.getName().equalsIgnoreCase(getString(uid))) {
                        list.add(file);
                    }

                }
            }
            current.put(UUID.fromString(uid), list);
        }
    }

    /**
     * Setzt die ausgewaehlten Wings als "CurrentWings".
     *
     * @param p    bukkit player
     * @param file the wing file
     */
    public void setCurrentWing(Player p, File file) {

        if (file == null) {
            if (current.containsKey(p.getUniqueId()))
                current.remove(p.getUniqueId());
            return;
        }
        ArrayList<File> list = new ArrayList<>();
        list.add(file);
        current.put(p.getUniqueId(), list);
        set(p.getUniqueId().toString(), file.getName());
        save();
    }

    public boolean addCurrentWing(Player p, File file) {

        ArrayList<File> list;
        if (CurrentWings.current.get(p.getUniqueId()) != null)
            list = current.get(p.getUniqueId());
        else
            list = new ArrayList<>();

        if (list.contains(file)) {
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 1f, 10f);
            if (Main.getInstance().getMsg().isShowMessages())
                p.sendMessage(Main.getInstance().getMsg().getAlreadyequipped());
            return false;
        }

        if (Main.getInstance().getMaxEquippedTwings() <= (list.size())) {
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 1f, 10f);
            if (Main.getInstance().getMsg().isShowMessages())
                p.sendMessage(Main.getInstance().getMsg().getMaxtwings());
            return false;
        }

        list.add(file);
        current.put(p.getUniqueId(), list);
        StringBuilder equipped = new StringBuilder();
        int i = 0;
        for (File files : current.get(p.getUniqueId())) {
            i++;
            equipped.append(files.getName());
            if (list.size() != i)
                equipped.append("(X-X)");

        }
        set(p.getUniqueId().toString(), equipped.toString());
        save();
        return true;
    }

    public void removeCurrentWing(Player p, File file) {

        if (!current.get(p.getUniqueId()).contains(file))
            return;

        ArrayList<File> list = current.get(p.getUniqueId());
        list.remove(file);
        current.put(p.getUniqueId(), list);
        StringBuilder equipped = new StringBuilder();
        int i = 0;
        for (File files : current.get(p.getUniqueId())) {
            i++;
            equipped.append(files.getName());
            if (list.size() != i)
                equipped.append("(X-X)");

        }
        set(p.getUniqueId().toString(), equipped.toString());

        p.playSound(p.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 0.4f, 10f);
        if (Main.getInstance().getMsg().isShowMessages())
            p.sendMessage(Main.getInstance().getMsg().getUnequip(WingUtils.winglist.get(file)));
        save();
        p.closeInventory();
    }

    public void removeAllCurrentWing(Player p) {
        if (!CurrentWings.getCurrent().isEmpty()) {
            if (CurrentWings.getCurrent().get(p.getUniqueId()) != null) {
                if (Main.getInstance().getMsg().isShowMessages())
                    for (File file : CurrentWings.getCurrent().get(p.getUniqueId())) {
                        p.sendMessage(Main.getInstance().getMsg().getUnequip(WingUtils.winglist.get(file)));
                    }
            }
        }
        current.remove(p.getUniqueId());
        set(p.getUniqueId().toString(), null);
        save();
    }

    public static HashMap<UUID, ArrayList<File>> getCurrent() {
        return current;
    }
}
