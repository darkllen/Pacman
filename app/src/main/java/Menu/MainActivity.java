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

    //initial class
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        Button game = findViewById(R.id.game);
        Button records = findViewById(R.id.records);
        Button settings = findViewById(R.id.settings);

        if(SettingsActivity.getLanguage().equals("English")){
            game.setText("Game");
            records.setText("Records");
            settings.setText("Settings");

        }
        if(SettingsActivity.getLanguage().equals("Ukranian")){
            game.setText("Гра");
            records.setText("Рекорди");
            settings.setText("Налаштування");
        }
        //open menus on button click
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

    }


}
