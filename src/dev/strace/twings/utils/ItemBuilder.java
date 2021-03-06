package dev.strace.twings.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * @author Jason Holweg [STRACE]
 * <b>TWINGS</b><br>
 * Website: <a>https://strace.dev/</a><br>
 * GitHub: <a>https://github.com/MrStrace</a><br>
 * Created: Jun 25, 2021<br>
 */
public class ItemBuilder {

    ItemStack item;
    ItemMeta meta;

    public ItemBuilder(Material mat) {

        item = new ItemStack(mat);
        meta = item.getItemMeta();

    }

    public ItemBuilder(ItemStack ite) {

        item = ite;
        meta = item.getItemMeta();
    }

    public ItemBuilder addEnchantment(Enchantment ench, int s) {

        meta.addEnchant(ench, s, true);
        return this;
    }

    public ItemBuilder addGlow() {

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder HideAll() {

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        return this;
    }

    public ItemBuilder setName(String name) {

        meta.setDisplayName(name);
        return this;

    }

    public ItemBuilder setAmount(int amount) {

        item.setAmount(amount);
        return this;

    }

    public ItemBuilder addLore(String lore) {

        ArrayList<String> l;
        if (meta.getLore() != null) {
            l = (ArrayList<String>) meta.getLore();
        } else {
            l = new ArrayList<>();

        }
        l.add(lore);
        meta.setLore(l);
        return this;
    }

    public ItemBuilder setLore(ArrayList<String> lore) {
        meta.setLore(lore);
        return this;
    }

    public ItemBuilder setLore(String creator, ArrayList<String> lore) {

        ArrayList<String> l;
        if (meta.getLore() != null) {
            l = (ArrayList<String>) meta.getLore();
        } else {
            l = new ArrayList<String>();
        }
        if (!creator.equalsIgnoreCase("")) {
            l.add(MyColors.format(creator));
        }
        l.addAll(lore);
        meta.setLore(l);
        return this;
    }

    public ItemStack build() {

        item.setItemMeta(meta);
        return item;
    }

}
