package edu.iastate.IDE_AND_A_DREAM;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.IDE_AND_A_DREAM.Login.MainActivity;
import edu.iastate.IDE_AND_A_DREAM.Login.WelcomeSplashScreen;
import edu.iastate.loginscreen.R;

public class UserProfile extends AppCompatActivity {

    private TextView Email;
    private TextView Password;
    private TextView Username;
    private RequestQueue mQueue;
    Button UpdateInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        Email = findViewById(R.id.editTextEmail);
        Password = findViewById(R.id.editTextPassword);
        Username = findViewById(R.id.editTextUsername);
        UpdateInfo = findViewById(R.id.Update_Info);
        mQueue = Volley.newRequestQueue(this);


        populate_profile();

    }

    public void populate_profile()
    {
        Bundle extras = getIntent().getExtras();
        String value = extras.getString("userid");

        String URL = "http://proj309-vc-04.misc.iastate.edu:8080/users?id="+value;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject user;
                            user = response;

                            String username = user.getString("username");
                            String email = user.getString("email");
                            String id = user.getString("id");

                            Email.setText(email);
                            Username.setText(username);

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
