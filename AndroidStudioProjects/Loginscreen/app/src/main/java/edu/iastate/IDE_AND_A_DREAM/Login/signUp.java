package edu.iastate.IDE_AND_A_DREAM.Login;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.IDE_AND_A_DREAM.RegisterRequest;
import edu.iastate.loginscreen.R;

public class signUp extends AppCompatActivity {

    TextView email;
    TextView password;
    TextView username;
    Button Signup_button;

    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

         email = findViewById(R.id.email);
         password = findViewById(R.id.password);
         username = findViewById(R.id.username);
        Signup_button = findViewById(R.id.SignUpButton);
        mQueue = Volley.newRequestQueue(this);


        Signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup_user();
                Intent UserCreated = new Intent(signUp.this, MainActivity.class);
                signUp.this.startActivity(UserCreated);
            }
        });

    }

    public void signup_user()
    {
        String Email = email.getText().toString();
        String passcode = password.getText().toString();
        String user_name = username.getText().toString();

        String URL = "http://proj309-vc-04.misc.iastate.edu:8080/users/new?username="+user_name+"&password="+passcode+"&email="+Email;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplication(), "Account Created. Please Log in", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(), "Could not create account, try again later", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(stringRequest);
    }


}
