package edu.iastate.IDE_AND_A_DREAM.SpaceShooterSingle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import edu.iastate.loginscreen.R;


/**@Author Alexander Stevenson
 * The type Blast.
 */
public class Blast {
        //bitmap object
        private Bitmap bitmap;

        //coordinates
        private int x;
        private int y;

    /**
     * Instantiates a new Blast.
     *
     * @param context the context
     */
//constructor
        public Blast(Context context) {
            //get image
            bitmap = BitmapFactory.decodeResource
                    (context.getResources(), R.drawable.boom);

            //setting the coordinate outside the screen
            //so that it won't shown up in the screen
            //it will be only visible for a fraction of second
            //after collission
            x = -250;
            y = -250;
        }

    /**
     * Sets x.
     *
     * @param x the x
     */
//sets x and y to collision
        public void setX(int x) {
            this.x = x;
        }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(int y) {
            this.y = y;
        }

    /**
     * Gets bitmap.
     *
     * @return the bitmap
     */
//getters
        public Bitmap getBitmap() {
            return bitmap;
        }

    /**
     * Sets bitmap.
     *
     * @param bitmap the bitmap
     */
    public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
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
