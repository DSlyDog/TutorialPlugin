package net.whispwriting.teleportplus.sql;

import net.whispwriting.teleportplus.TeleportPlus;
import org.bukkit.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class SQLUtil {

    private Set<PortalSet> portalSet = new HashSet<>();
    private TeleportPlus plugin;

    public SQLUtil(TeleportPlus pl){
        plugin = pl;
    }

    public void loadSet(){
        System.out.println("Beginning to load portals.");
        try {
            PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.table);
            ResultSet results = statement.executeQuery();
            while (results.next()){
                String name = results.getString("NAME");
                int x = results.getInt("X");
                int y = results.getInt("Y");
                int z = results.getInt("Z");
                String world = results.getString("WORLD");
                double x2 = results.getDouble("X2");
                double y2 = results.getDouble("Y2");
                double z2 = results.getDouble("Z2");
                String world2 = results.getString("WORLD2");
                PortalSet portal = new PortalSet(name, x, y, z, world, x2, y2, z2, world2);
                portalSet.add(portal);
            }
            System.out.println("Portals loaded.");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Set<PortalSet> getPortalSet(){
        return portalSet;
    }

    public void portalFrom(String name, Location loc) {
        for (PortalSet portal : portalSet){
            if (portal.getName().equals(name)){
                portal.setX((int) loc.getX());
                portal.setY((int) loc.getY());
                portal.setZ((int) loc.getZ());
                portal.setWorld(loc.getWorld().getName());
                return;
            }
        }
        PortalSet portal = new PortalSet(name, (int) loc.getX(), (int) loc.getY(), (int) loc.getZ(), loc.getWorld().getName(), 0, 0, 0, "");
        portalSet.add(portal);
    }

    public boolean portalTo(String name, Location loc){
        for (PortalSet portal : portalSet){
            if (portal.getName().equals(name)){
                portal.setX2(loc.getX());
                portal.setY2(loc.getY());
                portal.setZ2(loc.getZ());
                portal.setWorld2(loc.getWorld().getName());
                return true;
            }
        }
        return false;
    }

    public boolean portalDel(String name){
        for (PortalSet portal : portalSet){
            if (portal.getName().equals(name)){
                portalSet.remove(portal);
                return true;
            }
        }
        return false;
    }

    public String portalList(){
        String portalList = "";
        int i = 0;
        for (PortalSet portal : portalSet){
            if (i == portalSet.size() - 1){
                portalList += portal.getName();
            }else{
                portalList += portal.getName() + ", ";
            }
            i++;
        }
        return portalList;
    }

}
