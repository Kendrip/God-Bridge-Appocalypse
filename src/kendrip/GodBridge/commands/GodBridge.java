package kendrip.GodBridge.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodBridge implements CommandExecutor {
    private static String configPrefix = Bukkit.getServer().getPluginManager()
            .getPlugin("GodBridging").getConfig().getString("prefix");
    private static String pluginPrefix = ChatColor.translateAlternateColorCodes('&', configPrefix);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("godbridge") || cmd.getName().equalsIgnoreCase("gb")) {
            Player player = ((Player) sender);

            if (args.length == 1) {

                if (args[0].equalsIgnoreCase("enable")) {
                    if (Bukkit.getServer().getPluginManager().getPlugin("GodBridging")
                            .getConfig().getString("status").equalsIgnoreCase("enabled")) {
                        player.sendMessage(pluginPrefix + ChatColor.GOLD + " Already Enabled");
                    } else {
                        player.sendMessage(pluginPrefix + ChatColor.GREEN + " Enabled");
                        Bukkit.getServer().getPluginManager().getPlugin("GodBridging").getConfig().set("status", "Enabled");
                        Bukkit.getServer().getPluginManager().getPlugin("GodBridging").saveConfig();
                    }
                }
                if (args[0].equalsIgnoreCase("disable")) {
                    if (Bukkit.getServer().getPluginManager().getPlugin("GodBridging")
                            .getConfig().getString("status").equalsIgnoreCase("disabled")) {
                        player.sendMessage(pluginPrefix + ChatColor.RED + " Already Disabled");
                    } else {
                        player.sendMessage(pluginPrefix + ChatColor.RED + " Disabled");
                        Bukkit.getServer().getPluginManager().getPlugin("GodBridging").getConfig().set("status", "Disabled");
                        Bukkit.getServer().getPluginManager().getPlugin("GodBridging").saveConfig();
                    }
                }
                if (args[0].equalsIgnoreCase("reload")) {
                    Bukkit.getServer().getPluginManager().getPlugin("GodBridging").reloadConfig();
                    player.sendMessage(pluginPrefix + ChatColor.GREEN + " Reload Successful");
                }
            }
            return true;
        }
        return false;
    }

    public static void setConfigPrefix() {
       Bukkit.getServer().getPluginManager().getPlugin("GodBridging").getConfig().set("prefix", configPrefix);
       Bukkit.getServer().getPluginManager().getPlugin("GodBridging").saveConfig();
}
}
