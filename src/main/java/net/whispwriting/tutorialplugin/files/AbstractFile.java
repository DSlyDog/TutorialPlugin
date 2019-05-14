package net.whispwriting.tutorialplugin.files;

import net.whispwriting.tutorialplugin.TutorialPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class AbstractFile {

    protected TutorialPlugin plugin;
    private File file;
    protected FileConfiguration config;

    public AbstractFile(TutorialPlugin pl, String filename){
        plugin = pl;
        file = new File(pl.getDataFolder(), filename);
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
