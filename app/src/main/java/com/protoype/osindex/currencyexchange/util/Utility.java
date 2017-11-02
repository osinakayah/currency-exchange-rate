package com.protoype.osindex.currencyexchange.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class Utility {
    public static final String SHARED_PREF = "currenciesPREF";
    public static final String DATE_REFRESH_PREF = "dateRefreshed";
    private static SharedPreferences sharedPreferences;
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
        sharedPreferences = (SharedPreferences)context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

    }

    public boolean isNetWorkConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return  networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public void saveToSharedPref(String message){
        if(sharedPreferences == null){
            sharedPreferences = (SharedPreferences)context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DATE_REFRESH_PREF, message);
        editor.apply();
    }
    public String readSharedPref(){
        if(sharedPreferences == null){
            sharedPreferences = (SharedPreferences)context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(DATE_REFRESH_PREF, "Never");
    }
}
