package dev.strace.twings.utils.objects;

import dev.strace.twings.Main;
import dev.strace.twings.listener.PlayerMoveListener;
import dev.strace.twings.players.CurrentWings;
import dev.strace.twings.utils.ConfigManager;
import dev.strace.twings.utils.ItemBuilder;
import dev.strace.twings.utils.MyColors;
import dev.strace.twings.utils.calculate.General;
import dev.strace.twings.utils.calculate.Rotating;
import dev.strace.twings.utils.gui.GUI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TWING {

    // Die Neigung der Wings in Grad
    public int tilt;

    // If show when running is enabled
    // the tilt can also be adjusted >D
    public int runTilt;

    // Rotation
    public int rotation;

    // Das Pattern der Wings mit Codes
    public ParticleCode[][] pattern;

    public List<String> exclude = new ArrayList<>();

    // Animiert ja/nein
    public boolean animated = true;

    // If the Pattern should be mirrored
    public boolean mirrow = true;

    // Tilts the wings before rotating if true
    public boolean tiltBefore = false;

    // Grad Zahl die addiert wird beim Sneaken (damit es so aussieht als ob die ein
    // wenig zusammen gehen)
    public int sneakAddition;

    // Standart grad zahl wo weit die Wings zusammen sind.
    // (0) --
    // (45) /\
    // (90) |
    public int degreeAddition;

    // Falls ja werden die Wings weiterhin auch beim Rennen angezeigt.
    public boolean showWhenRunning = false;

    // Moves the Wings behind the Body (MULTIPLIES the Direction Vector)
    public double moveback;

    // Moves the Wing overall up on the Y coordinate
    public double moveup;

    // How far each particle is away from each other
    public double spacing = 0.07;

    // The Item which is going in the GUI;
    public ItemStack item;
    public Material material;
    public String itemName;
    public String creator;

    // Category
    public String category = "";

    public String permission;

    private final ConfigManager config;
    public File file;

    public General general;

    public TWING(ConfigManager config) {
        this.config = config;
        tilt = config.getInt("tilt");
        animated = config.getBoolean("Animated");
        showWhenRunning = config.getBoolean("ShowWhenRunning");
        sneakAddition = config.getInt("SneakingDegreeAddition");
        degreeAddition = config.getInt("DegreeAddition");
        moveback = config.getDouble("moveback");
        moveup = config.getDouble("moveup");
        material = Material.valueOf(config.getString("Item.Material"));
        itemName = config.getString("Item.Name");
        creator = config.getString("creator");

        if (config.getString("permission") == null)
            permission = "";
        else
            permission = config.getString("permission");

        file = config.getFile();
        mirrow = config.getBoolean("mirrow");
        runTilt = config.getInt("runtilt");
        tiltBefore = config.getBoolean("tiltbefore");
        rotation = config.getInt("rotation");

        if (config.getDouble("spacing") == 0)
            spacing = 0.07;
        else
            spacing = config.getDouble("spacing");
        exclude = config.getList("exclude");
        if (config.getString("category") == null)
            category = "wings";
        else
            category = config.getString("category");

        this.general = new General(this);
    }

    public TWING register() {

        /*
         * Fuer jede File geht er nun die ParticleListe durch und fuegt jeden
         * ParticleCode zur codelist hinzu.
         */
        ArrayList<ParticleCode> codelist = handleParticleCodeCache(config);

        @SuppressWarnings("unchecked")
        List<String> patterntiles = (List<String>) config.getCfg().getList("pattern");
        int row = patterntiles.size();
        int col = patterntiles.get(0).split(",").length + 1;
        ParticleCode[][] pattern = new ParticleCode[row][col];
        int i = 0;
        int j = 0;
        for (String patternCode : patterntiles) {
            for (String code : patternCode.split(",")) {

                for (ParticleCode pc : codelist) {
                    if (code.equalsIgnoreCase(pc.getCode())) {
                        pattern[i][j] = pc;
                        break;
                    } else {
                        pattern[i][j] = null;
                    }

                }
                j++;
            }
            j = 0;
            i++;
        }
        this.setPattern(pattern);

        // System.out.println("[TWINGS] "+this.getFile().getName().replace(".yml", ".twing") + " loaded.");
        return this;
    }

    /**
     * registers and handles all ParticleCodes and saves them in the List.
     *
     * @param config {@link ConfigManager}
     * @return {@link ArrayList} with {@link ParticleCode}'s
     */
    public ArrayList<ParticleCode> handleParticleCodeCache(ConfigManager config) {
        ArrayList<ParticleCode> list = new ArrayList<ParticleCode>();

        for (String particleCode : config.getStringList("Particles")) {
            if (!this.getExclude().contains(particleCode)) {
                String value = config.getString("Particles", particleCode);

                // Wenn Redstone als Partikel erkannt wird geht es davon aus das RGB vorhanden
                // ist, und filtert so die RGB Zahlen und

                // String teilen in 2 (PARTICLETYPE : RGB)
                String[] split = value.split(":");
                ParticleCode newcode;
                double speed = 0;
                if (value.contains("REDSTONE")) {

                    // in case no speed is given the redstone PArticle needs to have a bigger size
                    // then 0 so default i set 0.8
                    // For Redstone Particles speed = size.
                    speed = 0.8;

                    // int[] erstellt mit 3 Werten (R1 G2 B3)
                    int[] rgb = new int[3];

                    // Unnoetige Zeichen entfernen.
                    String code = split[1].replace(")", "").replace("(", "").replace(" ", "");

                    // RGB Zahlen nach "," filtern und zuweisen.
                    String[] SRGB = code.split(",");
                    rgb = new int[]{Integer.parseInt(SRGB[0]), Integer.parseInt(SRGB[1]), Integer.parseInt(SRGB[2])};

                    if (split.length > 2) {
                        speed = Double.parseDouble(split[2]);
                    }
                    // Erstellt neuen ParticleCode mit Farbe.
                    newcode = new ParticleColor(particleCode, rgb).setSpeed(speed);
                } else {
                    // Erstellt neuen ParticleCode.
                    Particle particle;
                    if (split.length > 1) {
                        particle = Particle.valueOf(split[0]);
                        speed = Double.parseDouble(split[1]);
                    } else
                        particle = Particle.valueOf(value);
                    newcode = new ParticleCode(particleCode, particle).setSpeed(speed);
                }
                list.add(newcode);
            }
        }
        return list;
    }

    /**
     * Creates the Item for the GUI's
     *
     * @return {@link ItemStack}
     */
    public ItemStack createItemStack(Player p, GUI.CAT gui) {
        ItemBuilder item = new ItemBuilder(material);
        item.setName(MyColors.format(itemName));
        ArrayList<String> lore = new ArrayList<String>();
        if (Main.instance.getConfig().getBoolean("Menu.lore.pattern")) {
            for (ParticleCode[] particleCodes : pattern) {
                StringBuilder add = new StringBuilder();
                for (ParticleCode particleCode : particleCodes) {
                    if (particleCode != null) {
                        if (particleCode instanceof ParticleColor)
                            add.append(MyColors.format(((ParticleColor) particleCode).getHexaCode()
                                    + Main.instance.getConfig().getString("Menu.symbol") + "&r"));
                        else
                            add.append(Main.instance.getConfig().getString("Menu.symbol"));
                    } else
                        add.append("§r§f").append(Main.instance.getConfig().getString("Menu.symbol"));

                }
                lore.add(add.toString());
            }
        }
        if (this.getCreator() != null && Main.instance.getConfig().getBoolean("Menu.lore.creator")) {
            lore.add(Main.getInstance().getConfigString("Menu.creator").replace("%creator%", this.getCreator()));
        }

        switch (gui) {
            case WINGS:
                if (Main.instance.getConfig().getBoolean("Menu.lore.permissions")) {
                    String perms = "";
                    /*
                     * If the person has Permission the Symbol has to change so we look if he has
                     * the perms, if so the Symbol is the right one.
                     */
                    if (p.hasPermission(this.getPermission())) {
                        perms = Main.getInstance().getConfigString("haspermission");
                    } else {
                        perms = Main.getInstance().getConfigString("nopermission");
                    }

                    // Add the Permission Lore
                    lore.add(Main.getInstance().getConfigString("Menu.permissions").replace("%perms%", perms));
                }
                // Enchant if equipped:
                if (CurrentWings.current != null)
                    if (!CurrentWings.current.isEmpty())
                        if (CurrentWings.current.containsKey(p.getUniqueId()))
                            if (CurrentWings.current.get(p.getUniqueId()).contains(this.file)) {
                                item.addGlow();
                                if (Main.instance.getConfig().getBoolean("Menu.lore.action1"))
                                    lore.add(Main.getInstance().getMsg().getRightclick());
                                if (Main.instance.getConfig().getBoolean("Menu.lore.action2"))
                                    lore.add(Main.getInstance().getMsg().getShiftrightclick());
                                break;
                            }

                if (Main.instance.getConfig().getBoolean("Menu.lore.action1"))
                    lore.add(Main.getInstance().getMsg().getLeftclick());
                if (Main.instance.getConfig().getBoolean("Menu.lore.action2"))
                    lore.add(Main.getInstance().getMsg().getShiftleftclick());
                break;
            case PREVIEW:
                // Add the Permission Lore
                lore.add(MyColors.format("&cLeft click to set the Preview Location"));
                lore.add(MyColors.format("&cRight click to remove the Preview"));
                break;
            case EDIT:
                lore.add(Main.getInstance().getMsg().getEditleft(this));
                lore.add(Main.getInstance().getMsg().getEditright(this));
                break;
            default:
                break;
        }

        item.setLore(lore);

        return item.build();

    }

    public String getCategory() {
        return category;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public boolean isTiltBefore() {
        return tiltBefore;
    }

    public void setTiltBefore(boolean tiltBefore) {
        this.tiltBefore = tiltBefore;
    }

    public int getRunTilt() {
        return runTilt;
    }

    public void setRunTilt(int runTilt) {
        this.runTilt = runTilt;
    }

    public boolean isMirrow() {
        return mirrow;
    }

    public void setMirrow(boolean mirrow) {
        this.mirrow = mirrow;
    }

    public double getMoveup() {
        return moveup;
    }

    public void setMoveup(double moveup) {
        this.moveup = moveup;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public ItemStack getItem(Player p, GUI.CAT gui) {
        return createItemStack(p, gui);
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public double getMoveback() {
        return moveback;
    }

    public void setMoveback(double moveback) {
        this.moveback = moveback;
    }

    public int getTilt() {
        return tilt;
    }

    public void setTilt(int tilt) {
        this.tilt = tilt;
    }

    public ParticleCode[][] getPattern() {
        return pattern;
    }

    public void setPattern(ParticleCode[][] pattern) {
        this.pattern = pattern;
    }

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public int getSneakAddition() {
        return sneakAddition;
    }

    public void setSneakAddition(int sneakAddition) {
        this.sneakAddition = sneakAddition;
    }

    public int getDegreeAddition() {
        return degreeAddition;
    }

    public void setDegreeAddition(int degreeAddition) {
        this.degreeAddition = degreeAddition;
    }

    public boolean isShowWhenRunning() {
        return showWhenRunning;
    }

    public void setShowWhenRunning(boolean showWhenRunning) {
        this.showWhenRunning = showWhenRunning;
    }

    public double getSpacing() {
        return spacing;
    }

    public void setSpacing(double spacing) {
        this.spacing = spacing;
    }

    public List<String> getExclude() {
        return exclude;
    }

    public void setExclude(List<String> exclude) {
        this.exclude = exclude;
    }

}
