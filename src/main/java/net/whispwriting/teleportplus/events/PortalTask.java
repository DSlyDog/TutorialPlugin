package net.whispwriting.teleportplus.events;

import net.whispwriting.teleportplus.TeleportPlus;
import net.whispwriting.teleportplus.sql.MySQLUtil;
import net.whispwriting.teleportplus.sql.PortalSet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.Collection;

public class PortalTask extends BukkitRunnable {

    private TeleportPlus plugin;

    public PortalTask(TeleportPlus pl){
        plugin = pl;
    }

    @Override
    public void run() {

        Collection players = Bukkit.getServer().getOnlinePlayers();
        if (!players.isEmpty()) {
            for (Object o : players) {
                Player player = (Player) o;
                Location loc = player.getLocation();
                int x = (int) loc.getX();
                int y = (int) loc.getY();
                int z = (int) loc.getZ();
                loc.setX(x);
                loc.setY(y);
                loc.setZ(z);

                String name = "NULL";
                Location locTo = player.getLocation();
                for (PortalSet portal : plugin.sqlUtil.getPortalSet()){
                    if (portal.getX() == x && portal.getY() == y && portal.getZ() == z){
                        World world = Bukkit.getWorld(portal.getWorld2());
                        locTo = new Location(world, portal.getX2(), portal.getY2(), portal.getZ2());
                        name = portal.getName();
                    }
                }

                if (!name.equals("NULL")) {
                    try {
                        locTo.setPitch(player.getLocation().getPitch());
                        locTo.setYaw(player.getLocation().getYaw());
                        if (player.isInsideVehicle()) {
                            if (player.getVehicle() instanceof Horse) {
                                Horse mount = (Horse) player.getVehicle();
                                player.teleport(locTo);
                                mount.teleport(locTo);
                                mount.setPassenger(player);
                            } else {
                                player.teleport(locTo);
                            }
                        } else {
                            player.teleport(locTo);
                        }
                        player.sendMessage(ChatColor.GREEN + "Teleporting...");
                    } catch (IllegalArgumentException e) {
                        // do nothing
                    }
                }
            }
        }
    }
}
