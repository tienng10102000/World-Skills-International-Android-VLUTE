package com.example.module1.Class;

public class AssetGroup {
    long ID;
    String Name;

    public AssetGroup() {
    }

    public AssetGroup(long ID, String name) {
        this.ID = ID;
        Name = name;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
