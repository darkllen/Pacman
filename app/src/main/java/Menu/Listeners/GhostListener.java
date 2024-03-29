package Menu.Listeners;

import android.animation.Animator;
import android.widget.ImageView;

import java.util.Random;

import Units.BlueGhost;
import Units.OrangeGhost;
import Units.PinkGhost;
import Units.RedGhost;
import Units.Unit;

public class GhostListener implements Animator.AnimatorListener {
    Unit unit;
    Unit pacman;
    int[][] map;
    int prev;

    int xNum = 0;
    int yNum = 0;

    int xAbolute = 0;
    int yAbsolute= 0;

    RedGhost ghost;

    static int movementType = 1;
    static boolean canEat = false;



    public int getPrev() {
        return prev;
    }

    public void setPrev(int prev) {
        this.prev = prev;
    }

    public int getxAbolute() {
        return xAbolute;
    }

    public void setxAbolute(int xAbolute) {
        this.xAbolute = xAbolute;
    }

    public int getyAbsolute() {
        return yAbsolute;
    }

    public void setyAbsolute(int yAbsolute) {
        this.yAbsolute = yAbsolute;
    }

    public GhostListener(Unit unit, Unit pacman, int[][] map, int prev, int x, int y) {
        this.unit = unit;
        this.pacman = pacman;
        this.map = map;
        this.prev = prev;
        this.xAbolute = x;
        yAbsolute = y;
    }

    public GhostListener(Unit unit, Unit pacman, int[][] map, int prev, int xAbolute, int yAbsolute, RedGhost ghost) {
        this.unit = unit;
        this.pacman = pacman;
        this.map = map;
        this.prev = prev;
        this.xAbolute = xAbolute;
        this.yAbsolute = yAbsolute;
        this.ghost = ghost;
    }

    public static boolean isCanEat() {
        return canEat;
    }

    public static void setCanEat(boolean canEat) {
        GhostListener.canEat = canEat;
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    public static int getMovementType() {
        return movementType;
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        switch (pacman.getPrev()){
            case 1:
                xNum = xAbolute;
                yNum = yAbsolute;
                break;
            case 2:
                xNum = - xAbolute;
                yNum = -yAbsolute;
                break;
            case 3:
                yNum = -xAbolute;
                xNum = -yAbsolute;
                break;
            case 4:
                yNum =  xAbolute;
                xNum = yAbsolute;
                break;
        }

        int pacmanX = (int) pacman.getImageView().getX() + 1080/26*xNum;
        int pacmanY = (int) pacman.getImageView().getY() + 1080/26*yNum;

        if (movementType==2){
            if (unit instanceof RedGhost){
                pacmanX = 1080;
                pacmanY = 100;
            } else if (unit instanceof BlueGhost){
                pacmanX = 1080;
                pacmanY = 1080;
            } else if (unit instanceof OrangeGhost){
                pacmanX = 0;
                pacmanY = 1080;
            } else if (unit instanceof PinkGhost){
                pacmanX = 0;
                pacmanY = 0;
            }
        }



        int inUpCaseX = (int) (unit.getImageView().getX()+ 8);
        int inUpCaseY = (int) (unit.getImageView().getY());

        int inLeftCaseX = (int) (unit.getImageView().getX());
        int inLeftCaseY = (int) (unit.getImageView().getY()+8);

        int inRightCaseX = (int) (unit.getImageView().getX()+ 16);
        int inRightCaseY = (int) (unit.getImageView().getY()+ 8);

        int inDownCaseX = (int) (unit.getImageView().getX()+ 8);
        int inDownCaseY = (int) (unit.getImageView().getY()+ 16);



        int leftDistance = (pacmanX-inLeftCaseX)*(pacmanX-inLeftCaseX) + (pacmanY-inLeftCaseY)*(pacmanY-inLeftCaseY);
        int rightDistance = (pacmanX-inRightCaseX)*(pacmanX-inRightCaseX) + (pacmanY-inRightCaseY)*(pacmanY-inRightCaseY);
        int upDistance = (pacmanX-inUpCaseX)*(pacmanX-inUpCaseX) + (pacmanY-inUpCaseY)*(pacmanY-inUpCaseY);
        int downDistance = (pacmanX-inDownCaseX)*(pacmanX-inDownCaseX) + (pacmanY-inDownCaseY)*(pacmanY-inDownCaseY);

        int currX = (Math.round(( unit.getImageView().getX())/(1080/26)));
        int currY = Math.round ((( unit.getImageView().getY()-100)/(1080/26)));

        int up = map[currX][currY-1];
        int down = map[currX][currY+1];
        int left = map[currX-1][currY];
        int right = map[currX+1][currY];//todo check currX+1!!!!!!!!!!

        if (unit instanceof BlueGhost && movementType==1){
            int xDif = pacmanX - ghost.getStartX();
            int yDif = pacmanY = ghost.getStartY();
            xDif *=2;
            yDif *=2;

            pacmanX = pacmanX + xDif;
            pacmanY = pacmanY + yDif;

             leftDistance = (pacmanX-inLeftCaseX)*(pacmanX-inLeftCaseX) + (pacmanY-inLeftCaseY)*(pacmanY-inLeftCaseY);
             rightDistance = (pacmanX-inRightCaseX)*(pacmanX-inRightCaseX) + (pacmanY-inRightCaseY)*(pacmanY-inRightCaseY);
             upDistance = (pacmanX-inUpCaseX)*(pacmanX-inUpCaseX) + (pacmanY-inUpCaseY)*(pacmanY-inUpCaseY);
             downDistance = (pacmanX-inDownCaseX)*(pacmanX-inDownCaseX) + (pacmanY-inDownCaseY)*(pacmanY-inDownCaseY);


        }else if (unit instanceof OrangeGhost && movementType==1){
            if (leftDistance<(1000/26*8)*(1000/26*8)){
                pacmanX = 0;
                pacmanY = 1080;

                 leftDistance = (pacmanX-inLeftCaseX)*(pacmanX-inLeftCaseX) + (pacmanY-inLeftCaseY)*(pacmanY-inLeftCaseY);
                 rightDistance = (pacmanX-inRightCaseX)*(pacmanX-inRightCaseX) + (pacmanY-inRightCaseY)*(pacmanY-inRightCaseY);
                 upDistance = (pacmanX-inUpCaseX)*(pacmanX-inUpCaseX) + (pacmanY-inUpCaseY)*(pacmanY-inUpCaseY);
                 downDistance = (pacmanX-inDownCaseX)*(pacmanX-inDownCaseX) + (pacmanY-inDownCaseY)*(pacmanY-inDownCaseY);
            }
        }
        Random random = new Random();
        int r = random.nextInt(4)+1;
        switch (prev){
            case 1:
                if (isCanEat()){
                    if (up!=1&&down!=1&&right!=1){
                        while (r==2){
                            r = random.nextInt(4)+1;
                        }
                    } else if (up!=1&&down!=1){
                        while (r==2 || r ==1){
                            r = random.nextInt(4)+1;
                        }
                    } else if (right!=1&&down!=1){
                        while (r==2 || r ==4){
                            r = random.nextInt(4)+1;
                        }
                    } else if (up!=1&&right!=1){
                        while (r==2 || r ==3){
                            r = random.nextInt(4)+1;
                        }
                    } else if (up!=1){
                        r=4;
                    } else if (down!=1){
                        r=3;
                    }
                    prev = r;
                    unit.changeMove(r);
                    return;
                }
                 if ((Math.min(upDistance, Math.min(rightDistance,downDistance))==upDistance &&(up!=1&&down!=1&&right!=1)) ||
                         (Math.min(upDistance, downDistance)==upDistance && up!=1 && down!=1 && right==1) ||
                         (Math.min(upDistance, rightDistance)==upDistance && up!=1 && right!=1 && down==1) ||
                         (right==1 && down==1)){
                     prev = 4;
                     unit.changeMove(4);
                     return;
                 }
                if ((Math.min(downDistance, Math.min(rightDistance,upDistance))==downDistance &&(down!=1&&up!=1&&right!=1)) ||
                        (Math.min(downDistance, upDistance)==downDistance && down!=1 && up!=1 && right==1) ||
                        (Math.min(downDistance, rightDistance)==downDistance && down!=1 && right!=1 && up==1) ||
                        (right==1 && up==1)){
                    prev = 3;
                    unit.changeMove(3);
                    return;
                }
                if ((Math.min(rightDistance, Math.min(upDistance,downDistance))==rightDistance &&(right!=1&&down!=1&&up!=1)) ||
                        (Math.min(rightDistance, downDistance)==rightDistance && right!=1 && down!=1 && up==1) ||
                        (Math.min(rightDistance, upDistance)==rightDistance && right!=1 && up!=1 && down==1) ||
                        (up==1 && down==1)){
                    prev = 1;
                    unit.changeMove(1);
                    return;
                }
            case 2:
                if (isCanEat()){
                    if (up!=1&&down!=1&&left!=1){
                        while (r==1){
                            r = random.nextInt(4)+1;
                        }
                    } else if (up!=1&&down!=1){
                        while (r==2 || r ==1){
                            r = random.nextInt(4)+1;
                        }
                    } else if (left!=1&&down!=1){
                        while (r==1 || r ==4){
                            r = random.nextInt(4)+1;
                        }
                    } else if (up!=1&&left!=1){
                        while (r==1 || r ==3){
                            r = random.nextInt(4)+1;
                        }
                    } else if (up!=1){
                        r=4;
                    } else if (down!=1){
                        r=3;
                    }
                    prev = r;
                    unit.changeMove(r);
                    return;
                }

                if ((Math.min(upDistance, Math.min(leftDistance,downDistance))==upDistance &&(up!=1&&down!=1&&left!=1)) ||
                        (Math.min(upDistance, downDistance)==upDistance && up!=1 && down!=1 && left==1) ||
                        (Math.min(upDistance, leftDistance)==upDistance && up!=1 && left!=1 && down==1) ||
                        (left==1 && down==1)){
                    prev = 4;
                    unit.changeMove(4);
                    return;
                }
                if ((Math.min(downDistance, Math.min(leftDistance,upDistance))==downDistance &&(down!=1&&up!=1&&left!=1)) ||
                        (Math.min(downDistance, upDistance)==downDistance && down!=1 && up!=1 && left==1) ||
                        (Math.min(downDistance, leftDistance)==downDistance && down!=1 && left!=1 && up==1) ||
                        (left==1 && up==1)){
                    prev = 3;
                    unit.changeMove(3);
                    return;
                }
                if ((Math.min(leftDistance, Math.min(upDistance,downDistance))==leftDistance &&(left!=1&&down!=1&&up!=1)) ||
                        (Math.min(leftDistance, downDistance)==leftDistance && left!=1 && down!=1 && up==1) ||
                        (Math.min(leftDistance, upDistance)==leftDistance && left!=1 && up!=1 && down==1) ||
                        (up==1 && down==1)){
                    prev = 2;
                    unit.changeMove(2);
                    return;
                }
            case 3:
                if (isCanEat()){
                    if (left!=1&&down!=1&&right!=1){
                        while (r==4){
                            r = random.nextInt(4)+1;
                        }
                    } else if (left!=1&&down!=1){
                        while (r==4 || r ==1){
                            r = random.nextInt(4)+1;
                        }
                    } else if (right!=1&&down!=1){
                        while (r==4 || r ==2){
                            r = random.nextInt(4)+1;
                        }
                    } else if (left!=1&&right!=1){
                        while (r==4 || r ==3){
                            r = random.nextInt(4)+1;
                        }
                    } else if (left!=1){
                        r=2;
                    } else if (right!=1){
                        r=1;
                    }
                    prev = r;
                    unit.changeMove(r);
                    return;
                }

                if ((Math.min(leftDistance, Math.min(rightDistance,downDistance))==leftDistance &&(left!=1&&down!=1&&right!=1)) ||
                        (Math.min(leftDistance, downDistance)==leftDistance && left!=1 && down!=1 && right==1) ||
                        (Math.min(leftDistance, rightDistance)==leftDistance && left!=1 && right!=1 && down==1) ||
                        (right==1 && down==1)){
                    prev = 2;
                    unit.changeMove(2);
                    return;
                }
                if ((Math.min(downDistance, Math.min(rightDistance,leftDistance))==downDistance &&(down!=1&&left!=1&&right!=1)) ||
                        (Math.min(downDistance, leftDistance)==downDistance && down!=1 && left!=1 && right==1) ||
                        (Math.min(downDistance, rightDistance)==downDistance && down!=1 && right!=1 && left==1) ||
                        (right==1 && left==1)){
                    prev = 3;
                    unit.changeMove(3);
                    return;
                }
                if ((Math.min(rightDistance, Math.min(leftDistance,downDistance))==rightDistance &&(right!=1&&down!=1&&left!=1)) ||
                        (Math.min(rightDistance, downDistance)==rightDistance && right!=1 && down!=1 && left==1) ||
                        (Math.min(rightDistance, leftDistance)==rightDistance && right!=1 && left!=1 && down==1) ||
                        (left==1 && down==1)){
                    prev = 1;
                    unit.changeMove(1);
                    return;
                }
            case 4:
                if (isCanEat()){
                    if (left!=1&&up!=1&&right!=1){
                        while (r==3){
                            r = random.nextInt(4)+1;
                        }
                    } else if (left!=1&&up!=1){
                        while (r==3 || r ==1){
                            r = random.nextInt(4)+1;
                        }
                    } else if (right!=1&&up!=1){
                        while (r==3 || r ==2){
                            r = random.nextInt(4)+1;
                        }
                    } else if (left!=1&&right!=1){
                        while (r==4 || r ==3){
                            r = random.nextInt(4)+1;
                        }
                    } else if (left!=1){
                        r=2;
                    } else if (right!=1){
                        r=1;
                    }
                    prev = r;
                    unit.changeMove(r);
                    return;
                }

                if ((Math.min(leftDistance, Math.min(rightDistance,upDistance))==leftDistance &&(left!=1&&up!=1&&right!=1)) ||
                        (Math.min(leftDistance, upDistance)==leftDistance && left!=1 && up!=1 && right==1) ||
                        (Math.min(leftDistance, rightDistance)==leftDistance && left!=1 && right!=1 && up==1) ||
                        (right==1 && up==1)){
                    prev = 2;
                    unit.changeMove(2);
                    return;
                }
                if ((Math.min(upDistance, Math.min(rightDistance,leftDistance))==upDistance &&(up!=1&&left!=1&&right!=1)) ||
                        (Math.min(upDistance, leftDistance)==upDistance && up!=1 && left!=1 && right==1) ||
                        (Math.min(upDistance, rightDistance)==upDistance && up!=1 && right!=1 && left==1) ||
                        (right==1 && left==1)){
                    prev = 4;
                    unit.changeMove(4);
                    return;
                }
                if ((Math.min(rightDistance, Math.min(leftDistance,upDistance))==rightDistance &&(right!=1&&up!=1&&left!=1)) ||
                        (Math.min(rightDistance, upDistance)==rightDistance && right!=1 && up!=1 && left==1) ||
                        (Math.min(rightDistance, leftDistance)==rightDistance && right!=1 && left!=1 && up==1) ||
                        (left==1 && up==1)){
                    prev = 1;
                    unit.changeMove(1);
                    return;
                }
        }
    }

    public static void setMovementType(int movementType) {
        GhostListener.movementType = movementType;
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
