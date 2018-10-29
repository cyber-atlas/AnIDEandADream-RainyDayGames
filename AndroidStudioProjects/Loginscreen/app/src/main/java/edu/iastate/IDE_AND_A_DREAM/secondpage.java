package edu.iastate.IDE_AND_A_DREAM;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import edu.iastate.IDE_AND_A_DREAM.Snake.GamesList;
import edu.iastate.loginscreen.R;

public class secondpage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondpage);

        Button Gamesbutton = findViewById(R.id.GamesButton);
        Button Profilebutton = findViewById(R.id.profilebutton);


        Gamesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    String value = extras.getString("userid");
                    Log.d("Message from Signup", value);
                }
                Intent i = new Intent(secondpage.this, GamesList.class);
                i.putExtra("userid",extras.getString("userid"));
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
                Intent i = new Intent(secondpage.this, UserProfile.class);
                i.putExtra("userid",extras.getString("userid"));
                secondpage.this.startActivity(i);
            }
        });

    }
}
