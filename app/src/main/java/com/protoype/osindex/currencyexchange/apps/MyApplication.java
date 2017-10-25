package com.protoype.osindex.currencyexchange.apps;

import android.content.res.Configuration;

import com.orm.SugarApp;
import com.protoype.osindex.currencyexchange.abstracts.CurrencyAbstract;
import com.protoype.osindex.currencyexchange.models.RealCurrency;
import com.protoype.osindex.currencyexchange.models.RealWorldCurrency;

import static com.orm.SugarRecord.findById;

/**
 * Created by osindex on 10/25/17.
 */

public class MyApplication extends SugarApp {
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RealWorldCurrency.findById(RealWorldCurrency.class, (long) 1);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
