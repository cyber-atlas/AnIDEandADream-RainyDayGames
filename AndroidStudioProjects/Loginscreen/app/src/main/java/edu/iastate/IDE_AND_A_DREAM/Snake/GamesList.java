package edu.iastate.IDE_AND_A_DREAM.Snake;

import android.os.Bundle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import edu.iastate.IDE_AND_A_DREAM.SpaceShooterSingle.MainActivity;
import edu.iastate.IDE_AND_A_DREAM.Snake_Multiplayer.SnakeSplash;



import edu.iastate.loginscreen.R;

public class GamesList extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);

        Button StartSnakeGame = findViewById(R.id.SnakeGame);
        Button SpaceShooter = findViewById(R.id.spaceShooter);
        Button Snake_Multi = findViewById(R.id.button3);

        StartSnakeGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    String value = extras.getString("userid");
                    Log.d("Message from Signup", value);
                }
                Intent i = new Intent(GamesList.this, SnakeSplash.class);
                i.putExtra("userid",extras.getString("userid"));
                GamesList.this.startActivity(i);
//                Intent StartSnakeIntent = new Intent(GamesList.this, SnakeSplash.class);
//                GamesList.this.startActivity(StartSnakeIntent);
            }
        });

        SpaceShooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    String value = extras.getString("userid");
                    Log.d("Message from Signup", value);
                }
                Intent i = new Intent(GamesList.this, MainActivity.class);
                i.putExtra("userid",extras.getString("userid"));
                GamesList.this.startActivity(i);
//                Intent StartSnakeIntent = new Intent(GamesList.this, SnakeSplash.class);
//                GamesList.this.startActivity(StartSnakeIntent);
            }
        });

        Snake_Multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    String value = extras.getString("userid");
                    Log.d("Message from Signup", value);
                }
                Intent i = new Intent(GamesList.this, SnakeSplash.class);
                i.putExtra("userid",extras.getString("userid"));
                GamesList.this.startActivity(i);
//                Intent StartSnakeIntent = new Intent(GamesList.this, SnakeSplash.class);
//                GamesList.this.startActivity(StartSnakeIntent);
            }
        });



    }
}
