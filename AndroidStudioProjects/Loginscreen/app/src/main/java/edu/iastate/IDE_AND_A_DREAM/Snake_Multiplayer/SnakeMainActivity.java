package edu.iastate.IDE_AND_A_DREAM.Snake_Multiplayer;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.java_websocket.client.WebSocketClient;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.util.List;

import edu.iastate.IDE_AND_A_DREAM.GlobalUser.User;
import edu.iastate.IDE_AND_A_DREAM.Snake_Multiplayer.Snake_Object.Map;


import edu.iastate.IDE_AND_A_DREAM.Snake_Multiplayer.Snake_Object.Snake;
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
    private TextView Personalbest;
    private TextView CurrentPlayers;
    private Gson gson;

    private WebSocketClient ws;
    User globaluser;
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
        setContentView(R.layout.activity_snakemulti_main);
        HighScore = findViewById(R.id.HighScoreTextView);
        CurrentScore = findViewById(R.id.CurrentScoreTextView);
        Personalbest = findViewById(R.id.personalbest);
        CurrentPlayers = findViewById(R.id.currentplayers);

        CurrentScore.setText("Current Score: "+prevScore);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        globaluser =  (User)getApplicationContext();
        Bundle extras = getIntent().getExtras();
        int value = extras.getInt("level");
        int monsterVal = extras.getInt("monsters");
        numMon = monsterVal;
        updateDelay = value;

        gameEngine = new SnakeEngine();
        gameEngine.initGame(numMon);
        mQueue = Volley.newRequestQueue(this);

        snakeView = findViewById(R.id.snakeViewM);
        snakeView.setOnTouchListener(this);
        get_personal_best();
        getGetMapFromAPI();

    }

    public void UpdateGameMap(String data)
    {
        JSONObject snakejsonobj;
        Map snake_map;
        try {
            snakejsonobj = new JSONObject(data);
            snake_map = gson.fromJson(snakejsonobj.toString(),Map.class);
            String uname = globaluser.getUsername();
            int snakeScore = 0;
            List<Snake> snakesinmap = snake_map.getSnakes();
            int i = 1;
            int HighestScore = 0;
            String players = "Players On Line: "+"\n";
            for(Snake snake: snakesinmap)
            {
                players += i+". "+snake.getName()+"\n";
                if(snake.getScore() > HighestScore) {
                    HighestScore = snake.getScore();
                    String hname = snake.getName();
                    updateHighScore(HighestScore, hname);
                }

                if(snake.getName().equalsIgnoreCase(uname))
                {
                    snakeScore = snake.getScore();
                }
                updateScore(snakeScore);
                CurrentPlayers.setText(players);
//                Log.d("no", String.valueOf(i));
//                Log.d("name",snake.getName());
//                Log.d("is alive", snake.isAliveDebug());
                i++;
                if(snake.getName().equalsIgnoreCase(uname) && snake.isAliveDebug().equalsIgnoreCase("false"))
                {
                    OnGameLost(snakeScore);
                }
            }
            snakeView.setSnakeViewMap(snake_map.getMap());
            snakeView.invalidate();
        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + data + "\"");
            t.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        ws.close();
        Log.d("CDA", "onBackPressed Called");
        Bundle extras = getIntent().getExtras();
        String value = extras.getString("userid");
        Intent EndGame = new Intent(SnakeMainActivity.this, SnakeStartup.class);
        EndGame.putExtra("userid",value);
        SnakeMainActivity.this.startActivity(EndGame);
    }

    public void getGetMapFromAPI()
    {
        final String serverAddy = "ws://proj309-vc-04.misc.iastate.edu:8080/snake/"+globaluser.getUsername()+"/true";
        Log.d("url", serverAddy);
        try {
            Draft[] drafts = {new Draft_6455()};
            ws = new WebSocketClient(new URI(serverAddy), drafts[0]) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    Log.d("OPEN", "run() returned: " + "is connecting");
                }
                @Override
                public void onMessage(final String message) {
                   // Log.d("", "run() returned: " + message);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Log.d("Map object", message);
                            UpdateGameMap(message);
                        }
                    });
                }
                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("CLOSE", "OnClose() returned: " + reason);
                }
                @Override
                public void onError(Exception e) {
                    Log.d("Exception:", e.toString());
                }
            };
        }
        catch (URISyntaxException e){
            Log.d("Excpetion: ", e.getMessage());
            e.printStackTrace();
        }
        ws.connect();
    }

    private void OnGameLost(int score) {
        if(score > HiScore)
        {
            Toast.makeText(this, "New High Score: "+score+" Good Job", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Score: " + score, Toast.LENGTH_LONG).show();
        }
        ws.close();
        send_score(score);
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

                if(Math.abs(newX - prevX) > Math.abs(newY - prevY)){
                    if(newX > prevX){
                        ws.send("dir e");
                    }else{
                        ws.send("dir w");
                    }
                }else{
                    if(newY > prevY){
                        ws.send("dir s");
                    }else{
                        ws.send("dir n");
                    }
                }
                break;
        }
        return true;
    }


    public void updateScore(int score)
    {
        CurrentScore.setText("Score: "+ score);
    }
    public void updateHighScore(int score, String name) {HighScore.setText("High Score:\n"+name+ " "+ score ); }

    public void get_personal_best()
    {
        Bundle extras = getIntent().getExtras();
        String value = extras.getString("userid");
        String url = "http://proj309-vc-04.misc.iastate.edu:8080/scores?userid="+value+"&game=6";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray user ;
                            user = response;
                            String highscore = user.getJSONObject(0).getString("score");
                            HiScore = Integer.parseInt(highscore);
                            Personalbest.setText("Personal Best: "+highscore);
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

    public void send_score(int score) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("userid");
            Log.d("Final User ID", value);
        }

        String server_url_post = "http://proj309-vc-04.misc.iastate.edu:8080/scores/new?userid="+extras.getString("userid")+"&game=6&score="+ score;
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
