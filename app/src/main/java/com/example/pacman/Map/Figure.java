package com.example.pacman.Map;

public class Figure {
    //array, where 1 - wall, 0 - space
    private int[][] type = new int[2][3];

    Figure(int[][] type) {
        this.type = type;
    }

    public int[][] getType() {
        return type;
    }

}
