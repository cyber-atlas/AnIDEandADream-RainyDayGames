package edu.iastate.loginscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class secondpage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondpage);

        Button Gamesbutton = findViewById(R.id.GamesButton);

        Gamesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    String value = extras.getString("userid");
                    Log.d("Message from Signup", value);
                }
                //startActivity(new Intent(MainActivity.this, signuppage.class));
//                Intent GamesListIntent = new Intent(secondpage.this, GamesList.class);
//                secondpage.this.startActivity(GamesListIntent);
                Intent i = new Intent(secondpage.this, GamesList.class);
                i.putExtra("userid",extras.getString("userid"));
                secondpage.this.startActivity(i);
            }
        });

    }
}
