package net.whispwriting.teleportplus.events;

import net.whispwriting.teleportplus.TeleportPlus;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnEvent implements Listener {

    private TeleportPlus plugin;

    public RespawnEvent(TeleportPlus plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        if (plugin.spawn.get().getString("world") != null) {
            World world = Bukkit.getWorld(plugin.spawn.get().getString("world"));
            double x = plugin.spawn.get().getDouble("x");
            double y = plugin.spawn.get().getDouble("y");
            double z = plugin.spawn.get().getDouble("z");
            double yaw = plugin.spawn.get().getDouble("yaw");
            double pitch = plugin.spawn.get().getDouble("pitch");
            Location loc = new Location(world, x, y, z, (float) yaw, (float) pitch);
            event.setRespawnLocation(loc);
        }
    }

}
