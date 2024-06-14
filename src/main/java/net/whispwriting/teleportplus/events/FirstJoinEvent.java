package net.whispwriting.teleportplus.events;

import net.whispwriting.teleportplus.TeleportPlus;
import net.whispwriting.teleportplus.files.SpawnFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class FirstJoinEvent implements Listener {

    private TeleportPlus plugin;

    public FirstJoinEvent(TeleportPlus pl){
        plugin = pl;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()){
            World world = Bukkit.getWorld(plugin.spawn.get().getString("world"));
            double x = plugin.spawn.get().getDouble("x");
            double y = plugin.spawn.get().getDouble("y");
            double z = plugin.spawn.get().getDouble("z");
            float yaw =  (float) plugin.spawn.get().getDouble("yaw");
            float pitch =  (float) plugin.spawn.get().getDouble("pitch");
            Location loc = new Location (world, x, y, z, yaw, pitch);
            player.teleport(loc);
        }
    }

}
