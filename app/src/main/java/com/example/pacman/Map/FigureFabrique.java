package com.example.pacman.Map;

import java.util.Random;

//fabrique for figure creation
public abstract class FigureFabrique {

    /**
     *
     * @param type name of figure
     * @return new Figure object
     */
    public static Figure getFigure(FigureNum type){
        switch (type){
            case FIGURE001010:return new Figure(new int[][]{{0,0},{1,0},{1,0}});
            case FIGURE001011:return new Figure(new int[][]{{0,0},{1,0},{1,1}});
            case FIGURE001100:return new Figure(new int[][]{{0,0},{1,1},{0,0}});
            case FIGURE001101:return new Figure(new int[][]{{0,0},{1,1},{0,1}});
            case FIGURE001110:return new Figure(new int[][]{{0,0},{1,1},{1,0}});
            case FIGURE011100:return new Figure(new int[][]{{0,1},{1,1},{0,0}});
        }
        return null;
    }

    /**
     *
     * @return random new Figure object
     */
    public static Figure getRandomFigure(){
        Random random=new Random();
        return getFigure(FigureNum.values()[random.nextInt(6)]);
    }
}
