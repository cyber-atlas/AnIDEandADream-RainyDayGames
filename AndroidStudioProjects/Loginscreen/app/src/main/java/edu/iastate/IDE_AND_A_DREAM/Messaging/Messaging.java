package edu.iastate.IDE_AND_A_DREAM.Messaging;

import android.app.ListActivity;
import android.bluetooth.BluetoothClass;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
 * public class Messaging extends AppCompatActivity {
 *
 * @Override protected void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState);
 * setContentView(R.layout.activity_messaging);
 * }
 * }*
 */
public class Messaging extends ListActivity{
    /**
     * The List items.
     */
//LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems=new ArrayList<String>();

    /**
     * The Adapter.
     */
//DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;


    /**
     * The Edit text dest.
     */
    EditText editTextDest;
    /**
     * The Button dest.
     */
    Button buttonDest;

    private WebSocketClient ws;

    /**
     * The Button send.
     */
//    Button buttonSend;
    ImageButton buttonSend;
    /**
     * The Edit text input.
     */
    EditText editTextInput;
    /**
     * The List view list.
     */
    ListView listViewList;

    /**
     * The Click counter.
     */
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



        editTextInput = findViewById(R.id.editTextInput);

        editTextDest = findViewById(R.id.editDest);

        //When this button is pressed the destination is updated
        buttonDest = findViewById(R.id.destBtn);

        buttonDest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Draft[] drafts = {new Draft_6455()};

                final String serverAddy = "ws://proj309-vc-04.misc.iastate.edu:8080/snake/" + editTextDest.getText().toString().trim() + "/false";
                try {
                    //TODO why are we adding a draft?
                    ws = new WebSocketClient(new URI(serverAddy), drafts[0]) {
                        @Override
                        public void onOpen(ServerHandshake serverHandshake) {
                            Log.d("OPEN", "run() returned: " + "is connecting");
//                            listItems.add("Connected: " + serverAddy);
                            listItems.add("Connected!");
                        }

                        @Override
                        public void onMessage(final String message) {
                            Log.d("", "run() returned: " + message);

                            String s = editTextDest.getText().toString();
//                        editTextDest.setText(s + " Server: " + message);

//                        listItems.add("server: " + s + "\n" + message);
//                        adapter.notifyDataSetChanged();
//                            runOnUiThread(updateList());

                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   updateList(message);
                               }
                           });
//                            updateList(message);
                        }

                        @Override
                        public void onClose(int code, String reason, boolean remote) {
                            Log.d("CLOSE", "OnClose() returned: " + reason);
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("Exception:", e.toString());

                        }
                    };
                }
                catch (URISyntaxException e){
                    Log.d("Excpetion: ", e.getMessage());
                    e.printStackTrace();
                }
                ws.connect();
            }

        });
        buttonSend = findViewById(R.id.addBtn);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    if (editTextInput.getText().toString().trim() == "" ){return;}
                    ws.send(editTextInput.getText().toString().trim());
                    //Clears the messagebox
                    editTextInput.getText().clear();
                }
                catch(Exception e){
                    Log.d("ExceptionSendMessage:", e.getMessage());
                }
            }
        });

        //TODO what and why is this?!
//        Draft[] drafts = {new Draft_6455()};

    }

    /**
     * Update list.
     *
     * @param message the message
     */
    public void updateList(String message){
            listItems.add(message);
            adapter.notifyDataSetChanged();
        }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            ws.close();
        }catch (Exception e){
            Log.d("ExceptionSendMessage:", e.getMessage());
        }
    }
}