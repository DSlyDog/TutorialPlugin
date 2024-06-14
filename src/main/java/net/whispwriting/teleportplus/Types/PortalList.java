package net.whispwriting.teleportplus.Types;

import org.bukkit.Location;

public class PortalList {

    private Location loc;
    private String name;

    public PortalList(Location l, String n){
        loc = l;
        name = n;
    }

    public Location getLocation(){
        return loc;
    }

    public String getName(){
        return name;
    }

}
