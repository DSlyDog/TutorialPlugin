package net.whispwriting.teleportplus.sql;

import net.whispwriting.teleportplus.TeleportPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLUtil {

    private static TeleportPlus plugin = TeleportPlus.getPlugin(TeleportPlus.class);

    public static boolean portalExists(String name){
        try {
            if (!plugin.enabled){
                plugin.sqliteSetup();
            }
            PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.table + " WHERE NAME=?");
            statement.setString(1, name);
            ResultSet results = statement.executeQuery();
            if (results.next()){
                if (!plugin.enabled){
                    plugin.getConnection().close();
                }
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static void createPortal(String name, int x, int y, int z, String world, double x2, double y2, double z2, String world2){
        try{
            if (!plugin.enabled){
                plugin.sqliteSetup();
            }
            PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.table + " WHERE NAME=?");
            statement.setString(1, name);
            ResultSet results = statement.executeQuery();

            results.next();
            if (!portalExists(name)){
                if (!plugin.enabled){
                    plugin.sqliteSetup();
                }
                PreparedStatement insert = plugin.getConnection().prepareStatement("INSERT INTO " + plugin.table + " (NAME, X, Y, Z, X2, Y2, Z2, WORLD, WORLD2) VALUES (?,?,?,?,?,?,?,?,?)");
                insert.setString(1, name);
                insert.setInt(2, x);
                insert.setInt(3, y);
                insert.setInt(4, z);
                insert.setDouble(5, x2);
                insert.setDouble(6, y2);
                insert.setDouble(7, z2);
                insert.setString(8, world);
                insert.setString(9, world2);
                insert.executeUpdate();
            }
            if (!plugin.enabled){
                plugin.getConnection().close();
            }

        }catch(SQLException e){
            e.printStackTrace();
            if (!plugin.enabled){
                try {
                    plugin.getConnection().close();
                }catch(SQLException f){
                    f.printStackTrace();
                }
            }
        }
    }

    public static void createPortal2(String name, double x2, double y2, double z2, String world2){
        try{
            if (!plugin.enabled){
                plugin.sqliteSetup();
            }
            PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.table + " WHERE NAME=?");
            statement.setString(1, name);
            ResultSet results = statement.executeQuery();
            results.next();
            if (portalExists(name)){
                updateHelperDouble(x2, name, "X2");
                updateHelperDouble(y2, name, "Y2");
                updateHelperDouble(z2, name, "Z2");
                updateHelperStr(world2, name, "WORLD2");
            }
            if (!plugin.enabled){
                plugin.getConnection().close();
            }

        }catch(SQLException e){
            e.printStackTrace();
            try {
                if (!plugin.enabled) {
                    plugin.getConnection().close();
                }
            }catch(SQLException f){
                f.printStackTrace();
            }
        }
    }

    public static void updateHelperInt(int val, String name, String field){
        try{
            if (!plugin.enabled){
                plugin.sqliteSetup();
            }
            PreparedStatement insert = plugin.getConnection().prepareStatement("UPDATE " + plugin.table + " SET "+field+"=? WHERE NAME=?");
            insert.setInt(1, val);
            insert.setString(2, name);
            insert.executeUpdate();
            if (!plugin.enabled){
                plugin.getConnection().close();
            }
        }catch(SQLException e){
            e.printStackTrace();
            try {
                if (!plugin.enabled) {
                    plugin.getConnection().close();
                }
            }catch(SQLException f){
                f.printStackTrace();
            }
        }
    }

    public static void updateHelperDouble(double val, String name, String field){
        try{
            if (!plugin.enabled){
                plugin.sqliteSetup();
            }
            PreparedStatement insert = plugin.getConnection().prepareStatement("UPDATE " + plugin.table + " SET "+field+"=? WHERE NAME=?");
            insert.setDouble(1, val);
            insert.setString(2, name);
            insert.executeUpdate();
            if (!plugin.enabled){
                plugin.getConnection().close();
            }
        }catch(SQLException e){
            e.printStackTrace();
            try {
                if (!plugin.enabled) {
                    plugin.getConnection().close();
                }
            }catch(SQLException f){
                f.printStackTrace();
            }
        }
    }

    public static void updateHelperStr(String val, String name, String field){
        try{
            if (!plugin.enabled){
                plugin.sqliteSetup();
            }
            PreparedStatement insert = plugin.getConnection().prepareStatement("UPDATE " + plugin.table + " SET "+field+"=? WHERE NAME=?");
            insert.setString(1, val);
            insert.setString(2, name);
            insert.executeUpdate();
            if (!plugin.enabled){
                plugin.getConnection().close();
            }
        }catch(SQLException e){
            e.printStackTrace();
            try {
                if (!plugin.enabled) {
                    plugin.getConnection().close();
                }
            }catch(SQLException f){
                f.printStackTrace();
            }
        }
    }

    public static void updateLocation(String name, int x, int y, int z, String world){
        updateHelperInt(x, name, "X");
        updateHelperInt(y, name, "Y");
        updateHelperInt(z, name, "Z");
        updateHelperStr(world, name, "WORLD");
    }

    public static void updateLocation2(String name, int x2, int y2, int z2, String world2){
        updateHelperInt(x2, name, "X2");
        updateHelperInt(y2, name, "Y2");
        updateHelperInt(z2, name, "Z2");
        updateHelperStr(world2, name, "WORLD2");
    }

    public static Location getLocation(String name){
        try {
            if (!plugin.enabled){
                plugin.sqliteSetup();
            }
            PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.table + " WHERE NAME=?");
            statement.setString(1, name);
            ResultSet results = statement.executeQuery();
            results.next();
            int x = results.getInt("X");
            int y = results.getInt("Y");
            int z = results.getInt("Z");
            String worldName = results.getString("WORLD");
            World world = Bukkit.getWorld(worldName);
            Location loc = new Location(world, x, y, z);
            if (!plugin.enabled){
                plugin.getConnection().close();
            }
            return loc;
        }catch (SQLException e){
            e.printStackTrace();
            try {
                if (!plugin.enabled) {
                    plugin.getConnection().close();
                }
            }catch(SQLException f){
                f.printStackTrace();
            }
        }
        return null;
    }

    public static Location getLocation2(String name){
        try {
            if (!plugin.enabled){
                plugin.sqliteSetup();
            }
            PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.table + " WHERE NAME=?");
            statement.setString(1, name);
            ResultSet results = statement.executeQuery();
            results.next();
            double x2 = results.getDouble("X2");
            double y2 = results.getDouble("Y2");
            double z2 = results.getDouble("Z2");
            String worldName2 = results.getString("WORLD2");
            World world2 = Bukkit.getWorld(worldName2);
            Location loc = new Location(world2, x2, y2, z2);
            if (!plugin.enabled){
                plugin.getConnection().close();
            }
            return loc;
        }catch (SQLException e){
            e.printStackTrace();
            try {
                if (!plugin.enabled) {
                    plugin.getConnection().close();
                }
            }catch(SQLException f){
                f.printStackTrace();
            }
        }
        return null;
    }

    public static String getPortalName(int x, int y, int z, String world){
        try {
            if (!plugin.enabled){
                plugin.sqliteSetup();
            }
            PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.table + " WHERE X=? AND Y=? AND Z=? AND WORLD=? ");
            statement.setInt(1, x);
            statement.setInt(2, y);
            statement.setInt(3, z);
            statement.setString(4, world);
            ResultSet results = statement.executeQuery();

            if (results.next()) {
                String name = results.getString("NAME");
                return name;
            }
            if (!plugin.enabled){
                plugin.getConnection().close();
            }
            return "NULL";
        }catch (SQLException e){
            e.printStackTrace();
            try {
                if (!plugin.enabled) {
                    plugin.getConnection().close();
                }
            }catch(SQLException f){
                f.printStackTrace();
            }
            return "NULL";
        }
    }

    public static void deletePortal(String name){
        try{
            if (!plugin.enabled){
                plugin.sqliteSetup();
            }else{
                try {
                    synchronized (plugin) {
                        Class.forName("com.mysql.jdbc.Driver");
                        plugin.setConnection(DriverManager.getConnection("jdbc:mysql://" + plugin.host + ":" + plugin.port
                                + "/" + plugin.database, plugin.username, plugin.password));
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            PreparedStatement statement = plugin.getConnection().prepareStatement("DELETE FROM " + plugin.table + " WHERE NAME=?");
            statement.setString(1, name);
            statement.executeUpdate();
            if (!plugin.enabled){
                plugin.getConnection().close();
            }
        }catch(SQLException e){
            e.printStackTrace();
            try {
                if (!plugin.enabled) {
                    plugin.getConnection().close();
                }
            }catch(SQLException f){
                f.printStackTrace();
            }
        }
    }

    public static String buildString(){
        try {
            if (!plugin.enabled){
                plugin.sqliteSetup();
            }
            PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.table);
            ResultSet results = statement.executeQuery();
            String endString = "";
            while (results.next()){
                endString += results.getString("NAME") + " ";
            }
            if (!plugin.enabled){
                plugin.getConnection().close();
            }
            return endString;
        }catch(SQLException e){
            e.printStackTrace();
            try {
                if (!plugin.enabled) {
                    plugin.getConnection().close();
                }
            }catch(SQLException f){
                f.printStackTrace();
            }
            return "Could not list portals.";
        }
    }
}
