package com.protoype.osindex.currencyexchange.networks;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by proteux on 10/26/17.
 */

public class VolleyClient {

    private static VolleyClient volleyClient;
    private RequestQueue requestQueue;
    private static Context context;

    private VolleyClient(Context c){
        context = c;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleyClient getVolleyClientInstance(Context c){
        if(volleyClient == null){
            volleyClient = new VolleyClient(c);
        }
        return volleyClient;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }
}
