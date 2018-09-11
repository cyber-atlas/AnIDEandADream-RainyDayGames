package com.example.ruski.mygame;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class GameView extends GLSurfaceView{

    private final GameRenderer gameRenderer;

    public GameView(Context context){

        super(context);

        setEGLContextClientVersion(2);

        gameRenderer = new Renderer(context);

        setRenderer(gameRenderer);

        //Android Canvas


    }



}
