package com.protoype.osindex.currencyexchange.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by proteux on 10/27/17.
 */

public class Utility {
    private static Utility utility = null;
    private static Context context;
    public static Utility getInstance(Context c){
        if(utility == null){
            utility = new Utility(c.getApplicationContext());
        }
        return utility;
    }

    private Utility(Context contex){
        context = contex;
    }

    public boolean isNetWorkConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return  networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
