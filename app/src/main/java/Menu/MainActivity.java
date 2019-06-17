package Menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pacman.Map.Map;
import com.example.pacman.R;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREFS="sharedPrefs";
    public static final String LANGUAGE="language";
    public static final String MUSIC="true";
    public static final String SOUND="1";

    //initial class
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        Button game = findViewById(R.id.game);
        Button records = findViewById(R.id.records);
        Button settings = findViewById(R.id.settings);

//        SettingsActivity sA=new SettingsActivity();
//        sA.loadData();
        loadData();

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


//    public void saveData() {
//        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        editor.putString(LANGUAGE,SettingsActivity);
//        editor.putBoolean(MUSIC,musicEnabled);
//        if(soundEnabled)editor.putInt(SOUND,1);else editor.putInt(SOUND,0);
//        //editor.putBoolean(SOUND,soundEnabled);
//
//        editor.apply();
//        Toast.makeText(this,"Data Saved",Toast.LENGTH_SHORT).show();
//    }

    public void loadData(){
        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SettingsActivity.setLanguage(sharedPreferences.getString(LANGUAGE,"English"));
        SettingsActivity.setMusicEnabled(sharedPreferences.getBoolean(MUSIC,true));
        int i=sharedPreferences.getInt(SOUND,1);
        if(i==1)SettingsActivity.setSoundEnabled(true);else SettingsActivity.setSoundEnabled(false);
    }


}
