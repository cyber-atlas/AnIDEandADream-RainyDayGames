package edu.iastate.loginscreen;

import android.annotation.TargetApi;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private TextView mTextViewResult;
    private TextView passcode;
    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewResult = findViewById(R.id.email);
        passcode = findViewById(R.id.password);
        Button button = findViewById(R.id.button);
        Button signup = findViewById(R.id.signup);

        mQueue = Volley.newRequestQueue(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Tag", "in signup button");
                //startActivity(new Intent(MainActivity.this, signuppage.class));
                Intent signUpIntent = new Intent(MainActivity.this, signUp.class);
                MainActivity.this.startActivity(signUpIntent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                jsonParse();
            }

        });
    }

    @TargetApi(19)
    public void jsonParse() {
        final String em = mTextViewResult.getText().toString();
        final String pc = passcode.getText().toString();
        final String url = "http://proj309-vc-04.misc.iastate.edu:8080/users?email=" + em + "&password=" + pc;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject user;
                            user = response;

                            String email = user.getString("email");
                            
                            if (email.equals(em)) {
                                Log.d("tag", "equals");
                                startActivity(new Intent(MainActivity.this, secondpage.class));
                            }
                            else {
                                Log.d("tag", "not connected");
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });


        mQueue.add(request);
    }


}
