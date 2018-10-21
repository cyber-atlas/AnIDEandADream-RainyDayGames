package edu.iastate.IDE_AND_A_DREAM.Login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import edu.iastate.loginscreen.R;

public class signuppage extends AppCompatActivity {

    private TextView userEmail;
    private TextView userPassword;
    private TextView userID;

    //@Override
    public void OnCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuppage);

        userEmail = findViewById((R.id.email));
        userPassword = findViewById(R.id.password);
        userID = findViewById((R.id.userID));

    }
}
