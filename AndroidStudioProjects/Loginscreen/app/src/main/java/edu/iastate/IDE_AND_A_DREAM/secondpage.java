package edu.iastate.IDE_AND_A_DREAM;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import edu.iastate.IDE_AND_A_DREAM.Leaderboards.LeaderboardList;
import edu.iastate.IDE_AND_A_DREAM.Snake.GamesList;
import edu.iastate.IDE_AND_A_DREAM.Snake.SnakeStartup;
import edu.iastate.IDE_AND_A_DREAM.UserProfile.User_Profile_Main;
import edu.iastate.loginscreen.Leaderbrd;
import edu.iastate.loginscreen.R;

public class secondpage extends AppCompatActivity {


    Button Profilebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondpage);

        Button Gamesbutton = findViewById(R.id.GamesButton);
        Button Leaderboard = findViewById(R.id.button6);
       Profilebutton = findViewById(R.id.profilebutton);


       Leaderboard.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                    Intent listpage = new Intent(secondpage.this, LeaderboardList.class);
                    secondpage.this.startActivity(listpage);
                   //Intent leader = new Intent(secondpage.this, Leaderbrd.class);
                   //secondpage.this.startActivity(leader);
               }
       });
               Gamesbutton.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Bundle extras = getIntent().getExtras();
                       if (extras != null) {
                           String value = extras.getString("userid");
                           Log.d("Message from Signup", value);
                       }
                       Intent i = new Intent(secondpage.this, GamesList.class);
                       i.putExtra("userid", extras.getString("userid"));
                       secondpage.this.startActivity(i);
                   }
               });

        Profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    String value = extras.getString("userid");
                    Log.d("Message from Signup", value);
                }
                Intent i = new Intent(secondpage.this, User_Profile_Main.class);
                i.putExtra("userid",extras.getString("userid"));
                secondpage.this.startActivity(i);
            }
        });

    }
}
