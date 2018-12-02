package edu.iastate.IDE_AND_A_DREAM.Messaging;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import edu.iastate.loginscreen.R;


public class MessagingMainActivity extends AppCompatActivity implements View.OnClickListener {

    //Setting the Pass button
    private Button buttonPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging_main);

        //Getting the pass button
        buttonPass  =  (Button) findViewById(R.id.buttonPass);
        buttonPass.setOnClickListener(this);

    }

    public void onClick(View v){
        startActivity(new Intent(this, Messaging.class ));
    }

}
