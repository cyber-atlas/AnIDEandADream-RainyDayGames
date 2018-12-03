package edu.iastate.IDE_AND_A_DREAM.Leaderboards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import edu.iastate.loginscreen.Leaderbrd;
import edu.iastate.loginscreen.R;


/**
 * @Author Merin Mundt. Activity that holds list to all leader boards in the game.
 * The type Leaderboard list.
 */
public class LeaderboardList extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_list);
        Button Snake = findViewById(R.id.Snake);
        Button Snakepp = findViewById(R.id.snakeplus);
        Button Shooter = findViewById(R.id.shooter);


        Snake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent leader = new Intent(LeaderboardList.this, Leaderbrd.class);
                LeaderboardList.this.startActivity(leader);
            }
        });

        Snakepp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent leader = new Intent(LeaderboardList.this, SnakeppLeaderboard.class);
                LeaderboardList.this.startActivity(leader);
            }
        });

        Shooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent leader = new Intent(LeaderboardList.this, ShooterLeaderboard.class);
                LeaderboardList.this.startActivity(leader);
            }
        });

    }
}






