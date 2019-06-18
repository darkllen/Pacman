package TimeThreads;


import android.os.Handler;
import android.os.Message;

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

        handlerRed.sendMessage(new Message());
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        handlerPink.sendMessage(new Message());
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        handlerOrange.sendMessage(new Message());
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        handlerBlue.sendMessage(new Message());


    }
}
