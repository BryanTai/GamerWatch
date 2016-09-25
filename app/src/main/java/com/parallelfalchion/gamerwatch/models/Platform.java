package com.parallelfalchion.gamerwatch.models;

public enum Platform {
    PS4("PS4"),
    XBoxOne("XBoxOne"),
    PC("PC"),
    THREEDS("THREEDS"),
    XBox360("XBox360"),
    Wii("Wii"),
    WiiU("WiiU"),
    DS("DS"),
    PS3("PS3");

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
