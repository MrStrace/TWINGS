package dev.strace.twings.commands;

import org.bukkit.entity.Player;

/**
 * 
 * @author Jason Holweg
 *
 */
public abstract class SubCommands {

	public final String cmd = "/twings ";
	/**
	 * getName
	 * 
	 * @return The args[0] or SubCMD base.
	 * 
	 */
	public abstract String getName();

	/**
	 * getDesc
	 * 
	 * @return The description of the SubCMD.
	 */
	public abstract String getDesc();

	/**
	 * getSyntax
	 * 
	 * @return How to use the full CMD. e.g. "/example subcmd args.."
	 */
	public abstract String getSyntax();

	/**
	 * Performing of the subCMD.
	 * 
	 * @param p    (player)
	 * @param args each subcommand
	 */
	public abstract void perform(Player p, String[] args);

}
