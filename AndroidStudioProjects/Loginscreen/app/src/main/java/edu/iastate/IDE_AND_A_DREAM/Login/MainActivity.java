package edu.iastate.IDE_AND_A_DREAM.Login;

import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.loginscreen.R;
import edu.iastate.IDE_AND_A_DREAM.secondpage;
import edu.iastate.IDE_AND_A_DREAM.GlobalUser.User;

/**
 * @Author Merin Mundt This is first page user sees after logging into the game. prompts for user name and
 * password so they log in
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {

    private TextView mTextViewResult;
    private TextView passcode;
    private RequestQueue mQueue;

    /**
     * The Username.
     */
    String username;
    /**
     * The Id.
     */
    int id;
    /**
     * The Email.
     */
    String email;



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

    /**
     * Json parse.
     * This method calls an api to the server to authenticate the user before starting the next activity
     */
    @TargetApi(19)
    public void jsonParse() {
        final String em = mTextViewResult.getText().toString();
        final String pc = passcode.getText().toString();
        final String url = "http://proj309-vc-04.misc.iastate.edu:8080/users?email=" + em + "&password=" + pc;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if(response == null){
                            Toast.makeText(getApplication(), "Something happened", Toast.LENGTH_SHORT).show();
                        }
                        try {
                            User globaluser = (User)getApplicationContext();
                            JSONObject user;
                            user = response;
                            username = user.getString("username");
                            email = user.getString("email");
                            id = user.getInt("id");
                            globaluser.setEmail(email);
                            globaluser.setUsername(username);
                            globaluser.setId(id);

                            Log.d("username", globaluser.getUsername());

                            if (email.equals(em)) {
                                Log.d("tag", "equals");
                                Intent i = new Intent(MainActivity.this, WelcomeSplashScreen.class);
                                i.putExtra("userid",user.getString("id"));
                                i.putExtra("username",user.getString("username"));
                                startActivity(i);
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
                Toast.makeText(getApplication(), "The password or email you entered is incorrect", Toast.LENGTH_SHORT).show();

                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }


}
