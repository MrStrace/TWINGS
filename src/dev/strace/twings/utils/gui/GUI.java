package dev.strace.twings.utils.gui;

import dev.strace.twings.Main;
import dev.strace.twings.utils.ItemBuilder;
import dev.strace.twings.utils.MyColors;
import dev.strace.twings.utils.WingUtils;
import dev.strace.twings.utils.objects.TWING;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUI {

    int slots;
    int rows;
    String title;
    Inventory inventory;
    Player p;
    int page;

    public GUI(Player p, String title, int page, String category) {
        int rows = 0;
        if (WingUtils.categorymap.get(category) == null) return;
        int size = WingUtils.categorymap.get(category).size();
        if (size <= 8) rows = 1;
        if (size <= 17 && size > 8) rows = 2;
        if (size <= 26 && size > 17) rows = 3;
        if (size <= 35 && size > 26) rows = 4;
        if (size <= 44 && size > 35) rows = 5;
        if (size > 44) rows = 6;
        this.slots = rows * 9;
        this.rows = rows;
        this.title = title;
        this.p = p;
        this.page = page;
        if (p == null) return;
        this.inventory = Bukkit.createInventory(p, slots, title);
        p.playSound(p.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.3f, 1f);
    }

    public GUI(Player p, String title, int page, int slots) {
        this.title = title;
        this.p = p;
        this.page = page;
        this.inventory = Bukkit.createInventory(p, slots, title);
        if (p != null)
            p.playSound(p.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.3f, 1f);
    }

    public void insertItems(File[] files) {
        List<File> list;
        List<File> flist = Arrays.asList(files);
        int size = flist.size();
        if (rows > 5) {
            int start = (page * 45);
            int end = (page + 1) * 45;
            if (size <= end)
                end = size;
            else
                inventory.setItem(53, arrowNext);
            list = flist.subList(start, end);
            inventory.setItem(49, unequipItem);
            if (start != 0) inventory.setItem(45, arrowBack);
        } else list = flist;
        for (File f : list) {
            inventory.addItem(new ItemBuilder(Material.PAPER).setName(MyColors.format("&a" + f.getName().replace(".yml", ""))).build());
        }
    }

    public void insertItems(ArrayList<TWING> twings, String category) {
        List<TWING> list;
        if (rows > 5) {
            int start = (page * 45);
            int end = (page + 1) * 45;
            if (twings.size() <= end)
                end = twings.size();
            else
                inventory.setItem(53, arrowNext);
            list = twings.subList(start, end);
            inventory.setItem(49, unequipItem);
            if (start != 0) inventory.setItem(45, arrowBack);
        } else list = twings;
        if (list != null)
            for (TWING twing : list) {
                if (category.equalsIgnoreCase("XXX") || twing.getCategory().equalsIgnoreCase(category))
                    inventory.addItem(twing.createItemStack(p, CAT.WINGS));
            }
    }

    public void insertItems(ArrayList<TWING> twings, String category, CAT cat) {
        List<TWING> list;
        if (rows > 5) {
            int start = (page * 45);
            int end = (page + 1) * 45;
            if (twings.size() <= end)
                end = twings.size();
            else
                inventory.setItem(53, arrowNext);
            list = twings.subList(start, end);
            inventory.setItem(49, unequipItem);
            if (start != 0) inventory.setItem(45, arrowBack);
        } else list = twings;
        for (TWING twing : list) {
            if (category.equalsIgnoreCase("XXX") || twing.getCategory().equalsIgnoreCase(category))
                inventory.addItem(twing.createItemStack(p, cat));
        }
    }

    public enum CAT {
        EDIT, WINGS, PREVIEW, PICTURE
    }

    public static ItemStack arrowNext = new ItemBuilder(Material.SPECTRAL_ARROW).setName(Main.getInstance().getConfigString("gui.arrownext")).build();
    public static ItemStack arrowBack = new ItemBuilder(Material.ARROW).setName(Main.getInstance().getConfigString("gui.arrowback")).build();
    public static ItemStack unequipItem = new ItemBuilder(Material.LAVA_BUCKET).setName(Main.getInstance().getConfigString("gui.unequip")).build();

    public int getSlots() {
        return slots;
    }

    public int getRows() {
        return rows;
    }

    public String getTitle() {
        return title;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Player getP() {
        return p;
    }

    public int getPage() {
        return page;
    }
}
