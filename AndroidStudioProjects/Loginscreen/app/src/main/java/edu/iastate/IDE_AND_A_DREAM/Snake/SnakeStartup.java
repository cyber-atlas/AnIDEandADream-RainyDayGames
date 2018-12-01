package edu.iastate.IDE_AND_A_DREAM.Snake;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import edu.iastate.loginscreen.Leaderbrd;



import edu.iastate.loginscreen.R;

public class SnakeStartup extends AppCompatActivity {

    Button Easy;
    Button Medium;
    Button Hard;
    Button Leaderboard;
    int EasyLevel = 300;
    int MediumLevel = 150;
    int HardLevel = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_startup);

        Easy = findViewById(R.id.Easybtn);
        Medium = findViewById(R.id.Mediumbtn);
        Hard = findViewById(R.id.Hardbtn);
        Leaderboard = findViewById(R.id.LBoardBtn);

        Leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent leader = new Intent(SnakeStartup.this, Leaderbrd.class);
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
        Medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    String value = extras.getString("userid");
                }
                Intent i = new Intent(SnakeStartup.this, SnakeMainActivity.class);
                i.putExtra("userid",extras.getString("userid"));
                i.putExtra("level", MediumLevel);
                i.putExtra("monsters", 6);

                SnakeStartup.this.startActivity(i);

            }
        });
        Hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    String value = extras.getString("userid");
                }
                Intent i = new Intent(SnakeStartup.this, SnakeMainActivity.class);
                i.putExtra("userid",extras.getString("userid"));
                i.putExtra("level", HardLevel);
                i.putExtra("monsters", 3);
                i.putExtra("monsters", 10);

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
