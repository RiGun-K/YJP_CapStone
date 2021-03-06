package com.example.capstone.dto.storage;

public class Box {
    private int small;
    private int medium;
    private int large;

    public Box() {
    }

    public Box(int small, int medium, int large) {
        this.small = small;
        this.medium = medium;
        this.large = large;
    }

    public int getSmall() {
        return small;
    }

    public void setSmall(int small) {
        this.small = small;
    }

    public int getMedium() {
        return medium;
    }

    public void setMedium(int medium) {
        this.medium = medium;
    }

    public int getLarge() {
        return large;
    }

    public void setLarge(int large) {
        this.large = large;
    }

}
