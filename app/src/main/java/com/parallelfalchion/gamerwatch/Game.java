package com.parallelfalchion.gamerwatch;


import java.io.File;
import java.util.TreeMap;

public class Game {
    String _title;
    long _regularPrice;
    String _cover;
    Platform _platform;
    TreeMap<Vendor, Long> _deals;

    public Game(String title, long regularPrice, String cover, Platform platform) {
        _title = title;
        _regularPrice = regularPrice;
        _cover = cover;
        _platform = platform;
    }

    /*public void setDeals(TreeMap<Vendor, Long> deals) {
        _deals = deals;
    }

    public TreeMap<Vendor, Long> getDeals() {
        return _deals;
    }*/
}
