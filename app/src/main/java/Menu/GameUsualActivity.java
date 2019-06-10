package Menu;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.pacman.R;

import java.util.Objects;

public class GameUsualActivity extends AppCompatActivity {

    AnimationDrawable pacmanAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.game_usual);

        ImageView imageView = (ImageView)findViewById(R.id.image);
        imageView.setBackgroundResource(R.drawable.pacman_move_animation);
        imageView.setRotation(90);
        imageView.setRotation(180);
        pacmanAnimation = (AnimationDrawable)imageView.getBackground();
    }


    //for pacman immediatly animation
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        pacmanAnimation.start();
    }}
