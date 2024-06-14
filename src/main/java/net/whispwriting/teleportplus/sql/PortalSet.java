package net.whispwriting.teleportplus.sql;

public class PortalSet {

    private String name;
    private int x;
    private int y;
    private int z;
    private String world;
    private double x2;
    private double y2;
    private double z2;
    private String world2;

    public PortalSet(String n, int xa, int ya, int za, String w, double xb, double yb, double zb, String w2){
        name = n;
        x = xa;
        y = ya;
        z = za;
        world = w;
        x2 = xb;
        y2 = yb;
        z2 = zb;
        world2 = w2;
    }

    public String getName(){
        return name;
    }

    public void setName(String n){
        name = n;
    }

    public int getX(){
        return x;
    }

    public void setX(int val){
        x = val;
    }

    public int getY(){
        return y;
    }

    public void setY(int val){
        y = val;
    }

    public int getZ(){
        return z;
    }

    public void setZ(int val){
        z = val;
    }

    public String getWorld(){
        return world;
    }

    public void setWorld(String n){
        world = n;
    }

    public double getX2(){
        return x2;
    }

    public void setX2(double val){
        x2 = val;
    }

    public double getY2(){
        return y2;
    }

    public void setY2(double val){
        y2 = val;
    }

    public double getZ2(){
        return z2;
    }

    public void setZ2(double val){
        z2 = val;
    }

    public String getWorld2(){
        return world2;
    }

    public void setWorld2(String n){
        world2 = n;
    }

}
