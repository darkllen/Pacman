package Units;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.example.pacman.Map.Map;
import com.example.pacman.R;


public class Pacman extends Unit {


    //values in numbers are tested and not accurate(

    public Pacman(ImageView imageView, int[][] map,  Handler handler) {
        super(imageView, map, handler);

    }

    @Override
    public void run() {
        //set pacman animation and start location
        this.getImageView().setBackgroundResource(R.drawable.pacman_move_animation);
        AnimationDrawable pacmanAnimation = (AnimationDrawable) this.getImageView().getBackground();
        this.getImageView().setY(this.getStartY()+100);
        this.getImageView().setX(this.getStartX());
        this.getImageView().bringToFront();
        pacmanAnimation.start();



        while (true){
            int currX = (Math.round(( this.getImageView().getX())/(1080/25)));
            int currY = Math.round ((( this.getImageView().getY()-100)/(1080/25)));
            map[xMap][yMap] = 0;
            xMap = currX;
            yMap = currY;
            if (Map.getBonus()[xMap][yMap].getType() != 0) {
                Map.setScore(Map.getScore() + Map.getBonus()[xMap][yMap].getScore());
                Map.setOneBonus(xMap, yMap, 0);
                Message msg = new Message();
                msg.obj = xMap + " " + yMap;
                msg.arg1 = xMap;
                msg.arg2 = yMap;
                this.getHandler().sendMessage(msg);

            }
        }
    }

}
