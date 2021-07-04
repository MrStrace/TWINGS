package dev.strace.twings.listener;

import dev.strace.twings.Main;
import dev.strace.twings.players.CurrentWings;
import dev.strace.twings.utils.*;
import dev.strace.twings.utils.gui.*;
import dev.strace.twings.utils.objects.Category;
import dev.strace.twings.utils.objects.TWING;
import dev.strace.twings.utils.objects.TWING.GUI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.File;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player))
            return;

        if (e.getView().getTitle().equalsIgnoreCase(
                Main.getInstance().getConfigString("Menu.title").replace("%prefix%", Main.getInstance().getPrefix()))) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            if (e.getCurrentItem() == null)
                return;

            for (TWING wing : WingUtils.winglist.values()) {
                handleNormalMenu(e, p, wing);
            }

        }

        if (e.getView().getTitle().equalsIgnoreCase(new WingPreviewGUI(0).getName())) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            if (e.getCurrentItem() == null)
                return;

            for (TWING wing : WingUtils.winglist.values()) {
                handlePreviewMenu(e, p, wing);
            }

        }

        if (e.getView().getTitle().equalsIgnoreCase(new WingEditGUI(0).getName())) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            if (e.getCurrentItem() == null)
                return;

            for (TWING wing : WingUtils.winglist.values()) {
                handleEditMenu(e, p, wing);
            }

        }

        if (e.getView().getTitle().equalsIgnoreCase(new PictureGUI().getName())) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            if (e.getCurrentItem() == null)
                return;

            handlePictureMenu(e, p);

        }

        if (e.getView().getTitle().equalsIgnoreCase(new CategoryGUI(1).getName())) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            if (e.getCurrentItem() == null)
                return;

            handleCategoryMenu(e, p);

        }


    }

    private void handleCategoryMenu(InventoryClickEvent e, Player p) {
        if (e.getClick().isLeftClick() || e.getClick().isRightClick())
            for (Category cats : Category.categories)
                if (cats.getItem().equals(e.getCurrentItem())) {
                    new WingGUI(WingUtils.winglist.size()).openGUI(p, cats.getName());
                    return;
                }
        new WingGUI(WingUtils.winglist.size()).openGUI(p, "XXX");
    }

    public void handlePreviewMenu(InventoryClickEvent e, Player p, TWING wing) {
        if (e.getCurrentItem() != null)
            if (e.getCurrentItem().equals(wing.getItem(p, GUI.PREVIEW))) {
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

    public void handleEditMenu(InventoryClickEvent e, Player p, TWING wing) {
        if (e.getCurrentItem() != null)
            if (e.getCurrentItem().equals(wing.getItem(p, GUI.EDIT))) {
                /*
                 * Sets the Particle in Edit mode.
                 */
                if (e.getClick().equals(ClickType.LEFT)) {

                    if (p.hasPermission("twings.setedit") || p.hasPermission("twings.admin")) {
                        p.sendMessage(Main.getInstance().getMsg().getEditmodeset(wing));
                        WingPreview.edit = wing;
                        p.closeInventory();
                        return;
                    }
                }

                /*
                 * Removes the Particles out of Edit mode.
                 */
                if (e.getClick().equals(ClickType.RIGHT)) {
                    if (p.hasPermission("twings.setpreview") || p.hasPermission("twings.admin")) {
                        WingPreview.edit = null;
                        p.closeInventory();
                    }
                }
            }
    }

    public void handleNormalMenu(InventoryClickEvent e, Player p, TWING wing) {
        if (e.getCurrentItem() != null)
            if (e.getCurrentItem().equals(wing.getItem(p, GUI.WINGS))) {
                if (!p.hasPermission(wing.getPermission())) {
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 0.4F, 0.7F);
                    p.sendMessage(Main.getInstance().getMsg().getNopermission());
                    return;
                }
                if (e.getClick().equals(ClickType.LEFT)) {
                    if (!CurrentWings.getCurrent().isEmpty()) {
                        if (CurrentWings.getCurrent().get(p.getUniqueId()) != null) {
                            if (Main.getInstance().getMsg().isShowMessages())
                                for (File file : CurrentWings.getCurrent().get(p.getUniqueId())) {
                                    p.sendMessage(Main.getInstance().getMsg().getUnequip(WingUtils.winglist.get(file)));
                                }

                        }
                    }
                    if (Main.getInstance().getMsg().isShowMessages()) {
                        p.sendMessage(Main.getInstance().getMsg().getEquip(wing));
                    }
                    new CurrentWings().setCurrentWing(p, wing.getFile());
                    p.playSound(p.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 0.4f, 10f);
                    p.closeInventory();
                    return;
                }

                /*
                 * Removes the clicked wing on rightclick.
                 */
                if (e.getClick().equals(ClickType.RIGHT)) {

                    if (CurrentWings.getCurrent().isEmpty())
                        return;
                    if (!CurrentWings.getCurrent().containsKey(p.getUniqueId()))
                        return;
                    new CurrentWings().removeCurrentWing(p, wing.getFile());
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
                 * Removes all equipped wings on shift + rightclick.
                 */
                if (e.getClick().equals(ClickType.SHIFT_RIGHT)) {

                    if (CurrentWings.getCurrent().isEmpty())
                        return;
                    new CurrentWings().removeAllCurrentWing(p);
                }

            }
    }

    public void handlePictureMenu(InventoryClickEvent e, Player p) {
        if (e.getClick().equals(ClickType.LEFT)) {
            if (p.hasPermission("twings.admin")) {

                // try's to create the particles per picture:
                if (e.getCurrentItem() != null)
                    if (e.getCurrentItem().getItemMeta() != null) {
                        boolean bool = new WingTemplate(e.getCurrentItem().getItemMeta().getDisplayName())
                                .createFromPicture(e.getCurrentItem().getItemMeta().getDisplayName());

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
        }
    }
}
