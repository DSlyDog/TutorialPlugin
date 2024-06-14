package net.whispwriting.teleportplus.events;

import net.whispwriting.teleportplus.TeleportPlus;
import net.whispwriting.teleportplus.files.SpawnFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Locale;

public class JoinEvent implements Listener {

    private TeleportPlus plugin;

    public JoinEvent(TeleportPlus plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if (!event.getPlayer().hasPlayedBefore()){
            World world = Bukkit.getWorld(plugin.spawn.get().getString("world"));
            double x = plugin.spawn.get().getDouble("x");
            double y = plugin.spawn.get().getDouble("y");
            double z = plugin.spawn.get().getDouble("z");
            double yaw = plugin.spawn.get().getDouble("yaw");
            double pitch = plugin.spawn.get().getDouble("pitch");
            Location loc = new Location(world, x, y, z, (float) yaw, (float) pitch);
            event.getPlayer().teleport(loc);
        }
    }
}
