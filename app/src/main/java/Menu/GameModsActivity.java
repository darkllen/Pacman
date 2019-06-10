package Menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.pacman.R;

import java.util.Objects;

public class GameModsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.game_mods);
        Button usualMod = findViewById(R.id.usualMod);
        usualMod.setOnClickListener(v -> {
            Intent intent = new Intent(GameModsActivity.this, GameUsualActivity.class);
            startActivity(intent);
        });
    }
}
