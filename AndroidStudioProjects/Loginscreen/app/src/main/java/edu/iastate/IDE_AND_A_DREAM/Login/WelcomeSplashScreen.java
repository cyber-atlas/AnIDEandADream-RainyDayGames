package edu.iastate.IDE_AND_A_DREAM.Login;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import edu.iastate.IDE_AND_A_DREAM.secondpage;

import edu.iastate.IDE_AND_A_DREAM.Snake.GamesList;
import edu.iastate.IDE_AND_A_DREAM.Snake.SnakeSplash;
import edu.iastate.IDE_AND_A_DREAM.Snake.SnakeStartup;
import edu.iastate.loginscreen.R;

public class WelcomeSplashScreen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2700;
    private TextView WelcomeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_splash_screen);
        WelcomeName = findViewById(R.id.WelcomeName);
        Bundle extras = getIntent().getExtras();
        String  username = extras.getString("username");
        WelcomeName.setText("Welcome "+ username);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Bundle extras = getIntent().getExtras();
                String value = extras.getString("userid");
                Intent i = new Intent(WelcomeSplashScreen.this, secondpage.class);
                i.putExtra("userid",extras.getString("userid"));
                WelcomeSplashScreen.this.startActivity(i);
                WelcomeSplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);



    }
}
