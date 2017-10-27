package com.protoype.osindex.currencyexchange.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;
import com.protoype.osindex.currencyexchange.R;
import com.protoype.osindex.currencyexchange.abstracts.CurrencyAbstract;
import com.protoype.osindex.currencyexchange.adapters.CurrencyAdapter;
import com.protoype.osindex.currencyexchange.interfaces.CurrencyInterface;
import com.protoype.osindex.currencyexchange.models.RealCurrency;
import com.protoype.osindex.currencyexchange.models.RealWorldCurrency;

import java.util.ArrayList;
import java.util.List;

import static com.protoype.osindex.currencyexchange.R.id.fab;
import static com.protoype.osindex.currencyexchange.R.id.theater;

public class MainActivity extends AppCompatActivity {

    private List<RealWorldCurrency> realWorldCurrencies  = new ArrayList<>();
    private List<RealWorldCurrency> unAddedCurrencies;
    private RecyclerView recyclerViewCurrency;
    private CurrencyAdapter currencyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prepareCurrencyList();
        recyclerViewCurrency  = (RecyclerView)findViewById(R.id.currency_list_recycler_view);
        currencyAdapter = new CurrencyAdapter(realWorldCurrencies);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewCurrency.setLayoutManager(layoutManager);
        recyclerViewCurrency.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCurrency.setAdapter(currencyAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(MainActivity.this)
                            .title("New Currency")
                            .content("Add new currency")
                            .positiveText("Save")
                            .negativeText("Cancel")
                            .items(getUnAddedCurrencies())
                            .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                                @Override
                                public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                    RealWorldCurrency worldCurrency;
                                    for(Integer integer: which){
                                        worldCurrency = unAddedCurrencies.get(integer);
                                        worldCurrency.setAddedToDash(true);
                                        worldCurrency.save();
                                    }
                                    prepareCurrencyList();
                                    return false;
                                }
                            })
                            .show();

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void prepareCurrencyList(){
        List<RealWorldCurrency> currenciesOnDashboard = RealWorldCurrency.listAll(RealWorldCurrency.class);
        if(currenciesOnDashboard.size() < 1){
             new RealWorldCurrency(R.drawable.afn, "Afghan Afghani", "AFN", true);
             new RealWorldCurrency(R.drawable.all, "Albanian Lek", "ALL", true);
             new RealWorldCurrency(R.drawable.eur, "European Euro", "EUR", true);
             new RealWorldCurrency(R.drawable.aoa, "Angolan Kwanza", "AOA",true);
             new RealWorldCurrency(R.drawable.ars, "Argentine Peso", "ARS", true);
             new RealWorldCurrency(R.drawable.aud, "Australian Dollar", "AUD", true);
             new RealWorldCurrency(R.drawable.azn, "Azerbaijani Manat", "AZN", true);
             new RealWorldCurrency(R.drawable.bam, "Bosnia-Herzegovina Convertible Mark", "BAM", true);
             new RealWorldCurrency(R.drawable.bbd, "Barbadian Dollar", "BBD", true);
             new RealWorldCurrency(R.drawable.bdt, "Bangladeshi Taka", "BDT", true);
             new RealWorldCurrency(R.drawable.bgn, "Bulgarian Lev", "BGN", true);
             new RealWorldCurrency(R.drawable.bhd, "Bahraini Dinar", "BHD", true);
             new RealWorldCurrency(R.drawable.bif, "Burundian Franc", "BIF", true);
             new RealWorldCurrency(R.drawable.bnd, "Brunei Dollar", "BND", true);
             new RealWorldCurrency(R.drawable.brl, "Brazilian Real", "BRL", true);
             new RealWorldCurrency(R.drawable.gbp, "British Pound Sterling", "GBP", true);
             new RealWorldCurrency(R.drawable.ghs, "Ghanaian Cedi", "GHS", true);
             new RealWorldCurrency(R.drawable.ngn, "Nigerian Naira", "NGN", true);
             new RealWorldCurrency(R.drawable.usd, "United States Dollar", "USD", true);
             new RealWorldCurrency(R.drawable.zwl, "Zimbabwean Dollar", "ZWL", false);
        }
        currenciesOnDashboard = RealWorldCurrency.find(RealWorldCurrency.class, "is_added_to_dash = ?", "1");
        realWorldCurrencies.clear();
        realWorldCurrencies.addAll(currenciesOnDashboard);
        if(currencyAdapter != null){
            currencyAdapter.notifyDataSetChanged();
        }

    }

    private String[] getUnAddedCurrencies(){
        unAddedCurrencies = RealWorldCurrency.find(RealWorldCurrency.class, "is_added_to_dash = ?", "0");
        String[] stringUnaddedCurrencies = new String[unAddedCurrencies.size()];
        for(int z = 0; z < unAddedCurrencies.size(); z++){
            RealWorldCurrency realWorldCurrency = unAddedCurrencies.get(z);
            stringUnaddedCurrencies[z] = realWorldCurrency.getFullname()+" ("+realWorldCurrency.getShortName()+")";
        }
        return stringUnaddedCurrencies;
    }
}
