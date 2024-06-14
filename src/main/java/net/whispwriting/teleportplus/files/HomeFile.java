package net.whispwriting.teleportplus.files;

import net.whispwriting.teleportplus.TeleportPlus;

public class HomeFile extends AbstractFile {

    public HomeFile(TeleportPlus pl, String filename){
        super(pl, filename+".yml", "/homes/");
    }

}
