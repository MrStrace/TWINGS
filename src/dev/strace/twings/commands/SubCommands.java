package dev.strace.twings.commands;

import org.bukkit.entity.Player;

/**
 * @author Jason Holweg
 */
public abstract class SubCommands {

    private final String name, desc, syntax;

    public SubCommands(String name, String desc, String syntax) {
        this.name = name;
        this.desc = desc;
        this.syntax = syntax;
    }

    /**
     * getName
     *
     * @return The args[0] or SubCMD base.
     */
    public String getName() {
        return name;
    }

    /**
     * getDesc
     *
     * @return The description of the SubCMD.
     */
    public String getDescription() {
        return desc;
    }

    /**
     * getSyntax
     *
     * @return How to use the full CMD. e.g. "/example subcmd args.."
     */
    public String getSyntax() {
        return syntax;
    }

    /**
     * Performing of the subCMD.
     *
     * @param p    (player)
     * @param args each subcommand
     */
    public abstract void perform(Player p, String[] args);

}
