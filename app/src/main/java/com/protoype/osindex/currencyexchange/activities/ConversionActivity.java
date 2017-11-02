package com.protoype.osindex.currencyexchange.activities;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.protoype.osindex.currencyexchange.models.RealWorldCurrency;
import com.protoype.osindex.currencyexchange.networks.CurrencyExchangeRate;
import com.protoype.osindex.currencyexchange.util.ConversionManager;
import com.transitionseverywhere.*;

import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.protoype.osindex.currencyexchange.R;
import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.Currency;


public class ConversionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private TextView txtViewrealCurrency, textViewToAmount, toTextViewCurrencySymbol, fromTextViewCurrencySymbol;
    private ConversionManager conversionManager;
    private boolean hasSwaped;
    private RealWorldCurrency realWorldCurrency;
    private EditText editTextFromAmount;
    private ViewGroup transitionsContainer;
    private Spinner crytoCurrenciesSpinner;
    private int selectedCryptoCurency;
    private Currency fromCurrencySymbol;
    private Currency toCurrencySymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        transitionsContainer = (FrameLayout)findViewById(R.id.frameLayoutCurrencyConversion);

        editTextFromAmount  = (EditText)findViewById(R.id.editText_from_amount);
        textViewToAmount    = (TextView) findViewById(R.id.editText_to_amount);


        toTextViewCurrencySymbol = (TextView) findViewById(R.id.to_amount_symbol_textview);
        fromTextViewCurrencySymbol = (TextView)findViewById(R.id.from_amount_symbol_textview);
        this.txtViewrealCurrency = (TextView)findViewById(R.id.textiew_real_currency_conversion_name);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.switch_between_crypto_and_real_currencies);


        crytoCurrenciesSpinner = (Spinner)findViewById(R.id.cryptocurrency_spinner);
        hasSwaped = false;

        this.realWorldCurrency = RealWorldCurrency.findById(RealWorldCurrency.class, getIntent().getLongExtra("REAL_CURRENCY_ID", -1l));

        fromCurrencySymbol = Currency.getInstance(realWorldCurrency.getShortName());

        fromTextViewCurrencySymbol.setText(fromCurrencySymbol.getSymbol());


        this.txtViewrealCurrency.setText(this.realWorldCurrency.getFullname());

        conversionManager = new ConversionManager();
        conversionManager.setFromRate(1/(Double.parseDouble(realWorldCurrency.getExchangeRateAgainstBTC())), hasSwaped);
        selectedCryptoCurency = CurrencyExchangeRate.BITCOIN;



        crytoCurrenciesSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> charSequenceArrayAdapter = ArrayAdapter.createFromResource(this, R.array.cryptocurrencies_array,
                android.R.layout.simple_spinner_item);
        charSequenceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        crytoCurrenciesSpinner.setAdapter(charSequenceArrayAdapter);

        Drawable fabSwitchDrawableIcon = MaterialDrawableBuilder.with(getApplicationContext())
                .setIcon(MaterialDrawableBuilder.IconValue.SWAP_VERTICAL)
                .setColor(Color.WHITE)
                .setToActionbarSize()
                .build();

        fab.setImageDrawable(fabSwitchDrawableIcon);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(hasSwaped){
                    switchCurrenciesBack();
                    hasSwaped = false;
                }else{
                    switchCurencies();
                    hasSwaped = true;
                }
                switch (selectedCryptoCurency){
                    case CurrencyExchangeRate.BITCOIN:
                        conversionManager.setFromRate(1/Double.parseDouble(realWorldCurrency.getExchangeRateAgainstBTC()), hasSwaped);
                        break;
                    case CurrencyExchangeRate.ETHEREUM:
                        conversionManager.setFromRate(1/Double.parseDouble(realWorldCurrency.getExchangeRateAgainstEth()), hasSwaped);
                        break;

                }
            }
        });
    }
    private void switchCurencies(){
        TransitionManager.beginDelayedTransition(transitionsContainer, new ChangeBounds().setPathMotion(new ArcMotion()).setDuration(500));

        FrameLayout.LayoutParams realCurrencyLayoutParams = (FrameLayout.LayoutParams)txtViewrealCurrency.getLayoutParams();
        FrameLayout.LayoutParams cryptoSpinnerLayoutParams = (FrameLayout.LayoutParams)crytoCurrenciesSpinner.getLayoutParams();

        realCurrencyLayoutParams.gravity = Gravity.BOTTOM;
        cryptoSpinnerLayoutParams.gravity = Gravity.TOP;

        txtViewrealCurrency.setLayoutParams(realCurrencyLayoutParams);
        crytoCurrenciesSpinner.setLayoutParams(cryptoSpinnerLayoutParams);
    }
    private void switchCurrenciesBack(){

        TransitionManager.beginDelayedTransition(transitionsContainer, new ChangeBounds().setPathMotion(new ArcMotion()).setDuration(500));

        FrameLayout.LayoutParams realCurrencyLayoutParams = (FrameLayout.LayoutParams)txtViewrealCurrency.getLayoutParams();
        FrameLayout.LayoutParams cryptoSpinnerLayoutParams = (FrameLayout.LayoutParams)crytoCurrenciesSpinner.getLayoutParams();

        realCurrencyLayoutParams.gravity = Gravity.TOP;
        cryptoSpinnerLayoutParams.gravity = Gravity.BOTTOM;

        txtViewrealCurrency.setLayoutParams(realCurrencyLayoutParams);
        crytoCurrenciesSpinner.setLayoutParams(cryptoSpinnerLayoutParams);
    }

    public void convertCurrency(View view){
        String stringAmount = editTextFromAmount.getText().toString();
        if(stringAmount.equalsIgnoreCase("")){
            Snackbar.make(view, "Enter a valid number", Snackbar.LENGTH_LONG).show();
            return;
        }

        double amount = Double.parseDouble(stringAmount);
        amount = (conversionManager.convert(amount));
        textViewToAmount.setText(String.format("%.3f", amount));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0){
            selectedCryptoCurency = CurrencyExchangeRate.BITCOIN;
            conversionManager.setFromRate(1/(Double.parseDouble(realWorldCurrency.getExchangeRateAgainstBTC())), hasSwaped);
        }
        else if(position == 1){
            selectedCryptoCurency = CurrencyExchangeRate.ETHEREUM;
            conversionManager.setFromRate(1/(Double.parseDouble(realWorldCurrency.getExchangeRateAgainstEth())), hasSwaped);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}