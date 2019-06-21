package Menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.pacman.R;

import java.util.Objects;

import Units.Unit;

//class for choose game mod
public class GameModsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hide top panel
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.game_mods);

        //start game on click
        Button usualMod = findViewById(R.id.usualMod);
        usualMod.setOnClickListener(v -> {
            SharedPreferences preferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor =preferences.edit();
            editor.putInt("score", 3);
            editor.apply();
            Intent intent = new Intent(GameModsActivity.this, GameUsualActivity.class);
            startActivity(intent);
            Unit.inversionMode=false;
        });

        //start game on click
        Button inversionMod = findViewById(R.id.inversionMod);
        inversionMod.setOnClickListener(v -> {
            SharedPreferences preferences = getSharedPreferences("sharedPrefsScore", MODE_PRIVATE);
            SharedPreferences.Editor editor =preferences.edit();
            editor.putInt("score", 3);
            editor.apply();
            Intent intent = new Intent(GameModsActivity.this, GameUsualActivity.class);
            startActivity(intent);
            Unit.inversionMode=true;
        });

        if(SettingsActivity.getLanguage().equals("English")){
            usualMod.setText("Classic");
            inversionMod.setText("Inversion");
        }
        if(SettingsActivity.getLanguage().equals("Ukranian")){
            usualMod.setText("Класика");
            inversionMod.setText("Інверсія");
        }
    }
}
