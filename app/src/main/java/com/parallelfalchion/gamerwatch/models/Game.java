package com.parallelfalchion.gamerwatch.models;


import java.io.File;
import java.io.Serializable;
import java.util.TreeMap;

public class Game implements Serializable{
    String _title;
    long _price;
    String _cover;
    Platform _platform;
    TreeMap<Vendor, Long> _deals;

    public Game(String title, long price, String cover, Platform platform) {
        _title = title;
        _price = price;
        _cover = cover;
        _platform = platform;
    }

    public void setDeals(TreeMap<Vendor, Long> deals) {
        _deals = deals;
    }

    public TreeMap<Vendor, Long> getDeals() {
        return _deals;
    }

    public String getTitle(){
        return this._title;
    }

    public Long getPrice(){
        return this._price;
    }

    public String getPlatform(){
        return this._platform.name();
    }
}
