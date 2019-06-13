package Menu;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.AudioRouting;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.pacman.Map.Map;
import com.example.pacman.R;

import java.io.IOException;
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

    MediaPlayer pacman_police;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide top panel
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.game_usual);
        ConstraintLayout layout = findViewById(R.id.pacmanLayout);

        pacman_police=MediaPlayer.create(this,R.raw.pacman_chomp);
        pacman_police.start();
pacman_police.setVolume(1000,1000);

pacman_police.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                          @Override
                                          public void onCompletion(MediaPlayer mediaPlayer) {
                                             // pacman_police.stop();
                                              try {
                                                  pacman_police.prepare();
                                              } catch (IOException e) {
                                                  e.printStackTrace();
                                              }
                                              pacman_police.start();
                                          }
                                      });

        //int side = 1000/30;
//        Display display = getWindowManager().getDefaultDisplay();//todo передавати параметр ширини в пакмана??
//        Point size = new Point();
//        display.getSize(size);
//        int width = size.x;
//        int height = size.y;
        int width=1080;
        int side=width/31;
        System.out.println(width);


        //generate map and create black images for walls
        Map map = Map.generateMap();
        int[][] m = map.getMap();

        for (int i = 0; i<m.length;i++){
            for (int j = 0; j<m[i].length;j++){
                ImageView imageView = new ImageView(this);
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(side,side);
                imageView.setLayoutParams(params);


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

                 imageView.setX(i*side);
                imageView.setY(j*side+100);
                layout.addView(imageView);
            }
        }

        //add bonus
        Bonus.Point[][]bonus=map.getBonus();
        for (int i = 0; i<m.length;i++) {
            for (int j = 0; j < m[i].length; j++) {
                ImageView imageView = new ImageView(this);
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(side, side);
                imageView.setLayoutParams(params);

                if(bonus[i][j].getType()==1)imageView.setImageResource(R.drawable.point);else
                if(bonus[i][j].getType()==2)imageView.setImageResource(R.drawable.big_point);

                imageView.setX(i * side);
                imageView.setY(j * side + 100);
                layout.addView(imageView);
            }
        }


        //create new Thread for pacman unit
        Pacman pacman = new Pacman(findViewById(R.id.image), m,this,layout);
        pacman.start();

        layout.setOnTouchListener(new RecordFirstAndLastCoordinatesOnTouchListener());
        layout.setOnClickListener(new ChangeMoveOnClickListener(pacman));
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        pacman_police.release();
//        finish();
//    }

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
