package com.protoype.osindex.currencyexchange.util;

/**
 * Created by proteux on 10/27/17.
 */

public class Utility {
    private static Utility utility = null;
    private static Utility getInstance(){
        if(utility == null){
            utility = new Utility();
        }
        return utility;
    }

    private Utility(){

    }

    public boolean isNetWorkConnected(){

        return false;
    }
}
