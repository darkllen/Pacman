package TimeThreads;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;


import com.example.pacman.Map.Map;
import com.example.pacman.R;

import Menu.GameUsualActivity;
import Menu.Listeners.GhostListener;
import Menu.SettingsActivity;
import Music.MusicThread;
import Units.BlueGhost;
import Units.OrangeGhost;
import Units.Pacman;
import Units.PinkGhost;
import Units.RedGhost;

public class EatPacman extends Thread {
    RedGhost redGhost;
    BlueGhost blueGhost;
    OrangeGhost orangeGhost;
    PinkGhost pinkGhost;
    Pacman pacman;

    Handler handler;
    Handler handlerRed;
    Handler handlerBlue;
    Handler handlerOrange;
    Handler handlerPink;

    Handler handlerScore;

    public boolean stop = false;

    GameUsualActivity gUA;

    public EatPacman(RedGhost redGhost, BlueGhost blueGhost, OrangeGhost orangeGhost, PinkGhost pinkGhost, Pacman pacman, Handler handler, Handler handlerRed, Handler handlerBlue,
                     Handler handlerOrange, Handler handlerPink, Handler handlerScore,GameUsualActivity gUA) {
        this.redGhost = redGhost;
        this.blueGhost = blueGhost;
        this.orangeGhost = orangeGhost;
        this.pinkGhost = pinkGhost;
        this.pacman = pacman;
        this.handler = handler;
        this.handlerRed = handlerRed;
        this.handlerBlue = handlerBlue;
        this.handlerOrange = handlerOrange;
        this.handlerPink = handlerPink;
        this.handlerScore = handlerScore;
        this.gUA=gUA;

    }

    @Override
    public void run() {

        MediaPlayer pacman_eatGhost=MediaPlayer.create(gUA, R.raw.pacman_eatghost);
        MusicThread musicThread=new MusicThread(pacman_eatGhost);
        while (true){
            if (stop){
                return;
            }
            int lives = GameUsualActivity.getLivesStartNumber();
            int currX = (Math.round(( redGhost.getImageView().getX())/(1080/26)));
            int currY = Math.round ((( redGhost.getImageView().getY()-100)/(1080/26)));
            if (redGhost.getMap()[currX][currY]==2){
                if (GhostListener.isCanEat()){
                    if(SettingsActivity.getSoundEnabled())musicThread.play();
                    Map.setLevelScore(Map.getLevelScore() + 100);
                    Message msg2 = new Message();
                    msg2.obj = " ";
                    msg2.arg1 = Map.getTotalScore()+Map.getLevelScore();
                    msg2.arg2 = 0;
                    handlerScore.sendMessage(msg2);
                    handlerRed.sendMessage(new Message());
                    try{
                        sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                redGhost.getMap()[currX][currY]=0;
                lives--;
                Message msg3 = new Message();
                msg3.obj = " ";
                msg3.arg1 = lives;
                msg3.arg2 = 0;
                handler.sendMessage(msg3);}
                try{
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
             currX = (Math.round(( pinkGhost.getImageView().getX())/(1080/26)));
             currY = Math.round ((( pinkGhost.getImageView().getY()-100)/(1080/26)));
            if (pinkGhost.getMap()[currX][currY]==2){
                if (GhostListener.isCanEat()){
                    if(SettingsActivity.getSoundEnabled())musicThread.play();
                    Map.setLevelScore(Map.getLevelScore() + 100);
                    Message msg2 = new Message();
                    msg2.obj = " ";
                    msg2.arg1 = Map.getTotalScore()+Map.getLevelScore();
                    msg2.arg2 = 0;
                    handlerScore.sendMessage(msg2);
                    handlerPink.sendMessage(new Message());
                    try{
                        sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                pinkGhost.getMap()[currX][currY]=0;
                lives--;
                Message msg3 = new Message();
                msg3.obj = " ";
                msg3.arg1 = lives;
                msg3.arg2 = 0;
                handler.sendMessage(msg3);}
                try{
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            currX = (Math.round(( orangeGhost.getImageView().getX())/(1080/26)));
            currY = Math.round ((( orangeGhost.getImageView().getY()-100)/(1080/26)));
            if (orangeGhost.getMap()[currX][currY]==2){
                if (GhostListener.isCanEat()){
                    if(SettingsActivity.getSoundEnabled()) musicThread.play();
                    Map.setLevelScore(Map.getLevelScore() + 100);
                    Message msg2 = new Message();
                    msg2.obj = " ";
                    msg2.arg1 = Map.getTotalScore()+Map.getLevelScore();
                    msg2.arg2 = 0;
                    handlerScore.sendMessage(msg2);
                    handlerOrange.sendMessage(new Message());
                    try{
                        sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                orangeGhost.getMap()[currX][currY]=0;
                lives--;
                Message msg3 = new Message();
                msg3.obj = " ";
                msg3.arg1 = lives;
                msg3.arg2 = 0;
                handler.sendMessage(msg3);}
                try{
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            currX = (Math.round(( blueGhost.getImageView().getX())/(1080/26)));
            currY = Math.round ((( blueGhost.getImageView().getY()-100)/(1080/26)));
            if (blueGhost.getMap()[currX][currY]==2){
                if (GhostListener.isCanEat()){
                    if(SettingsActivity.getSoundEnabled())musicThread.play();
                    Map.setLevelScore(Map.getLevelScore() + 100);
                    Message msg2 = new Message();
                    msg2.obj = " ";
                    msg2.arg1 = Map.getTotalScore()+Map.getLevelScore();
                    msg2.arg2 = 0;
                    handlerScore.sendMessage(msg2);
                    handlerBlue.sendMessage(new Message());
                    try{
                        sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                blueGhost.getMap()[currX][currY]=0;
                lives--;
                Message msg3 = new Message();
                msg3.obj = " ";
                msg3.arg1 = lives;
                msg3.arg2 = 0;
                handler.sendMessage(msg3);}
                try{
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            try{
                sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
