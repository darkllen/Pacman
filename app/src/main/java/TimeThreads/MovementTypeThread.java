package TimeThreads;

import android.os.Handler;
import android.os.Message;

import Menu.Listeners.GhostListener;

public class MovementTypeThread extends Thread {
    Handler red;
    Handler blue;
    Handler orange;
    Handler pink;

    public MovementTypeThread(Handler red, Handler blue, Handler orange, Handler pink) {
        this.red = red;
        this.blue = blue;
        this.orange = orange;
        this.pink = pink;
    }

    public boolean stop = false;
    @Override
    public void run() {
        while (true){
            try {
                sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GhostListener.setMovementType(2);
            try {
                sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GhostListener.setMovementType(1);
            try {
                sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GhostListener.setMovementType(2);
            try {
                sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GhostListener.setMovementType(1);
            try {
                sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GhostListener.setMovementType(2);
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GhostListener.setMovementType(1);
            try {
                sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GhostListener.setMovementType(2);
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GhostListener.setMovementType(1);
        }
    }
}
