package edu.iastate.IDE_AND_A_DREAM.Snake_Multiplayer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import edu.iastate.loginscreen.R;

/*
      ___           ___           ___                    ___                         ___
     /__/\         /__/\         /  /\                  /  /\         _____         /__/\
     \  \:\       |  |::\       /  /::\                /  /::\       /  /::\        \  \:\
      \  \:\      |  |:|:\     /  /:/\:\              /  /:/\:\     /  /:/\:\        \  \:\
  ___  \  \:\   __|__|:|\:\   /  /:/~/::\            /  /:/~/::\   /  /:/~/::\   ___  \  \:\
 /__/\  \__\:\ /__/::::| \:\ /__/:/ /:/\:\          /__/:/ /:/\:\ /__/:/ /:/\:| /__/\  \__\:\
 \  \:\ /  /:/ \  \:\~~\__\/ \  \:\/:/__\/          \  \:\/:/__\/ \  \:\/:/~/:/ \  \:\ /  /:/
  \  \:\  /:/   \  \:\        \  \::/                \  \::/       \  \::/ /:/   \  \:\  /:/
   \  \:\/:/     \  \:\        \  \:\                 \  \:\        \  \:\/:/     \  \:\/:/
    \  \::/       \  \:\        \  \:\                 \  \:\        \  \::/       \  \::/
     \__\/         \__\/         \__\/                  \__\/         \__\/         \__\/
 */


public class SnakeMainActivity extends AppCompatActivity implements View.OnTouchListener {

    private TextView HighScore;
    private TextView CurrentScore;


    private final Handler handler = new Handler();

    private SnakeEngine gameEngine;
    private SnakeView snakeView;
    int HiScore;
    int updateDelay;
    int numMon;
    int prevScore = 0;

    private RequestQueue mQueue;
    private float prevX, prevY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_main);
        HighScore = findViewById(R.id.HighScoreTextView);
        CurrentScore = findViewById(R.id.CurrentScoreTextView);
        CurrentScore.setText("Current Score: "+prevScore);


        Bundle extras = getIntent().getExtras();
        int value = extras.getInt("level");
        int monsterVal = extras.getInt("monsters");
        numMon = monsterVal;
        updateDelay = value;

        gameEngine = new SnakeEngine();
        gameEngine.initGame(numMon);
        mQueue = Volley.newRequestQueue(this);


        Log.d("Update Valley",String.valueOf(updateDelay));

        snakeView = findViewById(R.id.snakeView);
        snakeView.setOnTouchListener(this);
        get_high_score();
        startUpdateHandler();
    }

    private void startUpdateHandler() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gameEngine.Update();

                if (gameEngine.getCurrentGameState() == GameState.Running) {
                    checkupdateScore();
                    handler.postDelayed(this, updateDelay);
                }

                if (gameEngine.getCurrentGameState() == GameState.Lost) {
                    OnGameLost();
                }
                snakeView.setSnakeViewMap(gameEngine.getmap());
                snakeView.invalidate();

            }
        }, updateDelay);
    }

    private void OnGameLost() {
        if(gameEngine.score > HiScore)
        {
            Toast.makeText(this, "New HighScore: "+gameEngine.score+" Good Job", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Score: " + gameEngine.score, Toast.LENGTH_LONG).show();
        }
        send_score();
        Bundle extras = getIntent().getExtras();
        String value = extras.getString("userid");
        Intent EndGame = new Intent(SnakeMainActivity.this, SnakeStartup.class);
        EndGame.putExtra("userid",value);
        SnakeMainActivity.this.startActivity(EndGame);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                prevX = event.getX();
                prevY = event.getY();


                break;
            case MotionEvent.ACTION_UP:
                float newX = event.getX();
                float newY = event.getY();

                //ueabu where user swipe

                if(Math.abs(newX - prevX) > Math.abs(newY - prevY)){
                    if(newX > prevX){
                        gameEngine.updateDirection(Direction.East);
                    }else{
                        gameEngine.updateDirection(Direction.West);
                    }
                }else{
                    if(newY > prevY){
                        gameEngine.updateDirection(Direction.South);
                    }else{
                        gameEngine.updateDirection(Direction.North);
                    }
                }
                break;
        }
        return true;
    }

    public void checkupdateScore()
    {
        if(prevScore < gameEngine.score)
        {
            prevScore = gameEngine.score;
            updateScore(prevScore);
        }
    }

    public void updateScore(int score)
    {
        CurrentScore.setText("Current Score: "+ score);
    }


    public void get_high_score()
    {
        Bundle extras = getIntent().getExtras();
        String value = extras.getString("userid");
        String url = "http://proj309-vc-04.misc.iastate.edu:8080/scores?userid="+value+"&game=4";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray user ;
                            user = response;
                            Log.d("From get highscore", response.toString());
                            String highscore = user.getJSONObject(0).getString("score");
                            HiScore = Integer.parseInt(highscore);
                            Log.d("Highscore", highscore);
                            HighScore.setText("High Score: "+highscore);
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


    public void send_score() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("userid");
            Log.d("Final User ID", value);
        }

        String server_url_post = "http://proj309-vc-04.misc.iastate.edu:8080/scores/new?userid="+ extras.getString("userid")+"&game=4&score="+ gameEngine.score;
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
