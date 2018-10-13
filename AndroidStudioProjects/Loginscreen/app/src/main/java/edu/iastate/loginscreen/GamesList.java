package edu.iastate.loginscreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class GamesList extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);

        Button StartSnakeGame = findViewById(R.id.SnakeGame);

        StartSnakeGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Tag", "in signup button");
                //startActivity(new Intent(MainActivity.this, signuppage.class));
                Intent StartSnakeIntent = new Intent(GamesList.this, SnakeSplash.class);
                GamesList.this.startActivity(StartSnakeIntent);
            }
        });

    }
}
