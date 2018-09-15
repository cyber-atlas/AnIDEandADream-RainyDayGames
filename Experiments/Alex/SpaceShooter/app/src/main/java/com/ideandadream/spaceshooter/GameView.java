package com.ideandadream.spaceshooter;

import android.content.Context;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable{

    // a volatile variable will guarantee that changes made in one thread will be visible to others
    //Used to keep track if the game is playing or not
    volatile boolean playing;

    //The game thread
    private Thread gameThread = null;

    //Class constructor
    public GameView(Context context){
        super(context);
    }

    @Override
    public void run(){
        while (playing){
            //Update the frame
            update();

            //draw the frame
            draw();

            //Control
            control();

        }

    }

    private void update(){

    }

    private void draw(){

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
