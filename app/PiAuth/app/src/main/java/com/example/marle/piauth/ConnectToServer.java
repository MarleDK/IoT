package com.example.marle.piauth;

import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.marle.piauth.Data;

/**
 * Created by marle on 4/27/17.
 */

public class ConnectToServer extends AsyncTask<Data, Void, Void> {

    @Override
    protected Void doInBackground(Data... params) {
        // Instantiate the RequestQueue.
        for(Data param:params){
            RequestQueue queue = global.queue;
            String url ="http://192.168.0.105:2000/"+param.user+"/"+param.pass+"/"+param.key;

// Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            //mTextView.setText("Response is: "+ response.substring(0,500));
                            //System.out.println(response);
                            global.privateKey = response;


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("That didn't work!");
                }
            });
// Add the request to the RequestQueue.
            queue.add(stringRequest);
        }

        return null;
    }
}
