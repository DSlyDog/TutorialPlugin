package net.whispwriting.teleportplus.files;

import net.whispwriting.teleportplus.TeleportPlus;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class AbstractFileSQL {

    protected TeleportPlus plugin;
    private File file;
    protected FileConfiguration config;

    public AbstractFileSQL(TeleportPlus pl, String filename, String dir){
        plugin = pl;
        file = new File(pl.getDataFolder() + dir, filename);
        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void save(){
        try{
            config.save(file);
        }catch (IOException e){
            System.out.println("Could not save file.");
        }
    }

    public File get(){
        return file;
    }

}
