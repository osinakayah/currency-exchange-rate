package com.protoype.osindex.currencyexchange.networks;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class CurrencyExchangeRate {
    private VolleyClient volleyClient;
    private final String BITCOIN_EXCHANGE_URL   = "https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=USD,EUR,NGN,AFN,ALL,AOA,ARS,AUD,AZN,BAM,BBD,BDT,BGN,BHD,BIF,BND,BRL,GBP,GHS,ZAR";
    private final String ETHERUM_EXCHANGE_URL   = "https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=USD,EUR,NGN,AFN,ALL,AOA,ARS,AUD,AZN,BAM,BBD,BDT,BGN,BHD,BIF,BND,BRL,GBP,GHS,ZAR";
    public static final int BITCOIN             = 1;
    public static final int ETHEREUM            = 2;

    private RequestQueue requestQueue;

    /**
     *
     * @param cryptoCurrency
     */

    public void getCurrencyExchangeRate(int cryptoCurrency){
        String url = BITCOIN_EXCHANGE_URL;
        if(cryptoCurrency == ETHEREUM){
            url = ETHERUM_EXCHANGE_URL;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BITCOIN_EXCHANGE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(CurrencyExchangeRate.class.getName(), response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(CurrencyExchangeRate.class.getName(), error.toString());
            }
        });

        volleyClient.addToRequestQueue(stringRequest);
    }

    public CurrencyExchangeRate(Context context){
        volleyClient = VolleyClient.getVolleyClientInstance(context);
        requestQueue = volleyClient.getRequestQueue();

    }
}
