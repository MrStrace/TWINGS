package dev.strace.twings.commands;

import dev.strace.twings.Main;
import org.bukkit.entity.Player;

/**
 * @author Jason Holweg [STRACE]
 * <b>TWINGS</b><br>
 * Website: <a>https://strace.dev/</a><br>
 * GitHub: <a>https://github.com/MrStrace</a><br>
 * Created: Jul 7, 2021<br>
 */
public class Import extends SubCommands {

    @Override
    public String getName() {
        return "import";
    }

    @Override
    public String getDesc() {
        return "imports twing files.";
    }

    @Override
    public String getSyntax() {
        return cmd + getName();
    }

    @Override
    public void perform(Player p, String[] args) {

        if (!p.hasPermission("twings.admin")) {
            p.sendMessage(Main.getInstance().getMsg().getNopermission());
           // return;
        }

//        if (args.length == 1)
        // open GUI with addons to import twings


    }
}
