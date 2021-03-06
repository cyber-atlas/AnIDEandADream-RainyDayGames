package edu.iastate.IDE_AND_A_DREAM.Snake_Multiplayer;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import edu.iastate.IDE_AND_A_DREAM.Leaderboards.SnakeppLeaderboard;
import edu.iastate.IDE_AND_A_DREAM.Snake.GamesList;
import edu.iastate.loginscreen.R;

/**
 * The type Snake startup.
 */
public class SnakeStartup extends AppCompatActivity {

    /**
     * The Easy.
     */
    Button Easy;
    /**
     * The Medium.
     */
    Button Medium;
    /**
     * The Hard.
     */
    Button Hard;
    /**
     * The Leaderboard.
     */
    Button Leaderboard;
    /**
     * The Easy level.
     */
    int EasyLevel = 300;
    /**
     * The Medium level.
     */
    int MediumLevel = 150;
    /**
     * The Hard level.
     */
    int HardLevel = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snakemulti_startup);

        Easy = findViewById(R.id.Easybtn);
        Medium = findViewById(R.id.Mediumbtn);
        Hard = findViewById(R.id.Hardbtn);
        Leaderboard = findViewById(R.id.LBoardBtn);

        Leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent leader = new Intent(SnakeStartup.this, SnakeppLeaderboard.class);
                SnakeStartup.this.startActivity(leader);
            }
        });

        Easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    String value = extras.getString("userid");
                }
                Intent i = new Intent(SnakeStartup.this, SnakeMainActivity.class);
                i.putExtra("userid",extras.getString("userid"));
                i.putExtra("level", EasyLevel);
                i.putExtra("monsters", 3);
                SnakeStartup.this.startActivity(i);

            }
        });

    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Bundle extras = getIntent().getExtras();
        String value = extras.getString("userid");
        Intent BackToList = new Intent(SnakeStartup.this, GamesList.class);
        BackToList.putExtra("userid",value);
        SnakeStartup.this.startActivity(BackToList);
    }
}
