package edu.iastate.IDE_AND_A_DREAM.Messaging;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import edu.iastate.loginscreen.R;

/**
public class Messaging extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
    }
}

 **/
public class Messaging_bak extends ListActivity implements View.OnClickListener {
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;


   EditText editTextDest;
   Button buttonDest;

   private WebSocketClient ws;

    Button buttonSend;
    EditText editTextInput;
    ListView listViewList;

    //RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
    int clickCounter=0;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_messaging);
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        setListAdapter(adapter);

        buttonSend = findViewById(R.id.addBtn);

        //When this button is pressed the destination is updated
        buttonDest = findViewById(R.id.destBtn);
        buttonDest.setOnClickListener(this);

        editTextInput = findViewById(R.id.editTextInput);

        editTextDest = findViewById(R.id.editDest);

        //TODO what and why is this?!
        Draft[] drafts = {new Draft_6455()};


    }



    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void addItems(View v) throws URISyntaxException {
        //When the button is clicked it calls addItems
        //It adds to the list, then tells the adapter the list changed
        //Adapter prints out the stuff
//        listItems.add("Clicked : "+clickCounter++);
        //Should check if not empty
        if (!(editTextInput.getText().toString().trim().equals(""))){

            //TODO figure out how we are going to deal with this on the server side and
            Date tim = Calendar.getInstance().getTime();

//            Time t;
//            System.out.println(t.LocalDateTime);



            Draft[] drafts = {new Draft_6455()};

            final String serverAddy = "ws://proj309-vc-04.misc.iastate.edu:8080/rude/" + editTextDest.getText().toString().trim();
            //TODO why are we adding a draft?
            ws = new WebSocketClient(new URI(serverAddy), (Draft) drafts[0]) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    Log.d("OPEN", "run() returned: " + "is connecting");
                    listItems.add("Conencted: " + serverAddy);

                }

                @Override
                public void onMessage(String s) {

                }

                @Override
                public void onClose(int i, String reason, boolean b) {
                    Log.d("CLOSE", "OnClose() returned: " + reason);
                }

                @Override
                public void onError(Exception e) {
                    Log.d("Exception:", e.toString());

                }
            };

            //TODO when we integrate, figure out how to deal with user object and change sender to user

            Message temp = new Message(10, editTextInput.getText().toString(),
                    "alex", "danny");
//            listItems.add(editTextInput.getText().toString());
            listItems.add(temp.getDisplayMessage());
            listItems.add(tim.toString());
            editTextInput.getText().clear();

            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {


    }
}