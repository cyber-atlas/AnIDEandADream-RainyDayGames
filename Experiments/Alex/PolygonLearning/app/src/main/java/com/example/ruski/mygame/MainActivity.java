package com.example.ruski.mygame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        myGameView =  new GameView(this);
        setContentView(myGameView);
    }

    @Override
    protected void onPause(){
        super.onPause();
        //the following call pauses the rendering thread
        //If the OpenGL application is memory intensive, might be worth deallocating objects that
        //Consume alot of memory here
        myGameView.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        //the following call, resumes a paused rendering thread
        //if graphic objects were dealloctated, now is a good time to reallocate them
        myGameView.onResume();
    }



}