package com.example.pacman;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.pacman.Map.Map;


public class MainActivity extends AppCompatActivity {

    AnimationDrawable pacmanAnimation;

    @Override
    //Hi Ana
    //GGG
    //<3
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView)findViewById(R.id.image);
        imageView.setBackgroundResource(R.drawable.pacman_move_animation);

        imageView.setRotation(90);
        imageView.setRotation(180);
        pacmanAnimation = (AnimationDrawable)imageView.getBackground();
        Map map=Map.generateMap();
    }

    //for pacman immediatly animation
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        pacmanAnimation.start();
    }
}
