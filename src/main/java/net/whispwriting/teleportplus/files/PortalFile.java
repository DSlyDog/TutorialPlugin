package net.whispwriting.teleportplus.files;

import net.whispwriting.teleportplus.TeleportPlus;

public class PortalFile extends AbstractFileSQL{

    public PortalFile(TeleportPlus pl){
        super(pl, "portals.db", "");
    }

}
