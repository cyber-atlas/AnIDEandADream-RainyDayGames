package edu.iastate.loginscreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

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
