package com.protoype.osindex.currencyexchange.util;

/**
 * Created by osindex on 10/28/17.
 */

public class ConversionManager {
    private double fromRate;

    public double convert(double amount){
        return fromRate * amount;
    }

    public void setFromRate(double fromRate, boolean isSwapped) {
        if(isSwapped){
            this.fromRate = (1/fromRate);
        }else{
            this.fromRate = fromRate;
        }
    }

    public ConversionManager(){
        fromRate = 0d;
    }

    public void setFromRate(boolean isSwapped){
        if(isSwapped){
            this.fromRate = (1/fromRate);
        }
    }
}
