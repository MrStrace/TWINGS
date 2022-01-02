
package dev.strace.twings.commands;

import dev.strace.twings.Main;
import dev.strace.twings.utils.MyColors;
import dev.strace.twings.utils.WingUtils;
import dev.strace.twings.utils.objects.TWING;
import org.bukkit.entity.Player;

import java.io.File;

/**
 * @author Jason Holweg [STRACE] <b>TWINGS</b><br>
 * Website: <a>https://strace.dev/</a><br>
 * GitHub: <a>https://github.com/MrStrace</a><br>
 * Created: Jun 7, 2021<br>
 */
public class Help extends SubCommands {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDesc() {
        return "List of all Commands.";
    }

    @Override
    public String getSyntax() {
        return "/wings " + getName();
    }

    @Override
    public void perform(Player p, String[] args) {
        if (args.length == 1) {// First Line of the Wing list.

            p.sendMessage(Main.getInstance().getPrefix() + MyColors.format(" &7List of all TWINGS Commands:"));
            for (SubCommands cmd : WingsCommand.subcommands) {
                p.sendMessage(MyColors.format("#ff5985" + cmd.getSyntax() + "&7: " + cmd.getDesc()));
            }

        }
    }

}
