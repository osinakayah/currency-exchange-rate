package com.protoype.osindex.currencyexchange.abstracts;

import com.protoype.osindex.currencyexchange.interfaces.CurrencyInterface;

/**
 * Created by osindex on 10/7/17.
 */

public abstract class CurrencyAbstract implements CurrencyInterface {
    protected int iconResource;
    protected String shortName, fullName;
    public CurrencyAbstract(int iconResource, String fullName, String shortName){
        setFullname(fullName);
        setShortName(shortName);
        setResource(iconResource);
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
}
