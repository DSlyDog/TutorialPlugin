package net.whispwriting.teleportplus.commands;

import net.whispwriting.teleportplus.TeleportPlus;
import net.whispwriting.teleportplus.files.PortalFile;
import net.whispwriting.teleportplus.sql.MySQLUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class PortalCommand implements CommandExecutor {

    private TeleportPlus plugin;

    public PortalCommand(TeleportPlus pl){
        plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (plugin.dbfile.get().getBoolean("remote database")) {
            try {
                if (plugin.getConnection().isClosed()) {
                    plugin.mySqlSetup();
                }
            } catch (SQLException e) {
                // do nothing
            }
        }
        if (sender instanceof Player){
            if (sender.hasPermission("TeleportPlus.portal.admin")) {
                if (args.length == 2 && args[0].equals("from")) {
                    Player player = (Player) sender;
                    Location loc = player.getLocation();
                    plugin.sqlUtil.portalFrom(args[1], loc);
                    //loc.setY(loc.getY()-1);
                    player.sendMessage(ChatColor.GREEN + "Portal from location set.");
                    return true;
                }else if (args.length == 2 && args[0].equals("to")) {
                    Player player = (Player) sender;
                    if (plugin.sqlUtil.portalTo(args[1], player.getLocation())){
                        player.sendMessage(ChatColor.GREEN + "Portal to location set.");
                    }else{
                        player.sendMessage(ChatColor.RED + "No portal by that name could be found.");
                    }
                    return true;
                }else if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {
                    if (plugin.sqlUtil.portalDel(args[1])){
                        MySQLUtil.deletePortal(args[1]);
                        sender.sendMessage(ChatColor.GREEN + "Portal " + ChatColor.DARK_GREEN + args[1] + ChatColor.GREEN + " deleted.");
                    }else{
                        sender.sendMessage(ChatColor.RED + "No portal by that name could be found.");
                    }
                    return true;
                }else if (args.length == 1 && args[0].equalsIgnoreCase("list")){
                    String list = plugin.sqlUtil.portalList();
                    sender.sendMessage(ChatColor.GREEN + "Portals: ");
                    sender.sendMessage(list);
                    return true;
                }else{
                        sender.sendMessage(ChatColor.DARK_RED+"/portal "+ChatColor.RED+"<from:to:delete:list> <name>");
                        return true;
                    }
            }else{
                return false;
            }
        }else{
            sender.sendMessage(ChatColor.DARK_RED + "Only player may execute that command.");
            return true;
        }
    }

}
