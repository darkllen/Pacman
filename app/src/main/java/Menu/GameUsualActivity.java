package Menu;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.pacman.Map.Map;
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

        int side = 1000/30;


        //generate map and create black images for walls
        Map map = Map.generateMap();
        int[][] m = map.getMap();

        int height=m.length;
        int width=m[0].length;

        for (int i = 0; i<m.length;i++){
            for (int j = 0; j<m[i].length;j++){
                ImageView imageView = new ImageView(this);
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(side,side);
                imageView.setLayoutParams(params);


//                if (m[i][j]==1)
//                imageView.setBackgroundColor(Color.BLACK);
//                else
//                imageView.setBackgroundColor(Color.RED);

                // add images
                int left,right,up,down;//numbers on the sides
                boolean p=true;

                //границі
                if(i==1||i==m.length-2)  {imageView.setImageResource(R.drawable.tile2);imageView.setRotation(0);p=false;}
                if(j==0||j==m[0].length-1)   {imageView.setImageResource(R.drawable.tile2);imageView.setRotation(90);p=false;}
                //кути
                if(i==1&&j==m[0].length-1){imageView.setImageResource(R.drawable.tile1);imageView.setRotation(90);p=false;}
                if(i==m.length-2&&j==0) {imageView.setImageResource(R.drawable.tile1);imageView.setRotation(270);p=false;}
                if(i==1&&j==0)  {imageView.setImageResource(R.drawable.tile1);imageView.setRotation(180);p=false;}
                if(i==m.length-2&&j==m[0].length-1) {imageView.setImageResource(R.drawable.tile1);imageView.setRotation(0);p=false;}

                //поворот в тунель
                if (p==false){
                    if(i==1&&m[0][j]==1&&m[1][j+1]==0)  { imageView.setImageResource(R.drawable.tile1);imageView.setRotation(0);}
                    if(i==1&&m[0][j]==1&&m[1][j-1]==0)  { imageView.setImageResource(R.drawable.tile1);imageView.setRotation(-90);}
                    if(i==m.length-2&&m[m.length-1][j]==1&&m[m.length-2][j-1]==0)  { imageView.setImageResource(R.drawable.tile1);imageView.setRotation(180);}
                    if(i==m.length-2&&m[m.length-1][j]==1&&m[m.length-2][j+1]==0)  { imageView.setImageResource(R.drawable.tile1);imageView.setRotation(90);}


                }

                //порожні клітинки
                if (m[i][j]==0) {imageView.setImageResource(R.drawable.tile3);}
                //порожні клітинки та тунель
                if(i==0||i==m.length-1){p=false;if(m[i][j]==1){imageView.setImageResource(R.drawable.tile2);imageView.setRotation(90);}}
                //непорожні клітинки
                if(m[i][j]==1&&p){
                        left=m[i-1][j];
                        right=m[i+1][j];
                        up=m[i][j-1];
                        down=m[i][j+1];

                        //полостки
                    if(left==1&&right==1)
                    {imageView.setImageResource(R.drawable.tile2);imageView.setRotation(90);}

                    if(up==1&&down==1)
                    {imageView.setImageResource(R.drawable.tile2);imageView.setRotation(0);}

                    //кути
                    if((right==0&&down==0)||(left==1&&right==1&&up==1&&down==1&&m[i-1][j-1]==0))
                    { imageView.setImageResource(R.drawable.tile1);imageView.setRotation(0);}

                    if((left==0&&down==0)||(left==1&&right==1&&up==1&&down==1&&m[i+1][j-1]==0))
                    {imageView.setImageResource(R.drawable.tile1);imageView.setRotation(90);}

                    if((left==0&&up==0)||(left==1&&right==1&&up==1&&down==1&&m[i+1][j+1]==0))
                    {imageView.setImageResource(R.drawable.tile1);imageView.setRotation(180);}

                    if((right==0&&up==0)||(left==1&&right==1&&up==1&&down==1&&m[i-1][j+1]==0))
                    {imageView.setImageResource(R.drawable.tile1);imageView.setRotation(-90);}
}


                imageView.setX(i*side+50);
                imageView.setY(j*side+100);
                layout.addView(imageView);
            }
        }
        //create new Thread for pacman unit
        Pacman pacman = new Pacman(findViewById(R.id.image), m);
        pacman.start();

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
