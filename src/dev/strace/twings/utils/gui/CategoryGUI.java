package dev.strace.twings.utils.gui;

import dev.strace.twings.Main;
import dev.strace.twings.utils.ConfigManager;
import dev.strace.twings.utils.MyColors;
import dev.strace.twings.utils.objects.Category;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class CategoryGUI {

    private final int size;
    private String name;

    public CategoryGUI(int rows) {
        int size = rows;
        if (rows >= 6) {
            size = 6;
        }
        this.size = size * 9;
        if (rows != 0)
            name = MyColors.format(
                    new ConfigManager("CategoryGUI").getString("title").replace("%prefix%", Main.getInstance().getPrefix()));
    }

    public void openGUI(Player p) {
        p.openInventory(createGUI(p));
        p.playSound(p.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.3f, 1f);
    }

    /**
     * Creates the Inventory GUI for a specific Player.
     *
     * @param p {@link Player}
     * @return {@link Inventory}
     */
    private Inventory createGUI(Player p) {

        Inventory inv = Bukkit.createInventory(p, size, name);

        insertCategory(inv, p);

        return inv;
    }

    private void insertCategory(Inventory inv, Player p) {
        for (Category cats : Category.categories) {
            inv.setItem(cats.getSlot(), cats.getItem());
        }
    }

    /**
     * creates the CategoryGUI.yml file to customize the GUI for each plugin user.
     */
    public void createDefaultConfig() {
        ConfigManager config = new ConfigManager("CategoryGUI");
        config.addDefault("enabled", true);
        config.addDefault("title", "&bChoose the category!");
        config.addDefault("rows", 1);
        config.addDefault("category.wings.displayname", "&d&lWINGS");
        List<String> lore = new ArrayList<>();
        lore.add("&8Here you can find all wing particles!");
        lore.add("&7&oLeftclick to choose your wings!");
        config.addDefault("category.wings.lore", lore);
        config.addDefault("category.wings.material", Material.ELYTRA.toString());
        config.addDefault("category.wings.glow", true);
        config.addDefault("category.wings.slot", 4);
        config.save();
    }

    public String getName() {
        return name;
    }
}
