package edu.iastate.loginscreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SnakeStartup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_startup);

        Button StartEasy = findViewById(R.id.Easybtn);

        StartEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Tag", "in signup button");
                //startActivity(new Intent(MainActivity.this, signuppage.class));
                Intent StartSnakeGameIntent = new Intent(SnakeStartup.this, SnakeMainActivity.class);
                SnakeStartup.this.startActivity(StartSnakeGameIntent);
            }
        });

    }
}
