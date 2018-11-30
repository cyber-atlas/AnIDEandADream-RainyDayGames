package edu.iastate.IDE_AND_A_DREAM.SpaceShooterSingle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class GameView extends SurfaceView implements Runnable {

    //    public Rect btn;
    // a volatile variable will guarantee that changes made in one thread will be visible to others
    //Used to keep track if the game is playing or not
    volatile boolean playing;
    //TODO
    //ScreenX
    int screenX;
    //number of misses
    int numMisses;
    //indicate that enemy has just entered the game screen
    boolean flag;
    volatile Boolean tester = false;
    //The game thread
    private Thread gameThread = null;
    //adding the player
    private Player player;
    //Objects that will be used for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    //Adding enemies object array
    private Enemy[] enemies;
    //Adding 3 enemies
    private int numEnemies = 3;
    //Add list of stars
    private ArrayList<Star> stars = new ArrayList<>();
    //Add a blast object
    private Blast blast;
    //indicator if game is over
    private boolean isOver;
    //TODO make work for blaster
//    private Button btnShoot;

    //    private ArrayList<Rect> shotsList = new ArrayList<>();
    private LinkedList<Shots> shotsList = new LinkedList<>();


    //Class constructor
    public GameView(Context context, int screenX, int screenY) {
        super(context);

        //initialize the player context
//        player = new Player(context);
        player = new Player(context, screenX, screenY);

//        btn = new Rect(200, screenY / 2, screenY, 275);

        //initialize the drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

        //Adding list of stars
        int numStars = 100;
        for (int i = 0; i < numStars; i++) {
            Star s = new Star(screenX, screenY);
            stars.add(s);
        }

        //Initialize enemy array
        enemies = new Enemy[numEnemies];
        for (int i = 0; i < numEnemies; i++) {
            enemies[i] = new Enemy(context, screenX, screenY);
        }


        //Initialize the blast
        blast = new Blast(context);

        this.screenX = screenX;
        numMisses = 0;
        isOver = false;
//

    }

    @Override
    public void run() {
        while (playing) {
            //Update the frame
            update();
            //draw the frame
            draw();
            //Control
            control();
        }
    }

    private void update() {

//        tester = false;
        player.setShooting(false);

        //update the player position
        player.update();

        //Sets the blast outside of the screen
        blast.setX(-300);
        blast.setY(-300);


        //Updating all of the stars based on the player's speed
        for (Star s : stars) {
            s.update(player.getSpeed());
        }

        //flag is true when asteroids just enters the screen
//        if(enemies.getX()==screenX)

        //updating the enemy coordinate based on player speed
        for (int i = 0; i < numEnemies; i++) {
            enemies[i].update(player.getSpeed());
            //flag is true when asteroids just enters the screen
//            if(enemies[i].getX()==screenX){
//                flag == true;
//            }


            //if collision with player occurs
            if (Rect.intersects(player.getDetectCollision(), enemies[i].getDetectCollision())) {

                //Showing the blast at the enemies location
                blast.setX(enemies[i].getX());
                blast.setY(enemies[i].getY());

                //move enemies outside the left edge
                enemies[i].setX(-500);
            }
        }

        ListIterator<Shots> iter = shotsList.listIterator(0);

        if (shotsList.isEmpty()) {
            return;
        }
        while (iter.hasNext()){
            Shots shot = iter.next();
            //Update the shot
            shot.update();
            if(shot.getLive() == false) {
                iter.remove();
                continue;
            }
            //check if intersects with enemies
            for(int i = 0; i< numEnemies;i++) {
                if (Rect.intersects(shot.getDetectCollision(), enemies[i].getDetectCollision())){
                    shot.setLive(false);
                    enemies[i].setX(-500);
                }
            }

        }

    }

    private void draw() {
        //check the if the surface is valid
        if (surfaceHolder.getSurface().isValid()) {
            //locking the canvas
            canvas = surfaceHolder.lockCanvas();
            //drawing a background color for canvas
            canvas.drawColor(Color.BLACK);

            //TODO
//            Log.d("motion in draw", String.valueOf(tester));
            if (tester == true) {
                canvas.drawColor(Color.RED);
//                canvas.drawColor(Color.BLACK);
            }
            //after we change the screen, change the tester
//            tester = false;


            //TODO might be a good idea to make these different colors
            //Drawing all white stars at their location and size
            paint.setColor(Color.WHITE);

            for (Star s : stars) {
                paint.setStrokeWidth(s.getStarWidth());
                canvas.drawPoint(s.getX(), s.getY(), paint);

            }


            //Drawing the player
            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint);

            //Drawing the enemies
            for (int i = 0; i < numEnemies; i++) {
                canvas.drawBitmap(
                        enemies[i].getBitmap(),
                        enemies[i].getX(),
                        enemies[i].getY(),
                        paint);
            }

            //Draw the blast
            canvas.drawBitmap(
                    blast.getBitmap(),
                    blast.getX(),
                    blast.getY(),
                    paint
            );

            ListIterator<Shots> iter = shotsList.listIterator(0);

            while (iter.hasNext()) {
                Shots shot = iter.next();
                //Update the shot
                paint.setColor(Color.GREEN);
                canvas.drawRect(shot.getShotRect(), paint);
            }


            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        //To pause the game
        //Set the playing variable to false
        playing = false;

        //stop the thread
        //By using thread.join() waits for he thread to end
        try {
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        //To resume the game
        //Start the game thread again
        playing = true;
        /** Assigns gameThread to a new thread
         * this refers to this class as the target
         * and invokes it's run method*/
        gameThread = new Thread(this);
        //Causes the thread to begin executing, calls the run of that method
        gameThread.start();

    }

    //Gets the touch from the screen, and tracks the users motion
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        //For testing purposes
        Log.d("motion location X", String.valueOf(motionEvent.getX()));
        Log.d("motion location Y", String.valueOf(motionEvent.getY()));
        //Handles the logic of the touch
        touchLogic(motionEvent);
//        Log.d("motion rect", btn.toString());

        /**Gets the touch from the user
         * applies a bitmask to it so
         * we only deal with the bits we care about
         */
//        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
//
//            //When the user presses the screen
//            case MotionEvent.ACTION_UP:
//                //Stopping the boosting when screen is released
//                player.stopBoosting();
//                break;
//            case MotionEvent.ACTION_DOWN:
//
//                if (motionEvent.getX() > screenX / 3) {
//
//                    tester = true;
//                    player.setShooting(true);
//
//                    Log.d("motion shooting touch ", String.valueOf(player.isShooting()));
//                    break;
//                }
//
//                //boosting the space jet when screen is pressed
//                player.setBoosting();
//
//                player.setShooting(false);
//                tester = false;
//                Log.d("Tester working?", String.valueOf(tester));
//
//                break;
//        }

        return true;
    }
    /**
     private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
    @Override
    public boolean onDoubleTap(MotionEvent e) {

    return false;
    }

    });

     **/

    void touchLogic(MotionEvent m) {

//        //TODO Trying with a for loop
//        //The number of pointers-always >=1
        int pointerCount = m.getPointerCount();

        Log.d("PointerCount", String.valueOf(pointerCount));
        //get pointer index from the event
//        int pointerIndex = m.getActionIndex();


        //TODO print pointer count and see how that changes based on the presses/lifting of fingers
        //Loops through all of the pointers
        //TODO for some reason this loop only runs once....
        for (int i = 0; i < pointerCount; i++) {

//            int x = (int) m.getX(i);
//            int y = (int) m.getY(i);

            //get the pointer ID
            int pointerID = m.getPointerId(i);
            //get masked action
            int maskedAction = m.getActionMasked();
            int actionIndex = m.getActionIndex();

            //TODO for some reason the game crashes when the buttons are mashed or something
            switch (maskedAction) {

                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:

                    //If the user touches the right side of the screen then set tester to true
                    //(flashes the screen)
                    if (m.getX(pointerID) > screenX / 3) {

                        tester = true;
                        player.setShooting(true);

//                        shotsList.add(new Rect)
                        //Get the middle of the player
//                        int newShotTop = player.getY() - (player.getBitmap().getHeight() / 2);
                        int newShotTop = player.getY() + (player.getBitmap().getHeight() / 2);
                        int newShotLeft = player.getX() + (player.getBitmap().getWidth());
//                        shotsList.add(new Shots(newShotTop, newShotLeft, screenX));
                        shotsList.add(new Shots(newShotLeft, newShotTop, screenX));

//                        Log.d("motion shooting touch ", String.valueOf(player.isShooting()));
                        break;
                    }
                    else {
                        //boosting the space jet when screen is pressed
                        player.setBoosting();

                        //The next 2 lines might not be needed anymore since I deal with them in draw
//                    player.setShooting(false);
//                    tester = false;
//                    Log.d("Tester working?", String.valueOf(tester));
                        break;
                    }

                    //When they lift their finger up, then stop boosting
                    //TODO I think that it stopping the boosting when the right side is lifted...
                    //TODO might need to find a better way to deal with these actions
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
//                    Log.d("PointerID in ActionUp", String.valueOf(pointerID));
                    Log.d("PointerID in ActionUp", String.valueOf(actionIndex));
//                    Log.d("PointerID less than 3", String.valueOf(m.getX(pointerID) < screenX / 3));
//                    Log.d("PointerID coordinates", String.valueOf(m.getX(pointerID)));
//                    if (m.getX(pointerID) < screenX / 3) {
                    if (m.getX(actionIndex) < screenX / 3) {
//                        Log.d("pointer inside if", String.valueOf(m.getX(pointerID)) + "," + String.valueOf(screenX));
                        Log.d("pointer inside if", String.valueOf(m.getX(actionIndex)) + "," + String.valueOf(screenX));
                        player.stopBoosting();
                        break;
                    }
//                    if (m.getX(pointerID) > screenX / 3) {
                    if (m.getX(actionIndex) > screenX / 3) {
                        tester = false;
                        break;
                    }
                    break;

            }
        }

    }


}
