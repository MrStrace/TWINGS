package dev.strace.twings.utils.gui;

import dev.strace.twings.Main;
import dev.strace.twings.utils.MyColors;
import dev.strace.twings.utils.WingUtils;
import org.bukkit.entity.Player;

public class WingPreviewGUI extends GUI {

    public WingPreviewGUI(Player p, int page, String category) {
        super(p, Main.getInstance().getConfigString("Menu.title").replace("%prefix%", Main.getInstance().getPrefix()) + MyColors.format(" &c&lPREVIEW"), page, category);
        if (p == null) return;
        this.insertItems(WingUtils.categorymap.get(category), category, CAT.PREVIEW);
        p.openInventory(this.inventory);
    }
}
