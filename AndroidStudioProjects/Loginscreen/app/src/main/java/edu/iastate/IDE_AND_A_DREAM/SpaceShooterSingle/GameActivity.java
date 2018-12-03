package edu.iastate.IDE_AND_A_DREAM.SpaceShooterSingle;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import edu.iastate.loginscreen.R;


/**
 * @Author Alexander Stevenson
 * The type Game activity.
 */
public class GameActivity extends AppCompatActivity {

    //Declaring the gameview
    private GameView gameView;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_spaceshooter_game);

        /**
        btn=(Button)findViewById(R.id.btnShoot);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"here",Toast.LENGTH_LONG).show();
            }
        });
         **/
        //TODO look more into displays
        //Getting the display object
        Display display = getWindowManager().getDefaultDisplay();

        //Getting the screen resolution and stores it in a point obj
        Point size = new Point();
        display.getSize(size);

        //TODO logging the size for testing purposes
        Log.d("SizeX",Integer.toString(size.x));
        Log.d("SizeY",Integer.toString(size.y));


        //initialize the view
//        gameView = new GameView(this);
       //Pass in the screen size and the context to the gameView
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
