package net.whispwriting.tutorialplugin.commands;

import net.whispwriting.tutorialplugin.commands.helpers.LocationList;
import net.whispwriting.tutorialplugin.files.LocationsFile;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class HomeCommand implements CommandExecutor {

    private LocationsFile locations;

    public HomeCommand(LocationsFile l){
        locations = l;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("TutorialPlugin.home")) {
            if (command.getName().equalsIgnoreCase("sethome")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (args.length == 1) {
                        List<Map<?,?>> locList = new ArrayList<Map<?,?>>();
                        try {
                            locList = locations.get().getMapList(player.getUniqueId().toString());
                            Location loc = player.getLocation();
                            String name = args[0];
                            for (int i=0; i<locList.size(); i++){
                                if (locList.get(i).containsKey(name)){
                                    player.sendMessage(ChatColor.DARK_RED + "You already have a home with that name.");
                                    return true;
                                }
                            }
                            Map<String, Location> locMap = new HashMap<String, Location>();
                            locMap.put(name, loc);
                            locList.add(locMap);
                            locations.get().set(player.getUniqueId().toString(), locList);
                            locations.save();
                            player.sendMessage(ChatColor.GREEN + "Home set.");
                        } catch (Exception e) {
                            player.sendMessage(ChatColor.RED + "/sethome " + ChatColor.DARK_RED + "<name>");
                            Location loc = player.getLocation();
                            String name = args[0];
                            locList = new ArrayList<>();
                            Map<String, Location> locMap = new HashMap<String, Location>();
                            locMap.put(name, loc);
                            locList.add(locMap);
                            locations.get().set(player.getUniqueId().toString(), locList);
                            locations.save();
                            player.sendMessage(ChatColor.GREEN + "Home set.");
                        }
                        return true;
                    } else {
                        player.sendMessage(ChatColor.RED + "/sethome " + ChatColor.DARK_RED + "<name>");
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
                        List<Map<?,?>> locList = locations.get().getMapList(player.getUniqueId().toString());
                        for (int i=0; i<locList.size(); i++){
                            Map<?, ?> locMap = locList.get(i);
                            if (locMap.containsKey(args[0])){
                                Location loc = (Location) locMap.get(args[0]);
                                player.teleport(loc);
                                player.sendMessage(ChatColor.GOLD + "Teleporting to home " + ChatColor.RED + args[0]);
                                return true;
                            }
                        }
                        player.sendMessage(ChatColor.DARK_RED + "Home " + ChatColor.RED + args[0] + ChatColor.DARK_RED + " not found.");
                        return true;
                    }else{
                        player.sendMessage(ChatColor.RED + "/home " + ChatColor.DARK_RED + "<name>");
                        return true;
                    }
                }else{
                    sender.sendMessage(ChatColor.DARK_RED + "Only players may execute that command.");
                    return true;
                }
            }else if (command.getName().equalsIgnoreCase("homes")) {
                if (sender instanceof Player){
                    Player player = (Player) sender;
                    List<Map<?,?>> locList = locations.get().getMapList(player.getUniqueId().toString());
                    String homesString = "";
                    for (int i=0; i<locList.size(); i++) {
                        Map<?,?> locMap = locList.get(i);
                        for (Map.Entry<?, ?> e : locMap.entrySet()) {
                            String key = (String) e.getKey();
                            if (i != locList.size()-1) {
                                homesString += key + ", ";
                            }else{
                                homesString += key;
                            }
                        }
                    }
                    player.sendMessage(ChatColor.GREEN + "Homes: ");
                    player.sendMessage(homesString);
                    return true;
                } else {
                    sender.sendMessage(ChatColor.DARK_RED + "Only players may execute that command.");
                    return true;
                }
            }else if (command.getName().equalsIgnoreCase("delhome")){
                if (sender instanceof Player){
                    Player player = (Player) sender;
                    List<Map<?,?>> locList = locations.get().getMapList(player.getUniqueId().toString());
                    for (int i=0; i<locList.size(); i++){
                        if (locList.get(i).containsKey(args[0])){
                            locList.remove(i);
                        }
                    }
                    locations.get().set(player.getUniqueId().toString(), locList);
                    locations.save();
                    player.sendMessage(ChatColor.GREEN + "Home deleted.");
                    return true;
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

}
