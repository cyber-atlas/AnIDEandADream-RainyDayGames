package edu.iastate.IDE_AND_A_DREAM.SpaceShooterSingle;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import edu.iastate.loginscreen.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Image button is just a button that has an image and changes color
    private ImageButton buttonPlay;
    private ImageButton buttonScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleshooter_main);

        //setting the orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        //getting the play button
        buttonPlay  = (ImageButton) findViewById(R.id.buttonPlay);
        buttonScore = (ImageButton) findViewById(R.id.buttonScore);

        //add a click listener to the play button
        buttonPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
       //Start the game activity that we define
       startActivity(new Intent(this, GameActivity.class));

    }
}
