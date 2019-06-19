package Units;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.example.pacman.Map.Map;
import com.example.pacman.R;

import Menu.GameUsualActivity;
import Menu.SettingsActivity;
import Music.MusicThread;


public class Pacman extends Unit {

    MediaPlayer pacman_ch;
    MediaPlayer pacman_fruit;
    MediaPlayer pacman_death;

    GameUsualActivity gUA;

    int scoreBound=50*10+100;//score when berry appears
    int scoreBound2=100*10+100;//score when berry appears 2 time todo remove this parameter

     int scoreDelta=50*10+100;//delta score between 2 berries
    //int scoreDelta=100; //100 is value for tests. 50*10+100 - real value

    Handler handlerBonus;
    Handler handlerScore;
    Handler handlerLives;

    int lives = GameUsualActivity.getLivesStartNumber();

    long starttime = -1;
    long seconds=-1;
    long pauseTime=-1;
    int secondsForBerry=9;//time (in seconds) given to take the berry



    //values in numbers are tested and not accurate(

    public Pacman(ImageView imageView, int[][] map, Handler handler,Handler handlerBonus, Handler handlerScore,Handler handlerLives, GameUsualActivity gUA) {
        super(imageView, map, handler);
        this.handlerBonus=handlerBonus;
        this.handlerScore=handlerScore;
        this.handlerLives=handlerLives;
        this.gUA=gUA;
    }

    @Override
    public void run() {
        //if(Thread.currentThread().isInterrupted()) {return;}
       // if(Unit.getIsPaused()){return;}
        //set pacman animation and start location
        this.getImageView().setBackgroundResource(R.drawable.pacman_move_animation);
        AnimationDrawable pacmanAnimation = (AnimationDrawable) this.getImageView().getBackground();
        this.getImageView().setY(this.getStartY()+100);
        this.getImageView().setX(this.getStartX());
        this.getImageView().bringToFront();
        pacmanAnimation.start();

        pacman_ch=MediaPlayer.create(gUA,R.raw.pacman_chomp);
        pacman_fruit=MediaPlayer.create(gUA,R.raw.pacman_eatfruit);
        pacman_death=MediaPlayer.create(gUA,R.raw.pacman_death);

        MusicThread musicThread=new MusicThread(pacman_ch);
        MusicThread musicThreadFruit=new MusicThread(pacman_fruit);
        MusicThread musicThreadDeath=new MusicThread(pacman_death);


        while (true){
           // if(Unit.getIsPaused()){return;}
            int currX = (Math.round(( this.getImageView().getX())/(1080/26)));
            int currY = Math.round ((( this.getImageView().getY()-100)/(1080/26)));
            map[xMap][yMap] = 0;
            xMap = currX;
            yMap = currY;

if(Map.getBonus()[xMap][yMap]!=null)
            if (Map.getBonus()[xMap][yMap].getType() != 0) {


                if (Map.getBonus()[xMap][yMap].getType()==2){//todo убирать жизнь при проигрыше, а не при бонусе
                   // if(lives!=0)
                    //todo add losing pacman animation
                    lives--;
                    if( SettingsActivity.getSoundEnabled())musicThreadDeath.play();
                    Message msg3 = new Message();
                    msg3.obj = " ";
                    msg3.arg1 = lives;
                    msg3.arg2 = 0;
                    handlerLives.sendMessage(msg3);
//                    if(lives==0) {
//                        gUA.gameLose();
//                    }
                }


                if(Map.getBonus()[xMap][yMap].getType()==3)
                {if(SettingsActivity.getSoundEnabled())musicThreadFruit.play();}else
                {if(SettingsActivity.getSoundEnabled())musicThread.play();}


                Map.setLevelScore(Map.getLevelScore() + Map.getBonus()[xMap][yMap].getScore());
                Map.setOneBonus(xMap, yMap, 0);

                //add berry
                if(Map.getLevelScore()>=scoreBound){
                    scoreBound=Map.getLevelScore()+scoreDelta;
                    //add berry only if there is not another berry
                    if(Map.getBonus()[12][16].getType()!=3){
                        Map.setOneBonus(12,16,3);
                        Message msg=new Message(); msg.obj = xMap + " " + yMap; msg.arg1 = xMap; msg.arg2 = yMap;
                    handlerBonus.sendMessage(msg);
                    starttime=(int)System.currentTimeMillis()/1000;}
                    }
                Message msg = new Message();
                msg.obj = xMap + " " + yMap;
                msg.arg1 = xMap;
                msg.arg2 = yMap;
                this.getHandler().sendMessage(msg);

               // msg.arg1=Map.getTotalScore()+Map.getLevelScore();

                Message msg2 = new Message();
                msg2.obj = " ";
                msg2.arg1 = Map.getTotalScore()+Map.getLevelScore();
                msg2.arg2 = 0;
                handlerScore.sendMessage(msg2);

            }

            if(starttime!=-1) {
                if (GameUsualActivity.getIsPaused()){
                    if (pauseTime == -1) pauseTime = seconds;}
                    else {
                        if(pauseTime!=-1){
                            starttime=((int)System.currentTimeMillis()/1000)-pauseTime;
                            pauseTime=-1;
                        }

                        seconds = ((int) System.currentTimeMillis() / 1000) - starttime;

                        if (seconds >= secondsForBerry) {//remove berry
                            Map.setOneBonus(12, 16, 0);
                            Message msg = new Message();
                            msg.obj = 12 + " " + 16;
                            msg.arg1 = 12;
                            msg.arg2 = 16;
                            this.getHandler().sendMessage(msg);
                            starttime = -1;
                            seconds = -1;
                        }
                    }
            }


        }
    }


}
