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
        if(shortName.equalsIgnoreCase("NGN")){
            this.isAddedToDash = true;
        }
        if(shouldSave){
            save();
        }
        exchangeRateAgainstBTC      = 0d;
        exchangeRateAgainstEth      = 0d;
    }

    public String getExchangeRateAgainstBTC() {
        return Double.toString(exchangeRateAgainstBTC);
    }

    public void setExchangeRateAgainstBTC(double exchangeRateAgainstBTC) {
        this.exchangeRateAgainstBTC = exchangeRateAgainstBTC;
    }

    public String getExchangeRateAgainstEth() {
        return Double.toString(exchangeRateAgainstEth);
    }

    public void setExchangeRateAgainstEth(double exchangeRateAgainstEth) {
        this.exchangeRateAgainstEth = exchangeRateAgainstEth;
    }
}
