package com.example.pacman.Map;

import android.support.constraint.ConstraintLayout;
import android.widget.ImageView;

import com.example.pacman.R;

import java.util.Random;

//class for map and actions with it
public class Map {

    private static int x = 5;
    private static int y = 9;
    private static int width = x * 3 + (x - 1) * 3 + 5;
    private static int height = y * 3 + 3;
    //карта на яку накладаються фігури. не поле для гри
    private static int[][] startMap = new int[y + 2][x + 2];
    //карта для гри
    private static int[][] map = new int[height][width];
    //map with images
    private static ImageView[][]imageMap=new ImageView[height][width];


    static int figureNumber = 1;

    Map(int[][] map) {
        this.map = map;
    }

    /**
     * @return згенеровану карту
     */
    public static Map generateMap() {
        fillStartMap();

        figureNumber++;

        boolean startAgain = false;

        for (int j = 1; j < x + 1; j++)
            for (int i = 1; i < y + 1; i++) {
                Figure figure = FigureFabrique.getRandomFigure();
                if (startMap[i][j] == 0) {
                    startAgain = true;

                    if (oneTileFigure(i, j)) {
                        //add figure 1x1
                        startMap[i][j] = figureNumber;
                        figureNumber++;
                        putFigureOnMap(i - 1, j, new Figure(new int[][]{{0, 0}, {1, 0}, {0, 0}}));
                        i = 1;
                        j = 1;
                    } else

                    if (addFigure(figure, i, j)) {
                        //add figure
                        for (int i1 = 0; i1 < 3; i1++)
                            for (int j1 = 0; j1 < 2; j1++)
                                if (figure.getType()[i1][j1] == 1)
                                    startMap[i1 + i - 1][j1 + j] = figureNumber;
                        figureNumber++;
                        putFigureOnMap(i - 1, j, figure);
                        i = 1;
                        j = 1;
                    }

                }

                if (i == y && j == x && startAgain) {
                    startAgain = false;
                    i = 1;
                    j = 1;
                }
            }

        //баг, який трапляється раз на півроку
        if (startMap[1][1]==0)
            //add figure 1x1
            startMap[1][1] = figureNumber;
        figureNumber++;
        putFigureOnMap(0, 1, new Figure(new int[][]{{0, 0}, {1, 0}, {0, 0}}));

        //todo об'єднати деякі фігури

        addTunnels();

        mirror();

      // addImages();

        return new Map(getRotateArr(map));
    }


    public static int[][] getRotateArr(int[][] array) {
        int[][] resultArray = new int[array[0].length][array.length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                resultArray[array[i].length - j - 1][i] = array[i][j];
            }
        }
        return resultArray;
    }



    /**
     * add 1 or 2 tunnels
     */
    private static void addTunnels() {
        Random random=new Random();
        int k=random.nextInt(18)+7;//todo change bound depending on height
        System.out.println(k);
        map[k][width-2]=0;
        map[k][width-1]=0;
        map[k-1][width-1]=1;
        map[k+1][width-1]=1;
        if(random.nextInt(10)>=6){
            int kk=random.nextInt(18)+7;
            while (Math.abs(kk-k)<4) kk=random.nextInt(18)+7;
            map[kk][width-2]=0;
            map[kk][width-1]=0;
            map[kk-1][width-1]=1;
            map[kk+1][width-1]=1;
        }

    }

    /**
     * mirrror map from right to left
     */
    private static void mirror() {
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width / 2; j++)
                map[i][j] = map[i][width - j - 1];
    }

    /**
     * заповнює початкові границі
     */
    private static void fillStartMap() {
        //границі карти
        for (int i = 0; i < y + 2; i++)
            for (int j = 0; j < x + 2; j++)
                startMap[i][j] = 1;
        for (int i = 1; i < y + 1; i++)
            for (int j = 1; j < x + 1; j++)
                startMap[i][j] = 0;
        //home for monsters
        startMap[4][2] = 1;
        startMap[5][2] = 1;
        startMap[4][1] = 1;
        startMap[5][1] = 1;

        //границі карти для гри
        for (int i = 0; i < height; i++)
            for (int j = 1; j < width-1; j++)
                map[i][j] = 1;
        for (int i = 1; i < height - 1; i++)
            for (int j = 2; j < width - 2; j++)
                map[i][j] = 0;
        //home for monsters
        for (int i = 11; i < 16; i++)
            for (int j = 12; j < 20; j++)
                map[i][j] = 1;
        for (int i = 12; i < 15; i++)
            for (int j = 13; j < 19; j++)
                map[i][j] = 0;
        map[11][15] = 0;
        map[11][16] = 0;

    }

    /**
     * малює фігуру на мапі
     *
     * @param i      координати верхнього лівого кутка
     * @param j
     * @param figure фігура
     */
    private static void putFigureOnMap(int i, int j, Figure figure) {
        int x1;
        int y1;

        for (int k = 0; k < 3; k++)
            for (int l = 0; l < 2; l++) {
                //координата лівої верхньої точки клітинки 3х3
                y1 = (x - 1) * 3 + 3 + (j - 1) * 3 + l * 3;
                x1 = 2 + (i - 1) * 3 + k * 3;

                if (y1 > 0 && x1 > 0)
                    //малюємо клітинку фігури
                    if (figure.getType()[k][l] == 1) {
                        map[x1][y1] = 1;
                        map[x1 + 1][y1] = 1;
                        map[x1][y1 + 1] = 1;
                        map[x1 + 1][y1 + 1] = 1;

                        if (l != 1) if (figure.getType()[k][l + 1] == 1) {
                            map[x1][y1 + 2] = 1;
                            map[x1 + 1][y1 + 2] = 1;
                        }
                        if (k != 2) if (figure.getType()[k + 1][l] == 1) {
                            map[x1 + 2][y1] = 1;
                            map[x1 + 2][y1 + 1] = 1;
                        }
                    }
            }
    }


    /**
     * @param figure фігура яка накладається
     * @param x1     куди накладеться 2 рядок фігури
     * @param y1     куди накладеться 1 стовпчик фігури
     * @return чи можна накласти фігуру
     */
    private static boolean addFigure(Figure figure, int x1, int y1) {
        for (int i = x1 - 1; i < x1 + 2; i++)
            for (int j = y1; j < y1 + 2; j++)
                if (startMap[i][j] > 0 && figure.getType()[i - (x1 - 1)][j - y1] > 0) return false;
        return true;
    }

    /**
     * @param i координати клітинки
     * @param j
     * @return чи є ця клітинка одинарною фігурою
     */
    private static boolean oneTileFigure(int i, int j) {
        if (startMap[i - 1][j] == 0) return false;
        if (startMap[i + 1][j] == 0) return false;
        if (startMap[i][j - 1] == 0) return false;
        if (startMap[i][j + 1] == 0) return false;
        return true;
    }

    public int[][] getMap() {
        return map;
    }

    public int[][] getStartMap() {
        return startMap;
    }

    public ImageView[][] getImageMap() {
        return imageMap;
    }
}
