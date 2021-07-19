package dev.strace.twings.utils.gui;

import dev.strace.twings.Main;
import dev.strace.twings.utils.WingUtils;
import org.bukkit.entity.Player;

public class WingGUI extends GUI {

    public WingGUI(Player p, int page, String category) {
        super(p, Main.getInstance().getConfigString("Menu.title").replace("%prefix%", Main.getInstance().getPrefix()), page, category);
        if(p == null)return;
        this.insertItems(WingUtils.categorymap.get(category), category);
        p.openInventory(this.inventory);
    }
}
