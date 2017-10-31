package com.protoype.osindex.currencyexchange.apps;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orm.SugarApp;

/**
 * Created by osindex on 10/31/17.
 */

public class MyApplication extends SugarApp {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
