package net.whispwriting.tutorialplugin.commands.helpers;

import org.bukkit.Location;

public class LocationList {

    private String name;
    private Location loc;

    public LocationList(String n, Location l){
        name = n;
        loc = l;
    }

    public void setLocation(Location l){
        loc = l;
    }

    public void setName(String n){
        name = n;
    }

    public Location getLocation(){
        return loc;
    }

    public String getName(){
        return name;
    }

}
