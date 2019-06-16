package Menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.pacman.R;

import java.util.Objects;

//class for change settings
public class SettingsActivity extends AppCompatActivity {

    private static boolean musicEnabled=true;
    private static boolean soundEnabled=true;

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

    }

    public static boolean getMusicEnabled(){
        return musicEnabled;
        //return true;
    }
    public static boolean getSoundEnabled(){
        return soundEnabled;
        //return false;
    }



}
