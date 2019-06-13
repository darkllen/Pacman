package Units;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.pacman.Map.Map;
import com.example.pacman.R;

import Menu.GameUsualActivity;

public class Pacman extends Thread {
    //constants, shouldnt be changed in running time
    private ImageView imageView;
    //unnecessary value
    private final ObjectAnimator[] animator = {ObjectAnimator.ofFloat(imageView, "X",1000)};
    private final AnimatorSet[] set = {new AnimatorSet()};
    private int startX;
    private int startY;

    //variables to control area of movement and speed
    private int leftDestination = 0;
    private int rightDestination = 1000;
    private int upDestination = 0;
    private int bottomDestination = 2000;
    private int speedInPixelsForSecond = 300;

    int[][] map;

    //position in map
    int xMap = 16;
    int yMap = 28;

    //values in numbers are tested and not accurate(

    GameUsualActivity gUA;
    ConstraintLayout layout;

    public Pacman(ImageView imageView, int[][] map,GameUsualActivity gUA,ConstraintLayout layout) {
        this.imageView = imageView;
        this.map = map;
        this.gUA=gUA;
        this.layout=layout;
        startX = 1080/31*xMap;
        startY = 1080/31*yMap;
        map[xMap][yMap] = 2;
        imageView.setX(startX);
        imageView.setY(startY+ 100);
    }

    @Override
    public void run() {
        //set pacman animation and start location
        imageView.setBackgroundResource(R.drawable.pacman_move_animation);
        AnimationDrawable pacmanAnimation = (AnimationDrawable) imageView.getBackground();
        imageView.setY(startY+100);
        imageView.setX(startX);
        imageView.bringToFront();
        pacmanAnimation.start();


//    //detele point and plus score + check if there is invisible bonus to change monster`s mode
//    if (Map.getBonus()[xMap][yMap].getType() != 0) {
//        Map.setScore(Map.getScore() + Map.getBonus()[xMap][yMap].getScore());
//        Map.setOneBonus(xMap, yMap, 0);
//        //todo delete bonus (draw tiles3.png)
//        ImageView imageView = new ImageView(gUA);
//        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(1080 / 31, 1080 / 31);
//        imageView.setLayoutParams(params);
//        imageView.setImageResource(R.drawable.tiles3);
//
//        imageView.setX(xMap * 1080 / 31);
//        imageView.setY(yMap * 1080 / 31 + 100);
//        layout.addView(imageView);
//    }

    }

    /**
     *
     * @param change change pacman movement acording to the variable
     *               1 - right
     *               2 - left
     *               3 - bottom
     *               4 - up
     *               area and speed depends on global variables and calculated each time.
     */
    public void changeMove(int change){
        //set current location on swap
        int currX = (Math.round((imageView.getX())/(1080/31)));
        int currY = Math.round (((imageView.getY()-100)/(1080/31)));
        map[xMap][yMap] = 0;
        xMap = currX;
        yMap = currY;
        startX = 1080/31*xMap;
        startY = 1080/31*yMap;
        map[xMap][yMap] = 2;
        imageView.setX(startX);
        imageView.setY(startY+ 100);
        float xNew;
        long time;
        int t = 0;

        switch (change){
            case 1:
                t = xMap;
                for (int i = xMap; i< map.length; i++){
                    if (map[i][yMap]!=1){
                        t = i;
                    }else {
                        if (t ==xMap)return;
                        map[xMap][yMap] = 0;
                        xMap = t;
                        map[xMap][yMap] = 2;
                        break;
                    }
                }
                rightDestination = 1080/31*(t);
                imageView.setRotation(0);
                xNew = imageView.getX();
                set[0].cancel();
                set[0] = new AnimatorSet();
                set[0].setInterpolator(new LinearInterpolator());
                imageView.setX(xNew);
                animator[0] = ObjectAnimator.ofFloat(imageView, "X", rightDestination);
                set[0].play(animator[0]);
                time = (long) ((rightDestination - xNew)/speedInPixelsForSecond*1000);
                set[0].setDuration(time);
                set[0].start();
                break;
            case 2:
                t = xMap;
                for (int i = xMap; i>0;i--){
                    if (map[i][yMap]!=1){
                        t = i;
                    }else {
                        if (t ==xMap)return;
                        map[xMap][yMap] = 0;
                        xMap = t;
                        map[xMap][yMap] = 2;
                        break;
                    }
                }
                leftDestination = 1080/31*(t);
                imageView.setRotation(180);
                xNew = imageView.getX();
                set[0].cancel();
                set[0] = new AnimatorSet();
                set[0].setInterpolator(new LinearInterpolator());
                imageView.setX(xNew);
                animator[0] = ObjectAnimator.ofFloat(imageView, "X", leftDestination);
                set[0].play(animator[0]);
                time = (long) ((xNew - leftDestination)/speedInPixelsForSecond*1000);
                set[0].setDuration(time);
                set[0].start();
                break;
            case 3:
                t = xMap;
                for (int i = yMap; i< map[xMap].length; i++){
                    if (map[xMap][i]!=1){
                        t = i;
                    }else {
                        if (t ==yMap)return;
                        map[xMap][yMap] = 0;
                        yMap = t;
                        map[xMap][yMap] = 2;
                        break;
                    }
                }
                bottomDestination = 1080/31*(t)+100;
                imageView.setRotation(90);
                xNew = imageView.getY();
                set[0].cancel();
                set[0] = new AnimatorSet();
                set[0].setInterpolator(new LinearInterpolator());
                imageView.setY(xNew);
                animator[0] = ObjectAnimator.ofFloat(imageView, "Y", bottomDestination);
                set[0].play(animator[0]);
                time = (long) ((bottomDestination-xNew)/speedInPixelsForSecond*1000);
                set[0].setDuration(time);
                set[0].start();
                break;
            case 4:
                t = xMap;
                for (int i = yMap; i>0;i--){
                    if (map[xMap][i]!=1){
                        t = i;
                    }else {
                        if (t ==yMap)return;
                        map[xMap][yMap] = 0;
                        yMap = t;
                        map[xMap][yMap] = 2;
                        break;
                    }
                }
                upDestination = 1080/31*(t)+100;
                imageView.setRotation(270);
                xNew = imageView.getY();
                set[0].cancel();
                set[0] = new AnimatorSet();
                set[0].setInterpolator(new LinearInterpolator());
                imageView.setY(xNew);
                animator[0] = ObjectAnimator.ofFloat(imageView, "Y", upDestination);
                set[0].play(animator[0]);
                time = (long) ((xNew- upDestination)/speedInPixelsForSecond*1000);
                set[0].setDuration(time);
                set[0].start();
        }



    }

    public void setLeftDestination(int leftDestination) {
        this.leftDestination = leftDestination;
    }

    public void setRightDestination(int rightDestination) {
        this.rightDestination = rightDestination;
    }

    public void setUpDestination(int upDestination) {
        this.upDestination = upDestination;
    }

    public void setBottomDestination(int bottomDestination) {
        this.bottomDestination = bottomDestination;
    }

    public void setSpeedInPixelsForSecond(int speedInPixelsForSecond) {
        this.speedInPixelsForSecond = speedInPixelsForSecond;
    }
}
