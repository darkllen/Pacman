package Menu;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;

import com.example.pacman.R;

import java.util.Objects;

public class GameUsualActivity extends AppCompatActivity {

    AnimationDrawable pacmanAnimation;

    //class for classic game
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide top panel
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.game_usual);

        //create pacman animated
        ImageView imageView = (ImageView)findViewById(R.id.image);
        imageView.setBackgroundResource(R.drawable.pacman_move_animation);
        imageView.setRotation(90);
        imageView.setRotation(180);
        pacmanAnimation = (AnimationDrawable)imageView.getBackground();
        imageView.setX(500);

        //set move animation
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "X", 100);
        AnimatorSet set = new AnimatorSet();
        set.play(animator);
        set.setDuration(3000);
        set.start();


    }


    //for pacman immediatly animation
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        pacmanAnimation.start();
    }}
