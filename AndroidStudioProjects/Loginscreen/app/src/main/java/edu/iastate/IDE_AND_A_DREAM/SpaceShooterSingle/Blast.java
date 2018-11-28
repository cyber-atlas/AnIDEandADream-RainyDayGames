package edu.iastate.IDE_AND_A_DREAM.SpaceShooterSingle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import edu.iastate.loginscreen.R;


public class Blast {
        //bitmap object
        private Bitmap bitmap;

        //coordinates
        private int x;
        private int y;

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

        //sets x and y to collision
        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        //getters
        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
}
