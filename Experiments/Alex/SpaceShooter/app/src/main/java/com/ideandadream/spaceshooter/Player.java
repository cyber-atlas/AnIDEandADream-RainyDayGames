package com.ideandadream.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Player {
    //Bitmap to get character from image
    private Bitmap bitmap;

   //the coordinates
    private int x;
    private int y;

    //speed of the character
    private int speed = 0;

    //constructor
    public Player(Context context){
        x = 75;
        y = 50;
        speed = 1;

        //Get the bitmap from the drawable resource
        bitmap  = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
    }

}
