package Menu;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import com.example.pacman.R;

import java.util.Objects;

import Units.Pacman;
//class for classic game
public class GameUsualActivity extends AppCompatActivity {
    //additional variables for define swap
    final int[] clickX = {0};
    final int[] clickY = {0};
    final int[] clickStartX = {0};
    final int[] clickStartY = {0};
    final boolean[] first = {true};
    //1 - move right, 2- move left, 3 - move down, 4 - move up
    final int[] click = {-1};


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide top panel
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.game_usual);
        ConstraintLayout layout = findViewById(R.id.pacmanLayout);

        //create new Thread for pacman unit
        Pacman pacman = new Pacman(findViewById(R.id.image));
        pacman.run();

        layout.setOnTouchListener(new RecordFirstAndLastCoordinatesOnTouchListener());
        layout.setOnClickListener(new ChangeMoveOnClickListener(pacman));
    }

    //listener for record x and y to decide side of swap
    private class RecordFirstAndLastCoordinatesOnTouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (first[0]){
                clickStartX[0] = (int) event.getX();
                clickStartY[0] = (int) event.getY();
                first[0] = false;
            }
            clickX[0] = (int) event.getX();
            clickY[0] = (int) event.getY();
            return false;
        }
    }
    //listener for the end of the swap, that change pacman movement
    private class ChangeMoveOnClickListener implements View.OnClickListener{
        Pacman pacman;

        ChangeMoveOnClickListener(Pacman pacman) {
            this.pacman = pacman;
        }
        @Override
        public void onClick(View v) {
            first[0] = true;
            int differenceX = clickX[0] - clickStartX[0];
            int differenceY = clickY[0] - clickStartY[0];
            if (Math.abs(differenceX)>=Math.abs(differenceY)){
                if (differenceX>0){
                    click[0] = 1;
                }else {
                    click[0] = 2;
                }
            }else {
                if (differenceY>0){
                    click[0] =3;
                }else {
                    click[0] =4;
                }
            }
            pacman.changeMove(click[0]);
        }
    }
}
