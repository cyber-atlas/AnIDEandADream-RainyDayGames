package edu.iastate.IDE_AND_A_DREAM.SpaceShooterSingle;

import java.util.Random;

/**
 * @Author Alexander Stevenson
 * The type Star.
 */
public class Star {
    private int x;
    private int y;
    private int speed;

    private int maxX;
    private int maxY;
    private int minX;
    private int minY;

    /**
     * Instantiates a new Star.
     *
     * @param screenX the screen x
     * @param screenY the screen y
     */
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

    /**
     * Update.
     *
     * @param playerSpeed the player speed
     */
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

    /**
     * Get star width float.
     *
     * @return the float
     */
    public float getStarWidth(){
       //Making the star width randdom
        float minX = 1.0f;
        float maxX = 4.0f;
        Random rand = new Random();
        float finalX = rand.nextFloat() * (maxX - minX) + minX;
        return finalX;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public int getY() {
        return y;
    }
}
