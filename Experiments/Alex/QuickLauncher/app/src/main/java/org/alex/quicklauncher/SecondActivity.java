package org.alex.quicklauncher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //Need to handle any extra information that we pass in
        if (getIntent().hasExtra("org.alex.quicklauncher.SOMETHING")){
            TextView tv = (TextView) findViewById(R.id.textView);
            //Gets the String from the key passed into getExtras from the intent
            String text = getIntent().getExtras().getString("org.alex.quicklauncher.SOMETHING");
            tv.setText(text);
        }
    }
}
