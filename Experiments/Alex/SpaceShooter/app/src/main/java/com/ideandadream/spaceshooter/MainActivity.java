package com.ideandadream.spaceshooter;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Image button is just a button that has an image and changes color
    private ImageButton buttonPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting the orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        //getting the play button
        buttonPlay  = (ImageButton) findViewById(R.id.buttonPlay);

        //add a click listener to the play button
        buttonPlay.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){

       //Start the game activity that we define
       startActivity(new Intent(this, GameActivity.class));

    }
}
