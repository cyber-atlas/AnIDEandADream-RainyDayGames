package com.example.ruski.mygame;

import android.opengl.GLSurfaceView;

public class GameRenderer implements GLSurfaceView.Renderer{

    private static final String TAG = "GameRenderer";
    private Context context;
    public static float[] mMVPMatrix = new float[16];
    public static float[] mProjectionMatrix = new float[16];
    public static float[] mViewMatrix = new float[16];

    private Starfield starfield;



}
