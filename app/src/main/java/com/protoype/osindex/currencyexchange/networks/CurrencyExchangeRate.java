package com.protoype.osindex.currencyexchange.networks;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.protoype.osindex.currencyexchange.events.RefreshCompletedEvent;
import com.protoype.osindex.currencyexchange.models.RealWorldCurrency;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

    public void getCurrencyExchangeRate(final int cryptoCurrency){
        String url = BITCOIN_EXCHANGE_URL;
        if(cryptoCurrency == ETHEREUM){
            url = ETHERUM_EXCHANGE_URL;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(CurrencyExchangeRate.class.getName(), response);
                try{
                    saveExchangeRate(new JSONObject(response), cryptoCurrency);

                }catch (JSONException error){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                EventBus.getDefault().post(new RefreshCompletedEvent());
                Log.i(CurrencyExchangeRate.class.getName(), error.toString());
            }
        });

        volleyClient.addToRequestQueue(stringRequest);
    }

    public CurrencyExchangeRate(Context context){
        volleyClient = VolleyClient.getVolleyClientInstance(context);
        requestQueue = volleyClient.getRequestQueue();

    }

    private void saveExchangeRate(JSONObject jsonExchangeRate, int cryptoCurrency) throws JSONException{
        Iterator<String> keys = jsonExchangeRate.keys();
        while(keys.hasNext()){
            String key = keys.next();
            String val = null;
            try{
                val = jsonExchangeRate.getString(key);

            }catch(Exception e){
                Log.e(CurrencyExchangeRate.class.getName(), e.getMessage());
            }

            if(val != null){
                List<RealWorldCurrency> realWorldCurrencyList = RealWorldCurrency.find(RealWorldCurrency.class, "short_name = ?", key);
                if(realWorldCurrencyList.size() == 1){
                    RealWorldCurrency realWorldCurrency = realWorldCurrencyList.get(0);


                    if(cryptoCurrency == ETHEREUM){
                        realWorldCurrency.setExchangeRateAgainstEth(1/(Double.parseDouble(val)));
                    }else if(cryptoCurrency == BITCOIN){
                        realWorldCurrency.setExchangeRateAgainstBTC(1/(Double.parseDouble(val)));
                    }

                    realWorldCurrency.save();

                }
            }
        }
        EventBus.getDefault().post(new RefreshCompletedEvent());
    }
}
