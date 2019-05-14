package net.whispwriting.tutorialplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (player.hasPermission("TutorialPlugin.heal")){
                player.setHealth(20);
                player.setFoodLevel(20);
                player.sendMessage(ChatColor.GREEN + "You have been healed.");
                return true;
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
