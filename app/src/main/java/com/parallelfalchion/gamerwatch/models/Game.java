package com.parallelfalchion.gamerwatch.models;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Game implements Serializable{
    String _title;
    Double _price;
    //Map<String, Double> _price;
    String _cover;
    Platform _platform;
    TreeMap<Vendor, Long> _deals;

    public Game(String title, Double price, /*Map<String, Double> price,*/ String cover, Platform platform) {
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

    public Double getPrice(){
        return this._price;
    }

//    public Map<String, Double> getPrice(){
//        return this._price;
//    }

    public String getPlatform(){
        return this._platform.name();
    }

    public String getCover() { return this._cover; }
}
