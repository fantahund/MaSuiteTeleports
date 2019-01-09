package fi.matiaspaavilainen.masuiteteleports.bukkit.commands.requests;

import fi.matiaspaavilainen.masuitecore.core.objects.PluginChannel;
import fi.matiaspaavilainen.masuiteteleports.bukkit.MaSuiteTeleports;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Accept implements CommandExecutor {

    private MaSuiteTeleports plugin;

    public Accept(MaSuiteTeleports p) {
        plugin = p;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {

            if (args.length != 0) {
                plugin.formator.sendMessage(sender, plugin.config.load("teleports", "syntax.yml").getString("tpaccept"));
                return;
            }

            if (plugin.in_command.contains(sender)) {
                plugin.formator.sendMessage(sender, plugin.config.load(null, "messages.yml").getString("on-active-command"));
                return;
            }

            plugin.in_command.add(sender);
            Player p = (Player) sender;
            new PluginChannel(plugin, p, new Object[]{"MaSuiteTeleports", "TeleportAccept", sender.getName()}).send();
            plugin.in_command.remove(sender);

        });

        return true;
    }
}
