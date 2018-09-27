package com.ideandadream.rainydaygames;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {


    private int score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonUpScore = (Button) findViewById(R.id.buttonUpScore);


        final TextView textScore = (TextView) findViewById(R.id.textScore);


        buttonUpScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                score++;

                textScore.setText("Score: " + score);



            }
        });

    }
}
