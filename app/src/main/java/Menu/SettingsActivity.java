package Menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.pacman.R;

import java.util.Objects;

//class for change settings
public class SettingsActivity extends AppCompatActivity {

    private static boolean musicEnabled=true;
    private static boolean soundEnabled=true;
    private static String language="English";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.settings);

        Button back=findViewById(R.id.back_button);
        back.setOnClickListener(v->{
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        Switch musicSwitch=findViewById(R.id.music_button);
        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(musicSwitch.isChecked())
                    musicEnabled=false;else
                    musicEnabled=true;
            }
        });


        Switch soundSwitch=findViewById(R.id.sound_button);
        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(soundSwitch.isChecked())
                    soundEnabled=false;else
                    soundEnabled=true;
            }
        });

        ImageView languageImageView=findViewById(R.id.language_image);

        Button languageBack=findViewById(R.id.language_back);
        languageBack.setOnClickListener(v->{
            if(language.equals("Ukranian")) {
                languageImageView.setImageResource(R.drawable.english);
                language="English";
            }
            else {
                languageImageView.setImageResource(R.drawable.ukranian);
                language="Ukranian";
            }
        });

        Button languageForward=findViewById(R.id.language_forward);
        languageForward.setOnClickListener(v->{
            if(language.equals("Ukranian")) {
                languageImageView.setImageResource(R.drawable.english);
                language="English";
            }
            else {
                languageImageView.setImageResource(R.drawable.ukranian);
                language="Ukranian";
            }
        });

        TextView mText=findViewById(R.id.musicTextView);
        TextView sText=findViewById(R.id.soundTextView);

        if(SettingsActivity.getLanguage().equals("English")){
            mText.setText("Music:");
            sText.setText("Sound:");
        }
        if(SettingsActivity.getLanguage().equals("Ukranian")){
            mText.setText("Музика:");
            sText.setText("Звуки:");
        }

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



}
