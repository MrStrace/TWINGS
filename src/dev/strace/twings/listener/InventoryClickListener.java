package dev.strace.twings.listener;

import dev.strace.twings.Main;
import dev.strace.twings.players.CurrentWings;
import dev.strace.twings.utils.*;
import dev.strace.twings.utils.gui.*;
import dev.strace.twings.utils.objects.Category;
import dev.strace.twings.utils.objects.TWING;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player))
            return;
        Player p = (Player) e.getWhoClicked();

        if (e.getView().getTitle().contains(
                Main.getInstance().getConfigString("Menu.title").replace("%prefix%", Main.getInstance().getPrefix()))) {
            e.setCancelled(true);

            if (e.getCurrentItem() == null)
                return;
            if (e.getCurrentItem().equals(GUI.arrowNext)) {
                if (e.getView().getTitle().contains(MyColors.format(" &c&lPREVIEW"))) menuMap.put(p, GUI.CAT.PREVIEW);
                if (e.getView().getTitle().contains(MyColors.format(" &c&lEDIT"))) menuMap.put(p, GUI.CAT.EDIT);
                if (menuMap.containsKey(p))
                    switch (menuMap.get(p)) {
                        case EDIT:
                            new WingEditGUI(p, currentPage.get(p) + 1, currentCat.get(p).getName());
                            break;
                        case PREVIEW:
                            new WingPreviewGUI(p, currentPage.get(p) + 1, currentCat.get(p).getName());
                            break;
                    }
                else new WingGUI(p, currentPage.get(p) + 1, currentCat.get(p).getName());
                currentPage.put(p, currentPage.get(p) + 1);
                return;
            }
            if (e.getCurrentItem().equals(GUI.arrowBack)) {
                if (e.getView().getTitle().contains(MyColors.format(" &c&lPREVIEW"))) menuMap.put(p, GUI.CAT.PREVIEW);
                if (e.getView().getTitle().contains(MyColors.format(" &c&lEDIT"))) menuMap.put(p, GUI.CAT.EDIT);
                if (menuMap.containsKey(p))
                    switch (menuMap.get(p)) {
                        case EDIT:
                            new WingEditGUI(p, currentPage.get(p) - 1, currentCat.get(p).getName());
                            break;
                        case PREVIEW:
                            new WingPreviewGUI(p, currentPage.get(p) - 1, currentCat.get(p).getName());
                            break;
                    }
                else new WingGUI(p, currentPage.get(p) - 1, currentCat.get(p).getName());
                currentPage.put(p, currentPage.get(p) - 1);
                menuMap.remove(p);
                return;
            }
            if (e.getCurrentItem().equals(GUI.unequipItem)) {
                p.closeInventory();
                new CurrentWings().removeAllCurrentWing(p);
                return;
            }

        }
        handleCategoryMenu(e, p);
        handleNormalMenu(e, p);
        handlePreviewMenu(e, p);
        handleEditMenu(e, p);
        handlePictureMenu(e, p);
    }

    private final HashMap<Player, Category> currentCat = new HashMap<>();
    private final HashMap<Player, Integer> currentPage = new HashMap<>();
    public static HashMap<Player, GUI.CAT> menuMap = new HashMap<>();

    private void handleCategoryMenu(InventoryClickEvent e, Player p) {
        if (!e.getView().getTitle().equalsIgnoreCase(new CategoryGUI(null).getTitle())) return;
        if (e.getClick().isLeftClick() || e.getClick().isRightClick())
            for (Category cats : Category.categories)
                if (cats.getItem().equals(e.getCurrentItem())) {
                    if (menuMap.containsKey(p))
                        switch (menuMap.get(p)) {
                            case EDIT:
                                new WingEditGUI(p, 0, cats.getName());
                                break;
                            case PREVIEW:
                                new WingPreviewGUI(p, 0, cats.getName());
                                break;
                        }
                    else new WingGUI(p, 0, cats.getName());

                    currentCat.put(p, cats);
                    currentPage.put(p, 0);
                    menuMap.remove(p);
                    return;
                }
        new WingGUI(p, 0, "XXX");
    }

    public void handlePreviewMenu(InventoryClickEvent e, Player p) {
        if (!e.getView().getTitle().contains(MyColors.format(" &c&lPREVIEW"))) return;
        if (e.getCurrentItem() == null) return;
        String cat = currentCat.get(p).toString();
        for (TWING wing : WingUtils.categorymap.get(currentCat.get(p).getName())) {
            if (e.getCurrentItem().equals(wing.getItem(p, GUI.CAT.PREVIEW))) {
                /*
                 * Sets the Preview Location
                 */
                if (e.getClick().equals(ClickType.LEFT))
                    if (p.hasPermission("twings.setpreview") || p.hasPermission("twings.admin")) {
                        LocationBuilder lb = new LocationBuilder();
                        lb.setLocation(wing.getFile().getName().replace(".yml", ""), p.getLocation());
                        p.sendMessage(Main.getInstance().getMsg().getPreviewSet(wing));
                        p.closeInventory();
                        return;
                    }

                /*
                 * Removes the Preview Location
                 */
                if (e.getClick().equals(ClickType.RIGHT))
                    if (p.hasPermission("twings.setpreview") || p.hasPermission("twings.admin")) {
                        LocationBuilder lb = new LocationBuilder();
                        lb.set(wing.getFile().getName().replace(".yml", ""), null);
                        // Saves the file.
                        lb.save();
                        p.sendMessage(Main.getInstance().getMsg().getPreviewRemoved(wing));
                        p.closeInventory();
                    }
            }
        }
    }

    public void handleEditMenu(InventoryClickEvent e, Player p) {
        if (!e.getView().getTitle().contains(MyColors.format(" &c&lEDIT"))) return;
        if (e.getCurrentItem() == null) return;
        for (TWING wing : WingUtils.categorymap.get(currentCat.get(p).getName())) {
            if (e.getCurrentItem().equals(wing.getItem(p, GUI.CAT.EDIT))) {

                /*
                 * Sets the Particle in Edit mode.
                 */
                if (e.getClick().equals(ClickType.LEFT))
                    if (p.hasPermission("twings.setedit") || p.hasPermission("twings.admin")) {
                        p.sendMessage(Main.getInstance().getMsg().getEditmodeset(wing));
                        WingPreview.edit = wing;
                        p.closeInventory();
                        return;
                    }


                /*
                 * Removes the Particles out of Edit mode.
                 */
                if (e.getClick().equals(ClickType.RIGHT))
                    if (p.hasPermission("twings.setpreview") || p.hasPermission("twings.admin")) {
                        WingPreview.edit = null;
                        p.closeInventory();
                    }
            }
        }
    }

    private void equipNewTwing(Player p, TWING wing) {
        if (CurrentWings.getCurrent().get(p.getUniqueId()).contains(wing.getFile())) return;
        if (!p.hasPermission(wing.getPermission())) {
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 0.4F, 0.7F);
            p.sendMessage(Main.getInstance().getMsg().getNopermission());
            return;
        }
        new CurrentWings().removeAllCurrentWing(p);

        if (Main.getInstance().getMsg().isShowMessages()) {
            p.sendMessage(Main.getInstance().getMsg().getEquip(wing));
        }
        new CurrentWings().setCurrentWing(p, wing.getFile());
        p.playSound(p.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 0.4f, 10f);
        p.closeInventory();
    }

    public void handleNormalMenu(InventoryClickEvent e, Player p) {
        if (!e.getView().getTitle().equalsIgnoreCase(
                Main.getInstance().getConfigString("Menu.title").replace("%prefix%", Main.getInstance().getPrefix())))
            return;
        if (e.getCurrentItem() == null) return;
        for (TWING wing : WingUtils.categorymap.get(currentCat.get(p).getName())) {

            /*
             * equipes a new twing on leftclick.
             */
            if (e.getClick().equals(ClickType.LEFT))
                if (e.getCurrentItem().equals(wing.getItem(p, GUI.CAT.WINGS))) {
                    equipNewTwing(p, wing);
                    return;
                }


            /*
             * Adds another wing to equipped wings. (shift leftclick)
             */
            if (e.getClick().equals(ClickType.SHIFT_LEFT)) {
                boolean bool = new CurrentWings().addCurrentWing(p, wing.getFile());
                if (!bool) return;
                if (Main.getInstance().getMsg().isShowMessages())
                    p.sendMessage(Main.getInstance().getMsg().getEquip(wing));
                p.playSound(p.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 0.4f, 10f);
                p.closeInventory();
                return;
            }

            /*
             * Removes the clicked wing on rightclick.
             */
            if (e.getClick().equals(ClickType.RIGHT)) {
                if (CurrentWings.getCurrent().isEmpty()) return;
                if (!CurrentWings.getCurrent().containsKey(p.getUniqueId())) return;
                new CurrentWings().removeCurrentWing(p, wing.getFile());
                return;
            }

            /*
             * Removes all equipped wings on shift + rightclick.
             */
            if (e.getClick().equals(ClickType.SHIFT_RIGHT)) {
                if (CurrentWings.getCurrent().isEmpty()) return;
                if (!CurrentWings.getCurrent().containsKey(p.getUniqueId())) return;
                new CurrentWings().removeAllCurrentWing(p);
                return;
            }
        }
    }


    public void handlePictureMenu(InventoryClickEvent e, Player p) {
        if (!e.getView().getTitle().equalsIgnoreCase(new PictureGUI(null).getTitle())) return;
        if (!e.getClick().equals(ClickType.LEFT)) return;
        if (!p.hasPermission("twings.admin")) return;

        // try's to create the particles per picture:
        if (e.getCurrentItem() == null) return;
        if (e.getCurrentItem().getItemMeta() == null) return;

        boolean bool = new WingTemplate(e.getCurrentItem().getItemMeta().getDisplayName())
                .createFromPicture(new ReadImage().getImage(e.getCurrentItem().getItemMeta().getDisplayName()),
                        Material.COOKIE, MyColors.format("&c") + e.getCurrentItem().getItemMeta().getDisplayName(),
                        "picture", "twings.picture", "C1");

        // if success
        if (bool) {
            /*
             * the particles where created and the player receives a message.
             */
            p.sendMessage(e.getCurrentItem().getItemMeta().getDisplayName()
                    + MyColors.format(" &ato activate do &2/twings reload"));
            p.playSound(p.getLocation(), Sound.BLOCK_COMPOSTER_FILL_SUCCESS, 1, 10f);
            p.closeInventory();
            return;
        }
        new WingTemplate(e.getCurrentItem().getItemMeta().getDisplayName()).delete();
        /*
         * if the picture couldn't be created the player receives a message.
         */
        p.sendMessage(e.getCurrentItem().getItemMeta().getDisplayName()
                + MyColors.format(" &cthe Image is too large! Maximum size is 64x64"));
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 1, 100f);
        p.closeInventory();
    }

}
