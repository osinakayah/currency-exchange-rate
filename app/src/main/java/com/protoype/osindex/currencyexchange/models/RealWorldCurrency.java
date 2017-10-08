package com.protoype.osindex.currencyexchange.models;

import com.protoype.osindex.currencyexchange.abstracts.CurrencyAbstract;

/**
 * Created by osindex on 10/7/17.
 */

public class RealWorldCurrency extends CurrencyAbstract {
    private double exchangeRateAgainstDollar;
    public RealWorldCurrency(int iconResource, String fullName, String shortName) {
        super(iconResource, fullName, shortName);
    }
    private void computeExchangeRateAgainstBitcoin(){

    }
    private void computeExchangeRateAgainstEtherum(){

    }

}
