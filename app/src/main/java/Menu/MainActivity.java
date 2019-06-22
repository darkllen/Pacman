package Menu;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;


import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.widget.Button;
import android.widget.ImageView;



import com.example.pacman.R;

import java.util.Objects;



public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREFS="sharedPrefs";
    public static final String LANGUAGE="language";
    public static final String MUSIC="true";
    public static final String SOUND="1";

    //initial class
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        Button game = findViewById(R.id.game);
        Button records = findViewById(R.id.records);
        Button settings = findViewById(R.id.settings);

//        SettingsActivity sA=new SettingsActivity();
//        sA.loadData();
        loadData();

        if(SettingsActivity.getLanguage().equals("English")){
            game.setText("Game");
            records.setText("Records");
            settings.setText("Settings");

        }
        if(SettingsActivity.getLanguage().equals("Ukranian")){
            game.setText("Гра");
            records.setText("Рекорди");
            settings.setText("Налаштування");
        }
        //open menus on button click
        game.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, GameModsActivity.class);
            startActivity(intent);
        });
        records.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, RecordsActivity.class);
            startActivity(intent);
        });
        settings.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        ConstraintLayout layout = findViewById(R.id.activityMain);

        ImageView pacman = layout.findViewById(R.id.imageMain);
        pacman.setBackgroundResource(R.drawable.pacman_move_animation);
        AnimationDrawable pacmanAnimation = (AnimationDrawable) pacman.getBackground();
        pacmanAnimation.start();
        pacman.setY(1800);

        ImageView redGhost = layout.findViewById(R.id.redGhostMain);
        redGhost.setBackgroundResource(R.drawable.invisible);
        AnimationDrawable redGhostAnimation = (AnimationDrawable) redGhost.getBackground();
        redGhostAnimation.start();
        redGhost.setY(1800);
        redGhost.setX(150);

        ImageView blueGhost = layout.findViewById(R.id.blueGhostMain);
        blueGhost.setBackgroundResource(R.drawable.invisible);
        AnimationDrawable blueGhostAnimation = (AnimationDrawable) blueGhost.getBackground();
        blueGhostAnimation.start();
        blueGhost.setY(1800);
        blueGhost.setX(220);


        ImageView orangeGhost = layout.findViewById(R.id.orangeGhostMain);
        orangeGhost.setBackgroundResource(R.drawable.invisible);
        AnimationDrawable orangeGhostAnimation = (AnimationDrawable) orangeGhost.getBackground();
        orangeGhostAnimation.start();
        orangeGhost.setY(1800);
        orangeGhost.setX(290);


        ImageView pinkGhost = layout.findViewById(R.id.pinkGhostMain);
        pinkGhost.setBackgroundResource(R.drawable.invisible);
        AnimationDrawable pinkGhostAnimation = (AnimationDrawable) pinkGhost.getBackground();
        pinkGhostAnimation.start();
        pinkGhost.setY(1800);
        pinkGhost.setX(360);


        AnimatorSet setPacmanFirst = new AnimatorSet();
        Animator animatorPacman = ObjectAnimator.ofFloat(pacman, "X", -450, 1080+360);
        setPacmanFirst.addListener(new Animator.AnimatorListener() {
            int i = 0;
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (i%2==0){
                    i++;
                    pacman.setX(-450);
                    pacman.setRotation(180);
                    Animator animatorPacmanSecond = ObjectAnimator.ofFloat(pacman, "X", 1080+360, -450);
                    setPacmanFirst.play(animatorPacmanSecond);
                    setPacmanFirst.start();
                }else {
                    i++;
                    pacman.setX(1080+360);
                    pacman.setRotation(0);
                    Animator animatorPacman = ObjectAnimator.ofFloat(pacman, "X", -450, 1080+360);
                    setPacmanFirst.play(animatorPacman);
                    setPacmanFirst.start();
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        setPacmanFirst.play(animatorPacman);
        setPacmanFirst.setDuration(5000);
        setPacmanFirst.start();


        AnimatorSet setBlueGhostFirst = new AnimatorSet();
        Animator animatorBlueGhost = ObjectAnimator.ofFloat(blueGhost, "X", -400+140+50, 1080+360+140);
        setBlueGhostFirst.addListener(new Animator.AnimatorListener() {
            int i = 0;
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (i%2==0){
                    i++;
                    blueGhost.setX(-400+140+50);
                    blueGhost.setBackgroundResource(R.drawable.blue_left);
                    AnimationDrawable blueGhostAnimation = (AnimationDrawable) blueGhost.getBackground();
                    blueGhostAnimation.start();
                    Animator animatorBlueGhostSecond = ObjectAnimator.ofFloat(blueGhost, "X", 1080+360+140, -400+140+50);
                    setBlueGhostFirst.play(animatorBlueGhostSecond);
                    setBlueGhostFirst.start();
                }else {
                    i++;
                    blueGhost.setX(1080+360+140);
                    blueGhost.setBackgroundResource(R.drawable.invisible);
                    AnimationDrawable blueGhostAnimation = (AnimationDrawable) blueGhost.getBackground();
                    blueGhostAnimation.start();
                    Animator animatorPacman = ObjectAnimator.ofFloat(blueGhost, "X",  -400+140+50, 1080+360+140);
                    setBlueGhostFirst.play(animatorPacman);
                    setBlueGhostFirst.start();
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        setBlueGhostFirst.play(animatorBlueGhost);
        setBlueGhostFirst.setDuration(5000);
        setBlueGhostFirst.start();

        AnimatorSet setRedGhostFirst = new AnimatorSet();
        Animator animatorRedGhost = ObjectAnimator.ofFloat(redGhost, "X", -400+70+50, 1080+360+70);
        setRedGhostFirst.addListener(new Animator.AnimatorListener() {
            int i = 0;
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (i%2==0){
                    i++;
                    redGhost.setX(-400+70+50);
                    redGhost.setBackgroundResource(R.drawable.red_left);
                    AnimationDrawable redGhostAnimation = (AnimationDrawable) redGhost.getBackground();
                    redGhostAnimation.start();
                    Animator animatorRedGhostSecond = ObjectAnimator.ofFloat(redGhost, "X", 1080+360+70, -400+70+50);
                    setRedGhostFirst.play(animatorRedGhostSecond);
                    setRedGhostFirst.start();
                }else {
                    i++;
                    redGhost.setX(1080+360+70);
                    redGhost.setBackgroundResource(R.drawable.invisible);
                    AnimationDrawable redGhostAnimation = (AnimationDrawable) redGhost.getBackground();
                    redGhostAnimation.start();
                    Animator animatorPacman = ObjectAnimator.ofFloat(redGhost, "X",  -400+70+50, 1080+360+70);
                    setRedGhostFirst.play(animatorPacman);
                    setRedGhostFirst.start();
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        setRedGhostFirst.play(animatorRedGhost);
        setRedGhostFirst.setDuration(5000);
        setRedGhostFirst.start();



        AnimatorSet setOrangeGhostFirst = new AnimatorSet();
        Animator animatorOrangeGhost = ObjectAnimator.ofFloat(orangeGhost, "X", -400+210+50, 1080+360+210);
        setOrangeGhostFirst.addListener(new Animator.AnimatorListener() {
            int i = 0;
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (i%2==0){
                    i++;
                    orangeGhost.setX(-400+210+50);
                    orangeGhost.setBackgroundResource(R.drawable.orange_left);
                    AnimationDrawable orangeGhostAnimation = (AnimationDrawable) orangeGhost.getBackground();
                    orangeGhostAnimation.start();
                    Animator animatorOrangeGhostSecond = ObjectAnimator.ofFloat(orangeGhost, "X", 1080+360+210, -400+210+50);
                    setOrangeGhostFirst.play(animatorOrangeGhostSecond);
                    setOrangeGhostFirst.start();
                }else {
                    i++;
                    orangeGhost.setX(1080+360+210);
                    orangeGhost.setBackgroundResource(R.drawable.invisible);
                    AnimationDrawable orangeGhostAnimation = (AnimationDrawable) orangeGhost.getBackground();
                    orangeGhostAnimation.start();
                    Animator animatorPacman = ObjectAnimator.ofFloat(orangeGhost, "X",  -400+210+50, 1080+360+210);
                    setOrangeGhostFirst.play(animatorPacman);
                    setOrangeGhostFirst.start();
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        setOrangeGhostFirst.play(animatorOrangeGhost);
        setOrangeGhostFirst.setDuration(5000);
        setOrangeGhostFirst.start();


        AnimatorSet setPinkGhostFirst = new AnimatorSet();
        Animator animatorPinkGhost = ObjectAnimator.ofFloat(pinkGhost, "X", -400+280+50, 1080+360+280);
        setPinkGhostFirst.addListener(new Animator.AnimatorListener() {
            int i = 0;
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (i%2==0){
                    i++;
                    pinkGhost.setX(-400+280+50);
                    pinkGhost.setBackgroundResource(R.drawable.pink_left);
                    AnimationDrawable orangeGhostAnimation = (AnimationDrawable) pinkGhost.getBackground();
                    orangeGhostAnimation.start();
                    Animator animatorOrangeGhostSecond = ObjectAnimator.ofFloat(pinkGhost, "X", 1080+360+280, -400+280+50);
                    setPinkGhostFirst.play(animatorOrangeGhostSecond);
                    setPinkGhostFirst.start();
                }else {
                    i++;
                    pinkGhost.setX(1080+360+280);
                    pinkGhost.setBackgroundResource(R.drawable.invisible);
                    AnimationDrawable orangeGhostAnimation = (AnimationDrawable) pinkGhost.getBackground();
                    orangeGhostAnimation.start();
                    Animator animatorPacman = ObjectAnimator.ofFloat(pinkGhost, "X",  -400+280+50, 1080+360+280);
                    setPinkGhostFirst.play(animatorPacman);
                    setPinkGhostFirst.start();
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        setPinkGhostFirst.play(animatorPinkGhost);
        setPinkGhostFirst.setDuration(5000);
        setPinkGhostFirst.start();

    }

    public void loadData(){
        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SettingsActivity.setLanguage(sharedPreferences.getString(LANGUAGE,"English"));
        SettingsActivity.setMusicEnabled(sharedPreferences.getBoolean(MUSIC,true));
        int i=sharedPreferences.getInt(SOUND,1);
        if(i==1)SettingsActivity.setSoundEnabled(true);else SettingsActivity.setSoundEnabled(false);
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
