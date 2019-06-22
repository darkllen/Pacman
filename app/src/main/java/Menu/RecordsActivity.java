package Menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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

        ConstraintLayout layout = findViewById(R.id.recordsLay);

        //start game on click
        Button usualMod = layout.findViewById(R.id.recordsUsualMod);
        usualMod.setOnClickListener(v -> {
            Unit.inversionMode=false;
            Intent intent = new Intent(RecordsActivity.this, RecordsUsualActivity.class);
            startActivity(intent);
        });

        //start game on click
        Button inversionMod = findViewById(R.id.recordsInversionMod);
        inversionMod.setOnClickListener(v -> {
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
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RecordsActivity.this, MainActivity.class);
        startActivity(intent);
    }
}