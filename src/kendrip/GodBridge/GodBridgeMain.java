package kendrip.GodBridge;

import kendrip.GodBridge.commands.GodBridge;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class GodBridgeMain extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        GodBridge.setConfigPrefix();
        saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(this, this);
        getCommand("godbridge").setExecutor(new GodBridge());
        getCommand("gb").setExecutor(new GodBridge());
        getCommand("gb").setTabCompleter(this);
        getCommand("godbridge").setTabCompleter(this);
    }

    @Override
    public void onDisable() {
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {

        List<String> cmdList = new ArrayList<>();
        cmdList.add("enable");
        cmdList.add("disable");
        cmdList.add("reload");

        if (!cmd.getName().equalsIgnoreCase("godbridge") || cmd.getName().equalsIgnoreCase("gb")) {
            //do nothing
        }
        return cmdList;

    }

    @EventHandler
    private void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        World world = player.getWorld();
        boolean isSneaking = player.isSneaking();

        if (getConfig().getString("status").equalsIgnoreCase("enabled")) {
            if (!isSneaking) {
                int X = location.getBlockX();
                int Y = location.getBlockY() - 1;
                int Z = location.getBlockZ();

                world.getBlockAt(X, Y, Z).setType(Material.WOOL);
            }
        }
    }

    @EventHandler
    private void onFallDamage(EntityDamageEvent event) {
        if (getConfig().getString("status").equalsIgnoreCase("enabled")) {
            try {
                Player player = ((Player) event.getEntity());

                if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                    event.setCancelled(true);
                }
            } catch (Exception ignored) {
            }
        }

    }
}
