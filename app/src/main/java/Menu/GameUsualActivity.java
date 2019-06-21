package Menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.ArraySet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pacman.Map.Map;
import com.example.pacman.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import Menu.Listeners.GhostListener;
import TimeThreads.AppearingGhostsThread;
import TimeThreads.EatPacman;
import TimeThreads.MovementTypeThread;
import Units.BlueGhost;
import Units.OrangeGhost;
import Units.Pacman;
import Units.PinkGhost;
import Units.RedGhost;
import Units.Unit;


//class for classic game
public class GameUsualActivity extends AppCompatActivity {
    //additional variables for define swap
    final int[] clickX = {0};
    final int[] clickY = {0};
    final int[] clickStartX = {0};
    final int[] clickStartY = {0};
    final boolean[] first = {true};
    //1 - move right, 2- move left, 3 - move down, 4 - move up
    final int[] click = {-1};

    MediaPlayer pacman_police;
    MediaPlayer pacman_begin;

    private Handler handlerPacman;
    private Handler handlerBonus;
    private Handler handlerScore;
    private Handler handlerLives;

    private Handler handlerRed;
    private Handler handlerBlue;
    private Handler handlerOrange;
    private Handler handlerPink;

    private Handler handlerRedChangeMove;
    private Handler handlerBlueChangeMove;
    private Handler handlerOrangeChangeMove;
    private Handler handlerPinkChangeMove;

    private Handler nextLevelHandler;

    Map map;

    private ImageView imageBerryView;

    private TextView scoreTextView;
    // private TextView livesTextView;
    private TextView pauseTextView;

    private static int livesStartNumber = 3;

    private Button pauseButton;
    private Button menuButton;
    private static boolean isPaused = false;

    private boolean lose=false;
   // private int loseMenu=0;

    Pacman pacman;
    RedGhost redGhost;
    OrangeGhost orangeGhost;
    PinkGhost pinkGhost;
    BlueGhost blueGhost;

    int bonusNum;

    AppearingGhostsThread thread = null;

    public static int level;

    private Set<String> records=new HashSet<>();

    public static final String SHARED_PREFS="sharedPrefs";
    public static final String LANGUAGE="language";
    public static final String MUSIC="true";
    public static final String SOUND="1";
    public void loadData() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SettingsActivity.setLanguage(sharedPreferences.getString(LANGUAGE, "English"));
        SettingsActivity.setMusicEnabled(sharedPreferences.getBoolean(MUSIC, true));
        int i = sharedPreferences.getInt(SOUND, 1);
        if (i == 1) SettingsActivity.setSoundEnabled(true);
        else SettingsActivity.setSoundEnabled(false);
        String mode="records";
        if(Unit.inversionMode) mode="recordsInversion";
        records=sharedPreferences.getStringSet(mode,new HashSet<String>());
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint({"ClickableViewAccessibility", "HandlerLeak"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide top panel
        loadData();
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.game_usual);
        ConstraintLayout layout = findViewById(R.id.pacmanLayout);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        Map.setTotalScore(sharedPreferences.getInt("score", 0));
        level = sharedPreferences.getInt("level", 0);
        livesStartNumber = sharedPreferences.getInt("lives", 3);


        menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> {
            SharedPreferences preferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor =preferences.edit();

//            String mode="records";
//            if(Unit.inversionMode) mode="recordsInversion";
////todo if??
//                editor.remove(mode);
//                editor.commit();//}
//            // records=preferences.getStringSet(mode,new HashSet<String>());
//
//
//
//
//            if(records.size()<5){
//                records.add(new Integer(Map.getTotalScore()+Map.getLevelScore()).toString());
//            }
//            else {
//                List<String> sortedList = new ArrayList<String>(records);
//                Collections.sort(sortedList);
//                if(new Integer(sortedList.get(0))<Map.getTotalScore()+Map.getLevelScore()){
//                    sortedList.set(0,Integer.valueOf(Map.getTotalScore()+Map.getLevelScore()).toString());
//                    records=new HashSet<>(sortedList);
//
//                }
//            }
//
//            editor.putStringSet(mode,records);

            editor.putInt("score", 0);
            editor.putInt("level", 0);
            editor.putInt("lives", livesStartNumber);
            editor.apply();
            Intent intent = new Intent(GameUsualActivity.this, MainActivity.class);
            startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
        });

        pacman_begin = MediaPlayer.create(this, R.raw.pacman_beginning);
        if (SettingsActivity.getMusicEnabled()) pacman_begin.start();
        pacman_police = MediaPlayer.create(this, R.raw.pacman_police);


        pacman_begin.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (SettingsActivity.getMusicEnabled()) pacman_police.start();
                if (SettingsActivity.getMusicEnabled())
                    pacman_police.setLooping(true);//todo there is a little pause before starting to play again - cut music file (pacman_chomp)
                //pacman_police.setVolume(100,100);//??
            }
        });


        //int side = 1000/30;
//        Display display = getWindowManager().getDefaultDisplay();//todo передавати параметр ширини в пакмана??
//        Point size = new Point();
//        display.getSize(size);
//        int width = size.x;
//        int height = size.y;
        int width = 1080;
        int side = width / 26;

        layout.setBackgroundColor(Color.BLACK);
        //generate map and create black images for walls
        map = Map.generateMap();
        bonusNum = Map.getBonusNumber();
        int[][] m = map.getMap();

        scoreTextView = findViewById(R.id.scoreTextView);
        TextView livesTextView = findViewById(R.id.livesTextView);
        TextView levelTextView=findViewById(R.id.levelTextView);
        pauseButton = findViewById(R.id.pause_button);
        pauseTextView = findViewById(R.id.pauseTextView);

        if (SettingsActivity.getLanguage().equals("English")) {
            scoreTextView.setText("Score = 0");
            livesTextView.setText("Lives:");
            pauseButton.setText("PAUSE");
            pauseTextView.setText("PAUSE");
            menuButton.setText("MENU");
            levelTextView.setText("Level "+level);

        }
        if (SettingsActivity.getLanguage().equals("Ukranian")) {
            scoreTextView.setText("Рахунок = 0");
            livesTextView.setText("Життя:");
            pauseButton.setText("ПАУЗА");
            pauseTextView.setText("ПАУЗА");
            menuButton.setText("МЕНЮ");
            levelTextView.setText("Рівень "+level);
        }

        pauseTextView.setVisibility(View.INVISIBLE);


//        livesTextView.setX(side-5);
//        livesTextView.setY((m[0].length+2) * side + 300);

        ImageView[][] views = new ImageView[m.length][m[1].length];

        imageBerryView = new ImageView(this);
        ConstraintLayout.LayoutParams paramss = new ConstraintLayout.LayoutParams(side, side);
        imageBerryView.setLayoutParams(paramss);
        imageBerryView.setX(12 * side);
        imageBerryView.setY(16 * side + 100);

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                ImageView imageView = new ImageView(this);

                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(side, side);
                imageView.setLayoutParams(params);


                // add images
                int left, right, up, down;//numbers on the sides
                boolean p = true;

                //границі
                if (i == 1 || i == m.length - 2) {
                    imageView.setImageResource(R.drawable.tile2);
                    imageView.setRotation(0);
                    p = false;
                }
                if (j == 0 || j == m[0].length - 1) {
                    imageView.setImageResource(R.drawable.tile2);
                    imageView.setRotation(90);
                    p = false;
                }
                //кути
                if (i == 1 && j == m[0].length - 1) {
                    imageView.setImageResource(R.drawable.tile1);
                    imageView.setRotation(90);
                    p = false;
                }
                if (i == m.length - 2 && j == 0) {
                    imageView.setImageResource(R.drawable.tile1);
                    imageView.setRotation(270);
                    p = false;
                }
                if (i == 1 && j == 0) {
                    imageView.setImageResource(R.drawable.tile1);
                    imageView.setRotation(180);
                    p = false;
                }
                if (i == m.length - 2 && j == m[0].length - 1) {
                    imageView.setImageResource(R.drawable.tile1);
                    imageView.setRotation(0);
                    p = false;
                }

                //поворот в тунель
                if (p == false) {
                    if (i == 1 && m[0][j] == 1 && m[1][j + 1] == -1) {
                        imageView.setImageResource(R.drawable.tile1);
                        imageView.setRotation(0);
                    }
                    if (i == 1 && m[0][j] == 1 && m[1][j - 1] == -1) {
                        imageView.setImageResource(R.drawable.tile1);
                        imageView.setRotation(-90);
                    }
                    if (i == m.length - 2 && m[m.length - 1][j] == 1 && m[m.length - 2][j - 1] == -1) {
                        imageView.setImageResource(R.drawable.tile1);
                        imageView.setRotation(180);
                    }
                    if (i == m.length - 2 && m[m.length - 1][j] == 1 && m[m.length - 2][j + 1] == -1) {
                        imageView.setImageResource(R.drawable.tile1);
                        imageView.setRotation(90);
                    }


                }

                //порожні клітинки
                if (m[i][j] == 0 || m[i][j] == -1) {
                    imageView.setImageResource(R.drawable.tile3);
                }
                //порожні клітинки та тунель
                if (i == 0 || i == m.length - 1) {
                    p = false;
                    if (m[i][j] == 1) {
                        imageView.setImageResource(R.drawable.tile2);
                        imageView.setRotation(90);
                    }
                }
                //непорожні клітинки
                if (m[i][j] == 1 && p) {
                    left = m[i - 1][j];
                    right = m[i + 1][j];
                    up = m[i][j - 1];
                    down = m[i][j + 1];

                    if (left == -2) left = 1;
                    if (right == -2) right = 1;
                    if (down == -2) down = 1;
                    if (up == -2) up = 1;

                    //полостки
                    if (left == 1 && right == 1) {
                        imageView.setImageResource(R.drawable.tile2);
                        imageView.setRotation(90);
                    }

                    if (up == 1 && down == 1) {
                        imageView.setImageResource(R.drawable.tile2);
                        imageView.setRotation(0);
                    }

                    //пусті місця всередині
                    //if(left==1&&right==1&&up==1&&down==1&&m[i-1][j-1]==0&&m[i+1][j-1]==0&&m[i-1][j+1]==0&&m[i+1][j+1]==0)
                    if (left == 1 && right == 1 && up == 1 && down == 1) {
                        imageView.setImageResource(R.drawable.tile3);
                        imageView.setRotation(0);
                    }

                    //кути
                    if ((right == 0 && down == 0) || (left == 1 && right == 1 && up == 1 && down == 1 && m[i - 1][j - 1] == 0)) {
                        imageView.setImageResource(R.drawable.tile1);
                        imageView.setRotation(0);
                    }

                    if ((left == 0 && down == 0) || (left == 1 && right == 1 && up == 1 && down == 1 && m[i + 1][j - 1] == 0)) {
                        imageView.setImageResource(R.drawable.tile1);
                        imageView.setRotation(90);
                    }

                    if ((left == 0 && up == 0) || (left == 1 && right == 1 && up == 1 && down == 1 && m[i + 1][j + 1] == 0)) {
                        imageView.setImageResource(R.drawable.tile1);
                        imageView.setRotation(180);
                    }

                    if ((right == 0 && up == 0) || (left == 1 && right == 1 && up == 1 && down == 1 && m[i - 1][j + 1] == 0)) {
                        imageView.setImageResource(R.drawable.tile1);
                        imageView.setRotation(-90);
                    }
                }
                //вхід до монстрів
                if (m[i][j] == -2) {
                    imageView.setImageResource(R.drawable.tile4);
                    m[i][j] = 1;
                }

                imageView.setX(i * side);
                imageView.setY(j * side + 100);
                layout.addView(imageView);
            }
        }

        //add bonus
        Bonus.Point[][] bonus = map.getBonus();
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                ImageView imageView = new ImageView(this);
                views[i][j] = imageView;
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(side, side);
                imageView.setLayoutParams(params);

                if (bonus[i][j].getType() == 1) imageView.setImageResource(R.drawable.point);
                else if (bonus[i][j].getType() == 2)
                    imageView.setImageResource(R.drawable.big_point);

                imageView.setX(i * side);
                imageView.setY(j * side + 100);
                layout.addView(imageView);
            }
        }

        //add pacman lives
        ImageView[] lives = new ImageView[livesStartNumber];
        for (int i = 0; i < livesStartNumber; i++) {
            ImageView imageView = new ImageView(this);
            lives[i] = imageView;
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(side, side);
            imageView.setLayoutParams(params);
            imageView.setImageResource(R.drawable.pacman4);
            imageView.setX((i + 2) * side + i * 5 + 5 +270);
            imageView.setY((m[i].length + 2) * side + 90);
            layout.addView(imageView);
        }

        handlerPacman = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                layout.removeView(views[msg.arg1][msg.arg2]);
            }
        };

        handlerBonus = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                layout.removeView(views[12][16]);
                generateBonusBerryImage();
                views[12][16] = imageBerryView;
                layout.addView(imageBerryView);
            }
        };

        handlerScore = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                setScoreTextView(msg.arg1);
            }
        };

        handlerLives = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //todo add pacman lose animation
                pacman.getImageView().setBackgroundResource(R.drawable.pacman_lose_animation);
                AnimationDrawable pacmanAnimation = (AnimationDrawable) pacman.getImageView().getBackground();
                pacmanAnimation.start();
                pacman.getImageView().setRotation(0);

                if (msg.arg1 >= 0) layout.removeView(lives[msg.arg1]);
                if (msg.arg1 == 0) {lose=true;gameLose();}
                else {
                    livesStartNumber--;



                    redGhost.getSet()[0].removeAllListeners();
                    redGhost.getSet()[0].cancel();
                    redGhost.getImageView().setX(1080 / 26 * 13);
                    redGhost.getImageView().setY(1080 / 26 * 12 + 100);
                    redGhost.getMap()[13][11] = 0;
                    redGhost.setAnimatorListener(new GhostListener(redGhost, pacman, m, 1, 0, 0));
                    redGhost.changeMove(4);
                    redGhost.getMap()[13][11] = 1;

                    if (bonusNum - Map.getBonusNumber() >= bonusNum / 10) {
                        pinkGhost.getSet()[0].removeAllListeners();
                        pinkGhost.getSet()[0].cancel();
                        pinkGhost.getImageView().setX(1080 / 26 * 12);
                        pinkGhost.getImageView().setY(1080 / 26 * 12 + 100);
                        pinkGhost.getMap()[12][11] = 0;
                        pinkGhost.setAnimatorListener(new GhostListener(pinkGhost, pacman, m, 1, 4, 0));
                        pinkGhost.changeMove(4);
                        pinkGhost.getMap()[12][11] = 1;
                    }

                    if (bonusNum - Map.getBonusNumber() >= bonusNum / 4) {
                        blueGhost.getSet()[0].removeAllListeners();
                        blueGhost.getSet()[0].cancel();
                        blueGhost.getImageView().setX(1080 / 26 * 12);
                        blueGhost.getImageView().setY(1080 / 26 * 12 + 100);
                        blueGhost.getMap()[12][11] = 0;
                        blueGhost.setAnimatorListener(new GhostListener(blueGhost, pacman, m, 1, -2, 0, redGhost));
                        blueGhost.changeMove(4);
                        blueGhost.getMap()[12][11] = 1;
                    }

                    if (bonusNum - Map.getBonusNumber() >= bonusNum / 1.7) {
                        orangeGhost.getSet()[0].removeAllListeners();
                        orangeGhost.getSet()[0].cancel();
                        orangeGhost.getImageView().setX(1080 / 26 * 13);
                        orangeGhost.getImageView().setY(1080 / 26 * 12 + 100);
                        orangeGhost.getMap()[13][11] = 0;
                        orangeGhost.setAnimatorListener(new GhostListener(orangeGhost, pacman, m, 1, 0, 0));
                        orangeGhost.changeMove(4);
                        orangeGhost.getMap()[13][11] = 1;
                    }
                    lose=true;
                    PausePushed(pauseButton);
                }
            }
        };

        nextLevelHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                SharedPreferences preferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor =preferences.edit();
                editor.putInt("score", Map.getLevelScore()+Map.getTotalScore());
                editor.putInt("level", ++level);
                editor.putInt("lives", livesStartNumber);
                editor.apply();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(GameUsualActivity.this, GameUsualActivity.class);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        };


        //create new Thread for pacman unit
        pacman = new Pacman(findViewById(R.id.image), m, handlerPacman, handlerBonus, handlerScore, nextLevelHandler, this);
        pacman.start();
        pacman.getImageView().bringToFront();

        redGhost = new RedGhost(findViewById(R.id.redGhost), m, handlerLives);
        redGhost.start();
        redGhost.getImageView().bringToFront();
        redGhost.setAnimatorListener(new GhostListener(redGhost, pacman, m, 1, 0, 0));
        redGhost.setPrev(4);

        blueGhost = new BlueGhost(findViewById(R.id.blueGhost), m, handlerLives);
        blueGhost.start();
        blueGhost.getImageView().bringToFront();
        blueGhost.setAnimatorListener(new GhostListener(blueGhost, pacman, m, 1, -2, 0, redGhost));
        blueGhost.setPrev(4);

        orangeGhost = new OrangeGhost(findViewById(R.id.orangeGhost), m, handlerLives);
        orangeGhost.start();
        orangeGhost.getImageView().bringToFront();
        orangeGhost.setAnimatorListener(new GhostListener(orangeGhost, pacman, m, 1, 0, 0));
        orangeGhost.setPrev(4);

        pinkGhost = new PinkGhost(findViewById(R.id.pinkGhost), m, handlerLives);
        pinkGhost.start();
        pinkGhost.getImageView().bringToFront();
        pinkGhost.setAnimatorListener(new GhostListener(pinkGhost, pacman, m, 1, 4, 0));
        pinkGhost.setPrev(4);


        handlerRed = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                redGhost.getMap()[13][11] = 0;
                redGhost.changeMove(4);
                redGhost.getMap()[13][11] = 1;
            }
        };

        handlerBlue = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                blueGhost.getMap()[12][11] = 0;
                blueGhost.changeMove(4);
                blueGhost.getMap()[12][11] = 1;
            }
        };

        handlerOrange = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                orangeGhost.getMap()[13][11] = 0;
                orangeGhost.changeMove(4);
                orangeGhost.getMap()[13][11] = 1;
            }
        };
        handlerPink = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                pinkGhost.getMap()[12][11] = 0;
                pinkGhost.changeMove(4);
                pinkGhost.getMap()[12][11] = 1;
            }
        };

        handlerRedChangeMove = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                redGhost.getSet()[0].removeAllListeners();
                redGhost.getSet()[0].cancel();
                redGhost.setAnimatorListener(new GhostListener(redGhost, pacman, m, 1, 0, 0));
                redGhost.changeMove(redGhost.getOppositeMove());
            }
        };
        handlerBlueChangeMove = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                blueGhost.getSet()[0].removeAllListeners();
                blueGhost.getSet()[0].cancel();
                blueGhost.setAnimatorListener(new GhostListener(blueGhost, pacman, m, 1, -2, 0, redGhost));
                blueGhost.changeMove(blueGhost.getOppositeMove());
            }
        };
        handlerOrangeChangeMove = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                orangeGhost.getSet()[0].removeAllListeners();
                orangeGhost.getSet()[0].cancel();
                orangeGhost.setAnimatorListener(new GhostListener(orangeGhost, pacman, m, 1, 0, 0));
                orangeGhost.changeMove(orangeGhost.getOppositeMove());
            }
        };
        handlerPinkChangeMove = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                pinkGhost.getSet()[0].removeAllListeners();
                pinkGhost.getSet()[0].cancel();
                pinkGhost.setAnimatorListener(new GhostListener(pinkGhost, pacman, m, 1, 4, 0));
                pinkGhost.changeMove(pinkGhost.getOppositeMove());
            }
        };


        layout.setOnTouchListener(new RecordFirstAndLastCoordinatesOnTouchListener());
        layout.setOnClickListener(new ChangeMoveOnClickListener(pacman, thread));

        pauseButton.setOnClickListener(x -> {
            PausePushed(x);
        });


        // thread.start();


    }

    public void generateBonusBerryImage() {
        Random r = new Random();
        switch (r.nextInt(4)) {
            case 0:
                imageBerryView.setImageResource(R.drawable.cherry);
                break;
            case 1:
                imageBerryView.setImageResource(R.drawable.berry);
                break;
            case 2:
                imageBerryView.setImageResource(R.drawable.peach);
                break;
            case 3:
                imageBerryView.setImageResource(R.drawable.apple);
                break;
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (SettingsActivity.getMusicEnabled()) pacman_police.pause();
        // stop the clock
    }

    @Override
    public void onResume() {
        super.onResume();
        if (SettingsActivity.getMusicEnabled()) pacman_police.start();
    }

    boolean run = false;

    //listener for record x and y to decide side of swap
    private class RecordFirstAndLastCoordinatesOnTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (first[0]) {
                clickStartX[0] = (int) event.getX();
                clickStartY[0] = (int) event.getY();
                first[0] = false;
            }
            clickX[0] = (int) event.getX();
            clickY[0] = (int) event.getY();
            return false;
        }
    }

    //listener for the end of the swap, that change pacman movement
    private class ChangeMoveOnClickListener implements View.OnClickListener {
        Pacman pacman;


        ChangeMoveOnClickListener(Pacman pacman, AppearingGhostsThread thread) {
            this.pacman = pacman;

        }

        @Override
        public void onClick(View v) {
            if (!run) {
                run = true;
                thread = new AppearingGhostsThread(handlerRed, handlerBlue, handlerOrange, handlerPink);
                thread.start();
                MovementTypeThread typeThread = new MovementTypeThread(handlerRedChangeMove,handlerBlueChangeMove,
                        handlerOrangeChangeMove,handlerPinkChangeMove);
                typeThread.start();
                EatPacman eatPacman = new EatPacman(redGhost,blueGhost,orangeGhost,pinkGhost,pacman,handlerLives);
                eatPacman.start();
            }
            first[0] = true;
            int differenceX = clickX[0] - clickStartX[0];
            int differenceY = clickY[0] - clickStartY[0];
            if (Math.abs(differenceX) >= Math.abs(differenceY)) {
                if (differenceX > 0) {
                    click[0] = 1;
                } else {
                    click[0] = 2;
                }
            } else {
                if (differenceY > 0) {
                    click[0] = 3;
                } else {
                    click[0] = 4;
                }
            }
            pacman.changeMove(click[0]);
        }
    }

    public void setScoreTextView(int s) {
        if (SettingsActivity.getLanguage().equals("English"))
            scoreTextView.setText("Score: " + s);
        if (SettingsActivity.getLanguage().equals("Ukranian"))
            scoreTextView.setText("Рахунок: " + s);
    }

    public static int getLivesStartNumber() {
        return livesStartNumber;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void PausePushed(View view) {
        if (!isPaused) {
            isPaused = true;

            if (SettingsActivity.getLanguage().equals("English")) {
                pauseButton.setText("START");
            }
            if (SettingsActivity.getLanguage().equals("Ukranian")) {
                pauseButton.setText("СТАРТ");
            }

            pauseTextView.setVisibility(View.VISIBLE);
            pauseTextView.bringToFront();

            pacman.getSet()[0].pause();
            redGhost.getSet()[0].pause();
            blueGhost.getSet()[0].pause();
            orangeGhost.getSet()[0].pause();
            pinkGhost.getSet()[0].pause();


        } else {if(lose){
            lose=false;
            pacman.getSet()[0].cancel();
            pacman.getImageView().setX(1080 / 26 * 13);
            pacman.getImageView().setY(1080 / 26 * 28 + 100);
            pacman.getImageView().setBackgroundResource(R.drawable.pacman_move_animation);
            AnimationDrawable pacmanAnimation = (AnimationDrawable) pacman.getImageView().getBackground();
            pacmanAnimation.start();
            pacman.getImageView().setRotation(0);
        }
            isPaused = false;

            if (SettingsActivity.getLanguage().equals("English")) {
                pauseButton.setText("PAUSE");
            }
            if (SettingsActivity.getLanguage().equals("Ukranian")) {
                pauseButton.setText("ПАУЗА");
            }
            pauseTextView.setVisibility(View.INVISIBLE);

            pacman.getSet()[0].resume();
            redGhost.getSet()[0].resume();
            blueGhost.getSet()[0].resume();
            orangeGhost.getSet()[0].resume();
            pinkGhost.getSet()[0].resume();
        }

    }

    public void gameLose() {
        //todo add score to records
        SharedPreferences preferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor =preferences.edit();

        String mode="records";
        if(Unit.inversionMode) mode="recordsInversion";
        if(lose){
            lose=false;
        editor.remove(mode);
        editor.commit();

        }
       // records=preferences.getStringSet(mode,new HashSet<String>());



        if(records.size()<5){
            records.add(new Integer(Map.getTotalScore()+Map.getLevelScore()).toString());
        }
        else {
            List<String> sortedList = new ArrayList<String>(records);
            Collections.sort(sortedList);
            if(new Integer(sortedList.get(0))<Map.getTotalScore()+Map.getLevelScore()){
                sortedList.set(0,Integer.valueOf(Map.getTotalScore()+Map.getLevelScore()).toString());
                records=new HashSet<>(sortedList);

            }
        }

        editor.putStringSet(mode,records);

        editor.putInt("score", 0);
        editor.putInt("level", 0);



        //editor.putStringSet();
        editor.apply();

        isPaused = true;

        pacman.changeMove(pacman.getPrev());
        redGhost.changeMove(redGhost.getPrev());
        blueGhost.changeMove(blueGhost.getPrev());
        orangeGhost.changeMove(orangeGhost.getPrev());
        pinkGhost.changeMove(pinkGhost.getPrev());


        pauseTextView.setVisibility(View.VISIBLE);
        pauseTextView.bringToFront();
        pauseTextView.setTextSize(70);
        if (SettingsActivity.getLanguage().equals("English")) {
            pauseTextView.setText("GAME OVER");
        }
        if (SettingsActivity.getLanguage().equals("Ukranian")) {
            pauseTextView.setTextSize(60);
            pauseTextView.setText("ВИ ПРОГРАЛИ");
        }

        ConstraintLayout layout = findViewById(R.id.pacmanLayout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameUsualActivity.this, MainActivity.class);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());

            }
        });
    }

    public static boolean getIsPaused() {
        return isPaused;
    }

    public static void setIsPaused(boolean isPaused) {
        GameUsualActivity.isPaused = isPaused;
    }



}