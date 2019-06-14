package Menu.Listeners;

import android.animation.Animator;

import Units.Unit;

public class GhostListener implements Animator.AnimatorListener {
    Unit unit;

    public GhostListener(Unit unit) {
        this.unit = unit;
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        unit.changeMove(2);
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
