package edu.iastate.IDE_AND_A_DREAM.Login;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;

import com.android.volley.RequestQueue;

import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.IDE_AND_A_DREAM.RegisterRequest;
import edu.iastate.loginscreen.R;

public class signUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final TextView email = findViewById(R.id.email);
        final TextView password = findViewById(R.id.password);
        final TextView userID = findViewById(R.id.userID);
        Button button = findViewById(R.id.SignUpButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = email.getText().toString();
                final String passcode = password.getText().toString();
                final String UserID = userID.getText().toString();

                Response.Listener<String> responselistener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                              //  startActivity(new Intent(signUp.this, MainActivity.class));
                                Intent intent = new Intent(signUp.this, MainActivity.class);
                                signUp.this.startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(signUp.this);
                                builder.setMessage("Register Failed");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(Email, passcode, UserID, responselistener);
                RequestQueue queue = Volley.newRequestQueue(signUp.this);
                queue.add(registerRequest);

            }
        });

    }
}
