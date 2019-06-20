package TimeThreads;

import Menu.Listeners.GhostListener;


public class MovementTypeThread extends Thread {
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
