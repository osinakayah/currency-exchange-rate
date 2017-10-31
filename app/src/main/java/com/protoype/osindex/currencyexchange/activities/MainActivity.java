package com.protoype.osindex.currencyexchange.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;
import com.protoype.osindex.currencyexchange.R;
import com.protoype.osindex.currencyexchange.adapters.CurrencyAdapter;
import com.protoype.osindex.currencyexchange.events.RefreshCompletedEvent;
import com.protoype.osindex.currencyexchange.interfaces.CurrencyClickListener;
import com.protoype.osindex.currencyexchange.models.RealWorldCurrency;
import com.protoype.osindex.currencyexchange.networks.CurrencyExchangeRate;
import com.protoype.osindex.currencyexchange.util.Utility;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CurrencyClickListener{
    @Override
    public void onItemClick(View view, int pos) {
        RealWorldCurrency realWorldCurrency = realWorldCurrencies.get(pos);
        Intent intent = new Intent(this, ConversionActivity.class);
        intent.putExtra("REAL_CURRENCY_ID", realWorldCurrency.getId());
        startActivity(intent);
    }

    private List<RealWorldCurrency> realWorldCurrencies  = new ArrayList<>();
    private List<RealWorldCurrency> unAddedCurrencies;
    private RecyclerView recyclerViewCurrency;
    private CurrencyAdapter currencyAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prepareCurrencyList();
        recyclerViewCurrency  = (RecyclerView)findViewById(R.id.currency_list_recycler_view);

        currencyAdapter = new CurrencyAdapter(realWorldCurrencies, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewCurrency.setLayoutManager(layoutManager);
        recyclerViewCurrency.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCurrency.setAdapter(currencyAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshExhangeRate();
            }
        });

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

        swipeToDelete().attachToRecyclerView(recyclerViewCurrency);
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
        else if(id == R.id.menu_refresh){
            swipeRefreshLayout.setRefreshing(true);
            refreshExhangeRate();
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
             new RealWorldCurrency(R.drawable.zar, "South African Rand", "ZAR", true);
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

    private void refreshExhangeRate(){
        if(Utility.getInstance(this).isNetWorkConnected()){
            new CurrencyExchangeRate(this).getCurrencyExchangeRate(CurrencyExchangeRate.BITCOIN);
            new CurrencyExchangeRate(this).getCurrencyExchangeRate(CurrencyExchangeRate.ETHEREUM);
        }else{
            Snackbar.make(swipeRefreshLayout.getRootView(), "No Internet Connection", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    @Subscribe
    public void onRefreshCompletedEvent(RefreshCompletedEvent refreshCompletedEvent){
        swipeRefreshLayout.setRefreshing(false);
        prepareCurrencyList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private ItemTouchHelper swipeToDelete(){
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
            {
                Log.i(this.getClass().getName(), "Swipe");
                RealWorldCurrency realWorldCurrency = realWorldCurrencies.get(viewHolder.getAdapterPosition());
                realWorldCurrency.setAddedToDash(false);
                realWorldCurrency.save();
                prepareCurrencyList();
                Snackbar.make(recyclerViewCurrency, realWorldCurrency.getFullname()+" Removed", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return itemTouchHelper;
    }
}
