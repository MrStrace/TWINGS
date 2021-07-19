package dev.strace.twings.utils.gui;

import dev.strace.twings.utils.ConfigManager;
import dev.strace.twings.utils.MyColors;
import dev.strace.twings.utils.objects.Category;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CategoryGUI extends GUI {

    public static ConfigManager config = new ConfigManager("CategoryGUI");

    public CategoryGUI(Player p) {
        super(p, MyColors.format(config.getString("title")), 0, config.getInt("rows") * 9);
        if (p == null) return;
        insertCategory();
        p.openInventory(this.inventory);
    }

    private void insertCategory() {
        for (Category cats : Category.categories) {
            this.inventory.setItem(cats.getSlot(), cats.getItem());
        }
    }

    /**
     * creates the CategoryGUI.yml file to customize the GUI for each plugin user.
     */
    public static void createDefaultConfig() {

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
}
