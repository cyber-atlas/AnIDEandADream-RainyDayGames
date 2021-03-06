package edu.iastate.IDE_AND_A_DREAM.Snake_Multiplayer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import edu.iastate.loginscreen.R;

/**
 * The type Snake splash.
 */
public class SnakeSplash extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snakemulti_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    String value = extras.getString("userid");
                    Log.d("Message from Signup", value);
                }
                Log.d("Tag", "in signup button");
                Intent i = new Intent(SnakeSplash.this, SnakeStartup.class);
                i.putExtra("userid",extras.getString("userid"));
                SnakeSplash.this.startActivity(i);

//                Intent SnakeMainIntent = new Intent(SnakeSplash.this, SnakeStartup.class);
//                SnakeSplash.this.startActivity(SnakeMainIntent);
                SnakeSplash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
