package edu.iastate.loginscreen;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SnakeMainActivity extends AppCompatActivity implements View.OnTouchListener {


    private final Handler handler = new Handler();

    private SnakeEngine gameEngine;
    private SnakeView snakeView;

    public final long updateDelay = 150;

    private RequestQueue mQueue;
    private float prevX, prevY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_main);

        gameEngine = new SnakeEngine();
        gameEngine.initGame();
        mQueue = Volley.newRequestQueue(this);

        snakeView = findViewById(R.id.snakeView);
        snakeView.setOnTouchListener(this);

        startUpdateHandler();

    }

    private void startUpdateHandler() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gameEngine.Update();

                if (gameEngine.getCurrentGameState() == GameState.Running) {
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
        Toast.makeText(this, "You lost,\n Score : " + gameEngine.score, Toast.LENGTH_LONG).show();
        jsonParse();
        Intent EndGame = new Intent(SnakeMainActivity.this, SnakeStartup.class);
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

    public void jsonParse() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("userid");
            Log.d("Final User ID", value);
        }

        String server_url_post = "http://proj309-vc-04.misc.iastate.edu:8080/scores/new?userid="+ extras.getString("userid")+"&game=4&score="+ gameEngine.score;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url_post, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplication(), response, Toast.LENGTH_SHORT).show();
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
