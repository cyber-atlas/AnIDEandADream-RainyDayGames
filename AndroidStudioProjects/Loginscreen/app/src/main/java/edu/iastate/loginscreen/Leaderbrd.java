package edu.iastate.loginscreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import android.util.Log;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class Leaderbrd extends AppCompatActivity {


    private ListView simpleList;
    private RequestQueue mQueue;
    ArrayList<Integer> scorelist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleList = findViewById(R.id.simpleListView);
        final String url = "http://proj309-vc-04.misc.iastate.edu:8080/scores?game=4";

        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("tag", "in response");
                    JSONObject user;
                    user = response;

                    int score = user.getInt("score");
                    scorelist.add(score);


                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

                Collections.sort(scorelist);
                ArrayList<Integer> shortList = new ArrayList<>();
                for(int i = 0; i < 5; i++){
                    shortList.add(scorelist.get(i));
                }
                ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(Leaderbrd.this, R.layout.activity_leaderbrd, R.id.simpleListView, shortList);
                simpleList.setAdapter(arrayAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);



    }
}
