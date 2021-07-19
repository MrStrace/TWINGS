package dev.strace.twings.utils.gui;

import dev.strace.twings.Main;
import dev.strace.twings.utils.MyColors;
import org.bukkit.entity.Player;

import java.io.File;

public class PictureGUI extends GUI {

    public PictureGUI(Player p) {
        super(p, Main.getInstance().getConfigString("Menu.title").replace("%prefix%", Main.getInstance().getPrefix()) + MyColors.format(" &c&lCREATE"), 0, 6 * 9);
        if (p == null) return;
        this.insertItems(new File(Main.getInstance().getDataFolder(), "pictures").listFiles());
        p.openInventory(this.inventory);
    }

}
