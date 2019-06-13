package Units;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.pacman.R;

public class Pacman extends Thread {
    //constants, shouldnt be changed in running time
    private ImageView imageView;
    private final ObjectAnimator[] animator = {ObjectAnimator.ofFloat(imageView, "X",1000)};
    private final AnimatorSet[] set = {new AnimatorSet()};
    private int startX = 100;
    private int startY = 100;

    //variables to control area of movement and speed
    private int leftDestination = 0;
    private int rightDestination = 1000;
    private int upDestination = 0;
    private int bottomDestination = 2000;
    private int speedInPixelsForSecond = 300;

    public Pacman(ImageView imageView) {
        this.imageView = imageView;
        imageView.setX(startX);
        imageView.setY(startY);
    }

    @Override
    public void run() {
        //set pacman animation and start location
        imageView.setBackgroundResource(R.drawable.pacman_move_animation);
        AnimationDrawable pacmanAnimation = (AnimationDrawable) imageView.getBackground();
        imageView.setY(startY);
        imageView.setX(startX);
        pacmanAnimation.start();
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
        float xNew;
        long time;
        switch (change){
            case 1:
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
