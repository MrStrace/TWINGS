package dev.strace.twings.utils.objects;

import dev.strace.twings.utils.ConfigManager;
import dev.strace.twings.utils.ItemBuilder;
import dev.strace.twings.utils.MyColors;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Category {

    String name;
    String displayName;
    Material material;
    List<String> lore;
    boolean glow;
    ItemStack item;
    int slot;

    public static ArrayList<Category> categories;

    /**
     * creates a new Category.. the Categories can be created in the CategoryGUI.yml
     *
     * @param name        ID
     * @param displayName Colored name
     * @param material    bukkit Material
     * @param lore        List<String>
     * @param glow        boolean
     */
    public Category(String name, String displayName, String material, List<String> lore, boolean glow, int slot) {
        this.name = name;
        this.displayName = MyColors.format(displayName);
        if (Material.getMaterial(material) == null)
            this.material = Material.DIRT;
        else
            this.material = Material.getMaterial(material);
        this.lore = lore;
        this.glow = glow;
        this.slot = slot;
    }

    /**
     * creates the ItemStack
     *
     * @return {@link ItemStack}
     */
    public ItemStack getItem() {
        ItemBuilder item = new ItemBuilder(this.material);
        if (glow) item.addGlow();
        for (String l : lore) {
            item.addLore(MyColors.format(l));
        }
        item.setName(this.displayName);
        return item.build();
    }

    /**
     * Initializes all Categories from CategoryGUI.yml
     */
    public static void initAll() {
        //Init ArrayList
        Category.categories = new ArrayList<>();

        //Config Manager of CategoryGUI
        ConfigManager manager = new ConfigManager("CategoryGUI");

        //For each Category it will now look for all info and create the category.
        for (String cats : manager.getStringList("category")) {
            //Search prefix
            String prefix = "category." + cats + ".";
            String displayname = manager.getString(prefix + "displayname");
            List<String> lore = manager.getList(prefix + "lore");
            String material = manager.getString(prefix + "material");
            boolean glow = manager.getBoolean(prefix + "glow");
            int slot = manager.getInt(prefix + "slot");
            //Create new Category and save to ArrayList
            Category category = new Category(cats, displayname, material, lore, glow, slot);
            Category.categories.add(category);
        }
    }


    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Material getMaterial() {
        return material;
    }

    public List<String> getLore() {
        return lore;
    }

    public boolean isGlow() {
        return glow;
    }

    public int getSlot() {
        return slot;
    }
}
