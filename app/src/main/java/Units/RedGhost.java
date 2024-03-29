package Units;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.pacman.Map.Map;
import com.example.pacman.R;

import Menu.GameUsualActivity;
import Menu.Listeners.GhostListener;

public class RedGhost extends Unit {

    public RedGhost(ImageView imageView, int[][] map, Handler handler) {
        super(imageView, map, handler);
        this.setxMap(13);
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
        speedInPixelsForSecond = 150+GameUsualActivity.level*20;
        this.getImageView().setBackgroundResource(R.drawable.red_up);
        AnimationDrawable ghostAnimation = (AnimationDrawable) this.getImageView().getBackground();
        this.getImageView().setY(this.getStartY()+100);
        this.getImageView().setX(this.getStartX());
     //   this.getImageView().bringToFront();
        ghostAnimation.start();

    }

   // @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void changeMove(int change) {
//        if(Unit.getIsPaused()){set[0].pause();
//             return;}
//        if(set[0].isPaused())
//            set[0].resume();
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
               // imageView.setRotation(0);
                if (uneatable){
                    this.getImageView().setBackgroundResource(R.drawable.uneatable_monster);
                }else
                if (GhostListener.isCanEat()){
                    this.getImageView().setBackgroundResource(R.drawable.invisible);
                }else {
                    this.getImageView().setBackgroundResource(R.drawable.red_right);
                }
                AnimationDrawable redGhostAn = (AnimationDrawable) this.getImageView().getBackground();
                redGhostAn.start();
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
                t = xMap;
                for (int i = xMap; i>0;i--){
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
                //imageView.setRotation(180);
                if (uneatable){
                    this.getImageView().setBackgroundResource(R.drawable.uneatable_monster);
                }else
                if (GhostListener.isCanEat()){
                    this.getImageView().setBackgroundResource(R.drawable.invisible);
                }else {
                    this.getImageView().setBackgroundResource(R.drawable.red_left);
                }
                AnimationDrawable redGhostAn2 = (AnimationDrawable) this.getImageView().getBackground();
                redGhostAn2.start();
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
                if (uneatable){
                    this.getImageView().setBackgroundResource(R.drawable.uneatable_monster);
                }else
                if (GhostListener.isCanEat()){
                    this.getImageView().setBackgroundResource(R.drawable.invisible);
                }else {
                    this.getImageView().setBackgroundResource(R.drawable.red_down);
                }
                AnimationDrawable redGhostAn3 = (AnimationDrawable) this.getImageView().getBackground();
                redGhostAn3.start();
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
                ((GhostListener)this.getAnimatorListener()).setPrev(4);
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
                if (uneatable){
                    this.getImageView().setBackgroundResource(R.drawable.uneatable_monster);
                }else
                if (GhostListener.isCanEat()){
                    this.getImageView().setBackgroundResource(R.drawable.invisible);
                }else {
                    this.getImageView().setBackgroundResource(R.drawable.red_up);
                }
                AnimationDrawable redGhostAn4 = (AnimationDrawable) this.getImageView().getBackground();
                redGhostAn4.start();
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
