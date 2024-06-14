package net.whispwriting.teleportplus.commands;

import net.whispwriting.teleportplus.TeleportPlus;
import net.whispwriting.teleportplus.files.HomeFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class HomeCommand implements CommandExecutor {

    private HomeFile locations;
    private TeleportPlus plugin;

    public HomeCommand(TeleportPlus pl){
        plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("TeleportPlus.home")) {
            if (command.getName().equalsIgnoreCase("sethome")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    locations = new HomeFile(plugin, player.getUniqueId().toString());
                    int homeLimit = getHomeLimit(player);
                    if (args.length == 1) {
                        String home = locations.get().getString(args[0]);
                        if (home != null) {
                            Location loc = player.getLocation();
                            String world = loc.getWorld().getName();
                            double x = loc.getX();
                            double y = loc.getY();
                            double z = loc.getZ();
                            double yaw = loc.getYaw();
                            double pitch = loc.getPitch();
                            locations.get().set(args[0] + ".world", world);
                            locations.get().set(args[0] + ".x", x);
                            locations.get().set(args[0] + ".y", y);
                            locations.get().set(args[0] + ".z", z);
                            locations.get().set(args[0] + ".yaw", yaw);
                            locations.get().set(args[0] + ".pitch", pitch);
                            locations.save();
                            player.sendMessage(ChatColor.GREEN + "Home " + ChatColor.AQUA + args[0] + ChatColor.GREEN + " set.");
                        }else{
                            int currentHomes = locations.get().getInt("home-count");
                            if (currentHomes >= homeLimit && homeLimit != -1){
                                player.sendMessage(ChatColor.RED + "You may only have " + ChatColor.DARK_RED + homeLimit + ChatColor.RED + " homes.");
                            }else{
                                Location loc = player.getLocation();
                                String world = loc.getWorld().getName();
                                double x = loc.getX();
                                double y = loc.getY();
                                double z = loc.getZ();
                                double yaw = loc.getYaw();
                                double pitch = loc.getPitch();
                                locations.get().set(args[0] + ".world", world);
                                locations.get().set(args[0] + ".x", x);
                                locations.get().set(args[0] + ".y", y);
                                locations.get().set(args[0] + ".z", z);
                                locations.get().set(args[0] + ".yaw", yaw);
                                locations.get().set(args[0] + ".pitch", pitch);
                                locations.get().set("home-count", currentHomes + 1);
                                locations.save();
                                player.sendMessage(ChatColor.GREEN + "Home " + ChatColor.AQUA + args[0] + ChatColor.GREEN + " set.");
                            }
                        }
                        return true;
                    }else if (args.length == 0){
                        String home = locations.get().getString("home");
                        if (home != null) {
                            Location loc = player.getLocation();
                            String world = loc.getWorld().getName();
                            double x = loc.getX();
                            double y = loc.getY();
                            double z = loc.getZ();
                            double yaw = loc.getYaw();
                            double pitch = loc.getPitch();
                            locations.get().set("home.world", world);
                            locations.get().set("home.x", x);
                            locations.get().set("home.y", y);
                            locations.get().set("home.z", z);
                            locations.get().set("home.yaw", yaw);
                            locations.get().set("home.pitch", pitch);
                            locations.save();
                            player.sendMessage(ChatColor.GREEN + "Home " + ChatColor.AQUA + "home" + ChatColor.GREEN + " set.");
                        }else{
                            int currentHomes = locations.get().getInt("home-count");
                            if (currentHomes >= homeLimit && homeLimit != -1){
                                player.sendMessage(ChatColor.RED + "You may only have " + ChatColor.DARK_RED + homeLimit + ChatColor.RED + " homes.");
                            }else{
                                Location loc = player.getLocation();
                                String world = loc.getWorld().getName();
                                double x = loc.getX();
                                double y = loc.getY();
                                double z = loc.getZ();
                                double yaw = loc.getYaw();
                                double pitch = loc.getPitch();
                                locations.get().set("home.world", world);
                                locations.get().set("home.x", x);
                                locations.get().set("home.y", y);
                                locations.get().set("home.z", z);
                                locations.get().set("home.yaw", yaw);
                                locations.get().set("home.pitch", pitch);
                                locations.get().set("home-count", currentHomes + 1);
                                locations.save();
                                player.sendMessage(ChatColor.GREEN + "Home " + ChatColor.AQUA + "home" + ChatColor.GREEN + " set.");
                            }
                        }
                        return true;
                    }else {
                        player.sendMessage(ChatColor.GREEN + "/sethome " + ChatColor.AQUA + "<name>");
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.DARK_RED + "Only players may execute that command.");
                    return true;
                }
            }else if (command.getName().equalsIgnoreCase("home")){
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (args.length == 1) {
                        locations = new HomeFile(plugin, player.getUniqueId().toString());
                        if (locations.get().getString(args[0] + ".world") != null) {
                            World world = Bukkit.getWorld(locations.get().getString(args[0] + ".world"));
                            double x = locations.get().getDouble(args[0] + ".x");
                            double y = locations.get().getDouble(args[0] + ".y");
                            double z = locations.get().getDouble(args[0] + ".z");
                            float yaw = (float) locations.get().getDouble(args[0] + ".yaw");
                            float pitch = (float) locations.get().getDouble(args[0] + ".pitch");
                            Location loc = new Location(world, x, y, z, yaw, pitch);
                            player.sendMessage(ChatColor.GREEN + "Teleporting to home " + ChatColor.AQUA + args[0]);
                            player.teleport(loc);
                        }else {
                            player.sendMessage(ChatColor.RED + "Home " + ChatColor.AQUA + args[0] + ChatColor.RED + " not found.");
                        }
                        return true;
                    }else if (args.length == 0){
                        locations = new HomeFile(plugin, player.getUniqueId().toString());
                        if (locations.get().getString("home.world") != null) {
                            World world = Bukkit.getWorld(locations.get().getString("home.world"));
                            double x = locations.get().getDouble("home.x");
                            double y = locations.get().getDouble("home.y");
                            double z = locations.get().getDouble("home.z");
                            float yaw = (float) locations.get().getDouble("home.yaw");
                            float pitch = (float) locations.get().getDouble("home.pitch");
                            Location loc = new Location(world, x, y, z, yaw, pitch);
                            player.sendMessage(ChatColor.GREEN + "Teleporting to home " + ChatColor.AQUA + "home");
                            player.teleport(loc);
                        }else {
                            player.sendMessage(ChatColor.RED + "Home " + ChatColor.AQUA + "home" + ChatColor.RED + " not found.");
                        }
                        return true;
                    }else{
                        player.sendMessage(ChatColor.GREEN + "/home " + ChatColor.AQUA + "<name>");
                        return true;
                    }
                }else{
                    sender.sendMessage(ChatColor.DARK_RED + "Only players may execute that command.");
                    return true;
                }
            }else if (command.getName().equalsIgnoreCase("homes")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    locations = new HomeFile(plugin, player.getUniqueId().toString());
                    StringBuilder builder = new StringBuilder();
                    builder.append(ChatColor.AQUA);
                    player.sendMessage(ChatColor.GREEN + "Homes:");
                    for (String home : locations.get().getConfigurationSection("").getKeys(false))
                        if (!home.equals("home-count"))
                            builder.append(home).append(" ");
                    player.sendMessage(builder.toString());
                    return true;
                }else{
                    sender.sendMessage(ChatColor.DARK_RED + "Only players may execute that command.");
                    return true;
                }
            }else if (command.getName().equalsIgnoreCase("delhome")){
                if (sender instanceof Player){
                    if (args.length == 1) {
                        Player player = (Player) sender;
                        locations = new HomeFile(plugin, player.getUniqueId().toString());
                        int currentHomes = locations.get().getInt("home-count");
                        if (locations.get().getString(args[0] + ".world") != null) {
                            locations.get().set(args[0] + ".world", null);
                            locations.get().set(args[0] + ".x", null);
                            locations.get().set(args[0] + ".y", null);
                            locations.get().set(args[0] + ".z", null);
                            locations.get().set(args[0] + ".yaw", null);
                            locations.get().set(args[0] + ".pitch", null);
                            locations.get().set("home-count", currentHomes - 1);
                            locations.get().set(args[0], null);
                            locations.save();
                            player.sendMessage(ChatColor.GREEN + "Home deleted.");
                        }else {
                            player.sendMessage(ChatColor.RED + "Home " + ChatColor.AQUA + args[0] + ChatColor.RED + " not found.");
                        }
                        return true;
                    }else if (args.length == 0){
                        Player player = (Player) sender;
                        locations = new HomeFile(plugin, player.getUniqueId().toString());
                        int currentHomes = locations.get().getInt("home-count");
                        if (locations.get().getString("home.world") != null) {
                            locations.get().set("home.world", null);
                            locations.get().set("home.x", null);
                            locations.get().set("home.y", null);
                            locations.get().set("home.z", null);
                            locations.get().set("home.yaw", null);
                            locations.get().set("home.pitch", null);
                            locations.get().set("home", null);
                            locations.get().set("home-count", currentHomes - 1);
                            locations.save();
                            player.sendMessage(ChatColor.GREEN + "Home deleted.");
                        }else {
                            player.sendMessage(ChatColor.RED + "Home " + ChatColor.AQUA + "home" + ChatColor.RED + " not found.");
                        }
                        return true;
                    }else{
                        sender.sendMessage(ChatColor.GREEN + "/delhome " + ChatColor.AQUA + "<name>");
                        return true;
                    }
                }else{
                    sender.sendMessage(ChatColor.DARK_RED + "Only players may execute that command.");
                    return true;
                }
            }else{
                sender.sendMessage(ChatColor.RED + "That is not a valid option.");
                return true;
            }
        }else{
            sender.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
            return true;
        }
    }

    public int getHomeLimit(Player player){
        if (player.hasPermission("TeleportPlus.home.multiple.*")){
            return -1;
        }else{
            for (int i=0; i<100; i++){
                if (player.hasPermission("TeleportPlus.home.multiple." + i))
                    return i;
            }
            return 1;
        }
    }

}
