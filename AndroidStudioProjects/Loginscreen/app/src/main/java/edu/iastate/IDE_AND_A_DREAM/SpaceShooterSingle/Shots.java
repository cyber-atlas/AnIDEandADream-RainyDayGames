package edu.iastate.IDE_AND_A_DREAM.SpaceShooterSingle;

import android.graphics.Rect;

import java.util.concurrent.locks.ReentrantLock;

//Creates a rect shots object
public class Shots {

    private Rect shotRect;
//    private Rect detectCollision;
    //Is it a live bullet
    private Boolean isLive;
    private final int SPEED = 10;
    private int maxX;

    public Shots (int left, int top, int screenX){

//        shotRect = new Rect(top, left, left+50, top-50);
        shotRect = new Rect(left, top, left+50, top-50);
//        shotRect = new Rect(left, top, 50, 50);

//        shotRect = new Rect(top, left, 50, 50);
//        shotRect = new Rect(left, top, 50, 50);
//        new Rect(1,1,1,1);

        isLive = true;
        maxX = screenX;

    }

    public void update(){
        //Move the bullet across the screen
        shotRect.left += SPEED;
        shotRect.right += SPEED;

        //If the shot reaches the end of the screen, drop it
        if (shotRect.left > maxX){
            isLive = false;
        }
    }

    public void setLive(Boolean live) {
        isLive = live;
    }

    public Rect getDetectCollision(){
        return shotRect;
    }

    public Rect getShotRect() {
        return shotRect;
    }

    public Boolean getLive() {
        return isLive;
    }
}
