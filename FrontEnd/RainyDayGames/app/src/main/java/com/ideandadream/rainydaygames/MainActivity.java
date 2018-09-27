package com.ideandadream.rainydaygames;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {


    //the number of times the upScore button has been pressed the highest score
    private int score;
    private int highScore;
    //The textview that holds the score and the textview that holds the high score
    private TextView textScore;
    private TextView textHighScore;
    //The Button
    Button buttonUpScore;
    Button buttonSendReset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonUpScore = (Button) findViewById(R.id.buttonUpScore);
        buttonSendReset= (Button) findViewById(R.id.buttonSendReset);
        score = 0;
        textScore = (TextView) findViewById(R.id.textScore);

       //TODO set the highscore to the highest score in the database. Or change to last score or w.e
        /** Need to get the highest score in the db
         * set it to highscore
         * change the inside of textHighScore
         *
         *Should probably aso do this in method, but that's a later thing.
         * Let's just ace this demo first
         */
        highScore = 0;
        textHighScore = (TextView) findViewById(R.id.textHighScore);

        //Handles the user clicking the upScore button
        buttonUpScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Increments the score
                score++;
                //Sets the text in the score text view to reflect the score
                textScore.setText("Score: " + score);
            }
        });

        buttonSendReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO post the current score

                //Resets the score to 0
                score = 0;

                //Resets the score text to nothing, bad OOP I know. Probably should make an update method
                textScore.setText("Score: ");

                //TODO get the high score
                //TODO reset the text view that has the highscore
               textHighScore.setText("High Score: " + highScore);

            }
        });
    }

}
