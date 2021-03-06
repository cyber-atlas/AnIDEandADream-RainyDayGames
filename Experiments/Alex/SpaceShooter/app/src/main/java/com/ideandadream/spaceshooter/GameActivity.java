package com.ideandadream.spaceshooter;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

public class GameActivity extends AppCompatActivity {

    //Declaring the gameview
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO look more into displays
        //Getting the display object
        Display display = getWindowManager().getDefaultDisplay();

        //Getting the screen resolution and stores it in a point obj
        Point size = new Point();
        display.getSize(size);

        //initialize the view
//        gameView = new GameView(this);
       //this time we are
        gameView = new GameView(this, size.x, size.y);

        //adding the gameview to the contentview
        setContentView(gameView);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
    }

    //pauses the game when the activity is paused
    @Override
    protected void onPause(){
        super.onPause();
        //uses the pause that we defined in GameView
        gameView.pause();
    }

    //runs the game when the activity is resumed
    @Override
    protected void onResume(){
        super.onResume();
        //uses the resume that we defined in Gameview
        gameView.resume();

    }


}
