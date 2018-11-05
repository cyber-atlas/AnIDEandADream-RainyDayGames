package edu.iastate.IDE_AND_A_DREAM.UserProfile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.loginscreen.R;


public class EditUserName extends AppCompatActivity {

    private TextView OldUsername;
    private TextView NewUsername;
    private RequestQueue mQueue;
    Button Update_Username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_name);

        OldUsername = findViewById(R.id.Old_Username);
        NewUsername = findViewById(R.id.New_Username);
        Update_Username = findViewById(R.id.Update_Username);
        mQueue = Volley.newRequestQueue(this);


        Update_Username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update_Username();
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    String value = extras.getString("userid");
                    Log.d("Message from Signin", value);
                }
                Intent Back = new Intent(EditUserName.this, User_Profile_Main.class);
                Back.putExtra("userid",extras.getString("userid"));
                EditUserName.this.startActivity(Back);
            }
        });

        populate_Username();
    }


    public void populate_Username()
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

                            OldUsername.setText(username);

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

    public void Update_Username()
    {
        Bundle extras = getIntent().getExtras();
        String value = extras.getString("userid");

        //http://proj309-vc-04.misc.iastate.edu:8080/edit?id=1&old_username=+""+&new_username="";
        String Oldusername = OldUsername.getText().toString();
        String URL = "http://proj309-vc-04.misc.iastate.edu:8080/users/edit?id="+value+"&old_username="+Oldusername+"&new_username="+NewUsername.getText().toString();
        //Log.d("Post_URL",URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplication(), "Username Updated, Exit Profile to see changes.", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(), "Could not update, try again later", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(stringRequest);
    }

}


