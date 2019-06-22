package Menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pacman.R;

import java.util.Objects;

//class for change settings
public class SettingsActivity extends AppCompatActivity {

    private static boolean musicEnabled=true;
    private static boolean soundEnabled=true;
    private static String language="English";

    public static final String SHARED_PREFS="sharedPrefs";
    public static final String LANGUAGE="language";
    public static final String MUSIC="true";
    public static final String SOUND="1";

    ImageView languageImageView;
    Switch musicSwitch;
    Switch soundSwitch;
    TextView mText;
    TextView sText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.settings);

        Button back=findViewById(R.id.back_button);
        back.setOnClickListener(v->{
            saveData();
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        musicSwitch=findViewById(R.id.music_button);
        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(musicSwitch.isChecked())
                    musicEnabled=false;else
                    musicEnabled=true;
            }
        });


        soundSwitch=findViewById(R.id.sound_button);
        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(soundSwitch.isChecked())
                    soundEnabled=false;else
                    soundEnabled=true;
            }
        });

        languageImageView=findViewById(R.id.language_image);

        Button languageBack=findViewById(R.id.language_back);
        languageBack.setOnClickListener(v->{
            if(language.equals("Ukranian")) {
                language="English";
                updateViews();
            }
            else {
                language="Ukranian";
                updateViews();
            }
        });

        Button languageForward=findViewById(R.id.language_forward);
        languageForward.setOnClickListener(v->{
            if(language.equals("Ukranian")) {
                language="English";
                updateViews();
            }
            else {
                language="Ukranian";
                updateViews();
            }
        });

        mText=findViewById(R.id.musicTextView);
        sText=findViewById(R.id.soundTextView);

        loadData();
        updateViews();


    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(LANGUAGE,language);
        editor.putBoolean(MUSIC,musicEnabled);
        if(soundEnabled)editor.putInt(SOUND,1);else editor.putInt(SOUND,0);

        editor.apply();
        Toast.makeText(this,"Data Saved",Toast.LENGTH_SHORT).show();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        language=sharedPreferences.getString(LANGUAGE,"English");
        musicEnabled=sharedPreferences.getBoolean(MUSIC,true);
        int i=sharedPreferences.getInt(SOUND,1);
        if(i==1)soundEnabled=true;else soundEnabled=false;
    }



    public void updateViews(){
        if(language.equals("English")){
            languageImageView.setImageResource(R.drawable.english);
            mText.setText("Music:");
            sText.setText("Sound:");
        }
        if(language.equals("Ukranian")){
            languageImageView.setImageResource(R.drawable.ukranian);
            mText.setText("Музика:");
            sText.setText("Звуки:");
        }
        if(!musicEnabled)musicSwitch.setChecked(true);else musicSwitch.setChecked(false);
        if(!soundEnabled)soundSwitch.setChecked(true);else soundSwitch.setChecked(false);

    }

    public static boolean getMusicEnabled(){
        return musicEnabled;
        //return true;
    }
    public static boolean getSoundEnabled(){
        return soundEnabled;
        //return false;
    }

    public static String getLanguage(){
        return language;
    }

    public static void setLanguage(String language) {
        SettingsActivity.language = language;
    }

    public static void setMusicEnabled(boolean musicEnabled) {
        SettingsActivity.musicEnabled = musicEnabled;
    }

    public static void setSoundEnabled(boolean soundEnabled) {
        SettingsActivity.soundEnabled = soundEnabled;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
