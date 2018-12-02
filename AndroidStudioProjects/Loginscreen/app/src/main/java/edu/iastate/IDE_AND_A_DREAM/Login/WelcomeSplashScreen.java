package edu.iastate.IDE_AND_A_DREAM.Login;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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

import edu.iastate.IDE_AND_A_DREAM.GlobalUser.User;
import edu.iastate.IDE_AND_A_DREAM.secondpage;

import edu.iastate.IDE_AND_A_DREAM.Snake.GamesList;
import edu.iastate.IDE_AND_A_DREAM.Snake.SnakeSplash;
import edu.iastate.IDE_AND_A_DREAM.Snake.SnakeStartup;
import edu.iastate.loginscreen.R;

public class WelcomeSplashScreen extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 6000;
    private TextView WelcomeName;
    private TextView Quote;
    private RequestQueue mQueue;
    String QuoteMessage;
    String Author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_splash_screen);
        WelcomeName = findViewById(R.id.WelcomeName);
        Quote = findViewById(R.id.QOD);
        Bundle extras = getIntent().getExtras();
        String  username = extras.getString("username");
        WelcomeName.setText("Welcome "+ username);
        mQueue = Volley.newRequestQueue(this);
        PublicAPICall();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Bundle extras = getIntent().getExtras();
                String value = extras.getString("userid");
                Intent i = new Intent(WelcomeSplashScreen.this, secondpage.class);
                i.putExtra("userid",extras.getString("userid"));
                WelcomeSplashScreen.this.startActivity(i);
                WelcomeSplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    public void PublicAPICall() {
        final String url = "http://quotes.rest/qod.json?category=funny";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response == null){
                            Toast.makeText(getApplication(), "Something ", Toast.LENGTH_SHORT).show();
                        }
                        try {
                            JSONObject QuoteObj;
                            JSONObject Content;
                            JSONArray Quotes;
                            String STO;
                            QuoteObj = response;

                            Content = QuoteObj.getJSONObject("contents");
                            Quotes = Content.getJSONArray("quotes");
                            STO = Quotes.getString(0);
                            JSONObject FINALO = new JSONObject(STO);

                            QuoteMessage = FINALO.getString("quote");
                            Author = FINALO.getString("author");
                            String q = "Quote of the Day: \n"+QuoteMessage + "\n" +"-"+Author;

                            Quote.setText(q);

                            Log.d("The quote", QuoteMessage);
                            Log.d("The Author", Author);

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(), "The password or email you entered is incorrect", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}
