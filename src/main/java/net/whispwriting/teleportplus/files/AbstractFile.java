package net.whispwriting.teleportplus.files;

import net.whispwriting.teleportplus.TeleportPlus;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class AbstractFile {

    protected TeleportPlus plugin;
    private File file;
    protected FileConfiguration config;

    public AbstractFile(TeleportPlus pl, String filename, String d){
        plugin = pl;
        File dir = new File(plugin.getDataFolder() + d);
        if (!dir.exists()){
            dir.mkdir();
        }
        file = new File(dir, filename);
        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void save(){
        try{
            config.save(file);
        }catch (IOException e){
            System.out.println("Could not save file.");
        }
    }

    public FileConfiguration get(){
        return config;
    }

    public void reload(){
        config = YamlConfiguration.loadConfiguration(file);
    }

}
