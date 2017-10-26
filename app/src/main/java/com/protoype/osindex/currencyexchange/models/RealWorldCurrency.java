package com.protoype.osindex.currencyexchange.models;

import com.protoype.osindex.currencyexchange.abstracts.CurrencyAbstract;

/**
 * Created by osindex on 10/7/17.
 */

public class RealWorldCurrency extends CurrencyAbstract {
    private double exchangeRateAgainstBTC;
    private double exchangeRateAgainstEth;
    private boolean isAddedToDash;

    public boolean isAddedToDash() {
        return isAddedToDash;
    }

    public void setAddedToDash(boolean addedToDash) {
        isAddedToDash = addedToDash;
    }

    public RealWorldCurrency(){

    }

    public RealWorldCurrency(int iconResource, String fullName, String shortName, boolean shouldSave) {
        super(iconResource, fullName, shortName);
        this.isAddedToDash = false;
        if(shouldSave){
            save();
        }
        exchangeRateAgainstBTC      = 0d;
        exchangeRateAgainstEth      = 0d;
    }

}
