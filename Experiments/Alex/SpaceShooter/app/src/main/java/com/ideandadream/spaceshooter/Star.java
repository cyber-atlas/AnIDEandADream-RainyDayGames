package com.ideandadream.spaceshooter;

import java.util.Random;

public class Star {
    private int x;
    private int y;
    private int speed;

    private int maxX;
    private int maxY;
    private int minX;
    private int minY;

    //Constructor for the star
    public Star(int screenX, int screenY){
        //sets the the max X and Y value
        maxX =  screenX;
        maxY = screenY;
        //Sets the mins to be 0
        minX = 0;
        minY = 0;
        //generates a random number less than 10 for the speed
        Random generator = new Random();
        speed = generator.nextInt(10);

        //generate random coordinate that is inside the screen size
        x = generator.nextInt(maxX);
        y = generator.nextInt(maxY);

    }

    public void update(int playerSpeed){
        //animating the star along the x axis, by decreasing x with player speed
        x -= playerSpeed;
        x -= speed;
        //if the star reached the left edge of the screen
        if (x < 0){
            //start the star from right edge, gives infinite effect
            x = maxX;
            Random generator = new Random();
            y = generator.nextInt(maxY);
            speed = generator.nextInt(15);
        }

    }

    public float getStarWidth(){
       //Making the star width randdom
        float minX = 1.0f;
        float maxX = 4.0f;
        Random rand = new Random();
        float finalX = rand.nextFloat() * (maxX-minX) + minX;
        return finalX;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
