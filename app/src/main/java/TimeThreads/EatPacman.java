package TimeThreads;

import android.os.Handler;
import android.os.Message;



import Menu.GameUsualActivity;
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

    public boolean stop = false;
    public EatPacman(RedGhost redGhost, BlueGhost blueGhost, OrangeGhost orangeGhost, PinkGhost pinkGhost, Pacman pacman, Handler handler) {
        this.redGhost = redGhost;
        this.blueGhost = blueGhost;
        this.orangeGhost = orangeGhost;
        this.pinkGhost = pinkGhost;
        this.pacman = pacman;
        this.handler = handler;
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
                redGhost.getMap()[currX][currY]=0;
                lives--;
                Message msg3 = new Message();
                msg3.obj = " ";
                msg3.arg1 = lives;
                msg3.arg2 = 0;
                handler.sendMessage(msg3);
                try{
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
             currX = (Math.round(( pinkGhost.getImageView().getX())/(1080/26)));
             currY = Math.round ((( pinkGhost.getImageView().getY()-100)/(1080/26)));
            if (pinkGhost.getMap()[currX][currY]==2){
                pinkGhost.getMap()[currX][currY]=0;
                lives--;
                Message msg3 = new Message();
                msg3.obj = " ";
                msg3.arg1 = lives;
                msg3.arg2 = 0;
                handler.sendMessage(msg3);
                try{
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            currX = (Math.round(( orangeGhost.getImageView().getX())/(1080/26)));
            currY = Math.round ((( orangeGhost.getImageView().getY()-100)/(1080/26)));
            if (orangeGhost.getMap()[currX][currY]==2){
                orangeGhost.getMap()[currX][currY]=0;
                lives--;
                Message msg3 = new Message();
                msg3.obj = " ";
                msg3.arg1 = lives;
                msg3.arg2 = 0;
                handler.sendMessage(msg3);
                try{
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            currX = (Math.round(( blueGhost.getImageView().getX())/(1080/26)));
            currY = Math.round ((( blueGhost.getImageView().getY()-100)/(1080/26)));
            if (blueGhost.getMap()[currX][currY]==2){
                blueGhost.getMap()[currX][currY]=0;
                lives--;
                Message msg3 = new Message();
                msg3.obj = " ";
                msg3.arg1 = lives;
                msg3.arg2 = 0;
                handler.sendMessage(msg3);
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
