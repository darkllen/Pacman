package Units;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.pacman.Map.Map;
import com.example.pacman.R;

public class RightGost extends Unit {

    public RightGost(ImageView imageView, int[][] map, Handler handler) {
        super(imageView, map, handler);
        this.setxMap(13);
        this.setyMap(13);
        this.setStartX(1080/25*xMap);
        this.setStartY(1080/25*yMap);
        imageView.setX(1080/25*xMap);
        imageView.setY(1080/25*yMap+100);
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
        this.getImageView().setBackgroundResource(R.drawable.red_left);
        AnimationDrawable ghostAnimation = (AnimationDrawable) this.getImageView().getBackground();
        this.getImageView().setY(this.getStartY()+100);
        this.getImageView().setX(this.getStartX());
        this.getImageView().bringToFront();
        ghostAnimation.start();
    }

    @Override
    public void changeMove(int change) {
        int currX = (Math.round(( imageView.getX())/(1080/25)));
        int currY = Math.round ((( imageView.getY()-100)/(1080/25)));
        map[xMap][yMap] = 0;
        xMap = currX;
        yMap = currY;
        startX = 1080/25*xMap;
        startY = 1080/25*yMap;
        imageView.setX(startX);
        imageView.setY(startY+ 100);
        float xNew;
        long time;
        int t = 0;

        switch (change){
            case 1:
                t = xMap;
                for (int i = xMap; i< map.length; i++){
                    if (map[i][yMap]!=1 && (map[i][yMap+1]!=0 && map[i][yMap-1]!=0)){
                        t = i;
                    }else {
                        if (t ==xMap)return;
                        break;
                    }
                }
                rightDestination = 1080/25*(t);
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
                t = xMap;
                for (int i = xMap; i>0;i--){
                    if (map[i][yMap]!=1){
                        t = i;
                    }else {
                        if (t ==xMap)return;

                        break;
                    }
                }
                leftDestination = 1080/25*(t);
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
                t = xMap;
                for (int i = yMap; i< map[xMap].length; i++){
                    if (map[xMap][i]!=1){
                        t = i;
                    }else {
                        if (t ==yMap)return;

                        break;
                    }
                }
                bottomDestination = 1080/25*(t)+100;
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
                t = xMap;
                for (int i = yMap; i>0;i--){
                    if (map[xMap][i]!=1){
                        t = i;
                    }else {
                        if (t ==yMap)return;

                        break;
                    }
                }
                upDestination = 1080/25*(t)+100;
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
}
