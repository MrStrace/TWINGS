package dev.strace.twings.commands;

import java.util.ArrayList;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.strace.twings.Main;
import dev.strace.twings.utils.WingUtils;
import dev.strace.twings.utils.gui.WingGUI;

/**
 * 
 * @author Jason Holweg [STRACE]
 * <b>TWINGS</b><br>
 * Website: <a>https://strace.dev/</a><br>
 * GitHub: <a>https://github.com/MrStrace</a><br>
 * Created: May 30, 2021<br>
 *
 */

public class WingsCommand implements CommandExecutor{

	// Liste aller SubCommands von Setup.
	private ArrayList<SubCommands> subcommands = new ArrayList<SubCommands>();

	/**
	 * Initialisierung der SubCommands. Der ArrayListe werden die SubCommands
	 * hinzugefügt und beim Start des Plugins initialisiert.
	 */
	public WingsCommand() {

		//subcommands.add(new subcommand());
		// ..

		System.out.println(WingsCommand.class.getSimpleName() + " - subcommands wurden registriert!");

	}

	/**
	 * Opens the WING Menu
	 * 
	 * @param p
	 */
	private void mainCommand(Player p) {
		new WingGUI(WingUtils.winglist.size()).openGUI(p);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {

			Player p = (Player) sender;

			// Solange der Spieler die Rechte "admin" besitzt kann er alle Setup befehle
			// ausführen.
			if (p.hasPermission("twings.admin")) {

				if (args.length == 0) {
					mainCommand(p);
				} else {
					for (int i = 0; i < subcommands.size(); i++) {
						if (args[0].equalsIgnoreCase(subcommands.get(i).getName())) {
							subcommands.get(i).perform(p, args);
						}
					}

				}

			}
		}

		return false;
	}

}
