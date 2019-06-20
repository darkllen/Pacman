package com.example.pacman.Map;

import android.support.constraint.ConstraintLayout;
import android.widget.ImageView;

import com.example.pacman.R;

import java.util.ArrayList;
import java.util.Random;

import Bonus.Point;

//class for map and actions with it
public class Map {

    private static int x = 4;
    private static int y = 9;
    private static int width = x * 3 + (x - 1) * 3 + 5;
    private static int height = y * 3 + 3;
    //карта на яку накладаються фігури. не поле для гри
    private static int[][] startMap = new int[y + 2][x + 2];
    //карта для гри
    //1-wall;0-empty;-1-tunnel; -2 - entarance for monster`s home
    private static int[][] map = new int[height][width];
    //map with images
    private static ImageView[][]imageMap=new ImageView[height][width];

    private static Point[][]bonus=new Point[height][width];

    private static int levelScore=0;
    private static int totalScore=0;

    private static ArrayList<Integer> oneTileFiguresXArray=new ArrayList<>();
    private static ArrayList<Integer> oneTileFiguresYArray=new ArrayList<>();


    static int figureNumber = 1;

    private static int bonusNumber;

    Map(int[][] map) {
        this.map = map;
    }

    /**
     * @return згенеровану карту
     */
    public static Map generateMap() {

          x = 4;
          y = 9;
          width = x * 3 + (x - 1) * 3 + 5;
          height = y * 3 + 3;
        //карта на яку накладаються фігури. не поле для гри
        startMap = new int[y + 2][x + 2];
        //карта для гри
        //1-wall;0-empty;-1-tunnel; -2 - entarance for monster`s home
         map = new int[height][width];
        //map with images
         imageMap=new ImageView[height][width];

         bonus=new Point[height][width];

         levelScore=0;
         totalScore=0;

         oneTileFiguresXArray=new ArrayList<>();
         oneTileFiguresYArray=new ArrayList<>();


        figureNumber = 1;

        height = y * 3 + 3;
        width = x * 3 + (x - 1) * 3 + 5;
        startMap = new int[y + 2][x + 2];
        map = new int[height][width];
        bonus=new Point[height][width];
        //System.out.println(y+" "+x+" "+width+" "+height);
        fillStartMap();

        figureNumber++;

        boolean startAgain = false;

        for (int j = 1; j < x + 1; j++)
            for (int i = 1; i < y + 1; i++) {
                Figure figure = FigureFabrique.getRandomFigure();
                if (startMap[i][j] == 0) {
                    startAgain = true;

                    if (oneTileFigure(i, j)||(i==6&&j==1)||(i==4&&j==3)) {
                        //add figure 1x1
                        startMap[i][j] = figureNumber;
                        figureNumber++;
                        putFigureOnMap(i - 1, j, new Figure(new int[][]{{0, 0}, {1, 0}, {0, 0}}));
                        oneTileFiguresXArray.add(new Integer( 2 + (i - 1) * 3));
                        oneTileFiguresYArray.add(new Integer((x - 1) * 3 + 3 + (j - 1) * 3 ));
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



        combineOneTileFigures();

        //todo combine the smallest figures?
        combineRandom();

        addTunnels();

        mirror();

      addPoints();

        return new Map(getRotateArr(map));
    }

    private static void combineRandom() {
        Random r=new Random();
        int j=r.nextInt(5)+3+(x+1)*3;
        int i=r.nextInt(height-4)+2;
        while(map[i][j]!=0){j=r.nextInt(5)+3+(x+1)*3;i=r.nextInt(height-4)+2;}//todo remove this while????
        combine(j,i,0);
    }

    /**
     * combine all one tile figures with other random figures
     */
    private static void combineOneTileFigures() {
        Random random=new Random();
        int r;
        for(int i=0;i<oneTileFiguresXArray.size();i++){
            ArrayList<Integer>numbers=new ArrayList<>();
            numbers.add(0);
            numbers.add(1);
            numbers.add(2);
            numbers.add(3);

            if(oneTileFiguresXArray.get(i)<=2)numbers.remove(3);else
            if((oneTileFiguresXArray.get(i)-2)/3+1!=0)if(startMap[(oneTileFiguresXArray.get(i)-2)/3+1-1][(oneTileFiguresYArray.get(i)-(2+3*(x-1)))/3+1]==1)numbers.remove(3);
            if(oneTileFiguresYArray.get(i)<=2+(x-1)*3)numbers.remove(2);else
                if((oneTileFiguresYArray.get(i)-(2+3*(x-1)))/3+1!=0)if(startMap[(oneTileFiguresXArray.get(i)-2)/3+1][(oneTileFiguresYArray.get(i)-(2+3*(x-1)))/3+1-1]==1)numbers.remove(2);
            if(oneTileFiguresXArray.get(i)>=height-4)numbers.remove(1);else
                if((oneTileFiguresXArray.get(i)-2)/3+1!=startMap.length)if(startMap[(oneTileFiguresXArray.get(i)-2)/3+1+1][(oneTileFiguresYArray.get(i)-(2+3*(x-1)))/3+1]==1)numbers.remove(1);
            if(oneTileFiguresYArray.get(i)>=width-5)numbers.remove(0);


            r=random.nextInt(numbers.size());
            r=numbers.get(r);


            switch (r){
                //right,down,left,up
                case 0:
                    combine(oneTileFiguresYArray.get(i)+2,oneTileFiguresXArray.get(i),0);
                    break;
                case 1:
                    combine(oneTileFiguresYArray.get(i),oneTileFiguresXArray.get(i)+2,0);
                    break;
                case 2:
                    combine(oneTileFiguresYArray.get(i)-1,oneTileFiguresXArray.get(i),0);
                    break;
                case 3:
                    combine(oneTileFiguresYArray.get(i),oneTileFiguresXArray.get(i)-1,0);
                    break;
            }
        }
    }

    private static void combine(int j, int i,int first) {
        //map[i][j]=1;
        if(map[i][j]!=1)
        if(map[i-1][j]+map[i][j-1]+map[i+1][j]+map[i][j+1]-first>=2) {map[i][j]=1;combine(j,i-1,1);combine(j,i+1,1);combine(j-1,i,1);combine(j+1,i,1);}
    }

    /**
     * додає бонуси на карту
     */
    private static void addPoints() {
        bonusNumber=0;
        //all map
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                if(map[i][j]==0&&(j>1&&j<width-2)) {bonus[i][j]=new Point(j,i,1);bonusNumber++;}
                else bonus[i][j]=new Point(j,i,0);
        //home for monsters
        for (int i = 10; i < 17; i++)
            for (int j = 11-3; j < 21-3; j++){
                if(bonus[i][j].getType()!=0)   bonusNumber--;
                bonus[i][j]=new Point(j,i,0);}

            //add 4 invisible bonus
            bonus[4][2]=new Point(2,4,2);
            bonus[4][width-3]=new Point(width-3,4,2);
            bonus[height-4][2]=new Point(2,height-4,2);
            bonus[height-4][width-3]=new Point(width-3,height-4,2);

            bonus=getRotateArrBonus(bonus);

    }

    //todo write 1 method for different types
    /**
     *
     * @param array вхідний масив
     * @return масив, повернутий на 90 градусів праворуч
     */
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
     *
     * @param array вхідний масив
     * @return масив, повернутий на 90 градусів праворуч
     */
    public static Point[][] getRotateArrBonus(Point[][] array) {
        Point[][] resultArray = new Point[array[0].length][array.length];
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
        while (true){
            if(map[k][width-4]==0)
                k=random.nextInt(18)+7;
            else break;
        }
        map[k][width-2]=-1;
        map[k][width-1]=-1;
        map[k-1][width-1]=1;
        map[k+1][width-1]=1;
        if(random.nextInt(10)>=6){
            int kk=random.nextInt(18)+7;
            while (Math.abs(kk-k)<4) {
                while (true){
                    if(map[kk][width-4]==0)
                        kk=random.nextInt(18)+7;
                    else break;
                }kk=random.nextInt(18)+7;}
            map[kk][width-2]=-1;
            map[kk][width-1]=-1;
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
            for (int j = 0; j < width; j++)
                map[i][j] = 0;
        for (int i = 0; i < height; i++)
            for (int j = 1; j < width-1; j++)
                map[i][j] = 1;
        for (int i = 1; i < height - 1; i++)
            for (int j = 2; j < width - 2; j++)
                map[i][j] = 0;
        //home for monsters
        for (int i = 11; i < 16; i++)
            for (int j = 12-3; j < 20-3; j++)
                map[i][j] = 1;
        for (int i = 12; i < 15; i++)
            for (int j = 13-3; j < 19-3; j++)
                map[i][j] = 0;
        map[11][15-3] = -2;
        map[11][16-3] = -2;
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

    public static int getLevelScore() {
        return levelScore;
    }

    public static void setLevelScore(int score1) {
        levelScore = score1;
    }

    public static void setTotalScore(int totalScore) {
        Map.totalScore = totalScore;
    }

    public static int getTotalScore() {
        return totalScore;
    }

    public static Point[][] getBonus() {
        return bonus;
    }

    public static void setOneBonus(int x,int y,int type){
        bonus[x][y]=new Point(x,y,type);
    }

    public static int getBonusNumber() {
        return bonusNumber;
    }

    public static void setBonusNumber(int bonusNumber) {
        Map.bonusNumber = bonusNumber;
    }
}
