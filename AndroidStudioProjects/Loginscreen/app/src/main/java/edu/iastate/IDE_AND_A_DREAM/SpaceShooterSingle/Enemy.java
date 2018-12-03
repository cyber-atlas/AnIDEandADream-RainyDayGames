package edu.iastate.IDE_AND_A_DREAM.SpaceShooterSingle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

import edu.iastate.loginscreen.R;


/**
 * @Author Alexander Stevenson
 * The type Enemy.
 */
public class Enemy {

    //Enemy Bitmap
    private Bitmap bitmap;

    //x and y coordinates
    private int x;
    private int y;

    //Speed
    private int speed = 1;

    //min and max coordinates
    private int maxX;
    private int maxY;

    private int minX;
    private int minY;

    //rect object
    private Rect detectCollision;

    /**
     * Instantiates a new Enemy.
     *
     * @param context the context
     * @param screenX the screen x
     * @param screenY the screen y
     */
    public Enemy(Context context, int screenX, int screenY){
       //Get the bitmap:w
       bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.meteorite);

       //Initialize the min and max coordinates
       maxX = screenX;
       maxY = screenY;
       minX = 0;
       minY = 0;

       //randomly places the enemy
       Random rand = new Random();
       speed = rand.nextInt(6) +10;
       x = screenX;
       y = rand.nextInt(maxY) -  bitmap.getHeight();

       //Initializing rect object
       detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
   }

    /**
     * Update.
     *
     * @param playerSpeed the player speed
     */
    public void update(int playerSpeed){

       //decreasing x coordinate for the enemy motion
       x = x - playerSpeed;
       x = x - speed;

       //If the enemy reaches the left edge add them to the right edge
       if(x< minX - bitmap.getWidth()){
           Random rand = new Random();
           speed = rand.nextInt(10)+10;
           x = maxX;
           y = rand.nextInt(maxY) - bitmap.getHeight();
       }

       //Adding left, right, top, and bottom to the rect
       detectCollision.left = x;
       detectCollision. right  = x + bitmap.getWidth();
       detectCollision.top = y;
       detectCollision.bottom = y+ bitmap.getHeight();
   }

    /**
     * Set x.
     *
     * @param x the x
     */
//Setter to X coordinate to change after collision
    public void setX(int x){
       this.x = x;
    }

    /**
     * Get detect collision rect.
     *
     * @return the rect
     */
    public Rect getDetectCollision(){
       return detectCollision;
    }

    /**
     * Gets bitmap.
     *
     * @return the bitmap
     */
    public Bitmap getBitmap() {
        return bitmap;
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

    /**
     * Gets speed.
     *
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }
}
