package edu.iastate.IDE_AND_A_DREAM.SpaceShooterSingle;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import edu.iastate.IDE_AND_A_DREAM.Leaderboards.ShooterLeaderboard;
import edu.iastate.loginscreen.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Image button is just a button that has an image and changes color
    private ImageButton buttonPlay;
    private ImageButton buttonScore;
    static MediaPlayer startSound;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleshooter_main);

        //setting the orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        //getting the play button
        buttonPlay  = (ImageButton) findViewById(R.id.buttonPlay);
        buttonScore = (ImageButton) findViewById(R.id.buttonScore);

        //add a click listener to the play and high score buttons
        buttonPlay.setOnClickListener(this);
        buttonScore.setOnClickListener(this);

        startSound = MediaPlayer.create(this,R.raw.space_shooter_start);
        startSound.start();
    }

    @Override
    public void onClick(View v){
       //Start the game activity that we define
        if(v == buttonPlay) {
            startActivity(new Intent(this, GameActivity.class));
        }
        //Open the leader board
        if(v == buttonScore){
            startActivity(new Intent(this,ShooterLeaderboard.class));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        startSound.stop();
    }






}
