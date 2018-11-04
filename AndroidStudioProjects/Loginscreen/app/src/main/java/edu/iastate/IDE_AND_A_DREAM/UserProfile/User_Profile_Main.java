package edu.iastate.IDE_AND_A_DREAM.UserProfile;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import edu.iastate.IDE_AND_A_DREAM.secondpage;

import edu.iastate.loginscreen.R;

public class User_Profile_Main extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;

    private TextView Email;
    private TextView Username;
    private RequestQueue mQueue;
    Button Update_Username ;
    Button Update_Password;
    Button Exit_Profile;

    ImageView ProfilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        Email = findViewById(R.id.Email_View);
        //Password = findViewById(R.id.editTextPassword);
        Username = findViewById(R.id.Username_view);
        Update_Username = findViewById(R.id.Edit_Username);
        Update_Password = findViewById(R.id.Change_pwd);
        Exit_Profile = findViewById(R.id.Exit_Btn);


        ProfilePicture = (ImageView)findViewById(R.id.Profile_Pic_view);



        mQueue = Volley.newRequestQueue(this);
        populate_profile();
        ProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(GalleryIntent, RESULT_LOAD_IMAGE);
            }
        });


        Update_Username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent EUserName = new Intent(User_Profile_Main.this, EditUserName.class);
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    String value = extras.getString("userid");
                    Log.d("Message from Signin", value);
                }
                EUserName.putExtra("userid",extras.getString("userid"));
                User_Profile_Main.this.startActivity(EUserName);
            }
        });
        Update_Password.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent CPassword = new Intent(User_Profile_Main.this, ChangePassword.class);
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    String value = extras.getString("userid");
                    Log.d("Message from Signin", value);
                }
                CPassword.putExtra("userid",extras.getString("userid"));
                User_Profile_Main.this.startActivity(CPassword);
            }
        });
        Exit_Profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Exit = new Intent(User_Profile_Main.this, secondpage.class);
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    String value = extras.getString("userid");
                    Log.d("Message from Signin", value);
                }
                Exit.putExtra("userid",extras.getString("userid"));
                User_Profile_Main.this.startActivity(Exit);
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null)
        {
            Uri selectedImage = data.getData();
            ProfilePicture.setImageURI(selectedImage);
        }


    }

}
