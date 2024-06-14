package net.whispwriting.teleportplus.commands;

import net.whispwriting.teleportplus.TeleportPlus;
import net.whispwriting.teleportplus.files.WarpFile;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarpCommand implements CommandExecutor {

    private WarpFile warps;
    private TeleportPlus plugin;

    public WarpCommand(TeleportPlus pl){
        plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (command.getName().equalsIgnoreCase("setwarp")){
                if (player.hasPermission("TeleportPlus.setwarp")){
                    if (args.length == 1) {
                        warps = new WarpFile(plugin);
                        try {
                            Location loc = player.getLocation();
                            List<Map<?, ?>> warpList = warps.get().getMapList("warps");
                            String name = args[0];
                            for (int i=0; i<warpList.size(); i++){
                                if (warpList.get(i).containsKey(name)){
                                    player.sendMessage(ChatColor.DARK_RED + "A warp with that name already exists.");
                                    return true;
                                }
                            }
                            Map<String, Location> warpMap = new HashMap<String, Location>();
                            warpMap.put(args[0], loc);
                            warpList.add(warpMap);
                            warps.get().set("warps", warpList);
                            warps.save();
                        } catch (NullPointerException e) {
                            Location loc = player.getLocation();
                            List<Map<?,?>> warpList = new ArrayList<>();
                            Map<String, Location> warpMap = new HashMap<String, Location>();
                            warpMap.put(args[0], loc);
                            warpList.add(warpMap);
                            warps.get().set("warps", warpList);
                            warps.save();
                        }finally{
                            player.sendMessage(ChatColor.GREEN+"Warp added.");
                            warps.reload();
                        }
                        return true;
                    }else{
                        player.sendMessage(ChatColor.RED + "/setwarp" + ChatColor.DARK_RED + " <name>");
                        return true;
                    }
                }else{
                    return false;
                }
            }else if (command.getName().equalsIgnoreCase("warp")){
                if (player.hasPermission("TeleportPlus.warp")){
                    if (args.length == 1){
                        warps = new WarpFile(plugin);
                        List<Map<?,?>> warpList = warps.get().getMapList("warps");
                        for (Map<?,?> warpMap : warpList){
                            if (warpMap.containsKey(args[0])){
                                Location loc = (Location) warpMap.get(args[0]);
                                player.teleport(loc);
                                player.sendMessage(ChatColor.GREEN + "Warping...");
                                return true;
                            }
                        }
                        player.sendMessage(ChatColor.DARK_RED + "Warp " + ChatColor.RED + args[0] + ChatColor.DARK_RED + " not found.");
                        return true;
                    }else{
                        player.sendMessage(ChatColor.RED + "/warp" + ChatColor.DARK_RED + " <name>");
                        return true;
                    }
                }else{
                    return false;
                }
            }else if (command.getName().equalsIgnoreCase("warps")){
                if (player.hasPermission("TeleportPlus.warps")){
                    warps = new WarpFile(plugin);
                    List<Map<?,?>> warpList = warps.get().getMapList("warps");
                    String warpString = "";
                    for (int i=0; i<warpList.size(); i++) {
                        Map<?,?> locMap = warpList.get(i);
                        for (Map.Entry<?, ?> e : locMap.entrySet()) {
                            String key = (String) e.getKey();
                            if (i != warpList.size()-1) {
                                warpString += key + ", ";
                            }else{
                                warpString += key;
                            }
                        }
                    }
                    player.sendMessage(ChatColor.GREEN + "Warps: ");
                    player.sendMessage(warpString);
                    return true;
                }else{
                    return false;
                }
            }else {
                if (player.hasPermission("TeleportPlus.delwarp")) {
                    if (args.length == 1) {
                        warps = new WarpFile(plugin);
                        List<Map<?, ?>> warpList = warps.get().getMapList("warps");
                        for (int i = 0; i < warpList.size(); i++) {
                            Map<?, ?> warpMap = warpList.get(i);
                            if (warpMap.containsKey(args[0])) {
                                warpList.remove(i);
                                warps.get().set("warps", warpList);
                                warps.save();
                                player.sendMessage(ChatColor.DARK_RED + "Warp " + ChatColor.RED + args[0] + ChatColor.DARK_RED + " deleted.");
                                warps.reload();
                                return true;
                            }
                        }
                        player.sendMessage(ChatColor.DARK_RED + "Warp " + ChatColor.RED + args[0] + ChatColor.DARK_RED + " does not exist.");
                        return true;
                    }else{
                        player.sendMessage(ChatColor.RED + "/delwarp" + ChatColor.DARK_RED + " <name>");
                        return true;
                    }
                }else{
                    return false;
                }
            }
        }else{
            sender.sendMessage(ChatColor.DARK_RED+"Only players may execute that command.");
            return true;
        }
    }

}
