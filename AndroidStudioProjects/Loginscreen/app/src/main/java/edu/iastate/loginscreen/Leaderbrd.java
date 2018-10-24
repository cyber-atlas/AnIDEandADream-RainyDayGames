package edu.iastate.loginscreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class Leaderbrd extends AppCompatActivity {


    private ListView simpleList;
    private RequestQueue mQueue;
    ArrayList<Integer> scorelist = new ArrayList<>();
    String url = "http://proj309-vc-04.misc.iastate.edu:8080/scores?game=4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleList = findViewById(R.id.simpleListView);
        mQueue = Volley.newRequestQueue(this);
        populate_leaderboard();
    }

    private void populate_leaderboard()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Log.d("response",response);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Score[] scores = gson.fromJson(response, Score[].class);
                for (int i = 0; i < scores.length; i++) {
                    scorelist.add(i, scores[i].getScore());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        ArrayList<Integer> shortList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            shortList.add(scorelist.get(i));
        }
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(Leaderbrd.this, R.layout.activity_leaderbrd, R.id.simpleListView, shortList);
        simpleList.setAdapter(arrayAdapter);
        mQueue.add(stringRequest);

    }


}