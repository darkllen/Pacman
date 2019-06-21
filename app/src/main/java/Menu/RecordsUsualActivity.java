package Menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pacman.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import Units.Unit;

public class RecordsUsualActivity extends AppCompatActivity {

    public void loadData() {

        SharedPreferences sharedPreferences = this.getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String mode = "records";
        if (Unit.inversionMode) mode = "recordsInversion";
        records = new HashSet(sharedPreferences.getStringSet(mode, new HashSet<String>()));

    }

    Set records;
    ArrayList<Integer> recordsArray=new ArrayList<>();
    ArrayList<String> recordsArrayStr=new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadData();
        //hide top panel
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.records_usual);


        recordsArrayStr = new ArrayList<>(records);

        for(int i=0;i<recordsArrayStr.size();i++)
            recordsArray.add(new Integer(recordsArrayStr.get(i)));
        Collections.sort(recordsArray);

        ArrayList<TextView> textViews = new ArrayList<>();

        textViews.add(findViewById(R.id.records1));
        textViews.add(findViewById(R.id.records2));
        textViews.add(findViewById(R.id.records3));
        textViews.add(findViewById(R.id.records4));
        textViews.add(findViewById(R.id.records5));

        for(int i=5;i>recordsArray.size();i--)
            textViews.get(i-1).setVisibility(View.INVISIBLE);

        for(int i=0;i<recordsArray.size();i++)
            textViews.get(i).setText((i+1)+".  "+recordsArray.get(recordsArray.size()-1-i));

        if(recordsArray.size()==0){
            textViews.get(0).setTextSize(16);//todo change text size&location
            if(SettingsActivity.getLanguage().equals("English"))textViews.get(0).setText("No games in this mode");
            if(SettingsActivity.getLanguage().equals("Ukranian"))textViews.get(0).setText("Немає ігор у цьому режимі");
            textViews.get(0).setVisibility(View.VISIBLE);
        }

        Button back=findViewById(R.id.recordsUsualBack);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(RecordsUsualActivity.this, RecordsActivity.class);
            startActivity(intent);
        });

        if(SettingsActivity.getLanguage().equals("English")){
            back.setText("Back");
        }
        if(SettingsActivity.getLanguage().equals("Ukranian")){
            back.setText("Назад");
        }
    }
}
