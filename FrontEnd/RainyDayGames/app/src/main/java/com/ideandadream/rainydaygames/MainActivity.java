package com.ideandadream.rainydaygames;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.w3c.dom.Text;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonUpScore = (Button) findViewById(R.id.buttonUpScore);
        buttonSendReset= (Button) findViewById(R.id.buttonSendReset);
        score = 0;
        textScore = (TextView) findViewById(R.id.textScore);
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

                //Increments the score
                score++;
                //Sets the text in the score text view to reflect the score
                textScore.setText("Score: " + score);
                jsonParse();
            }
        });

        buttonSendReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO post the current score

                //Resets the score to 0
                score = 0;

                //Resets the score text to nothing, bad OOP I know. Probably should make an update method
                textScore.setText("Score: ");

                //TODO get the high score
                //TODO reset the text view that has the highscore
               textHighScore.setText("High Score: " + highScore);
                jsonParse();

            }
        });
    }

    private void jsonParse() {

        String url = "http://proj309-vc-04.misc.iastate.edu:8080/scores";

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
                                JSONObject resultObj = response.getJSONObject(i);
                                /* resultObj : { "name": "requisitions", "id": "PR2" } */
                                String resultObjid = resultObj.getString("id");
                                String resultObjuserid = resultObj.getString("userid");
                                Log.d("name",resultObjuserid );
                                Log.d("id",resultObjid );
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


//        JsonArrayRequest request = new JsonArrayRequest((Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONArray array = new JSONArray(response);
//                            println(array.toString());
//
////                            for (int i = 0; i < jsonArray.length(); i++) {
////                                JSONObject employee = jsonArray.getJSONObject(i);
////
////                                String firstName = employee.getString("firstname");
////                                int age = employee.getInt("age");
////                                String mail = employee.getString("mail");
////
////                                mTextViewResult.append(firstName + ", " + String.valueOf(age) + ", " + mail + "\n\n");
////                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });

//        mQueue.add(request);
    }


}
