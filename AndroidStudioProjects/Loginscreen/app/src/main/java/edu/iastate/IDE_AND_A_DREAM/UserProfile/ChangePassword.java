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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import edu.iastate.loginscreen.R;

public class ChangePassword extends AppCompatActivity {

    private TextView Old_Password;
    private TextView New_Password;
    private RequestQueue mQueue;
    Button Update_Password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Old_Password = findViewById(R.id.Old_Password);
        New_Password = findViewById(R.id.New_Password);
        Update_Password = findViewById(R.id.Update_Password);
        mQueue = Volley.newRequestQueue(this);

        Update_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update_Password();
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    String value = extras.getString("userid");
                    Log.d("Message from Signin", value);
                }
                Intent Back = new Intent(ChangePassword.this, User_Profile_Main.class);
                Back.putExtra("userid",extras.getString("userid"));
                ChangePassword.this.startActivity(Back);
            }
        });

    }


    public void Update_Password()
    {
        Bundle extras = getIntent().getExtras();
        String value = extras.getString("userid");

        //http://proj309-vc-04.misc.iastate.edu:8080/edit?id=1&old_username=+""+&new_username="";
        String OldPassword = Old_Password.getText().toString();
        String NewPassword = New_Password.getText().toString();
        String URL = "http://proj309-vc-04.misc.iastate.edu:8080/users/edit?id="+value+"&old_password="+OldPassword+"&new_password="+NewPassword;
        Log.d("Post_URL",URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplication(), "Password Updated", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(), error+"", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(stringRequest);
    }
}
