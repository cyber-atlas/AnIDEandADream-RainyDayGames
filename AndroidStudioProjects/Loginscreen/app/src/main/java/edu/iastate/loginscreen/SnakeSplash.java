package edu.iastate.loginscreen;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SnakeSplash extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent SnakeMainIntent = new Intent(SnakeSplash.this, SnakeStartup.class);
                SnakeSplash.this.startActivity(SnakeMainIntent);
                SnakeSplash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
