package edu.iastate.IDE_AND_A_DREAM.Leaderboards;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import edu.iastate.loginscreen.R;

public class SnakeppLeaderboard extends AppCompatActivity {

    private ListView simpleList;
    private RequestQueue mQueue;

    ArrayList<Integer> scorelist = new ArrayList<>();
    ArrayList<Integer> userids = new ArrayList<>();
    HashMap<Integer, String> names = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snakepp_leaders);
        simpleList = findViewById(R.id.simpleListView);
        Log.d("Tag", "in java");
        final String url = "http://proj309-vc-04.misc.iastate.edu:8080/scores?game=6";
        final String userurl = "http://proj309-vc-04.misc.iastate.edu:8080/users";

        mQueue = Volley.newRequestQueue(this);

        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, userurl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{

                    for (int i = 0; i < response.length(); i++){
                        JSONObject usernames;
                        usernames = response.getJSONObject(i);

                        int id = usernames.getInt("id");
                        String name = usernames.getString("username");

                        names.put(id, name);



                    }
                }catch (JSONException e1) {
                    e1.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(req);
        for(int i = 0; i < 10000; i ++){
            int x = 0;
            int y = 1;
            int z = x + y;
        }
        mQueue.start();


        JsonArrayRequest request= new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for (int i = 0; i < response.length(); i++) {
                        Log.d("tag", "in response");
                        JSONObject user;
                        user = response.getJSONObject(i);

                        int score = user.getInt("score");
                        int name = user.getInt("userid");
                        Log.d("Tag", user.toString());
                        scorelist.add(score);
                        userids.add(name);
                    }

                } catch (JSONException e1) {
                    e1.printStackTrace();
                }



                ArrayList<String> shortList = new ArrayList<>();
                for(int i = 0; i < 10; i++){
                    shortList.add(scorelist.get(i) + " - "+ names.get(userids.get(i)));
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(SnakeppLeaderboard.this, R.layout.activity_text_view, shortList);
                ListView listView = findViewById(R.id.simpleListView);
                listView.setAdapter(adapter);


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

