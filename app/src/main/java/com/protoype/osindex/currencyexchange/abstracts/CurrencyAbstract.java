package com.protoype.osindex.currencyexchange.abstracts;

import com.orm.SugarRecord;
import com.protoype.osindex.currencyexchange.interfaces.CurrencyInterface;

import java.util.Currency;


/**
 * Created by osindex on 10/7/17.
 */

public abstract class CurrencyAbstract extends SugarRecord<CurrencyInterface> implements CurrencyInterface {
    protected int iconResource;
    protected String shortName, fullName, symbol;
    private Currency currency;

    public CurrencyAbstract(){

    }
    public CurrencyAbstract(int iconResource, String fullName, String shortName){
        setFullname(fullName);
        setShortName(shortName);
        setResource(iconResource);

        currency = Currency.getInstance(shortName);
        symbol = currency.getSymbol();
    }
    @Override
    public void setResource(int iconResource) {
        this.iconResource = iconResource;
    }
    @Override
    public void setFullname(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public int getResource() {
        return iconResource;
    }

    @Override
    public String getFullname() {
        return fullName;
    }

    @Override
    public String getShortName() {
        return shortName;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }
}
