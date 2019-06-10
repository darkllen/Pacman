package Menu;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.example.pacman.Map.Map;
import com.example.pacman.R;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    AnimationDrawable pacmanAnimation;

    @Override
    //Hi Ana
    //GGG
    //<3
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        Button game = findViewById(R.id.game);
        Button records = findViewById(R.id.records);
        Button settings = findViewById(R.id.settings);
        game.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, GameModsActivity.class);
            startActivity(intent);
        });
        records.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, RecordsActivity.class);
            startActivity(intent);
        });
        settings.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
  /*      ImageView imageView = (ImageView)findViewById(R.id.image);
        imageView.setBackgroundResource(R.drawable.pacman_move_animation);
        imageView.setRotation(90);
        imageView.setRotation(180);
        pacmanAnimation = (AnimationDrawable)imageView.getBackground();*/
    }

    //for pacman immediatly animation
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
   /*     pacmanAnimation.start();*/
    }
}
