package Menu.Listeners;

import android.animation.Animator;
import android.widget.ImageView;

import java.util.Random;

import Units.Unit;

public class GhostListener implements Animator.AnimatorListener {
    Unit unit;
    Unit pacman;
    int[][] map;
    int prev = 1;


    public GhostListener(Unit unit, Unit pacman, int[][] map) {
        this.unit = unit;
        this.pacman = pacman;
        this.map = map;
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        int pacmanX = (int) pacman.getImageView().getX();
        int pacmanY = (int) pacman.getImageView().getY();
        Random random = new Random();
        int now = random.nextInt(4)+1;
        if (now==prev){
           now = random.nextInt(4)+1;
        }
        unit.changeMove(now);
        prev = now;
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
