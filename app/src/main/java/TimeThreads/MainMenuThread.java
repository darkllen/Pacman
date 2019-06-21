package TimeThreads;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.widget.ImageView;

public class MainMenuThread extends Thread {
    Handler handler;

    public MainMenuThread(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        int i = 0;
        while (true){
            if (i%2==0){

/*
                AnimatorSet setRedGhostFirst = new AnimatorSet();
                Animator animatorRedGhost = ObjectAnimator.ofFloat(redGhost, "X", -400+70+50, 1080+360+70);
                setRedGhostFirst.play(animatorRedGhost);
                setRedGhostFirst.setDuration(5000);
                setRedGhostFirst.start();*/

             /*

                AnimatorSet setOrangeGhostFirst = new AnimatorSet();
                Animator animatorOrangeGhost = ObjectAnimator.ofFloat(orangeGhost, "X", -400+210+50, 1080+360+210);
                setOrangeGhostFirst.play(animatorOrangeGhost);
                setOrangeGhostFirst.setDuration(5000);
                setOrangeGhostFirst.start();

                AnimatorSet setPinkGhostFirst = new AnimatorSet();
                Animator animatorPinkGhost = ObjectAnimator.ofFloat(pinkGhost, "X", -400+280+50, 1080+360+280);
                setPinkGhostFirst.play(animatorPinkGhost);
                setPinkGhostFirst.setDuration(5000);
                setPinkGhostFirst.start();*/


            }else {
               /* AnimatorSet setPacmanSecond = new AnimatorSet();
                Animator animatorPacmanSecond = ObjectAnimator.ofFloat(pacman, "X", 1080+360, -450);
                setPacmanSecond.play(animatorPacmanSecond);
                setPacmanSecond.setDuration(5000);
                setPacmanSecond.start();

                AnimatorSet setRedGhostSecond = new AnimatorSet();
                Animator animatorRedGhostSecond = ObjectAnimator.ofFloat(redGhost, "X", 1080+360+70,-400+70+50 );
                setRedGhostSecond.play(animatorRedGhostSecond);
                setRedGhostSecond.setDuration(5000);
                setRedGhostSecond.start();

                AnimatorSet setBlueGhostSecond = new AnimatorSet();

                setBlueGhostSecond.play(animatorBlueGhostSecond);
                setBlueGhostSecond.setDuration(5000);
                setBlueGhostSecond.start();

                AnimatorSet setOrangeGhostSecond = new AnimatorSet();
                Animator animatorOrangeGhostSecond = ObjectAnimator.ofFloat(orangeGhost, "X", 1080+360+210, -400+210+50);
                setOrangeGhostSecond.play(animatorOrangeGhostSecond);
                setOrangeGhostSecond.setDuration(5000);
                setOrangeGhostSecond.start();

                AnimatorSet setPinkGhostSecond = new AnimatorSet();
                Animator animatorPinkGhostSecond = ObjectAnimator.ofFloat(pinkGhost, "X", 1080+360+280,-400+280+50 );
                setPinkGhostSecond.play(animatorPinkGhostSecond);
                setPinkGhostSecond.setDuration(5000);
                setPinkGhostSecond.start();*/
            }
        }
    }
}
