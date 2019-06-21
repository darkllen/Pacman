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
public class RecordsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hide top panel
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.records);

        //start game on click
        Button usualMod = findViewById(R.id.recordsUsualMod);
        usualMod.setOnClickListener(v -> {
//            SharedPreferences preferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
//            SharedPreferences.Editor editor =preferences.edit();
//            editor.putInt("score", 0);
//            editor.putInt("level", 0);
//            editor.putInt("lives", 3);
//            editor.apply();
            Unit.inversionMode=false;
            Intent intent = new Intent(RecordsActivity.this, RecordsUsualActivity.class);
            startActivity(intent);
        });

        //start game on click
        Button inversionMod = findViewById(R.id.recordsInversionMod);
        inversionMod.setOnClickListener(v -> {
//            SharedPreferences preferences = getSharedPreferences("sharedPrefsScore", MODE_PRIVATE);
//            SharedPreferences.Editor editor =preferences.edit();
//            editor.putInt("score", 0);
//            editor.putInt("level", 0);
//            editor.putInt("lives", 3);
//            editor.apply();
            Unit.inversionMode=true;
            Intent intent = new Intent(RecordsActivity.this, RecordsUsualActivity.class);
            startActivity(intent);
        });

        Button back=findViewById(R.id.recordsBack);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(RecordsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        if(SettingsActivity.getLanguage().equals("English")){
            usualMod.setText("Classic records");
            inversionMod.setText("Inversion records");
            back.setText("Back");
        }
        if(SettingsActivity.getLanguage().equals("Ukranian")){
            usualMod.setText("Рекорди класика");
            inversionMod.setText("Рекорди інверсія");
            back.setText("Назад");
        }
    }
}