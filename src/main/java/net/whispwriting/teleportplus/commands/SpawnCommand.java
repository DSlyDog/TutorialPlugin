package net.whispwriting.teleportplus.commands;

import net.whispwriting.teleportplus.TeleportPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpawnCommand implements CommandExecutor {

    private TeleportPlus plugin;

    public SpawnCommand(TeleportPlus pl){
        plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only players may execute that command");
            return true;
        }
        if (command.getName().equals("setspawn")){
            Player player = (Player) sender;
            if (!player.hasPermission("TeleportPlus.setspawn")){
                player.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
                return true;
            }
            Location loc = player.getLocation();
            String world = loc.getWorld().getName();
            double x = loc.getX();
            double y = loc.getY();
            double z = loc.getZ();
            float pitch = loc.getPitch();
            float yaw = loc.getYaw();
            plugin.spawn.get().set("world", world);
            plugin.spawn.get().set("x", x);
            plugin.spawn.get().set("y", y);
            plugin.spawn.get().set("z", z);
            plugin.spawn.get().set("yaw", yaw);
            plugin.spawn.get().set("pitch", pitch);
            plugin.spawn.save();
            player.sendMessage(ChatColor.GREEN + "Spawnpoint set.");
            return true;
        }
        if (command.getName().equals("spawn")) {
            Player player = (Player) sender;
            if (!player.hasPermission("TeleportPlus.spawn")) {
                player.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
                return true;
            }
            if (plugin.spawn.get().getString("world") != null) {
                World world = Bukkit.getWorld(plugin.spawn.get().getString("world"));
                double x = plugin.spawn.get().getDouble("x");
                double y = plugin.spawn.get().getDouble("y");
                double z = plugin.spawn.get().getDouble("z");
                double yaw = plugin.spawn.get().getDouble("yaw");
                double pitch = plugin.spawn.get().getDouble("pitch");

                Location loc = new Location(world, x, y, z, (float) yaw, (float) pitch);
                player.sendMessage(ChatColor.GREEN + "Teleporting...");
                player.teleport(loc);
                return true;
            }else{
                player.sendMessage(ChatColor.RED + "Spawn has not been set.");
            }
        }
        return true;
    }
}
