package com.ideandadream.spaceshooter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    // a volatile variable will guarantee that changes made in one thread will be visible to others
    //Used to keep track if the game is playing or not
    volatile boolean playing;

    //The game thread
    private Thread gameThread = null;

    //adding the player
    private Player player;

    //Objects that will be used for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    //Class constructor
    public GameView(Context context) {
        super(context);

        //initialize the player context
        player = new Player(context);

        //initialize the drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

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
        //update the player position
        player.update();

    }

    private void draw() {
        //check the if the surface is valid
        if (surfaceHolder.getSurface().isValid()) {
            //locking the canvas
            canvas = surfaceHolder.lockCanvas();
            //drawing a background color for canvas
            canvas.drawColor(Color.BLACK);
            //drawing the player
            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint);

            //unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
    }

}

    private void control(){
        try{
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause(){
        //To pause the game
        //Set the playing variable to false
        playing = false;

        //stop the thread
        //By using thread.join() waits for he thread to end
        try{
            gameThread.join();
        }catch(InterruptedException e){
        }
    }

    public void resume(){
       //To resume the game
       //Start the game thread again
       playing = true;
       /** Assigns gameThread to a new thread
        * this refers to this class as the target
        * and invokes it's run method*/
       gameThread = new Thread(this );
       //Causes the thread to begin executing, calls the run of that method
       gameThread.start();

    }


}
