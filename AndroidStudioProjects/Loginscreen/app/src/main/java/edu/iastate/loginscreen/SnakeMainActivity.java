package edu.iastate.loginscreen;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class SnakeMainActivity extends AppCompatActivity implements View.OnTouchListener {

  //  @Override
//    public Intent getIntent() {
//        return super.getIntent();
//    }

    private final Handler handler = new Handler();

    private SnakeEngine gameEngine;
    private SnakeView snakeView;

    //    Intent Diff = getIntent();
//    long Nou = Diff.getLongExtra("UD",0);
//
//    long No = getIntent().getExtras().getLong("UD");
    public final long updateDelay = 150;


    private float prevX, prevY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_main);

        gameEngine = new SnakeEngine();
        gameEngine.initGame();

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
        Toast.makeText(this, "You lost", Toast.LENGTH_LONG).show();
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
}
