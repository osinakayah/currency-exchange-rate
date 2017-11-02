package com.protoype.osindex.currencyexchange.interfaces;

/**
 * Created by osindex on 10/7/17.
 */

public interface CurrencyInterface {
    void setResource(int resource);
    void setFullname(String currencyName);
    void setShortName(String shortName);

    int getResource();
    String getFullname();
    String getShortName();
    String getSymbol();
}
