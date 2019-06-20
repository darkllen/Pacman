package TimeThreads;


import android.os.Handler;
import android.os.Message;

import com.example.pacman.Map.Map;

import Units.PinkGhost;

public class AppearingGhostsThread extends Thread {
    Handler handlerRed;
    Handler handlerBlue;
    Handler handlerOrange;
    Handler handlerPink;

    public boolean stop = false;

    public AppearingGhostsThread(Handler handlerRed, Handler handlerBlue, Handler handlerOrange, Handler handlerPink) {
        this.handlerRed = handlerRed;
        this.handlerBlue = handlerBlue;
        this.handlerOrange = handlerOrange;
        this.handlerPink = handlerPink;
    }

    @Override
    public void run() {
        int bonusNum = Map.getBonusNumber();
        while (bonusNum-Map.getBonusNumber()<2) {
            if (stop){
                return;
            }
        }
        handlerRed.sendMessage(new Message());
        while (bonusNum-Map.getBonusNumber()<bonusNum/10) {
            if (stop){
                return;
            }
        }
        handlerPink.sendMessage(new Message());
        while (bonusNum-Map.getBonusNumber()<bonusNum/4) {
            if (stop){
                return;
            }
        }
        handlerBlue.sendMessage(new Message());
        while (bonusNum-Map.getBonusNumber()<bonusNum/1.7) {
            if (stop){
                return;
            }
        }
        handlerOrange.sendMessage(new Message());
    }
}
