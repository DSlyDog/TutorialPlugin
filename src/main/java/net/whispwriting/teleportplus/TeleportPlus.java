package net.whispwriting.teleportplus;

import net.whispwriting.teleportplus.commands.*;
import net.whispwriting.teleportplus.events.FirstJoinEvent;
import net.whispwriting.teleportplus.events.PortalTask;
import net.whispwriting.teleportplus.events.RespawnEvent;
import net.whispwriting.teleportplus.files.DatabaseFile;
import net.whispwriting.teleportplus.files.PortalFile;
import net.whispwriting.teleportplus.files.SpawnFile;
import net.whispwriting.teleportplus.sql.MySQLUtil;
import net.whispwriting.teleportplus.sql.PortalSet;
import net.whispwriting.teleportplus.sql.SQLUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.sql.*;
import java.util.Set;

public final class TeleportPlus extends JavaPlugin {

    public BukkitTask task;
    private Connection connection;
    public String host, database, username, password, table;
    public int port;
    public DatabaseFile dbfile = new DatabaseFile(this);
    public PortalFile localDBFile = new PortalFile(this);
    public boolean enabled = dbfile.get().getBoolean("remote database");
    public SQLUtil sqlUtil = new SQLUtil(this);
    public SpawnFile spawn = new SpawnFile(this);

    public void sqliteSetup() {
        table = dbfile.get().getString("table");
        try {
            synchronized (this) {
                if (getConnection() != null && !getConnection().isClosed()) {
                    return;
                }
                System.out.println("setting connection.");
                Class.forName("org.sqlite.JDBC");
                setConnection(DriverManager.getConnection("jdbc:sqlite:" + localDBFile.get()));
                System.out.println("connection was successful.");
            }
            if (!tableExists(table)) {
                createTable(table);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public boolean tableExists(String name){
        try {
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, table, null);
            if (tables.next()){
                return true;
            }else{
                return false;
            }
        }catch(SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public void createTable(String name){
        try {
            PreparedStatement statement = getConnection().prepareStatement("CREATE TABLE " + name + " (NAME TEXT NOT NULL, X INT NOT NULL, Y INT NOT NULL, Z INT NOT NULL, X2 DOUBLE NOT NULL, Y2 DOUBLE NOT NULL, Z2 DOUBLE NOT NULL, WORLD TEXT NOT NULL, WORLD2 TEXT NOT NULL)");
            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void mySqlSetup(){
        host = dbfile.get().getString("host");
        port = dbfile.get().getInt("port");
        database = dbfile.get().getString("database");
        password = dbfile.get().getString("password");
        username = dbfile.get().getString("username");
        table = dbfile.get().getString("table");

        try{
            synchronized (this){
                if (getConnection() != null && !getConnection().isClosed()){
                    return;
                }
                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port
                        + "/" + this.database, this.username, this.password));
            }
            if(!tableExists(dbfile.get().getString("table"))){
                createTable(dbfile.get().getString("table"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException f){
            f.printStackTrace();
        }
    }

    public Connection getConnection(){
        return connection;
    }
    public void setConnection(Connection c){
        connection = c;
    }

    @Override
    public void onEnable() {
        dbfile.createConfig();
        dbfile.get().options().copyDefaults(true);
        dbfile.save();

        this.getCommand("sethome").setExecutor(new HomeCommand(this));
        this.getCommand("home").setExecutor(new HomeCommand(this));
        this.getCommand("homes").setExecutor(new HomeCommand(this));
        this.getCommand("delhome").setExecutor(new HomeCommand(this));
        this.getCommand("setwarp").setExecutor(new WarpCommand(this));
        this.getCommand("warp").setExecutor(new WarpCommand(this));
        this.getCommand("warps").setExecutor(new WarpCommand(this));
        this.getCommand("delwarp").setExecutor(new WarpCommand(this));
        this.getCommand("portal").setExecutor(new PortalCommand(this));
        this.getCommand("spawn").setExecutor(new SpawnCommand(this));
        this.getCommand("setspawn").setExecutor(new SpawnCommand(this));

        System.out.println("config and commands initialized.");

        if (enabled) {
            System.out.println("Using remote database.");
            mySqlSetup();
            System.out.println("Database setup complete, and connection successful.");
            sqlUtil.loadSet();
        }else{
            System.out.println("Using local database.");
            sqliteSetup();
            System.out.println("Database setup complete, and connection successful.");
            sqlUtil.loadSet();
            try {
                connection.close();
            }catch(SQLException e){
                // do nothing
            }
        }

        task = new PortalTask(this).runTaskTimer(this, 0, dbfile.get().getInt("teleport delay")*20);
        Bukkit.getServer().getPluginManager().registerEvents(new FirstJoinEvent(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new RespawnEvent(this), this);
    }

    @Override
    public void onDisable() {
        Set<PortalSet> portalSet = sqlUtil.getPortalSet();
        System.out.println("Portals to save: " + portalSet.size());
        for (PortalSet portal : portalSet){
            try {
                getConnection().close();
            } catch (SQLException e) {
                // do nothing
            }
            try {
                System.out.println(enabled);
                if (enabled) {
                    synchronized (this) {
                        System.out.println("Starting synchronized task.");
                        Class.forName("com.mysql.jdbc.Driver");
                        setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port
                                + "/" + this.database, this.username, this.password));
                    }
                }else{
                    System.out.println("opening connection for local SQLite");
                    Class.forName("org.sqlite.JDBC");
                    setConnection(DriverManager.getConnection("jdbc:sqlite:" + localDBFile.get()));
                }
            }catch(Exception e){
                System.out.println("Opening connection failed.");
                e.printStackTrace();
            }
            try {
                if (!portalExists(portal.getName())) {
                    System.out.println("Portal does not exist in database");
                    PreparedStatement insert = getConnection().prepareStatement("INSERT INTO " + table + " (NAME, X, Y, Z, X2, Y2, Z2, WORLD, WORLD2) VALUES (?,?,?,?,?,?,?,?,?)");
                    insert.setString(1, portal.getName());
                    insert.setInt(2, portal.getX());
                    insert.setInt(3, portal.getY());
                    insert.setInt(4, portal.getZ());
                    insert.setDouble(5, portal.getX2());
                    insert.setDouble(6, portal.getY2());
                    insert.setDouble(7, portal.getZ2());
                    insert.setString(8, portal.getWorld());
                    insert.setString(9, portal.getWorld2());
                    insert.executeUpdate();
                }else{
                    System.out.println("Portal does exist in database");
                    updateHelperInt(portal.getX(), portal.getName(), "X");
                    updateHelperInt(portal.getY(), portal.getName(), "Y");
                    updateHelperInt(portal.getZ(), portal.getName(), "Z");
                    updateHelperStr(portal.getWorld(), portal.getName(), "WORLD");
                    updateHelperDouble(portal.getX2(), portal.getName(), "X2");
                    updateHelperDouble(portal.getY2(), portal.getName(), "Y2");
                    updateHelperDouble(portal.getZ2(), portal.getName(), "Z2");
                    updateHelperStr(portal.getWorld2(), portal.getName(), "WORLD2");
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        try {
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public boolean portalExists(String name){
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM " + table + " WHERE NAME=?");
            statement.setString(1, name);
            ResultSet results = statement.executeQuery();
            if (results.next()){
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void updateHelperInt(int val, String name, String field){
        try{
            PreparedStatement insert = getConnection().prepareStatement("UPDATE " + table + " SET "+field+"=? WHERE NAME=?");
            insert.setInt(1, val);
            insert.setString(2, name);
            insert.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void updateHelperDouble(double val, String name, String field){
        try{
            PreparedStatement insert = getConnection().prepareStatement("UPDATE " + table + " SET "+field+"=? WHERE NAME=?");
            insert.setDouble(1, val);
            insert.setString(2, name);
            insert.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void updateHelperStr(String val, String name, String field){
        try{
            PreparedStatement insert = getConnection().prepareStatement("UPDATE " + table + " SET "+field+"=? WHERE NAME=?");
            insert.setString(1, val);
            insert.setString(2, name);
            insert.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
