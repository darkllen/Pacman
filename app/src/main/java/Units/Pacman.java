package Units;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.example.pacman.Map.Map;
import com.example.pacman.R;

import Menu.GameUsualActivity;
import Music.MusicThread;


public class Pacman extends Unit {

    MediaPlayer pacman_ch;
    GameUsualActivity gUA;

    int scoreBound=50*10+100;//score when berry appears
    int scoreBound2=100*10+100;//score when berry appears 2 time todo remove this parameter

    // int scoreDelta=50*10+100;//delta score between 2 berries
    int scoreDelta=100; //100 is value for tests. 50*10+100 - real value

    Handler handlerBonus;


    //values in numbers are tested and not accurate(

    public Pacman(ImageView imageView, int[][] map, Handler handler,Handler handlerBonus, GameUsualActivity gUA) {
        super(imageView, map, handler);
        this.handlerBonus=handlerBonus;
        this.gUA=gUA;
    }

    @Override
    public void run() {
        //set pacman animation and start location
        this.getImageView().setBackgroundResource(R.drawable.pacman_move_animation);
        AnimationDrawable pacmanAnimation = (AnimationDrawable) this.getImageView().getBackground();
        this.getImageView().setY(this.getStartY()+100);
        this.getImageView().setX(this.getStartX());
        this.getImageView().bringToFront();
        pacmanAnimation.start();

        pacman_ch=MediaPlayer.create(gUA,R.raw.pacman_chomp);

        MusicThread musicThread=new MusicThread(pacman_ch);


        while (true){
            int currX = (Math.round(( this.getImageView().getX())/(1080/26)));
            int currY = Math.round ((( this.getImageView().getY()-100)/(1080/26)));
            map[xMap][yMap] = 0;
            xMap = currX;
            yMap = currY;
            if (Map.getBonus()[xMap][yMap].getType() != 0) {
                musicThread.play();

                Map.setLevelScore(Map.getLevelScore() + Map.getBonus()[xMap][yMap].getScore());
                Map.setOneBonus(xMap, yMap, 0);

                //add berry
                if(Map.getLevelScore()>=scoreBound){
                    //scoreBound=scoreBound2; wrong variant with 2 scores
                    //scoreBound+=scoreDelta;  sometimes working variant with delta
                    scoreBound=Map.getLevelScore()+scoreDelta; //more right but also SOMETIMES working variant:(
                    //add berry only if there is not another berry
                    if(Map.getBonus()[12][16].getType()!=3){
                        Map.setOneBonus(12,16,3);
                        Message msg=new Message(); msg.obj = xMap + " " + yMap; msg.arg1 = xMap; msg.arg2 = yMap;
                    handlerBonus.sendMessage(msg);}
                    }
                Message msg = new Message();
                msg.obj = xMap + " " + yMap;
                msg.arg1 = xMap;
                msg.arg2 = yMap;
                this.getHandler().sendMessage(msg);


            }


        }
    }


}
