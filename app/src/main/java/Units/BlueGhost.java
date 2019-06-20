package Units;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.pacman.R;

import Menu.GameUsualActivity;

public class BlueGhost extends Unit {
    public BlueGhost(ImageView imageView, int[][] map, Handler handler) {
        super(imageView, map, handler);
        this.setxMap(12);
        this.setyMap(12);
        this.setStartX(1080/26*xMap);
        this.setStartY(1080/26*yMap);
        imageView.setX(1080/26*xMap);
        imageView.setY(1080/26*yMap+100);
    }

    public Animator.AnimatorListener getAnimatorListener() {
        return animatorListener;
    }

    public void setAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.animatorListener = animatorListener;
        this.getSet()[0].addListener(animatorListener);
    }

    @Override
    public void run() {
        this.getImageView().setBackgroundResource(R.drawable.blue_up);
        AnimationDrawable ghostAnimation = (AnimationDrawable) this.getImageView().getBackground();
        this.getImageView().setY(this.getStartY()+100);
        this.getImageView().setX(this.getStartX());
     //   this.getImageView().bringToFront();
        ghostAnimation.start();


        while (true){
            int lives = GameUsualActivity.getLivesStartNumber();

            int currX = (Math.round(( imageView.getX())/(1080/26)));
            int currY = Math.round ((( imageView.getY()-100)/(1080/26)));

            int currXRight=currX;
            if(currX+1<map.length)currXRight++;
            int currXLeft=currX;
            if(currX-1>=0)currXLeft--;
            if (map[currX][currY]==2 || map[currXRight][currY]==2 || map[currXLeft][currY]==2 || map[currX][currY+1]==2 || map[currX][currY-1]==2){
                lives--;
                Message msg3 = new Message();
                msg3.obj = " ";
                msg3.arg1 = lives;
                msg3.arg2 = 0;
                handler.sendMessage(msg3);
            }
            try {
                sleep(270);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void changeMove(int change) {
        if(GameUsualActivity.getIsPaused()){set[0].cancel();return;}

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

        switch (change){
            case 1:
                xNew = imageView.getX();
                if(xMap>=map.length-3){ xNew=(0)*(1080/26);xMap=0;System.out.println("!R");}

                t = xMap;
                for (int i = xMap; i< map.length; i++){
                    if (map[i][yMap]!=1){
                        t = i;
                        if ((map[i][yMap+1]!=1 || map[i][yMap-1]!=1) && t!=xMap){
                            break;
                        }
                    }else {
                        if (t ==xMap)return;
                        break;
                    }
                }
                rightDestination = 1080/26*(t);
                // imageView.setRotation(0);
                this.getImageView().setBackgroundResource(R.drawable.blue_right);
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
                xNew = imageView.getX();

                if(xMap<=2){ xNew=(map.length-1)*(1080/26);xMap=map.length-1;System.out.println("!");}

                t = xMap;
                for (int i = xMap; i>=0;i--){
                    if (map[i][yMap]!=1){
                        t = i;
                        if ((map[i][yMap+1]!=1 || map[i][yMap-1]!=1) && t!=xMap){
                            break;
                        }
                    }else {
                        if (t ==xMap)return;

                        break;
                    }
                }
                leftDestination = 1080/26*(t);
                //imageView.setRotation(180);
                this.getImageView().setBackgroundResource(R.drawable.blue_left);
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
                t = yMap;
                for (int i = yMap; i< map[xMap].length; i++){
                    if (map[xMap][i]!=1){
                        t = i;
                        if ((map[xMap+1][i]!=1 || map[xMap-1][i]!=1) && t!=yMap){
                            break;
                        }
                    }else {
                        if (t ==yMap)return;

                        break;
                    }
                }
                bottomDestination = 1080/26*(t)+100;
                //imageView.setRotation(90);
                this.getImageView().setBackgroundResource(R.drawable.blue_down);
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
                t = yMap;
                for (int i = yMap; i>0;i--){
                    if (map[xMap][i]!=1){
                        t = i;
                        if ((map[xMap+1][i]!=1 || map[xMap-1][i]!=1) && t!=yMap){
                            break;
                        }
                    }else {
                        if (t ==yMap)return;

                        break;
                    }
                }
                upDestination = 1080/26*(t)+100;
                //imageView.setRotation(270);
                this.getImageView().setBackgroundResource(R.drawable.blue_up);
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
}
