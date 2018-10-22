package edu.iastate.IDE_AND_A_DREAM;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String registerURL = "http://proj309-vc-04.misc.iastate.edu:8080/users/new";
    private Map<String, String> params;

    public RegisterRequest(String email, String password, String userID, Response.Listener<String> Listener){
        super(Method.POST, registerURL, Listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        params.put("username", userID);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

