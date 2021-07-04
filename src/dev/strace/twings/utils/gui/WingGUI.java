package dev.strace.twings.utils.gui;

import org.bukkit.Bukkit;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import dev.strace.twings.Main;
import dev.strace.twings.utils.WingUtils;
import dev.strace.twings.utils.objects.TWING;
import dev.strace.twings.utils.objects.TWING.GUI;

public class WingGUI {

    private final int size;

    public WingGUI(int wingcount) {
        int size = (wingcount + 9) / 9;
        if (size >= 6) {
            size = 6;
        }
        this.size = size * 9;
    }

    public void openGUI(Player p, String category) {
        p.openInventory(createGUI(p, category));
        p.playSound(p.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.3f, 1f);
    }

    /**
     * Creates the Inventory GUI for a specific Player.
     *
     * @param p {@link Player}
     * @return {@link Inventory}
     */
    private Inventory createGUI(Player p, String category) {

        Inventory inv = Bukkit.createInventory(null, size,
                Main.getInstance().getConfigString("Menu.title").replace("%prefix%", Main.getInstance().getPrefix()));

        insertWings(inv, p, category);

        return inv;
    }

    private void insertWings(Inventory inv, Player p, String category) {

        for (TWING wing : WingUtils.winglist.values()) {
            if (category.equalsIgnoreCase("XXX"))
                inv.addItem(wing.createItemStack(p, GUI.WINGS));
            if (wing.getCategory().equalsIgnoreCase(category))
                inv.addItem(wing.createItemStack(p, GUI.WINGS));

        }

    }
}
