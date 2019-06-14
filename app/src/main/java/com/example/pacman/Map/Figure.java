package com.example.pacman.Map;

//Figure object class
public class Figure {
    //array, where 1 - wall, 0 - space
    private int[][] type = new int[3][2];

    Figure(int[][] type) {
        this.type = type;
    }

    public int[][] getType() {
        return type;
    }

}
