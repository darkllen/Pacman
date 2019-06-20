package Units;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import Menu.GameUsualActivity;

public abstract class Unit extends Thread {
    //constants, shouldnt be changed in running time
    ImageView imageView;
    //unnecessary value
    final ObjectAnimator[] animator = {ObjectAnimator.ofFloat(imageView, "X",1000)};
    final AnimatorSet[] set = {new AnimatorSet()};
    int startX;
    int startY;

    //variables to control area of movement and speed
    int leftDestination = 0;
    int rightDestination = 1000;
    int upDestination = 0;
    int bottomDestination = 2000;
    int speedInPixelsForSecond = 300;

    int[][] map;

    //position in map
    int xMap = 13;
    int yMap = 28;

    int prev = 1;

    Animator.AnimatorListener animatorListener;

    //values in numbers are tested and not accurate(
    Handler handler;


    public static boolean inversionMode;

    public Unit() {
    }

    public Unit(ImageView imageView, int[][] map, Handler handler) {
        this.imageView = imageView;
        this.map = map;
        this.handler = handler;
        startX = 1080/26*xMap;
        startY = 1080/26*yMap;
        map[xMap][yMap] = 2;
        imageView.setX(startX);
        imageView.setY(startY+ 100);
        animatorListener = null;
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
       // if(Thread.currentThread().isInterrupted()) {return;}

        //set current location on swap
        int currX = (Math.round(( imageView.getX())/(1080/26)));
        int currY = Math.round ((( imageView.getY()-100)/(1080/26)));
        map[xMap][yMap] = 0;
        xMap = currX;
        yMap = currY;
        startX = 1080/26*xMap;
        startY = 1080/26*yMap;
        imageView.setX(startX);
        imageView.setY(startY+ 100);
        float xNew;
        long time;
        int t = 0;

        if(GameUsualActivity.getIsPaused()){set[0].cancel();return;}
        if(inversionMode){if(change%2==1)change++;else change--;}

        switch (change){
            case 1:
                prev = 1;
                t = xMap;
                for (int i = xMap; i<map.length; i++){

                    if (map[i][yMap]!=1){
                        t = i;
                    }else {
                        if (t ==xMap)return;
                        break;
                    }
                }
                System.out.println(t);
                if (t==25){
                    rightDestination = 1080/26*(t+1);
                    imageView.setX(1080/26*1);
                    xMap=1;
                    for (int i = xMap; i<map.length; i++){
                        if (map[i][yMap]!=1){
                            t = i;
                        }else {
                            if (t ==xMap)return;
                            break;
                        }
                    }

                }
                rightDestination = 1080/26*(t);
                imageView.setRotation(0);
                xNew = imageView.getX();
                set[0].cancel();
                set[0] = new AnimatorSet();
                if (animatorListener!=null)set[0].addListener(animatorListener);
                set[0].setInterpolator(new LinearInterpolator());
                imageView.setX(xNew);
                animator[0] = ObjectAnimator.ofFloat(imageView, "X", rightDestination);
                set[0].play(animator[0]);
                time = (long) ((rightDestination - xNew)/speedInPixelsForSecond*1000);
                set[0].setDuration(time);
               set[0].start();
                break;
            case 2:
                prev = 2;
                t = xMap;
                for (int i = xMap; i>0;i--){
                    if (map[i][yMap]!=1){
                        t = i;
                    }else {
                        if (t ==xMap)return;

                        break;
                    }
                }
                if (t==1){
                    leftDestination = 1080-1080/26*2;
                    imageView.setX(1080/26*25);
                    xMap=25;
                    for (int i = xMap; i>0;i--){
                        if (map[i][yMap]!=1){
                            t = i;
                        }else {
                            if (t ==xMap)return;

                            break;
                        }
                    }

                }
                leftDestination = 1080/26*(t);
                imageView.setRotation(180);
                xNew = imageView.getX();
                set[0].cancel();
                set[0] = new AnimatorSet();
                if (animatorListener!=null)set[0].addListener(animatorListener);
                set[0].setInterpolator(new LinearInterpolator());
                imageView.setX(xNew);
                animator[0] = ObjectAnimator.ofFloat(imageView, "X", leftDestination);
                set[0].play(animator[0]);
                time = (long) ((xNew - leftDestination)/speedInPixelsForSecond*1000);
                set[0].setDuration(time);
                set[0].start();
                break;
            case 3:
                prev =3;
                t = xMap;
                for (int i = yMap; i< map[xMap].length; i++){
                    if (map[xMap][i]!=1){
                        t = i;
                    }else {
                        if (t ==yMap)return;

                        break;
                    }
                }
                bottomDestination = 1080/26*(t)+100;
                imageView.setRotation(90);
                xNew = imageView.getY();
                set[0].cancel();
                set[0] = new AnimatorSet();
                if (animatorListener!=null)set[0].addListener(animatorListener);
                set[0].setInterpolator(new LinearInterpolator());
                imageView.setY(xNew);
                animator[0] = ObjectAnimator.ofFloat(imageView, "Y", bottomDestination);
                set[0].play(animator[0]);
                time = (long) ((bottomDestination-xNew)/speedInPixelsForSecond*1000);
                set[0].setDuration(time);
                set[0].start();
                break;
            case 4:
                prev = 4;
                t = xMap;
                for (int i = yMap; i>0;i--){
                    if (map[xMap][i]!=1){
                        t = i;
                    }else {
                        if (t ==yMap)return;

                        break;
                    }
                }
                upDestination = 1080/26*(t)+100;
                imageView.setRotation(270);
                xNew = imageView.getY();
                set[0].cancel();
                set[0] = new AnimatorSet();
                if (animatorListener!=null)set[0].addListener(animatorListener);
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

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ObjectAnimator[] getAnimator() {
        return animator;
    }

    public AnimatorSet[] getSet() {
        return set;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getLeftDestination() {
        return leftDestination;
    }

    public int getRightDestination() {
        return rightDestination;
    }

    public int getUpDestination() {
        return upDestination;
    }

    public int getBottomDestination() {
        return bottomDestination;
    }

    public int getSpeedInPixelsForSecond() {
        return speedInPixelsForSecond;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public int getxMap() {
        return xMap;
    }

    public void setxMap(int xMap) {
        this.xMap = xMap;
    }

    public int getyMap() {
        return yMap;
    }

    public void setyMap(int yMap) {
        this.yMap = yMap;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public int getPrev() {
        return prev;
    }

    public void setPrev(int prev) {
        this.prev = prev;
    }



}
