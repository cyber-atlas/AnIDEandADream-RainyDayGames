package edu.iastate.IDE_AND_A_DREAM.SpaceShooterSingle;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import edu.iastate.IDE_AND_A_DREAM.GlobalUser.User;
import edu.iastate.loginscreen.R;


/**
 * @Author Alexander Stevenson
 * The type Game activity.
 */
public class GameActivity extends AppCompatActivity {

    //Declaring the gameview
    private GameView gameView;
    private Button btn;
    private RequestQueue mQueue;
    User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        u =  (User)getApplicationContext();
        mQueue= Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_spaceshooter_game);

        /**
        btn=(Button)findViewById(R.id.btnShoot);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"here",Toast.LENGTH_LONG).show();
            }
        });
         **/
        //TODO look more into displays
        //Getting the display object
        Display display = getWindowManager().getDefaultDisplay();

        //Getting the screen resolution and stores it in a point obj
        Point size = new Point();
        display.getSize(size);

        //TODO logging the size for testing purposes
        Log.d("SizeX",Integer.toString(size.x));
        Log.d("SizeY",Integer.toString(size.y));


        //initialize the view
//        gameView = new GameView(this);
       //Pass in the screen size and the context to the gameView
        gameView = new GameView(this, size.x, size.y);

        //adding the gameview to the contentview
        setContentView(gameView);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
    }

    //pauses the game when the activity is paused
    @Override
    protected void onPause(){

        super.onPause();
        //uses the pause that we defined in GameView
        gameView.pause();
        try {
            send_score();
        }catch (Exception e){
            Log.d("error", e.toString());
        }
    }


    @Override
    protected void onStop() {
        super.onStop();

    }

    //runs the game when the activity is resumed
    @Override
    protected void onResume(){
        super.onResume();
        //uses the resume that we defined in Gameview
        gameView.resume();

    }

/**

    public void sendScore() {
        Bundle extras = getIntent().getExtras();
       if (extras != null) {
            String value = extras.getString("userid");
        Log.d("Final User ID", value);
        }

        int score = gameView.getScore();
        String server_url_post = "http://proj309-vc-04.misc.iastate.edu:8080/scores/new?userid="+extras.getString("userid")+"&game=1&score="+ score;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url_post, new Response.Listener<String>() {

           @Override
            public void onResponse(String response) {
                // Toast.makeText(getApplication(), response, Toast.LENGTH_SHORT).show();
                Log.d("msg", "here");

            }
        }, new Response.ErrorListener() {
            @Override
             public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplication(), error+"", Toast.LENGTH_SHORT).show();
            }
        });

        mQueue.add(stringRequest);
    }
 **/


 public void send_score() {
//        Bundle extras = getIntent().getExtras();

        int id = u.getId();
        Log.d("userid ss", Integer.valueOf(id).toString());
        /**
        if (extras != null) {
            String value = extras.getString("userid");
            Log.d("Final User ID", value);
        }
         **/

//        String server_url_post = "http://proj309-vc-04.misc.iastate.edu:8080/scores/new?userid="+ extras.getString("userid")+"&game=4&score="+ gameView.getScore();
     String server_url_post = "http://proj309-vc-04.misc.iastate.edu:8080/scores/new?userid="+ Integer.valueOf(id).toString() +"&game=1&score="+ gameView.getScore();
     Log.d("server", server_url_post);
     StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url_post, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(getApplication(), response, Toast.LENGTH_SHORT).show();
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
