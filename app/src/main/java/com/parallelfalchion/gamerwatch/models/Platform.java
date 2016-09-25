package com.parallelfalchion.gamerwatch.models;

public enum Platform {
    PS4("PS4"),
    XBOXONE("XBOXONE"),
    PC("PC"),
    THREEDS("THREEDS");

    private final String name;

    private Platform(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
