package net.whispwriting.teleportplus.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.HashMap;
import java.util.Map;

public class MakePortal {

    private Location start;
    private Material portalMaterial;
    private static int MAX_LENGTH = 20;
    private static int HALF_LENGTH = 10;
    private Map<Integer, Block> blocks;

    public MakePortal(Location start, Material portalMaterial){
        this.start = start;
        this.portalMaterial = portalMaterial;
    }

    public boolean buildPortal(){
        Location ground = new Location(start.getWorld(), start.getX(), start.getY()-1, start.getZ());
        Material groundType = ground.getBlock().getType();
        int currentDirection = 1;
        blocks = new HashMap<>();
        if (checkDirectionBase(1, 0)){

        }else{
            blocks = new HashMap<>();
        }
        if (checkDirectionBase(0, 1)){

        }
        return false;
    }

    private boolean checkDirectionBase(int inX, int inZ){
        for (int i=1; i<HALF_LENGTH; i++){
            Location loc = start.clone();
            loc.add(inX, 0, inZ);
            Block block = loc.getBlock();
            if (block.getType() != Material.AIR){
                blocks.put(i, block);
                return true;
            }
            loc.setY(loc.getY()-1);
            Block blockStored = loc.getBlock();
            blocks.put(i, blockStored);
        }
        return false;
    }

    private void checkRest(){

    }

}
