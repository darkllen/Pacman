package Bonus;

import android.widget.ImageView;

public class Point {

    private int type;//1-regular point, 2-big point for eating monsters, 3-bonus (fruits), 0 - empty?
    private int score;

    public Point(int type){
        this.type=type;
        switch (type){
            case 1:
                score=10;
                break;
            case 2:
                score=50;
                break;
            case 3:
                score=50;
                break;
        }
    }

    public int getType() {
        return type;
    }

    public int getScore() {
        return score;
    }
}
