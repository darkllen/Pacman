package Music;


import android.media.MediaPlayer;



public class MusicThread implements Runnable
{

    private MediaPlayer mP;
    public MusicThread(MediaPlayer mP) {this.mP=mP; }

    public void play()
    {
        Thread t = new Thread(this);
        t.start();
    }

    public void run()
    {
        //todo remove thy catch????
                    try {
                Thread.sleep( 10 );
            } catch ( InterruptedException e ) {
                return;
            }
        playSound(mP);
    }

    private void playSound(MediaPlayer mP)
    {
         mP.start();
    }
}

//package Music;
//
//
//import android.media.MediaPlayer;
//
//public class MusicThread extends Thread {
////    Minim minim;
////    AudioPlayer player;
//    MediaPlayer mediaPlayer;
//    boolean quit;
//    boolean playNow;
//
//    public MusicThread(MediaPlayer mediaPlayer) {
//        this.mediaPlayer=mediaPlayer;
//        quit    = false;
//        playNow = false;
//    }
//
//    public void playNow() {
//        playNow = true;
//    }
//
//    public void quit() {
//        quit = true;
//    }
//
//    public void run() {
//        while ( !quit ) {
//            // wait 10 ms, then check if need to play
//            try {
//                Thread.sleep( 10 );
//            } catch ( InterruptedException e ) {
//                return;
//            }
//
//            // if we have to play the sound, do it!
//            if ( playNow ) {
//                playNow = false;
//                mediaPlayer.start();
////                player.play();
////                player.rewind();
//            }
//
//            // go back and wait again for 10 ms...
//        }
//    }
//}
