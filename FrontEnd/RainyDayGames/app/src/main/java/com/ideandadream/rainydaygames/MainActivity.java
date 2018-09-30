package com.ideandadream.rainydaygames;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.sql.DriverManager.println;

public class MainActivity extends AppCompatActivity {


    //the number of times the upScore button has been pressed the highest score
    private int score;
    private int highScore;
    //The textview that holds the score and the textview that holds the high score
    private TextView textScore;
    private TextView textHighScore;
    //The Button
    Button buttonUpScore;
    Button buttonSendReset;
    private RequestQueue mQueue;
    String sever_url = "http://proj309-vc-04.misc.iastate.edu:8080/scores?userid=2&game=1";
    String server_url_post = "http://proj309-vc-04.misc.iastate.edu:8080/scores/new?userid=2&game=1&score=13";
    ArrayList list = new ArrayList();
    int position = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonUpScore = (Button) findViewById(R.id.buttonUpScore);
        buttonSendReset= (Button) findViewById(R.id.buttonSendReset);
        score = 0;
        textScore = (TextView) findViewById(R.id.textScore);
        String resultObjScore;
        mQueue = Volley.newRequestQueue(this);


        //TODO set the highscore to the highest score in the database. Or change to last score or w.e
        /** Need to get the highest score in the db
         * set it to highscore
         * change the inside of textHighScore
         *
         *Should probably aso do this in method, but that's a later thing.
         * Let's just ace this demo first
         */
        highScore = 0;
        textHighScore = (TextView) findViewById(R.id.textHighScore);

        //Handles the user clicking the upScore button
        buttonUpScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // jsonParse();
                //Increments the score
                score++;
                //Sets the text in the score text view to reflect the score
                textScore.setText("Score: " + score);
            }
        });

        buttonSendReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
                //getData();
               // jsonParse();
                //TODO post the current score

                //Resets the score to 0


                //Resets the score text to nothing, bad OOP I know. Probably should make an update method
                textScore.setText("Score: ");

                //TODO get the high score
                //TODO reset the text view that has the highscore
                textHighScore.setText("High Score: " + score);
            }
        });
    }




    private void getData()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, sever_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Log.d("response",response);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Score[] scores = gson.fromJson(response, Score[].class);
                textHighScore.setText("High Score: " + scores[0].getScore().toString());
                score = scores[0].getScore();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(stringRequest);
    }

    private void sendData()
    {
        {
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

    private void jsonParse() {

               String url = "http://proj309-vc-04.misc.iastate.edu:8080/scores?userid=2&game=1";

            // prepare the Request
            JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>()
                    {
                        @Override
                        public void onResponse(JSONArray response) {
                            // display response
                            // Log.d("Response", response.toString());
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    HashMap<String, String> data = new HashMap<String, String>();
                                    JSONObject resultObj = response.getJSONObject(i);

                                    data.put("id", resultObj.getString("id"));
                                    data.put("userid", resultObj.getString("userid"));
                                 data.put("game", resultObj.getString("game"));
                                    String Score = resultObj.getString("score");
                                data.put("score", resultObj.getString("score"));
                                data.put("date", resultObj.getString("date"));
                                list.add(data);

                                    textHighScore.setText("High Score: " + Score);


                                    println(data.toString());
                                    Log.d("data",data.toString());
                                }
                            } catch ( JSONException jsone) {
                                Log.e( "JSON EXCEPTION", jsone.getMessage() );
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error.Response", error.toString());
                        }
                    }
            );
        // add it to the RequestQueue
        mQueue.add(getRequest);
    }





}
