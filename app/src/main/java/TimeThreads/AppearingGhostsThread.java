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

    public AppearingGhostsThread(Handler handlerRed, Handler handlerBlue, Handler handlerOrange, Handler handlerPink) {
        this.handlerRed = handlerRed;
        this.handlerBlue = handlerBlue;
        this.handlerOrange = handlerOrange;
        this.handlerPink = handlerPink;
    }

    @Override
    public void run() {
        int bonusNum = Map.getBonusNumber();
        handlerRed.sendMessage(new Message());
        while (bonusNum-Map.getBonusNumber()<bonusNum/10) {}
        handlerPink.sendMessage(new Message());
        while (bonusNum-Map.getBonusNumber()<bonusNum/4) {}
        handlerBlue.sendMessage(new Message());
        while (bonusNum-Map.getBonusNumber()<bonusNum/1.7) {}
        handlerOrange.sendMessage(new Message());
    }
}
