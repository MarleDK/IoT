package com.example.marle.piauth;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.function.Function;

/**
 * Created by marle on 4/20/17.
 */

public class global {
    public static RequestQueue queue;
    public static String url = "";
    public static boolean loggedIn = false;
    public static String password;
    public static String user;
    public static String publicKey;
    public static String privateKey;

    public static void setQueue(android.content.Context cont){queue = Volley.newRequestQueue(cont);};
}
