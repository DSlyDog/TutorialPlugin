package net.whispwriting.teleportplus.files;

import net.whispwriting.teleportplus.TeleportPlus;

public class DatabaseFile extends AbstractFile {

    public DatabaseFile(TeleportPlus pl){
        super(pl, "database_config.yml", "");
    }

    public void createConfig(){
        config.addDefault("remote database", false);
        config.addDefault("teleport delay", 0);
        config.addDefault("host", "localhost");
        config.addDefault("port", 3306);
        config.addDefault("database", "root");
        config.addDefault("username", "username");
        config.addDefault("password", "password");
        config.addDefault("table", "portals");

    }

}
