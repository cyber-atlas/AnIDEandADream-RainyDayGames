package edu.iastate.IDE_AND_A_DREAM.Leaderboards;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;


import edu.iastate.IDE_AND_A_DREAM.secondpage;
import edu.iastate.loginscreen.Leaderbrd;
import edu.iastate.loginscreen.R;


public class LeaderboardList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_list);
        Button Snake = findViewById(R.id.Snake);

       Snake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent leader = new Intent(LeaderboardList.this, Leaderbrd.class);
                LeaderboardList.this.startActivity(leader);
            }
        });
    }


}
