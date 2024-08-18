package org.vip.bookmyshow.models;

public enum Feature {
    TWO_D(1, "2D"),
    THREE_D(2, "3D"),
    DOLBY(3, "DO"),
    IMAX(4, "IM");

    private int id;
    private String shortName;

    Feature(int id, String shortName) {
        this.id = id;
        this.shortName = shortName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
