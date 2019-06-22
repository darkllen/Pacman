package TimeThreads;

import android.os.Handler;
import android.os.Message;



import Menu.GameUsualActivity;
import Menu.Listeners.GhostListener;
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

    public boolean stop = false;

    public EatPacman(RedGhost redGhost, BlueGhost blueGhost, OrangeGhost orangeGhost, PinkGhost pinkGhost, Pacman pacman, Handler handler, Handler handlerRed, Handler handlerBlue, Handler handlerOrange, Handler handlerPink) {
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
    }

    @Override
    public void run() {
        while (true){
            if (stop){
                return;
            }
            int lives = GameUsualActivity.getLivesStartNumber();
            int currX = (Math.round(( redGhost.getImageView().getX())/(1080/26)));
            int currY = Math.round ((( redGhost.getImageView().getY()-100)/(1080/26)));
            if (redGhost.getMap()[currX][currY]==2){
                if (GhostListener.isCanEat()){
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
