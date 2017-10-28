package com.protoype.osindex.currencyexchange.activities;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.protoype.osindex.currencyexchange.models.RealWorldCurrency;
import com.protoype.osindex.currencyexchange.util.ConversionManager;
import com.transitionseverywhere.*;

import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.protoype.osindex.currencyexchange.R;
import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;


public class ConversionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private TextView txtViewrealCurrency;
    private ConversionManager conversionManager;
    private boolean hasSwaped;
    private RealWorldCurrency realWorldCurrency;
    private EditText editTextFromAmount, editTextToAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextFromAmount  = (EditText)findViewById(R.id.editText_from_amount);
        editTextToAmount    = (EditText)findViewById(R.id.editText_to_amount);

        hasSwaped = false;

        this.realWorldCurrency = RealWorldCurrency.findById(RealWorldCurrency.class, getIntent().getLongExtra("REAL_CURRENCY_ID", -1l));

        this.txtViewrealCurrency = (TextView)findViewById(R.id.textiew_real_currency_conversion_name);
        this.txtViewrealCurrency.setText(this.realWorldCurrency.getFullname());

        conversionManager = new ConversionManager();

        conversionManager.setFromRate(Double.parseDouble(realWorldCurrency.getExchangeRateAgainstBTC()), hasSwaped);

        Spinner crytoCurrenciesSpinner = (Spinner)findViewById(R.id.cryptocurrency_spinner);
        crytoCurrenciesSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> charSequenceArrayAdapter = ArrayAdapter.createFromResource(this, R.array.cryptocurrencies_array,
                android.R.layout.simple_spinner_item);
        charSequenceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        crytoCurrenciesSpinner.setAdapter(charSequenceArrayAdapter);

        Drawable fabSwitchDrawableIcon = MaterialDrawableBuilder.with(getApplicationContext())
                .setIcon(MaterialDrawableBuilder.IconValue.SWAP_HORIZONTAL)
                .setColor(Color.WHITE)
                .setToActionbarSize()
                .build();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.switch_between_crypto_and_real_currencies);
        fab.setImageDrawable(fabSwitchDrawableIcon);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(hasSwaped){
                    switchCurrenciesBack();
                }else{
                    switchCurencies();
                }
                conversionManager.setFromRate(hasSwaped);
            }
        });
    }
    private void switchCurencies(){
        final ViewGroup transitionsContainer = (FrameLayout)findViewById(R.id.frameLayoutCurrencyConversion);
        final TextView realCurrency = (TextView)findViewById(R.id.textiew_real_currency_conversion_name);
        final Spinner crytoCurrenciesSpinner = (Spinner)findViewById(R.id.cryptocurrency_spinner);

        TransitionManager.beginDelayedTransition(transitionsContainer, new ChangeBounds().setPathMotion(new ArcMotion()).setDuration(500));

        FrameLayout.LayoutParams realCurrencyLayoutParams = (FrameLayout.LayoutParams)realCurrency.getLayoutParams();
        FrameLayout.LayoutParams cryptoSpinnerLayoutParams = (FrameLayout.LayoutParams)crytoCurrenciesSpinner.getLayoutParams();

        realCurrencyLayoutParams.gravity = Gravity.RIGHT;
        cryptoSpinnerLayoutParams.gravity = Gravity.LEFT;

        realCurrency.setLayoutParams(realCurrencyLayoutParams);
        crytoCurrenciesSpinner.setLayoutParams(cryptoSpinnerLayoutParams);
        hasSwaped = true;
    }
    private void switchCurrenciesBack(){
        final ViewGroup transitionsContainer = (FrameLayout)findViewById(R.id.frameLayoutCurrencyConversion);
        final TextView realCurrency = (TextView)findViewById(R.id.textiew_real_currency_conversion_name);
        final Spinner crytoCurrenciesSpinner = (Spinner)findViewById(R.id.cryptocurrency_spinner);

        TransitionManager.beginDelayedTransition(transitionsContainer, new ChangeBounds().setPathMotion(new ArcMotion()).setDuration(500));

        FrameLayout.LayoutParams realCurrencyLayoutParams = (FrameLayout.LayoutParams)realCurrency.getLayoutParams();
        FrameLayout.LayoutParams cryptoSpinnerLayoutParams = (FrameLayout.LayoutParams)crytoCurrenciesSpinner.getLayoutParams();

        realCurrencyLayoutParams.gravity = Gravity.LEFT;
        cryptoSpinnerLayoutParams.gravity = Gravity.RIGHT;

        realCurrency.setLayoutParams(realCurrencyLayoutParams);
        crytoCurrenciesSpinner.setLayoutParams(cryptoSpinnerLayoutParams);
        hasSwaped=false;
    }

    public void convertCurrency(View view){
        double amount = Double.parseDouble(editTextFromAmount.getText().toString());
        editTextToAmount.setText(Double.toString(conversionManager.convert(amount)));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0){
            conversionManager.setFromRate(Double.parseDouble(realWorldCurrency.getExchangeRateAgainstBTC()), hasSwaped);
        }
        else if(position == 1){
            conversionManager.setFromRate(Double.parseDouble(realWorldCurrency.getExchangeRateAgainstEth()), hasSwaped);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}