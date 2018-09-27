package com.ideandadream.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.GregorianCalendar;

public class Player {
    //Bitmap to get character from image
    private Bitmap bitmap;

   //the coordinates
    private int x;
    private int y;

    //speed of the character
    private int speed = 0;

    //Boolean variable to track whether the ship is boosting or not
    private boolean boosting;

    //Gravity Value to add gravity effect on the ship
    private final int GRAVITY = -10;

    //Control the Y coordinate so that the ship stays on screen
    private int mayY;
    private int maxY;

    //limit the bounds of the ship speed
    private final int MIN_SPEED =1;
    private final int MAX_SPEED = 20;

    //constructor
    public Player(Context context){
        x = 75;
        y = 50;
        speed = 1;

        //Get the bitmap from the drawable resource
        bitmap  = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);

        //Calculate max y
        maxY = screenY - bitmap.getHeight();

        //top edge's y point is 0  so min y will always be 0
        minY = 0;

        //Set the boosting value to false when itialized
        boosting = false;
    }

    //Setting boosting
    public void setBoosting(){
        boosting = true;
    }
    public void update() {

        //If the ship is boosting speed up by 2 else, slow down by 5
        speed = (boosting)? speed+2 : speed -5;

        //Bounds the top speed
        if (speed > MAX_SPEED){
            speed = MAX_SPEED;
        }
        //Bounds the min speed
        if (speed < MIN_SPEED){
            speed = MIN_SPEED;
        }

        //moves the ship down
        y -= speed + GRAVITY;

        //make sure the ship does not go off of the screen
        if (y < minY){
            y =  minY;
        }
        if (y > maxY){
            y = maxY;
        }

        //update x
//        x++;
    }

    //auto generated Bitmap getter
    public Bitmap getBitmap() {
        return bitmap;
    }

    //Autgenerated getter
    public int getX() {
        return x;
    }

    //Autogenerated getter
    public int getY() {
        return y;
    }

    //autogenerated getter
    public int getSpeed() {
        return speed;
    }
}