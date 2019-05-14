package net.whispwriting.tutorialplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (player.hasPermission("TutorialPlugin.fly")){
                if (player.getAllowFlight()){
                    player.setAllowFlight(false);
                    player.sendMessage(ChatColor.DARK_GREEN + "Flight mode is now " + ChatColor.RED + "disabled.");
                    return true;
                }else{
                    player.setAllowFlight(true);
                    player.sendMessage(ChatColor.DARK_GREEN + "Flight mode is now " + ChatColor.GREEN + "enabled.");
                    return true;
                }
            }else{
                player.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
                return true;
            }
        }else{
            sender.sendMessage(ChatColor.DARK_RED + "Only players may execute that command.");
            return true;
        }
    }

}
