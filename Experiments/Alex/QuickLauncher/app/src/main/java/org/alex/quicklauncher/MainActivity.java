package org.alex.quicklauncher;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Attempts to launch another activity within our app
        Button seconActivityBtn = (Button) findViewById(R.id.secondActivityBtn);
        //sets a click listener for the button
        seconActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent takes application context, what is the context of this action? What class do we want to create an object of?
                Intent startIntent = new Intent(getApplicationContext(),SecondActivity.class);
                //Show how to pass information to another activity, changes the text on the next page
                startIntent.putExtra("org.alex.quicklauncher.SOMETHING", "Hello Alex!");
                //Starts the second activity using the intent
                startActivity(startIntent);
            }
        });

        //Attempt to launch an activity outside our app
        Button googleBtn = (Button)findViewById(R.id.googleBtn);
        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String google = "https://www.google.com";
                //Parses the webaddress
                Uri webaddress = Uri.parse(google);

                //Intent to open another app to open the webaddress
                //Checks if there are any apps that allow the device to view the address
                Intent gotoGoogle = new Intent (Intent.ACTION_VIEW, webaddress);

                if (gotoGoogle.resolveActivity(getPackageManager()) != null){
                    //If there is an app that can resolve our intent, start the activity
                    startActivity(gotoGoogle);
                }

            }
        });

    }
}
