package kendrip.GodBridge;

import kendrip.GodBridge.commands.GodBridge;
import org.bukkit.Bukkit;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class GodBridgeMain extends JavaPlugin implements Listener {

    public static boolean status = false;

    @Override
    public void onEnable() {
        System.out.println("\n\n\n\n" + Material.matchMaterial("WOOL") + "\n\n\n\n");


        getConfig().options().copyDefaults(true);
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

        if (status) {
            int X = location.getBlockX();
            int Y = location.getBlockY() - 1;
            int Z = location.getBlockZ();
            Material bridgeBlock = checkMaterial(getConfig().getString("block-id"));
            world.getBlockAt(X, Y, Z).setType(bridgeBlock);
        }
    }

    @EventHandler
    private void onFallDamage(EntityDamageEvent event) {
        if (status) {
            if (event.getEntity() instanceof Player) {
                if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    private static Material checkMaterial(String BlockData) {

        Material _BlockData = null;

        if (BlockData.contains(":")) {
            String[] idArray = BlockData.split(":");
            Material blockName = Material.getMaterial(Integer.parseInt(idArray[0]));
            byte blockID = Byte.parseByte(idArray[1]);
            ItemStack item = new ItemStack(blockName, 1, (byte) blockID);

            _BlockData = item.getType();
        }

        return _BlockData;
    }
}
