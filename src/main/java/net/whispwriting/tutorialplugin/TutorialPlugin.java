package net.whispwriting.tutorialplugin;

import net.whispwriting.tutorialplugin.commands.FlyCommand;
import net.whispwriting.tutorialplugin.commands.HealCommand;
import net.whispwriting.tutorialplugin.commands.HomeCommand;
import net.whispwriting.tutorialplugin.files.LocationsFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class TutorialPlugin extends JavaPlugin {

    public LocationsFile locations = new LocationsFile(this);

    @Override
    public void onEnable() {
        locations.get().options().copyDefaults(true);
        locations.save();
        this.getCommand("heal").setExecutor(new HealCommand());
        this.getCommand("fly").setExecutor(new FlyCommand());
        this.getCommand("sethome").setExecutor(new HomeCommand(locations));
        this.getCommand("home").setExecutor(new HomeCommand(locations));
        this.getCommand("homes").setExecutor(new HomeCommand(locations));
        this.getCommand("delhome").setExecutor(new HomeCommand(locations));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
